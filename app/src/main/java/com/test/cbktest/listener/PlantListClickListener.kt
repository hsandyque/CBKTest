package com.test.cbktest.listener

import android.widget.ImageView
import com.test.cbktest.model.Plant

interface PlantListClickListener {
    fun onClick(plant: Plant, imageView: ImageView)
}