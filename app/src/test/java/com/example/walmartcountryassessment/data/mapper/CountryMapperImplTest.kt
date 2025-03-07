package com.example.walmartcountryassessment.data.mapper

import com.example.walmartcountryassessment.data.dto.CountryResponse
import com.example.walmartcountryassessment.data.dto.Currency
import com.example.walmartcountryassessment.data.dto.Language
import com.example.walmartcountryassessment.domain.data.CountryDetails
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
//This Test Class provides Testcase to CountryMapperImpl Class
class CountryMapperImplTest {

    @Test
    fun `mapEntityToDomain is invoked with CountryResponse then CountryDetails is returned`() {
        val res = CountryMapperImpl().mapEntityToDomain(
            CountryResponse(
                "New Delhi",
                "IN",
                mockk<Currency>(),
                "",
                "",
                mockk<Language>(),
                "India",
                "AS"
            )
        )
        assertEquals("IN", res.countryCode)
    }

    @Test
    fun `mapEntityToDomainList is invoked with list of CountryResponse then list of CountryDetails is returned`() {
        val res = listOf(
            CountryResponse(
                "New Delhi",
                "IN",
                mockk<Currency>(),
                "",
                "",
                mockk<Language>(),
                "India",
                "AS"
            )
        )
            .map { CountryMapperImpl().mapEntityToDomain(it) }

        assertTrue(res.isNotEmpty())
        assertTrue(res.all { it is CountryDetails })
    }

    @Test
    fun `mapEntityToDomainList is invoked with empty list then empty list is returned`() {
        val res = emptyList<CountryResponse>()
            .map { CountryMapperImpl().mapEntityToDomain(it) }

        assertTrue(res.isEmpty())
    }

}