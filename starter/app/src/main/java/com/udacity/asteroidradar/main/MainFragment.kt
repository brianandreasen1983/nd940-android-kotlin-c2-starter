package com.udacity.asteroidradar.main

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.FragmentMainBinding
import com.udacity.database.AsteroidDatabase

class MainFragment : Fragment() {
    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this

        val application = requireNotNull(this.activity).application

        // Setup Database as a datasource and then assign the viewModelFactory
        val dataSource = AsteroidDatabase.getInstance(application).asteroidDao
//        val viewModelFactory = AsteroidViewModelFactory(dataSource, application)

        // Is this the right way to do this or is there a better way? DI?
        val viewModelFactory = AsteroidViewModelFactory(AsteroidApplication().repository)

        // Assign the view model factory.
        val asteroidViewModel =
            ViewModelProvider(
                this, viewModelFactory).get(MainViewModel::class.java)

        // old code
//        binding.mainViewModel = viewModel
        binding.mainViewModel = asteroidViewModel

        val adapter = AsteroidAdapter()
        // Setup the binding for the asteroid list and the adapter.
        binding.asteroidRecycler.adapter = adapter

        // Setup the observer to observe the asteroids.
        // This observes changes in the list.
        asteroidViewModel.asteroids.observe(viewLifecycleOwner, Observer {
            it?.let {
                // call adapter.submitList which will require a refactor.
            }
        })

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return true
    }
}
