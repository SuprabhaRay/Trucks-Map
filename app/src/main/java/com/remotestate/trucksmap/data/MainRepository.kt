package com.remotestate.trucksmap.data

class MainRepository(private val apiHelper: ApiHelper) {

    suspend fun getTrucksList()= apiHelper.getTrucksList()
}