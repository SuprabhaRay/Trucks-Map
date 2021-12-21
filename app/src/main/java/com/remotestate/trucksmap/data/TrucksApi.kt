package com.remotestate.trucksmap.data

import com.remotestate.trucksmap.model.TrucksData
import retrofit2.http.GET
import retrofit2.http.Query

interface TrucksApi {

    @GET("/tt/mobile/logistics/searchTrucks")
    suspend fun getTrucksList(@Query("auth-company") authCompany: String,
                              @Query("companyId") companyId: String,
                              @Query("deactivated") deactivated: String,
                              @Query("key") key: String,
                              @Query("q-expand") qExpand: String,
                              @Query("q-include") qInclude: String): TrucksData
}