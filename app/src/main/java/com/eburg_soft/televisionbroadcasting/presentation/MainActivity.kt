package com.eburg_soft.televisionbroadcasting.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.eburg_soft.televisionbroadcasting.R
import com.eburg_soft.televisionbroadcasting.R.layout
import com.eburg_soft.televisionbroadcasting.presentation.main.TVMenuFragment

class MainActivity : AppCompatActivity(layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (supportFragmentManager.findFragmentByTag(TVMenuFragment::class.java.simpleName) == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.frame_container, TVMenuFragment.getNewInstance())
                .addToBackStack(TVMenuFragment::class.java.simpleName)
                .commit()
        }
    }
}