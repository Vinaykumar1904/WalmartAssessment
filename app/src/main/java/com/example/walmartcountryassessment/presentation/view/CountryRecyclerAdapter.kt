package com.example.walmartcountryassessment.presentation.view

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.walmartcountryassessment.databinding.CountryItemBinding
import com.example.walmartcountryassessment.domain.data.CountryDetails
//This class is the Recycler Adapter to display CountryDetails into Recycler view
class CountryRecyclerAdapter():Adapter<CountryViewHolder>() {
    private var countries:List<CountryDetails> = emptyList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val binding = CountryItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CountryViewHolder(binding)
    }

    override fun getItemCount(): Int = countries.size

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.bind(countries[position])
    }
    @SuppressLint("NotifyDataSetChanged")
    fun updateCountries(newCountryList: List<CountryDetails>) {
        countries = newCountryList
        notifyDataSetChanged()
    }
}

