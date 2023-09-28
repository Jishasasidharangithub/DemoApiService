package com.example.demoapiservice.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.demoapiservice.data.Repository.Repository
import com.example.demoapiservice.data.model.FaqsResponse
import com.example.demoapiservice.utils.Constants
import com.example.demoapiservice.utils.Event
import com.example.demoapiservice.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class FaqViewModel @Inject
constructor(private val repository: Repository) :
    ViewModel() {

    private val mFaqsResponse: MutableLiveData<Event<Resource<FaqsResponse>>> = MutableLiveData()

    val faqsResponse: LiveData<Event<Resource<FaqsResponse>>>
        get() = mFaqsResponse

    init {
        fetchFaqs()
    }

    fun fetchFaqs() = viewModelScope.launch(Dispatchers.IO) {
        mFaqsResponse.postValue(Event(Resource.Loading()))
        try {
            val response = repository.fetchFaqs()
            if (response.isSuccessful) {
                response.body()?.let { resultResponse ->
                    mFaqsResponse.postValue(Event(Resource.Success(resultResponse)))
                }
            } else {
                mFaqsResponse.postValue(Event(Resource.Error(response.message())))
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> mFaqsResponse.postValue(
                    Event(Resource.Error(Constants.NETWORK_FAILURE))
                )
                else -> mFaqsResponse.postValue(
                    Event(Resource.Error(Constants.CONVERSION_FAILURE))
                )
            }
        }
    }
}