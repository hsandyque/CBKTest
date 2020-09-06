package com.test.cbktest.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.test.cbktest.connection.RetrofitClient
import com.test.cbktest.model.House
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import okio.IOException

class HouseListViewModel(application: Application) : AndroidViewModel(application){
    private val viewModelJob = SupervisorJob()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    private lateinit var houseList: MutableLiveData<ArrayList<House>>

    init {
        if(!::houseList.isInitialized) {
            houseList = MutableLiveData()
        }
    }

    fun getHouseList(): MutableLiveData<ArrayList<House>> {
        if(!::houseList.isInitialized) {
            houseList = MutableLiveData()
        }
        return houseList
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun fetchHouseList(){
        uiScope.launch(Dispatchers.IO) {
            try {
                val response = RetrofitClient.csvReqApi.getHouse()
                response.body()?.let{
                    houseList.postValue(it)
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}