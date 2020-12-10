package com.udacity.asteroidradar.main

import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.FragmentMainBinding
import com.udacity.database.AsteroidDatabase
import com.udacity.network.NasaAPIStatus

class MainFragment : Fragment() {
    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this

        val application = requireNotNull(this.activity).application

        val dataSource = AsteroidDatabase.getInstance(application).asteroidDao

        val viewModelFactory = AsteroidViewModelFactory(AsteroidApplication().repository)

        val asteroidViewModel =
            ViewModelProvider(
                this, viewModelFactory).get(MainViewModel::class.java)

        binding.mainViewModel = asteroidViewModel

        val adapter = AsteroidAdapter(AsteroidAdapter.AsteroidListener {
//            asteroidId ->  Toast.makeText(context, "$asteroidId", Toast.LENGTH_SHORT).show()
            asteroid ->  asteroidViewModel.onAsteroidClicked(asteroid)
        })

        binding.asteroidRecycler.adapter = adapter

        asteroidViewModel.asteroids.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        // Observe navigation
        asteroidViewModel.navigateToAsteroidDetails.observe(viewLifecycleOwner, Observer { asteroid ->
            asteroid?.let {
                this.findNavController().navigate(MainFragmentDirections.actionShowDetail(asteroid))
                asteroidViewModel.onAsteroidClickedNavigated()
            }
        })

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    // The goal here is to issue dynamic database queries based on this.
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            // Show all asteroids in past 7 days.
            R.id.show_all_menu -> MainViewModel.NasaApiFilter.FILTER_ASTEROIDS_BY_WEEK
            // Show asteroids for today.
            R.id.show_rent_menu -> MainViewModel.NasaApiFilter.FILTER_ASTEROIDS_TODAY
            // Show all saved asteroids.
            R.id.show_buy_menu -> MainViewModel.NasaApiFilter.FILTER_BY_SAVED_ASTEROIDS
            else -> MainViewModel.NasaApiFilter.FILTER_BY_SAVED_ASTEROIDS
        }
        return true
    }
}
