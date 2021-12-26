package com.alio.ulio.ui.alarm.record

import android.content.ContentUris
import android.content.ContentValues
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.Environment.DIRECTORY_ALARMS
import android.provider.MediaStore
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.alio.ulio.R
import com.alio.ulio.databinding.FragmentRecordAlarmBinding
import com.alio.ulio.ext.getIntValue
import com.alio.ulio.ext.getMimeType
import com.alio.ulio.ext.queryCursor
import com.alio.ulio.ui.alarm.MakeAlarmViewModel
import com.alio.ulio.ui.base.BaseViewBindingFragment
import com.alio.ulio.ui.model.Recording
import com.anggrayudi.storage.media.MediaStoreCompat
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
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
    private var fileName: String = "alioulio_alarm.m4a"
    private val dirPath by lazy {
        requireContext().getExternalFilesDir(Environment.DIRECTORY_ALARMS)?.absolutePath + "/AlioUlio/"
    }

    private var status = RECORDING_STOPPED

    private var absolutePath = ""

    companion object {
        const val RECORDING_RUNNING = 0
        const val RECORDING_STOPPED = 1
        const val RECORDING_PAUSED = 2
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUi()
    }

    private fun setUi() {
        activityViewModel.setTitle("뭐라고 보내면\n좋을까요?")
        activityViewModel.setProgressImg(R.drawable.ic_progress_line02)
        activityViewModel.setBtnNextEnable(true)
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
        } catch (e: IOException) {
            Timber.e(e)
        }

        mediaPlayer?.let {
            viewBinding.visualizerView.link(it)
        }
    }

    private fun stopMediaPlay() {
        mediaPlayer?.pause()
//        mediaPlayer?.release()
//        mediaPlayer = null
    }

    private fun setUiAndEventOfAudioVisualizer() {
//        mediaPlayer = MediaPlayer.create(requireContext(), R.raw.houshang_ebtehaj_arghavan)
//            .apply { isLooping = true }

//        val filepath = MediaStoreCompat.fromRelativePath(requireContext(),dirPath +fileName)
//        Timber.d("path ${File(dirPath).absolutePath}")
//

        activityViewModel.setBtnNextAction {
            togglePlayBack()
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
                }
            }
        }
    }

    private fun togglePlayBack() {

        if (mediaPlayer?.isPlaying == true) {
            stopMediaPlay()
        } else {

            mediaPlayer?.start()
        }
    }

    private fun startRecording() {

        if (status == RECORDING_RUNNING) {
            return
        }

        recorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.CAMCORDER)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setOutputFile(fileName)
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            setAudioSamplingRate(44100)
            setOutputFile(dirPath)

            try {
                prepare()
            } catch (e: IOException) {
                Timber.e("prepare() failed")
            }

            start()
            duration = 0
            status = RECORDING_RUNNING
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


            val recording = Recording(id)
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

    override fun onStop() {
        super.onStop()
        mediaPlayer?.release()
    }

}