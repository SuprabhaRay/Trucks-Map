package com.remotestate.trucksmap.model

data class TrucksData(val responseCode: ResponseCode,
                      val data: ArrayList<DataResponse>?)

data class ResponseCode(val responseCode: Int,
                        val message: String)

data class DataResponse(val id: String,
                        val companyId: String,
                        val truckTypeId: String,
                        val truckSizeId: String,
                        val truckNumber: String,
                        val transporterId: String,
                        val trackerType: String,
                        val imeiNumber: String,
                        val simNumber: String,
                        val name: String,
                        val password: String,
                        val createTime: Long,
                        val deactivated: Boolean,
                        val breakdown: Boolean,
                        val lastWaypoint: LastWaypoint,
                        val lastRunningState: LastRunningState,
                        val durationInsideSite: String,
                        val fuelSensorInstalled: Boolean,
                        val externalTruck: Boolean)

data class LastWaypoint(val id: Int,
                        val lat: Double,
                        val lng: Double,
                        val createTime: Long,
                        val accuracy: Double,
                        val bearing: Double,
                        val truckId: Int,
                        val speed: Double,
                        val updateTime: Long,
                        val ignitionOn: Boolean,
                        val odometerReading: Double,
                        val batteryPower: Boolean,
                        val fuelLevel: Int,
                        val batteryLevel: Int)

data class LastRunningState(val truckId: Int,
                            val stopStartTime: Long,
                            val truckRunningState: Int,
                            val lat: Double,
                            val lng: Double,
                            val stopNotficationSent: Int)
