package com.remotestate.trucksmap.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.remotestate.trucksmap.data.MainRepository
import com.remotestate.trucksmap.model.DataResponse
import com.remotestate.trucksmap.tools.Resource
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class TrucksViewModel(private val mainRepository: MainRepository): ViewModel() {

    fun getTrucksList()= liveData(Dispatchers.IO) {

        emit(Resource.loading(null))

        try {

            emit(Resource.success(mainRepository.getTrucksList()))
        } catch (e: Exception){emit(Resource.error(null, e.message?: "Error occurred!"))}
    }

    private var trucksData: ArrayList<DataResponse>? = null

    fun getTrucksData(): ArrayList<DataResponse>? {
        return trucksData
    }

    fun setTrucksData(data: ArrayList<DataResponse>?) {
        trucksData = data
    }
    private var filteredListData: MutableLiveData<ArrayList<DataResponse>>?= MutableLiveData()

    fun getFilteredListData(): LiveData<ArrayList<DataResponse>>?{

        return filteredListData
    }
    fun setFilteredListData(data: ArrayList<DataResponse>?){

        filteredListData?.value= data
    }
}