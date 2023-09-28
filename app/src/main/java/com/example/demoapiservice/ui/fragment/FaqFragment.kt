package com.example.demoapiservice.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.demoapiservice.R
import com.example.demoapiservice.data.model.FaqItem
import com.example.demoapiservice.databinding.FragmentFaqBinding
import com.example.demoapiservice.ui.adapter.FaqAdapter
import com.example.demoapiservice.ui.viewmodel.FaqViewModel
import com.example.demoapiservice.utils.Constants
import com.example.demoapiservice.utils.Resource
import com.example.demoapiservice.utils.hide
import com.example.demoapiservice.utils.show
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FaqFragment : Fragment() {

    private var binding:FragmentFaqBinding ?= null
    private lateinit var faqAdapter: FaqAdapter
    private var faqsList = ArrayList<FaqItem>()
    private val viewModel by viewModels<FaqViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding = FragmentFaqBinding.inflate(inflater,container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }
    private fun init(){
        binding?.includeToolbar?.tvToolbarTitle?.text = getString(R.string.faq)
        onClickEvents()
        binding?.rvFaq?.apply {
            setHasFixedSize(true)
            //DefaultItemAnimator()
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false
            )
        }
        subscribeToObservers()
    }
    private fun onClickEvents() {
        binding?.includeToolbar?.ivBack?.setOnClickListener {
            findNavController().navigate(R.id.buttonFragment)
        }

        binding?.layoutNetworkError?.btnRetry?.setOnClickListener {
            viewModel.fetchFaqs()
            subscribeToObservers()
        }

        binding?.layoutServerError?.btnRetry?.setOnClickListener {
            viewModel.fetchFaqs()
            subscribeToObservers()
        }
    }

    private fun subscribeToObservers() {

        viewModel.faqsResponse.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { response ->
                when (response) {
                    is Resource.Success -> {
                        hideProgressBar()

                        response.data?.let { res ->
                            if (res.status == "true") {

                                faqsList.addAll(res.data as ArrayList<FaqItem>)
                                faqAdapter = FaqAdapter(
                                    faqsList,
                                )
                                binding?.rvFaq?.adapter = faqAdapter
                            } else {
                                showErrorMsg(response.message ?: "Something went wrong, Please try again!")
                            }
                        }

                    }
                    is Resource.Error -> {
                        showErrorMsg(response.message ?: "Something went wrong, Please try again!")
                    }
                    is Resource.Loading -> {
                        showProgressBar()
                    }
                }
            }
        }
    }
    private fun hideProgressBar() {
        binding?.includeProgress?.root?.hide()
        binding?.layoutNetworkError?.root?.hide()
        binding?.layoutServerError?.root?.hide()
    }

    private fun showProgressBar() {
        binding?.includeProgress?.root?.show()
        binding?.layoutNetworkError?.root?.hide()
        binding?.layoutServerError?.root?.hide()
    }

    private fun showErrorMsg(message: String) {
        binding?.includeProgress?.root?.hide()
        if (message == Constants.NETWORK_FAILURE) {
            binding?.layoutNetworkError?.root?.show()
            binding?.layoutServerError?.root?.hide()
        } else {
            binding?.layoutNetworkError?.root?.hide()
            binding?.layoutServerError?.root?.show()
        }
    }
}