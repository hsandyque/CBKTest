package com.test.cbktest.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.test.cbktest.R
import com.test.cbktest.adapter.HouseListAdapter
import com.test.cbktest.listener.HouseListClickListener
import com.test.cbktest.model.House
import com.test.cbktest.viewmodel.HouseListViewModel
import com.test.cbktest.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.house_list_fragment.*

class HouseListFragment : Fragment(), HouseListClickListener {
    private val viewModel by lazy {
        ViewModelProvider(this).get(HouseListViewModel::class.java)
    }

    private val mainViewModel by lazy {
        ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
    }

    private val adapter by lazy {
        HouseListAdapter(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.house_list_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupView()
        setupViewModel()
        fetchData()
    }

    override fun onClick(house: House) {
        mainViewModel.setSelectHouse(house)
        findNavController().navigate(R.id.action_houseListFragment_to_houseDetailFragment)
    }

    private fun setupView() {
        rv_house_list.layoutManager =
            LinearLayoutManager(requireContext(),
                LinearLayoutManager.VERTICAL,
                false)
        rv_house_list.adapter = adapter
    }

    private fun setupViewModel() {
        viewModel.getHouseList().observe(
            viewLifecycleOwner, Observer {
                adapter.setData(it)
            })
    }

    private fun fetchData() {
        viewModel.fetchHouseList()
    }
}