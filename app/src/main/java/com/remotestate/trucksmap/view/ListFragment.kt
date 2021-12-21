package com.remotestate.trucksmap.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.appbar.MaterialToolbar
import com.remotestate.trucksmap.R
import com.remotestate.trucksmap.data.ApiHelper
import com.remotestate.trucksmap.data.RetrofitBuilder
import com.remotestate.trucksmap.databinding.FragmentListBinding
import com.remotestate.trucksmap.model.DataResponse
import com.remotestate.trucksmap.model.TrucksData
import com.remotestate.trucksmap.tools.Resource
import com.remotestate.trucksmap.tools.Status
import com.remotestate.trucksmap.viewModel.TrucksViewModel
import com.remotestate.trucksmap.viewModel.ViewModelFactory

class ListFragment : Fragment() {

    private lateinit var trucksViewModel: TrucksViewModel
    private lateinit var trucksAdapter: TrucksAdapter
    private lateinit var binding: FragmentListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding= FragmentListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        trucksViewModel= ViewModelProvider(requireActivity(),
            ViewModelFactory(ApiHelper(RetrofitBuilder.retrofitApi)))
            .get(TrucksViewModel::class.java)

        trucksAdapter= TrucksAdapter(arrayListOf())

        val layoutManager =LinearLayoutManager(requireContext())

        binding.recyclerView.layoutManager= layoutManager
        binding.recyclerView.addItemDecoration(DividerItemDecoration(binding.recyclerView.context,
            (layoutManager).orientation))

        binding.recyclerView.adapter= trucksAdapter

        trucksViewModel.getTrucksList().observe(viewLifecycleOwner, trucksObserver)
        trucksViewModel.getFilteredListData()?.observe(viewLifecycleOwner, filterObserver)

        binding.editTextInput.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(numberStartsWith: CharSequence?, start: Int, before: Int,
                                       count: Int) {

                val filteredList= trucksViewModel.getTrucksData()?.filter{
                    it.truckNumber.contains(numberStartsWith!!, true) }

                if (!filteredList.isNullOrEmpty())
                    trucksViewModel.setFilteredListData(filteredList as ArrayList<DataResponse>)
                else
                    trucksViewModel.setFilteredListData(arrayListOf())
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }

    private val trucksObserver: Observer<Resource<TrucksData?>> = Observer<Resource<TrucksData?>> {
        it?.let { resource ->
            when(resource.status){

                Status.SUCCESS-> {binding.loadingProgressBar.visibility= View.GONE

                    resource.data?.data?.let { addTrucks->
                        trucksViewModel.setTrucksData(addTrucks)
                        trucksViewModel.setFilteredListData(addTrucks)
                        trucksAdapter.addData(addTrucks)
                    }
                }

                Status.ERROR-> {binding.loadingProgressBar.visibility= View.GONE

                    Toast.makeText(requireContext(), resource.message, Toast.LENGTH_LONG).show()}

                Status.LOADING-> {binding.loadingProgressBar.visibility= View.VISIBLE }
            } }  }

    private val filterObserver: Observer<ArrayList<DataResponse>> = Observer<ArrayList<DataResponse>>{

        trucksAdapter.addData(it)
    }
}