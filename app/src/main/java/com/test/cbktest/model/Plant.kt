package com.test.cbktest.model

import com.google.gson.annotations.SerializedName

data class Plant(
    @SerializedName("_id")
    val id: Int,
    @SerializedName("F_Code")
    val code: String,
    @SerializedName("F_CID")
    val cid: String,
    @SerializedName("F_Name_Latin")
    val name_latin: String,
    @SerializedName("\uFEFFF_Name_Ch")
    val name_ch: String,
    @SerializedName("F_Name_En")
    val name_en: String,
    @SerializedName("F_pdf01_ALT")
    val pdf01_alt: String,
    @SerializedName("F_pdf01_URL")
    val pdf01_url: String,
    @SerializedName("F_pdf02_ALT")
    val pdf02_alt: String,
    @SerializedName("F_pdf02_URL")
    val pdf02_url: String,
    @SerializedName("F_Pic01_ALT")
    val pic01_alt: String,
    @SerializedName("F_Pic01_URL")
    val pic01_url: String,
    @SerializedName("F_Pic02_ALT")
    val pic02_alt: String,
    @SerializedName("F_Pic02_URL")
    val pic02_url: String,
    @SerializedName("F_Pic03_ALT")
    val pic03_alt: String,
    @SerializedName("F_Pic03_URL")
    val pic03_url: String,
    @SerializedName("F_Pic04_ALT")
    val pic04_alt: String,
    @SerializedName("F_Pic04_URL")
    val pic04_url: String,

    @SerializedName("F_Voice01_ALT")
    val voice01_alt: String,
    @SerializedName("F_Voice01_URL")
    val voice01_url: String,
    @SerializedName("F_Voice02_ALT")
    val voice02_alt: String,
    @SerializedName("F_Voice02_URL")
    val voice02_url: String,
    @SerializedName("F_Voice03_ALT")
    val voice03_alt: String,
    @SerializedName("F_Voice03_URL")
    val voice03_url: String,
    @SerializedName("F_Location")
    val location: String,
    @SerializedName("F_Summary")
    val summary: String,
    @SerializedName("F_Brief")
    val brief: String,
    @SerializedName("F_Keywords")
    val keyword: String,
    @SerializedName("F_Feature")
    val feature: String,
    @SerializedName("F_Family")
    val family: String,
    @SerializedName("F_AlsoKnown")
    val also_known: String,
    @SerializedName("F_Genus")
    val genus: String,
    @SerializedName("F_Function＆Application")
    val function_application: String,
    @SerializedName("F_Vedio_URL")
    val video_url: String,
    @SerializedName("F_Geo")
    val geo: String,
    @SerializedName("F_Update")
    val update: String,
)