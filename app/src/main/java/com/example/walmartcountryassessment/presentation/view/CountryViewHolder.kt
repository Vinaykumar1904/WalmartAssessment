package com.example.walmartcountryassessment.presentation.view

import androidx.recyclerview.widget.RecyclerView
import com.example.walmartcountryassessment.databinding.CountryItemBinding
import com.example.walmartcountryassessment.domain.data.CountryDetails

class CountryViewHolder(val binding: CountryItemBinding): RecyclerView.ViewHolder(binding.root){
    fun bind(countryDetails: CountryDetails){
        with(binding){
            tvCountryNameRegion.text = "${countryDetails.countryName}, ${countryDetails.countryRegion}"
            tvCode.text = countryDetails.countryCode
            tvCapital.text = countryDetails.countryCapital
        }
    }
}