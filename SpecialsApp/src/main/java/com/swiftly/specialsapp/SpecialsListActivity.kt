package com.swiftly.specialsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class SpecialsListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.specials_list_activity)

        val fragment = SpecialsListFragment()
        val fragmentTag = fragment::class.java.simpleName
        if (savedInstanceState == null)
            supportFragmentManager.beginTransaction().add(R.id.fragmentContainer, fragment, fragmentTag).commit()
    }
}