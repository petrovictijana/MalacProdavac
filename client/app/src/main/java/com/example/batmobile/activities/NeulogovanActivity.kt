package com.example.batmobile.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RelativeLayout
import com.etebarian.meowbottomnavigation.MeowBottomNavigation
import com.example.batmobile.R

class NeulogovanActivity : AppCompatActivity() {

    private lateinit var bottomNavigation: MeowBottomNavigation
    private lateinit var home: RelativeLayout
    private lateinit var explore: RelativeLayout
    private lateinit var inspect: RelativeLayout
    private lateinit var profile: RelativeLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_neulogovan)

//        bottomNavigation = findViewById(R.id.bottomNavigation)
//        home = findViewById(R.id.home)
//        explore = findViewById(R.id.explore)
//        inspect = findViewById(R.id.inspect)
//        profile = findViewById(R.id.profile)

        bottomNavigation.show(2,true)
        bottomNavigation.add(MeowBottomNavigation.Model(1, R.drawable.home))
        bottomNavigation.add(MeowBottomNavigation.Model(2, R.drawable.explore))
        bottomNavigation.add(MeowBottomNavigation.Model(3, R.drawable.inspect))
        bottomNavigation.add(MeowBottomNavigation.Model(4, R.drawable.profile))

        bottomNavigation.setOnClickMenuListener { model ->
            when (model.id) {
                1 -> {
                    home.visibility = View.VISIBLE
                    explore.visibility = View.GONE
                    inspect.visibility = View.GONE
                    profile.visibility = View.GONE
                }
                2 -> {
                    home.visibility = View.GONE
                    explore.visibility = View.VISIBLE
                    inspect.visibility = View.GONE
                    profile.visibility = View.GONE
                }
                3 -> {
                    home.visibility = View.GONE
                    explore.visibility = View.GONE
                    inspect.visibility = View.VISIBLE
                    profile.visibility = View.GONE
                }
                4 -> {
                    home.visibility = View.GONE
                    explore.visibility = View.GONE
                    inspect.visibility = View.GONE
                    profile.visibility = View.VISIBLE
                }
            }
        }

        bottomNavigation.setOnShowListener { model ->
            when (model.id) {
                1 -> {
                    home.visibility = View.VISIBLE
                    explore.visibility = View.GONE
                    inspect.visibility = View.GONE
                    profile.visibility = View.GONE
                }
                2 -> {
                    home.visibility = View.GONE
                    explore.visibility = View.VISIBLE
                    inspect.visibility = View.GONE
                    profile.visibility = View.GONE
                }
                3 -> {
                    home.visibility = View.GONE
                    explore.visibility = View.GONE
                    inspect.visibility = View.VISIBLE
                    profile.visibility = View.GONE
                }
                4 -> {
                    home.visibility = View.GONE
                    explore.visibility = View.GONE
                    inspect.visibility = View.GONE
                    profile.visibility = View.VISIBLE
                }
            }
        }

    }
}

