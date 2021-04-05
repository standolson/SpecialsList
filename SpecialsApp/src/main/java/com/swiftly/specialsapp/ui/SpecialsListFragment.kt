package com.swiftly.specialsapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.swiftly.specialsapp.R
import com.swiftly.specialsapp.model.ScreenState
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.run {
            screenState.observe(viewLifecycleOwner, Observer { setScreenState(viewModel, it) })
            error.observe(viewLifecycleOwner, Observer { showError(viewModel) })
        }
    }

    override fun onResume() {
        super.onResume()
        loadScreen()
    }

    private fun loadScreen() {
        showContent(false)
        viewModel.loadSpecials()
    }

    private fun setScreenState(viewModel: SpecialsViewModel?, loadState: ScreenState) {
        when (loadState) {
            ScreenState.LOADING -> showContent(false)
            ScreenState.READY -> showContent(true)
            else -> hideAllContent()
        }
    }

    private fun showError(viewModel: SpecialsViewModel) {
        Toast.makeText(context, "Error: " + viewModel.error.value, Toast.LENGTH_LONG).show()
    }

    private fun showContent(show: Boolean) {
        activity?.findViewById<View>(R.id.progress_spinner)?.visibility = if (show) View.GONE else View.VISIBLE
        activity?.findViewById<View>(R.id.fragment_contents)?.visibility = if (show) View.VISIBLE else View.GONE
    }

    private fun hideAllContent() {
        activity?.findViewById<View>(R.id.progress_spinner)?.visibility = View.GONE
        activity?.findViewById<View>(R.id.fragment_contents)?.visibility = View.GONE
    }
}