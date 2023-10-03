package com.example.demoapiservice.ui.fragment

import android.content.res.Resources
import android.graphics.Rect
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.example.demoapiservice.R
import com.example.demoapiservice.databinding.FragmentButtonBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ButtonFragment : Fragment() {

    private var binding:FragmentButtonBinding ?= null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentButtonBinding.inflate(inflater,container,false)
        return binding?.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        handleEvents()
    }

    private fun handleEvents(){
        binding?.btnSubmit?.setOnClickListener {
            findNavController().navigate(R.id.registerFragment)
        }
    }

}