package com.example.walmartcountryassessment.presentation.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.walmartcountryassessment.databinding.ActivityMainBinding
import com.example.walmartcountryassessment.data.utils.FetchCountryStates
import com.example.walmartcountryassessment.presentation.viewmodel.CountryViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

//This Activity provides UI which displays Country Details To Users
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val countryViewModel:CountryViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    private lateinit var countryRecyclerAdapter: CountryRecyclerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpRecyclerView()
        displayCountries()
    }

    private fun setUpRecyclerView() {
        countryRecyclerAdapter = CountryRecyclerAdapter()
        binding.rvCountry.adapter = countryRecyclerAdapter
    }

    private fun displayCountries() {
        lifecycleScope.launch {
            countryViewModel.countryStateFlow.collect{
                with(binding){
                    when (it) {
                        is FetchCountryStates.Failure -> {
                            pbLoading.visibility = View.GONE
                            btnRetry.visibility = View.VISIBLE
                            btnRetry.setOnClickListener {
                                countryViewModel.fetchCountryDetails()
                            }
                            Toast.makeText(this@MainActivity,it.message,Toast.LENGTH_LONG).show()
                        }

                        is FetchCountryStates.Loading -> {
                            pbLoading.visibility = View.VISIBLE
                            btnRetry.visibility = View.GONE
                        }

                        is FetchCountryStates.Success -> {
                            pbLoading.visibility = View.GONE
                            btnRetry.visibility = View.GONE
                            countryRecyclerAdapter.updateCountries(it.data)
                        }
                    }
                }
            }
        }
    }
}