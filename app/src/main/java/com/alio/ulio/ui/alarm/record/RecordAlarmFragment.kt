package com.alio.ulio.ui.alarm.record

import android.content.ContentUris
import android.content.ContentValues
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.alio.ulio.R
import com.alio.ulio.databinding.FragmentRecordAlarmBinding
import com.alio.ulio.ext.*
import com.alio.ulio.ui.alarm.MakeAlarmViewModel
import com.alio.ulio.ui.base.BaseViewBindingFragment
import com.alio.ulio.ui.model.Recording
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okio.BufferedSink
import okio.source
import timber.log.Timber
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.util.*

@AndroidEntryPoint
class RecordAlarmFragment :
    BaseViewBindingFragment<FragmentRecordAlarmBinding>(R.layout.fragment_record_alarm) {

    private val viewModel: RecordAlarmViewModel by viewModels()
    private val activityViewModel: MakeAlarmViewModel by activityViewModels()

    private var mediaPlayer: MediaPlayer? = null
    private var recorder: MediaRecorder? = null
    private var durationTimer = Timer()
    private var amplitudeTimer = Timer()

    private var duration = 0
    private var fileName: String = "alioulio_alarm.mp3"
    private val dirPath by lazy {
        requireContext().getExternalFilesDir(Environment.DIRECTORY_ALARMS)?.absolutePath + "/AlioUlio/"
    }

    private var status = RECORDING_STOPPED
    private var playerStatus = PLAYING_STOPPED

    private var absolutePath = ""

    companion object {
        const val RECORDING_RUNNING = 0
        const val RECORDING_STOPPED = 1
        const val RECORDING_PAUSED = 2
        const val PLAYING_RUNNING = 3
        const val PLAYING_STOPPED = 4
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeViewModel()
        setUi()
    }

    private fun setUi() {
        activityViewModel.setTitle("뭐라고 보내면\n좋을까요?")
        activityViewModel.setProgressImg(R.drawable.ic_progress_line02)
        activityViewModel.setBtnNextEnable(false)
        setUiAndEventOfAudioVisualizer()
    }

    private fun initMediaPlay() {
        mediaPlayer = MediaPlayer()
        try {
            mediaPlayer?.setDataSource(
                requireContext(),
                getAudioFileContentUri(getMediaStoreRecordings().last().id.toLong())
            )
        } catch (e: IOException) {
            Timber.e(e)
        }

        try {
            mediaPlayer?.prepareAsync()
            viewBinding.visualizerView.link(mediaPlayer)
        } catch (e: IOException) {
            Timber.e(e)
        }
    }

    private fun stopMediaPlay() {
        mediaPlayer?.pause()
    }

    private fun setUiAndEventOfAudioVisualizer() {
//        mediaPlayer = MediaPlayer.create(requireContext(), R.raw.houshang_ebtehaj_arghavan)
//            .apply { isLooping = true }

//        val filepath = MediaStoreCompat.fromRelativePath(requireContext(),dirPath +fileName)
//        Timber.d("path ${File(dirPath).absolutePath}")
//

        activityViewModel.setBtnNextAction {
            viewModel.findUploadUrl(fileName)
        }

        viewBinding.btnRecord.setOnClickListener {
            when (status) {
                RECORDING_STOPPED -> {
                    it.isSelected = true
                    startRecording()
                }
                else -> {
                    it.isSelected = false
                    stopRecording()

                    viewBinding.ivRecordIndicator.isVisible = false
                    viewBinding.tvDuration.setTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.normal_duration_color
                        )
                    )

                    viewBinding.tvPlay.isEnabled = true
                    viewBinding.tvPlay.setTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.normal_duration_color
                        )
                    )
                    viewBinding.tvPlay.text = "다시 듣기"

                    activityViewModel.setBtnNextEnable(true)
                }
            }
        }


        viewBinding.tvPlay.setOnClickListener {
            togglePlayBack()
        }
    }

    private fun togglePlayBack() {
        if (mediaPlayer?.isPlaying == true) {
            viewBinding.tvPlay.text = "다시 듣기"
            stopMediaPlay()
        } else {
            viewBinding.tvPlay.text = "그만 듣기"
            lifecycleScope.launch {

                viewBinding.visualizerView.setEnable()
                mediaPlayer?.start()

                mediaPlayer?.setOnCompletionListener {
                    viewBinding.tvPlay.text = "다시 듣기"
                }
            }
        }
    }

    private fun startRecording() {

        if (status == RECORDING_RUNNING || mediaPlayer?.isPlaying == true) {
            return
        }

        recorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.CAMCORDER)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setOutputFile(fileName)
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            setAudioSamplingRate(44100)
            setAudioEncodingBitRate(128000)
            setOutputFile(dirPath)

            try {
                prepare()
            } catch (e: IOException) {
                Timber.e("prepare() failed")
            }

            start()
            duration = 0
            status = RECORDING_RUNNING

            durationTimer = Timer()
            lifecycleScope.launch {
                durationTimer.scheduleAtFixedRate(getDurationUpdateTask(), 1000, 1000)
                delay(1000)
                viewBinding.ivRecordIndicator.isVisible = true
            }
        }
    }

    private fun stopRecording() {
        durationTimer.cancel()
        amplitudeTimer.cancel()
        status = RECORDING_STOPPED

        recorder?.apply {
            try {
                stop()
                release()
                viewBinding.visualizerView.release()

                lifecycleScope.launch(Dispatchers.Default) {
                    addFileInNewMediaStore()
                    initMediaPlay()
                }

            } catch (e: Exception) {
                Timber.e("stopRecording() failed")
            }
        }
        recorder = null
    }

    private fun addFileInNewMediaStore() {
        val audioCollection =
            MediaStore.Audio.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
        val storeFilename = dirPath + fileName

        val dirFile = File(dirPath)
        if (!dirFile.exists()) {
            dirFile.mkdirs()
        }

        val newSongDetails = ContentValues().apply {
            put(MediaStore.Audio.Media.DISPLAY_NAME, fileName)
            put(MediaStore.Audio.Media.TITLE, fileName)
            put(MediaStore.Audio.Media.MIME_TYPE, fileName.getMimeType())
            put(MediaStore.Audio.Media.RELATIVE_PATH, "${Environment.DIRECTORY_ALARMS}/AlioUlio")
        }

        val selection = "${MediaStore.Audio.Media.OWNER_PACKAGE_NAME} = ?"
        val selectionArgs = arrayOf(requireContext().packageName)

        requireActivity().contentResolver.delete(
            audioCollection, selection, selectionArgs
        )


        val newUri = requireActivity().contentResolver.insert(audioCollection, newSongDetails)
        if (newUri == null) {
//            toast(R.string.unknown_error_occurred)
            return
        }

        try {
            val outputStream = requireActivity().contentResolver.openOutputStream(newUri)
            val inputStream = FileInputStream(dirFile)
            inputStream.copyTo(outputStream!!, DEFAULT_BUFFER_SIZE)
//            recordingSavedSuccessfully()
        } catch (e: Exception) {
//            showErrorToast(e)
        }
    }


    private fun getMediaStoreRecordings(): ArrayList<Recording> {
        val recordings = ArrayList<Recording>()

        val uri = MediaStore.Audio.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
        val projection = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.DISPLAY_NAME,
            MediaStore.Audio.Media.DATE_ADDED,
            MediaStore.Audio.Media.DURATION,
            MediaStore.Audio.Media.SIZE
        )

        val selection = "${MediaStore.Audio.Media.OWNER_PACKAGE_NAME} = ?"
        val selectionArgs = arrayOf(requireContext().packageName)
        val sortOrder = "${MediaStore.Audio.Media.DATE_ADDED} DESC"

        requireContext().queryCursor(
            uri,
            projection,
            selection,
            selectionArgs,
            sortOrder,
            true
        ) { cursor ->
            val id = cursor.getIntValue(MediaStore.Audio.Media._ID)
            val title = cursor.getStringValue(MediaStore.Audio.Media.DISPLAY_NAME)

            val recording = Recording(id, title)
            recordings.add(recording)
        }

        return recordings
    }

    fun getAudioFileContentUri(id: Long): Uri {
        val baseUri =
//        if (isQPlus()) {
            MediaStore.Audio.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
//        } else {
//            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
//        }

        return ContentUris.withAppendedId(baseUri, id)
    }


    fun makeMultipartFromUri(): RequestBody {

        val uri = getAudioFileContentUri(getMediaStoreRecordings().last().id.toLong())
        val title = getMediaStoreRecordings().last().title

        val requestBody = object : RequestBody() {
            override fun contentType(): MediaType? {
                return requireActivity().contentResolver.getType(uri)?.toMediaTypeOrNull()
            }

            override fun writeTo(sink: BufferedSink) {
                requireActivity().contentResolver.openInputStream(uri)
                    ?.let { sink.writeAll(it.source()) }
            }

        }


        return requestBody
//        return MultipartBody.Part.createFormData(fileName, title, requestBody)
    }

    private fun observeViewModel() = with(viewModel) {
        uploadUrl.observe(viewLifecycleOwner) { url ->
            upload(makeMultipartFromUri())
        }
    }

    private fun getDurationUpdateTask() = object : TimerTask() {
        override fun run() {
            if (status == RECORDING_RUNNING) {
                duration++
                updateDuration()
            }
        }
    }

    private fun updateDuration() {
        viewBinding.tvDuration.setTextColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.record_duration_color
            )
        )
        lifecycleScope.launch(Dispatchers.Main) {
            viewBinding.tvDuration.text = duration.getFormattedDuration(true)
        }
    }

    override fun onStop() {
        super.onStop()
        mediaPlayer?.release()
    }

}