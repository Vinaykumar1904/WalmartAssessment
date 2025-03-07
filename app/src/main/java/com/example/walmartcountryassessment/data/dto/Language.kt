package com.example.walmartcountryassessment.data.dto

import com.google.gson.annotations.SerializedName

data class Language(

    @SerializedName("code")
    val code: String,

    @SerializedName("iso639_2")
    val iso639_2: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("nativeName")
    val nativeName: String
)