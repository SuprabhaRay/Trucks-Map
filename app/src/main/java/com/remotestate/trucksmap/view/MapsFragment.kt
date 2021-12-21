package com.remotestate.trucksmap.view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Build
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.remotestate.trucksmap.R
import com.remotestate.trucksmap.data.ApiHelper
import com.remotestate.trucksmap.data.RetrofitBuilder
import com.remotestate.trucksmap.model.DataResponse
import com.remotestate.trucksmap.viewModel.TrucksViewModel
import com.remotestate.trucksmap.viewModel.ViewModelFactory
import java.time.Duration
import java.util.*
import kotlin.collections.ArrayList

class MapsFragment : Fragment() {

    private var trucks: ArrayList<DataResponse>? = null
    private lateinit var viewModel: TrucksViewModel
    @RequiresApi(Build.VERSION_CODES.O)
    private val callback = OnMapReadyCallback { googleMap ->

        val trucksLocations: ArrayList<LatLng> = arrayListOf()

        trucks?.forEach { trucksLocations.add(LatLng(it.lastWaypoint.lat, it.lastWaypoint.lng))

            val epochDate= Date(it.createTime)
            val difference: Duration = Duration.between(epochDate.toInstant(),
                Calendar.getInstance().toInstant())

            val hours = difference.toHours()
            val point= LatLng(it.lastWaypoint.lat, it.lastWaypoint.lng)

            if (it.lastRunningState.truckRunningState.toString().contentEquals("0")){
                if (it.lastWaypoint.ignitionOn.not()) {

                    googleMap.addMarker(
                        MarkerOptions().position(point).title(it.truckNumber)
                            .icon(
                                bitmapFromVector(
                                    requireContext(),
                                    R.drawable.ic_blu_baseline_local_shipping_24)))
                }
                else if (it.lastWaypoint.ignitionOn){

                    googleMap.addMarker(
                        MarkerOptions().position(point).title(it.truckNumber)
                            .icon(
                                bitmapFromVector(
                                    requireContext(),
                                    R.drawable.ic_yello_baseline_local_shipping_24)))
                }

            }
            else if (hours>4){

                googleMap.addMarker(
                    MarkerOptions().position(point).title(it.truckNumber)
                        .icon(
                            bitmapFromVector(
                                requireContext(),
                                R.drawable.icc_baseline_local_shipping_244)))
            }
            else {
                googleMap.addMarker(
                    MarkerOptions().position(point).title(it.truckNumber)
                        .icon(
                            bitmapFromVector(
                                requireContext(),
                                R.drawable.ic_baseline_local_shipping_24
                            )
                        )
                )}
        }
        val zoomLevel= 6.0f

        if (!trucksLocations.isNullOrEmpty())

            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(trucksLocations.first(), zoomLevel))
    }
    private fun bitmapFromVector(context: Context, vectorResId: Int): BitmapDescriptor {

        val vectorDrawable= ContextCompat.getDrawable(context, vectorResId)

        vectorDrawable?.setBounds(0, 0, vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight)

        val bitmap= Bitmap.createBitmap(vectorDrawable?.intrinsicWidth!!,
            vectorDrawable.intrinsicHeight, Bitmap.Config.ARGB_8888)

        val canvas= Canvas(bitmap)

        vectorDrawable.draw(canvas)

        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel= ViewModelProvider(requireActivity(),
            ViewModelFactory(ApiHelper(RetrofitBuilder.retrofitApi))
        ).get(TrucksViewModel::class.java)

        trucks = viewModel.getFilteredListData()?.value
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }
}