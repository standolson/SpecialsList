package com.swiftly.specialsapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.swiftly.specialsapp.R
import dagger.hilt.android.AndroidEntryPoint

/**
 * Simple [Activity] class to hold the [Fragment] displaying the manager's specials
 */
@AndroidEntryPoint
class SpecialsListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.specials_list_activity)

        val fragment = SpecialsListFragment()
        val fragmentTag = fragment::class.java.simpleName
        if (savedInstanceState == null)
            supportFragmentManager.beginTransaction().add(R.id.fragment_container, fragment, fragmentTag).commit()
    }
}