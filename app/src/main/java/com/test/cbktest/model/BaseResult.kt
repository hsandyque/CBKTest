package com.test.cbktest.model

import com.google.gson.annotations.SerializedName

data class BaseResult(
    @SerializedName("limit")
    val limit: Int,
    @SerializedName("offset")
    val offset: Int,
    @SerializedName("count")
    val count: Int,
    @SerializedName("sort")
    val sort: String,
    @SerializedName("results")
    val plants: ArrayList<Plant>
)