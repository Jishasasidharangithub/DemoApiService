package com.example.demoapiservice.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import com.example.demoapiservice.R
import com.example.demoapiservice.data.model.BranchDataItem
import com.example.demoapiservice.data.requestbody.RegisterBody
import com.example.demoapiservice.databinding.FragmentRegisterBinding
import com.example.demoapiservice.ui.viewmodel.RegisterViewModel
import com.example.demoapiservice.utils.Resource
import com.example.demoapiservice.utils.SessionSaveUser
import com.example.demoapiservice.utils.hide
import com.example.demoapiservice.utils.show
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private var binding:FragmentRegisterBinding ?= null
    private val viewModel by viewModels<RegisterViewModel>()
    private var mName: String? = null
    private var mEmail: String? = null
    private var mMobile: String? = null
    private var mCompany: String? = null
    private var mEmirates: String? = null
    private var mAddress: String? = null
    private var mArea: String? = null
    private var mPassword: String? = null
    private var mConfirmPassword: String? = null
    private var deviceId:String ?= null
    private var deviceName:String ?= null
    private var deviceToken:String = " "
    private var branchId: Int? = null
    var phoneCount: String? = null
    private var termsBool = false

    private var branchesList = ArrayList<BranchDataItem>()
    val branchNames = ArrayList<String>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(inflater,container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        handleEvents()
    }

    private fun init(){
        deviceId=
            SessionSaveUser.getInstance(requireContext())?.getSharedString("deviceId","4b85ed28cfeba54c")
        deviceToken=
            SessionSaveUser.getInstance(requireContext())?.getSharedString("deviceToken","9400debb-f37b-4d82-a2fa-747d4952223b").toString()
        deviceName=
            SessionSaveUser.getInstance(requireContext())?.getSharedString("deviceName","Android SDK built for x86")

        branchListObservers()
    }

    private fun handleEvents(){
        binding?.tvRegister?.setOnClickListener {
            mName = binding!!.etName.text.toString()
            mEmail = binding!!.etEmail.text.toString()
            mMobile = binding!!.etPhone.text.toString()
            mCompany = binding!!.etCompanyName.text.toString()
            if (branchId == 1){
                binding!!.spEmirates.selectedItem?.let {
                    mEmirates = it.toString()
                }
            }
            else{
                mEmirates = ""
            }
            mAddress = binding!!.etAddress.text.toString()
            mArea = binding!!.etArea.text.toString()
            mPassword = binding!!.etPassword.text.toString()
            mConfirmPassword = binding!!.etConfirmPassword.text.toString()
            mEmirates?.let {
                if (validation(
                        mName!!,
                        mEmail!!,
                        mMobile!!,
                        mCompany!!,
                        mAddress!!,
                        mArea!!,
                        mPassword!!,
                        mConfirmPassword!!
                    )
                ) {
                    register(
                        mName!!,
                        mEmail!!,
                        mMobile!!,
                        mEmirates!!,
                        mCompany!!,
                        mAddress!!,
                        mArea!!,
                        mPassword!!,
                        mConfirmPassword!!,
                        branchId!!
                    )
                }
            }
        }
    }

    private fun validation(
        name: String,
        email: String,
        mobile: String,
        company: String,
        address: String,
        area: String,
        password: String,
        confirmPassword: String
    ): Boolean {

        return if (TextUtils.isEmpty(name)) {
            Toast.makeText(context,"Please enter your name.",Toast.LENGTH_SHORT).show()
            false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(context,"Please enter your valid email.",Toast.LENGTH_SHORT).show()
            false
        } else if (mobile.length != phoneCount?.toIntOrNull()) {
            Toast.makeText(context,"Please enter your valid Phone Number.",Toast.LENGTH_SHORT).show()
            false
        } else if (TextUtils.isEmpty(company)) {
            Toast.makeText(context,"Please enter your company.",Toast.LENGTH_SHORT).show()
            false
        } else if (TextUtils.isEmpty(address)) {
            Toast.makeText(context,"Please enter your address",Toast.LENGTH_SHORT).show()
            false
        }else if (TextUtils.isEmpty(area)) {
            Toast.makeText(context,"Please enter your area.",Toast.LENGTH_SHORT).show()
            false
        } else if (password.length < 8) {
            Toast.makeText(context,"Must contain at least 8 or more characters",Toast.LENGTH_SHORT).show()
            false
        } else if (confirmPassword != password) {
            Toast.makeText(context,"The passwords do not match.",Toast.LENGTH_SHORT).show()
            false
        } else {
            true
        }
    }
    private fun subscribeToObservers() {
        viewModel.regResponse.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { response ->
                when (response) {
                    is Resource.Success -> {
                        binding?.includeProgress?.root?.hide()
                        response.data?.let { res ->
                            if (res.status == "true") {
                                println("------------------------>RegisterResponse${res.status}")
                                Toast.makeText(context, "Registration successbranchesList", Toast.LENGTH_SHORT).show()
                                SessionSaveUser.getInstance(requireContext())?.saveUser(res.data)
                                SessionSaveUser.getInstance(requireContext())
                                    ?.putSharedString("login", "true")
                                /*val currency = res.data?.currency
                                SessionSaveUser.getInstance(requireContext())
                                    ?.putSharedString("currency", currency.toString())*/
                            } else {
                                Toast.makeText(context,"Registration failed",Toast.LENGTH_SHORT).show()
                            }
                        }

                    }
                    is Resource.Error -> {
                        binding?.includeProgress?.root?.hide()
                        Toast.makeText(context,"Something went wrong, Please try again!",Toast.LENGTH_SHORT).show()
                    }
                    is Resource.Loading -> {
                        binding?.includeProgress?.root?.show()
                    }
                }
            }
        }
    }


    private fun register(
        name: String,
        email: String,
        phone: String,
        emirates: String,
        company: String,
        address: String,
        area: String,
        password: String,
        passwordConfirmation: String,
        branchId: Int
    ) {
        val body = RegisterBody(
            name,
            email,
            phone,
            emirates,
            company,
            address,
            area,
            password,
            passwordConfirmation,
            branchId,
            "Android",
            deviceName.toString(),
            deviceId.toString(),
            deviceToken.toString(),
        )
        viewModel.regFields(body)
        subscribeToObservers()
    }
    private fun branchListObservers() {
        viewModel.branchListResponse.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { response ->
                when (response) {
                    is Resource.Success -> {
                        binding?.includeProgress?.root?.hide()
                        response.data?.let { res ->
                            if (res.status == "true") {
                                res.data?.let { branchDataList ->
                                    val branchNames = branchDataList.mapNotNull { it?.branchName }
                                    SessionSaveUser.getInstance(requireContext())
                                        ?.putSharedString("branchName", branchNames.toString())
                                    if (branchNames.isNotEmpty()) {
                                        val adapter = ArrayAdapter<String>(
                                            requireContext(),
                                            R.layout.spinner_tv, // Replace with your spinner item layout
                                            branchNames
                                        )
                                        adapter.setDropDownViewResource(R.layout.spinner_item)
                                        binding?.spBranch?.adapter = adapter
                                        binding?.spBranch?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                                            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                                                // Get the selected branch name from the spinner
                                                val selectedBranchName = branchNames[position]
                                                // Find the corresponding BranchDataItem for the selected branch name
                                                val selectedBranchDataItem = branchDataList.find { it?.branchName == selectedBranchName }
                                                // Get the branch ID from the selected branch data
                                                branchId = selectedBranchDataItem?.branchId
                                                SessionSaveUser.getInstance(requireContext())
                                                    ?.putSharedString("branchId", branchId.toString())
                                                val branch =   SessionSaveUser.getInstance(requireContext())?.getSharedString("branchId","")
                                                // Check if a matching branch data item was found
                                                if (selectedBranchDataItem != null) {
                                                    // Retrieve the phone code and phone digit count from the selected branch data
                                                    val phoneCode = selectedBranchDataItem.phoneCode ?: ""
                                                    phoneCount= selectedBranchDataItem.phoneDigitCount.toString() ?: ""


                                                    // Now you can use phoneCode and phoneDigitCount as needed
                                                    val formattedPhoneCode = "+$phoneCode"
                                                    val totalPhoneCount = phoneCount + formattedPhoneCode.length

                                                    // Update the UI elements with the phone code and digit count
                                                    binding?.tvCountryCode?.text = formattedPhoneCode
                                                    // You can perform phone digit count validation here

                                                    val citiesForSelectedBranch = selectedBranchDataItem.cities
                                                    if (citiesForSelectedBranch!!.isEmpty()) {
                                                        binding?.cslSpEmirates?.hide()
                                                    }else{
                                                        binding?.cslSpEmirates?.show()
                                                        val cityAdapter = ArrayAdapter<String>(
                                                            requireContext(), // Use your activity context
                                                            R.layout.spinner_tv, // Replace with your spinner item layout
                                                            citiesForSelectedBranch
                                                        )
                                                        cityAdapter.setDropDownViewResource(R.layout.spinner_item)
                                                        binding?.spEmirates?.adapter = cityAdapter
                                                    }
                                                }

                                            }
                                            override fun onNothingSelected(parent: AdapterView<*>?) {
                                                // Do nothing if nothing is selected
                                            }
                                        }
                                    }


                                }

                            } else {
                                Toast.makeText(context, "Something went wrong, Please try again!", Toast.LENGTH_SHORT).show()
                            }
                        }

                    }
                    is Resource.Error -> {
                        binding?.includeProgress?.root?.hide()
                        Toast.makeText(
                            context,
                            "Something went wrong, Please try again!",
                            Toast.LENGTH_SHORT
                        ).show()
                        subscribeToObservers()
                    }
                    is Resource.Loading -> {
                        binding?.includeProgress?.root?.show()
                    }
                }
            }
        }
    }
}