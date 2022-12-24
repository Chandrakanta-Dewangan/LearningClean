package com.learning.assignment.ui.main.listing

import android.content.res.Configuration
import android.os.Bundle
import android.view.*
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.learning.assignment.R
import com.learning.assignment.databinding.ListingFragmentBinding
import com.learning.assignment.ui.main.base.BaseFragment
import com.learning.data.core.extension.gone
import com.learning.data.core.extension.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CakeListingFragment : BaseFragment<ListingFragmentBinding, CakeListingViewModel>() {
    private var isError = true
    override val layoutId: Int
        get() = R.layout.listing_fragment

    override fun setupViewBinding(view: View): ListingFragmentBinding =
        ListingFragmentBinding.bind(view)

    override fun setupViewModel(): CakeListingViewModel {
        val viewModel: CakeListingViewModel by viewModels()
        return viewModel
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_sort -> {
                viewModel.sort()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onViewCreated(inflater: LayoutInflater, view: View, savedInstanceState: Bundle?) {
        val adapter = CakesAdapter(requireContext(), OnClickListener { cakes, _ ->
            findNavController().navigate(
                directions = CakeListingFragmentDirections.actionCakeListingFragmentToCakeDetailsJavaFragment(
                    cakes
                )
            )
        })
        viewBinding.list.addItemDecoration(
            MiddleDividerItemDecoration(
                requireContext(),
                MiddleDividerItemDecoration.ALL
            )
        )
        setSpanCount()
        viewBinding.list.adapter = adapter

        viewBinding.swipeContainer.setOnRefreshListener {
            viewModel.loadData()
        }
        viewBinding.retry.main.setOnClickListener {
            viewModel.loadData()
        }

        // Configure the refreshing colors
        viewBinding.swipeContainer.setColorSchemeResources(
            android.R.color.holo_blue_bright,
            android.R.color.holo_green_light,
            android.R.color.holo_orange_light,
            android.R.color.holo_red_light
        )

        var isDataLoaded = false

        viewModel.cakes.observe(viewLifecycleOwner) {
            isDataLoaded = true
            isError = false
            enableListView()
            adapter.setCakes(it)
            viewBinding.swipeContainer.setRefreshing(false)
        }

        viewModel.error.observe(viewLifecycleOwner) {
            isDataLoaded = true
            isError = true
            enableRetryView()
        }

        viewModel.connectivityLiveData.observe(viewLifecycleOwner) {
            if (!it && !isDataLoaded) {
                enableInternetView()
            } else if (it && !isDataLoaded) {
                viewModel.loadData()
                enableListView()
            } else if (!it && isDataLoaded) {
                enableInternetView()
            } else {
                enableListView()
            }

        }

    }

    private fun enableInternetView() {
        setHasOptionsMenu(false)
        viewModel.hideLoader()
        viewBinding.list.gone()
        viewBinding.swipeContainer.gone()
        viewBinding.retry.main.gone()
        viewBinding.internet.main.visible()
    }

    private fun enableListView() {
        setHasOptionsMenu(true)
        viewBinding.internet.main.gone()
        viewBinding.retry.main.gone()
        viewBinding.swipeContainer.visible()
        viewBinding.list.visible()
    }

    private fun enableRetryView() {
        setHasOptionsMenu(false)
        viewModel.hideLoader()
        viewBinding.internet.main.gone()
        viewBinding.swipeContainer.gone()
        viewBinding.list.gone()
        viewBinding.swipeContainer.setRefreshing(false)
        viewBinding.retry.main.visible()
    }

    private var orientationLand: Boolean = false
    private fun setSpanCount() {
        if (orientationLand) {
            viewBinding.list.layoutManager = GridLayoutManager(requireContext(), 3)
        } else {
            viewBinding.list.layoutManager = GridLayoutManager(requireContext(), 2)
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        orientationLand = newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE
        setSpanCount()
    }


}