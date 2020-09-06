package com.test.cbktest.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.test.cbktest.connection.RetrofitClient
import com.test.cbktest.model.NetworkResponse
import com.test.cbktest.model.Plant
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException

class HouseDetailViewModel(application: Application) : AndroidViewModel(application){
    private val viewModelJob = SupervisorJob()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    private lateinit var plantList: MutableLiveData<ArrayList<Plant>>
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    init {
        if(!::plantList.isInitialized) {
            plantList = MutableLiveData()
        }
    }

    fun getPlantList(): MutableLiveData<ArrayList<Plant>> {
        if(!::plantList.isInitialized) {
            plantList = MutableLiveData()
        }
        return plantList
    }


    fun fetchPlantList(location: String){
        uiScope.launch(Dispatchers.IO) {
            try {
                val response = RetrofitClient.jsonReqApi.getPlants()
                val plants = response.result.plants.filter {
                    it.location.contains(location)
                }
                plantList.postValue(ArrayList(plants))
            } catch (throwable: Throwable) {
                when (throwable) {
                    is IOException -> {
                        NetworkResponse.NetworkError
                    }
                    is HttpException -> {
                        val code = throwable.code()
                        val message = throwable.message()
                        NetworkResponse.GenericError(code, message)
                    }
                    else -> {
                        NetworkResponse.GenericError(null, null)
                    }
                }
            }
        }
    }
}