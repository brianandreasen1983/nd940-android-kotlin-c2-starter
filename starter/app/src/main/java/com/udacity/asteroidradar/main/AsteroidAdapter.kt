package com.udacity.asteroidradar.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.R


// This is the adapter for the recycler view
class AsteroidAdapter : RecyclerView.Adapter<AsteroidAdapter.ViewHolder>() {
    // Inefficient because this tells that recycler view that the whole LIST has changed rather than a specific item.
    // The solution to this is diffutil which is part of the RecyclerView API.
    // This will also be removed when refactoring to list adapter as list adapter provides this.
    var data = listOf<Asteroid>()
        set (value) {
            field = value
            notifyDataSetChanged()
        }

    // Used to get how many items are in the adapter...
    // This will be removed when we refactor to list adapter.
    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // We need to setup the items to bind to in the view holder class before we implement this.
        val item = data[position]

        // bind the views with data
        holder.codename.text = item.closeApproachDate
        holder.asteroidDate.text = item.closeApproachDate

    }

    // Used to inflate the recycler view layout
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.list_item_asteroid, parent, false)
        return ViewHolder(view)
    }

    // DiffUtil handles if an item has changed a lot more efficiency than the data object above.
    class AsteroidDiffCallback : DiffUtil.ItemCallback<Asteroid>() {
        override fun areItemsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
            return oldItem.equals(newItem)
        }

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val codename: TextView = itemView.findViewById(R.id.tv_codename)
        val asteroidDate: TextView = itemView.findViewById(R.id.tv_asteroidDate)
    }
}