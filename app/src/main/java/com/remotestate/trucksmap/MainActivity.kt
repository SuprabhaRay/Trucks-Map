package com.remotestate.trucksmap

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils.replace
import android.util.Log
import android.widget.SearchView
import androidx.fragment.app.ListFragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.remotestate.trucksmap.data.ApiHelper
import com.remotestate.trucksmap.data.RetrofitBuilder
import com.remotestate.trucksmap.databinding.ActivityMainBinding
import com.remotestate.trucksmap.model.DataResponse
import com.remotestate.trucksmap.view.MapsFragment
import com.remotestate.trucksmap.viewModel.TrucksViewModel
import com.remotestate.trucksmap.viewModel.ViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var trucksViewModel: TrucksViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        trucksViewModel= ViewModelProvider(this,
            ViewModelFactory(ApiHelper(RetrofitBuilder.retrofitApi)))
            .get(TrucksViewModel::class.java)

        binding.topAppBar.setNavigationOnClickListener {
            // Handle navigation icon press
        }
        binding.topAppBar.setOnMenuItemClickListener { menuItem-> when(menuItem.itemId){
            R.id.refresh-> {
                // Handle refresh icon press
                true
            }
            R.id.mapView-> {

                supportFragmentManager.commit {
                    replace<MapsFragment>(R.id.fragmentMain)
                    setReorderingAllowed(true)
                }

                menuItem.isVisible = false

                binding.topAppBar.menu.findItem(R.id.listView).isVisible = true

                true
            }
            R.id.listView-> {

                supportFragmentManager.commit {
                    replace<ListFragment>(R.id.fragmentMain)
                    setReorderingAllowed(true)
                }

                menuItem.isVisible= false

                binding.topAppBar.menu.findItem(R.id.mapView).isVisible= true

                true
            }
            else-> false} }

    }
}