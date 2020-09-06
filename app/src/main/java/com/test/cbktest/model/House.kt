package com.test.cbktest.model

import com.google.gson.annotations.SerializedName

data class House(
    @SerializedName("E_no")
    val no: Int,
    @SerializedName("E_Category")
    val category: String,
    @SerializedName("E_Name")
    val name: String,
    @SerializedName("E_Pic_URL")
    val pic_url: String,
    @SerializedName("E_Memo")
    val memo: String,
    @SerializedName("E_Geo")
    val geo: String,
    @SerializedName("E_URL")
    val url: String,
    @SerializedName("E_Info")
    val info: String
)