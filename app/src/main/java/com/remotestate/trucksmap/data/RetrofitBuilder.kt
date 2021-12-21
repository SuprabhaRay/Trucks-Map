package com.remotestate.trucksmap.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {

    val retrofitApi: TrucksApi=
        Retrofit.Builder().baseUrl("https://api.mystral.in")
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(TrucksApi::class.java)
}