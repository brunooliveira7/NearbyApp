package com.example.nearby.ui.screen.home

import com.example.nearby.data.model.Category
import com.example.nearby.data.model.MarKet
import com.google.android.gms.maps.model.LatLng

data class HomeUiState(
    val categories: List<Category>? = null,
    val markets: List<MarKet>? = null,
    val marketLocations: List<LatLng>? = null,
)
