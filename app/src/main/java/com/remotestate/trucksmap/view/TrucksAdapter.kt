package com.remotestate.trucksmap.view

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.remotestate.trucksmap.R
import com.remotestate.trucksmap.databinding.ItemTruckBinding
import com.remotestate.trucksmap.model.DataResponse
import java.lang.Long.parseLong
import java.text.SimpleDateFormat
import java.time.Duration
import java.time.LocalDateTime
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList
import kotlin.time.toDuration

class TrucksAdapter(private val trucksList: ArrayList<DataResponse>):
    RecyclerView.Adapter<TrucksAdapter.TrucksViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TrucksViewHolder=
        TrucksViewHolder(ItemTruckBinding.inflate(LayoutInflater.from(parent.context)))

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: TrucksViewHolder, position: Int) {

        holder.bind(trucksList[position])
    }
    override fun getItemCount()=trucksList.size

    @SuppressLint("NotifyDataSetChanged")
    fun addData(trucks: ArrayList<DataResponse>){
        trucksList.clear()
        trucksList.addAll(trucks)

        notifyDataSetChanged()
    }

    class TrucksViewHolder(private val binding: ItemTruckBinding): RecyclerView.ViewHolder(binding.root) {

        @RequiresApi(Build.VERSION_CODES.O)
        @SuppressLint("ResourceAsColor", "SetTextI18n")
        fun bind(truck: DataResponse){

            if (truck.breakdown)

                binding.cardItemTruck.setCardBackgroundColor(R.color.red)
            binding.textViewTruckNumber.text= truck.truckNumber
            binding.textViewLastUpdated.text= "${getTime(truck.lastWaypoint.createTime)} ago"
            binding.textViewRunningState.text=

                if (truck.lastRunningState.truckRunningState.toString().equals(0))

                    "Stopped since last ${getTime(truck.lastRunningState.stopStartTime)}"

                else

                    "Running since last ${getTime(truck.lastRunningState.stopStartTime)}"

            binding.textViewRunningSpeed.text= "${truck.lastWaypoint.speed} "
        }
        @RequiresApi(Build.VERSION_CODES.O)
        private fun getTime(epoch: Long):CharSequence{

            val epochDate= Date(epoch)
            var difference: Duration= Duration.between(epochDate.toInstant(),
                Calendar.getInstance().toInstant())

            val days= difference.toDays()
            val daysFound= itemView.resources.getQuantityString(R.plurals.numberOfDays,
                days.toInt(), days.toInt())

            difference = difference.minusDays(days)

            val hours = difference.toHours()
            val hoursFound= itemView.resources.getQuantityString(R.plurals.numberOfHours,
                hours.toInt(), hours.toInt()
            )

            difference = difference.minusHours(hours)

            val minutes = difference.toMinutes()
            val minutesFound= itemView.resources.getQuantityString(R.plurals.numberOfMinutes,
                minutes.toInt(), minutes.toInt())

            return if (days == 0L){ if (hours==0L){

                minutesFound
            }

            else{

                "$hoursFound $minutesFound"}
            }
            else

                daysFound
        }
    }
}