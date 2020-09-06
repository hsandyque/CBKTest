package com.test.cbktest.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import com.bumptech.glide.Glide
import com.test.cbktest.R
import com.test.cbktest.model.Plant
import com.test.cbktest.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_plant_detail.*

class PlantDetailFragment : Fragment() {

    private val mainViewModel by lazy {
        ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
    }

    private val args: PlantDetailFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(R.transition.shared_image)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_plant_detail, container, false)
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
        toolbar_plant_detail.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        callback.isEnabled = true
    }
    private fun setupViewModel() {
        mainViewModel.getSelectPlant().observe(viewLifecycleOwner, Observer {
            updateView(it)
        })
    }
    private fun updateView(plant: Plant) {
        iv_plant_cover.transitionName = args.uri
        Glide.with(iv_plant_cover).load(plant.pic01_url).into(iv_plant_cover)
        toolbar_plant_detail.title = plant.name_ch
        tv_plant_name_cht.text = plant.name_ch
        tv_plant_name_lating.text = plant.name_latin
        tv_plant_also_known.text = plant.also_known
        tv_plant_brief.text = plant.brief
        tv_plant_feature.text = plant.feature
        tv_plant_function.text = plant.function_application
        tv_plant_update.text = String.format(getString(R.string.label_update), plant.update)
    }
}