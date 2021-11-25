package com.alio.ulio.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.alio.ulio.databinding.BaseFragmentToolbarBinding

abstract class BaseViewBindingFragment<V : ViewDataBinding>(
    private val layoutRes: Int
) : Fragment() {

    private lateinit var rootViewBinding: BaseFragmentToolbarBinding

    private var _viewBinding: V? = null
    protected val viewBinding: V
        get() = _viewBinding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        rootViewBinding = BaseFragmentToolbarBinding.inflate(layoutInflater)


        _viewBinding =
            DataBindingUtil.inflate(
                layoutInflater, layoutRes, rootViewBinding.mainContent, true
            )
        return rootViewBinding.rootView
    }

    override fun onDestroyView() {
        _viewBinding = null
        super.onDestroyView()
    }

    fun setupBackKeyEvent() {
        activity?.onBackPressedDispatcher?.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    requireActivity().finish()
                }
            })
    }
}