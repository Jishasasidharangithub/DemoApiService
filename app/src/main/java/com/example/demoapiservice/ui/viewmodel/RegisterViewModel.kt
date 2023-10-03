package com.example.demoapiservice.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.demoapiservice.data.Repository.Repository
import com.example.demoapiservice.data.model.BranchResponse
import com.example.demoapiservice.data.model.RegisterResponse
import com.example.demoapiservice.data.requestbody.RegisterBody
import com.example.demoapiservice.utils.Constants
import com.example.demoapiservice.utils.Event
import com.example.demoapiservice.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject
constructor(private val repository: Repository) :
    ViewModel() {

    private val mRegResponse: MutableLiveData<Event<Resource<RegisterResponse>>> = MutableLiveData()
    val regResponse: LiveData<Event<Resource<RegisterResponse>>>
        get() = mRegResponse

    private val mBranchListResponse: MutableLiveData<Event<Resource<BranchResponse>>> = MutableLiveData()
    val branchListResponse: LiveData<Event<Resource<BranchResponse>>>
        get() = mBranchListResponse
    init {
        fetchBranchList()
    }
    fun regFields(body : RegisterBody) = viewModelScope.launch(Dispatchers.IO){
        mRegResponse.postValue(Event(Resource.Loading()))
        println("----------------->RegisterBody$body")
        try {
            val response = repository.register(body)
            if (response.isSuccessful) {
                response.body()?.let { resultResponse ->
                    mRegResponse.postValue(Event(Resource.Success(resultResponse)))
                }
            } else {
                mRegResponse.postValue(Event(Resource.Error(response.message())))
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> mRegResponse.postValue(
                    Event(Resource.Error(Constants.NETWORK_FAILURE))
                )
                else -> mRegResponse.postValue(
                    Event(Resource.Error(Constants.CONVERSION_FAILURE))
                )
            }
        }
    }

    fun fetchBranchList() = viewModelScope.launch(Dispatchers.IO) {
        mBranchListResponse.postValue(Event(Resource.Loading()))
        try {
            val response = repository.fetchBranchList()
            if (response.isSuccessful) {
                response.body()?.let { resultResponse ->
                    mBranchListResponse.postValue(Event(Resource.Success(resultResponse)))
                }
            } else {
                mBranchListResponse.postValue(Event(Resource.Error(response.message())))
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> mBranchListResponse.postValue(
                    Event(Resource.Error(Constants.NETWORK_FAILURE))
                )
                else -> mBranchListResponse.postValue(
                    Event(Resource.Error(Constants.CONVERSION_FAILURE))
                )
            }
        }
    }
}