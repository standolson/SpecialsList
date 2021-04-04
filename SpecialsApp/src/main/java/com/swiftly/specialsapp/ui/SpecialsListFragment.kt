package com.swiftly.specialsapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.swiftly.specialsapp.R
import com.swiftly.specialsapp.viewmodel.SpecialsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SpecialsListFragment : Fragment() {

    lateinit var rootView: View
    val viewModel: SpecialsViewModel by activityViewModels<SpecialsViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = LayoutInflater.from(getActivity()).inflate(R.layout.specials_list_fragment, container, false)
        return rootView
    }

    override fun onResume() {
        super.onResume()
        loadScreen()
    }

    private fun loadScreen() {
        viewModel.loadSpecials()
    }
}