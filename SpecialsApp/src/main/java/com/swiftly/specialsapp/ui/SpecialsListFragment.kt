package com.swiftly.specialsapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.swiftly.specialsapp.R
import com.swiftly.specialsapp.model.ScreenState
import com.swiftly.specialsapp.model.SpecialsList
import com.swiftly.specialsapp.viewmodel.SpecialsViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * The [Fragment] which displays the list of manager's specials.
 */
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
            screenState.observe(viewLifecycleOwner, Observer { setScreenState(it) })
            specialsItems.observe(viewLifecycleOwner, Observer { showItems(it) })
            error.observe(viewLifecycleOwner, Observer { showError(viewModel) })
        }
    }

    override fun onResume() {
        super.onResume()
        loadScreen()
    }

    /**
     * Loads the list of specials from the service while showing an intdeterminate spinner
     */
    private fun loadScreen() {
        showContent(false)
        viewModel.loadSpecials()
    }

    /**
     * When a list of manager's specials is received from the [SpecialsViewModel], display
     * those using a [SpecialsListAdapter] on the view's [RecyclerView]
     */
    private fun showItems(items: SpecialsList) {
        Toast.makeText(context,
            "Received " + items.managerSpecials!!.size + " items, canvasUnit " + items.canvasUnit,
            Toast.LENGTH_LONG).show()

        val recyclerView = rootView.findViewById(R.id.fragment_contents) as RecyclerView
        val adapter = SpecialsListAdapter(items)
        recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        recyclerView.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    /**
     * Sets the proper state of the screen to display either a spinner while loading, the
     * content when received, or no content on error
     */
    private fun setScreenState(loadState: ScreenState) {
        when (loadState) {
            ScreenState.LOADING -> showContent(false)
            ScreenState.READY -> showContent(true)
            else -> hideAllContent()
        }
    }

    // TODO: Show a dialog
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