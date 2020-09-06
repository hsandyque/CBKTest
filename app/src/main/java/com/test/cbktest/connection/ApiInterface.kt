package com.test.cbktest.connection

import com.test.cbktest.model.BaseResponse
import com.test.cbktest.model.BaseResult
import com.test.cbktest.model.House
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Streaming

interface ApiInterface {
    @GET("api/v1/dataset/f18de02f-b6c9-47c0-8cda-50efad621c14?scope=resourceAquire")
    suspend fun getPlants(): BaseResponse<BaseResult>

    @Headers("Content-Type: application/json;charset=utf-8", "Accept: application/json")
    @GET("api/getDatasetInfo/downloadResource?id=1ed45a8a-d26a-4a5f-b544-788a4071eea2&rid=5a0e5fbb-72f8-41c6-908e-2fb25eff9b8a")
    @Streaming
    suspend fun getHouse(): Response<ArrayList<House>>
}