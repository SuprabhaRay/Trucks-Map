package com.remotestate.trucksmap.data

class ApiHelper(private val apiService: TrucksApi) {

    private val authCompany= "PCH"
    private val companyId= "33"
    private val deactivated= "false"
    private val key= "g2qb5jvucg7j8skpu5q7ria0mu"
    private val qExpand= "true"
    private val qInclude= "lastRunningState,lastWaypoint"

    suspend fun getTrucksList()= apiService.getTrucksList(authCompany, companyId, deactivated, key,
        qExpand, qInclude)
}