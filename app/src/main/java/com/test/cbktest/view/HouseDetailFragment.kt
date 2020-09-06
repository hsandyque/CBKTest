package com.test.cbktest.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.TransitionInflater
import com.test.cbktest.R
import com.test.cbktest.adapter.HouseHeaderAdapter
import com.test.cbktest.adapter.PlantListAdapter
import com.test.cbktest.listener.HouseHeaderClickListener
import com.test.cbktest.listener.PlantListClickListener
import com.test.cbktest.model.Plant
import com.test.cbktest.viewmodel.HouseDetailViewModel
import com.test.cbktest.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.house_detail_fragment.*


class HouseDetailFragment : Fragment(),
    PlantListClickListener, HouseHeaderClickListener {
    private val viewModel by lazy {
        ViewModelProvider(this).get(HouseDetailViewModel::class.java)
    }

    private val mainViewModel by lazy {
        ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
    }

    private val adapter by lazy {
        PlantListAdapter(this)
    }

    private val headerAdapter by lazy {
        HouseHeaderAdapter(requireContext(), this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.house_detail_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupView()
        setupViewModel()
    }

    private fun setupView() {
        val callback = requireActivity().onBackPressedDispatcher.addCallback {
            findNavController().popBackStack()
        }
        toolbar_house_detail.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        callback.isEnabled = true
        rv_plant_list.layoutManager =
            LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false
            )
        rv_plant_list.adapter = ConcatAdapter(headerAdapter, adapter)
    }
    private fun setupViewModel() {
        viewModel.getPlantList().observe(viewLifecycleOwner, Observer {
            adapter.setData(it)
        })
        mainViewModel.getSelectHouse().observe(viewLifecycleOwner, Observer {
            toolbar_house_detail.title = it.name
            headerAdapter.setData(it)
            fetchData(it.name)
        })
    }
    private fun fetchData(location: String) {
        viewModel.fetchPlantList(location)
    }

    override fun onClick(plant: Plant, imageView: ImageView) {
        mainViewModel.setSelectPlant(plant)

        val extras = FragmentNavigatorExtras(
            imageView to plant.pic01_url
        )

        val action = HouseDetailFragmentDirections.actionHouseDetailFragmentToPlantDetailFragment(
            uri = plant.pic01_url)
        findNavController().navigate(action, extras)
    }

    override fun onClick(url: String) {
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        startActivity(i)
    }
}