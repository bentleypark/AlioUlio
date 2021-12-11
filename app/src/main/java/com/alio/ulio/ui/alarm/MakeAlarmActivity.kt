package com.alio.ulio.ui.alarm

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.alio.ulio.databinding.ActivityMakeAlarmBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MakeAlarmActivity : AppCompatActivity() {

    private val viewBinding: ActivityMakeAlarmBinding by lazy {
        ActivityMakeAlarmBinding.inflate(LayoutInflater.from(this))
    }

    private val viewModel: MakeAlarmViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)

        observeViewModel()
//        viewBinding.progressAnim.setAnimation("progressbar.json")
    }

    private fun setTitle(title: String) {
        if (title.isNotEmpty()) {
            viewBinding.tvTitle.text = title
        }

    }

    private fun observeViewModel() = with(viewModel) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                title.collect {
                    if (it.isNotEmpty()) {
                        this@MakeAlarmActivity.setTitle(it)
                    }
                }

                progressAnim.collect {
//                    if (it.isNotEmpty()) {
//                        viewBinding.progressAnim.setAnimation(it)
//                        viewBinding.progressAnim.playAnimation()
//                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()

        viewBinding.progressAnim.playAnimation()

    }

    companion object {
        fun newIntent(
            context: Context,
            alarmType: String
        ): Intent {
            return Intent(context, MakeAlarmActivity::class.java).apply {

            }
        }
    }
}