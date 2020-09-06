package com.test.cbktest.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.test.cbktest.model.House
import com.test.cbktest.model.Plant
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

class MainViewModel(application: Application) : AndroidViewModel(application){
    private val viewModelJob = SupervisorJob()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    private lateinit var selectHouse: MutableLiveData<House>
    private lateinit var selectPlant: MutableLiveData<Plant>

    init {
        if(!::selectHouse.isInitialized){
            selectHouse = MutableLiveData()
        }
        if(!::selectPlant.isInitialized){
            selectPlant = MutableLiveData()
        }
    }

    fun getSelectHouse(): MutableLiveData<House> {
        if(!::selectHouse.isInitialized){
            selectHouse = MutableLiveData()
        }
        return selectHouse
    }

    fun setSelectHouse(house: House){
        selectHouse.postValue(house)
    }

    fun getSelectPlant(): MutableLiveData<Plant> {
        if(!::selectPlant.isInitialized){
            selectPlant = MutableLiveData()
        }
        return selectPlant
    }

    fun setSelectPlant(plant: Plant){
        selectPlant.postValue(plant)
    }
}