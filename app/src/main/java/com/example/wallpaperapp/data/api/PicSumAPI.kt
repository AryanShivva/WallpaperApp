package com.example.wallpaperapp.data.api

import com.example.wallpaperapp.data.api.model.PicsumItem
import com.example.wallpaperapp.utils.constants.BASE_URL
import com.example.wallpaperapp.utils.constants.END_POINT

import retrofit2.http.GET
import retrofit2.http.Query

interface PicsumAPI {
    @GET(BASE_URL + END_POINT)
    suspend fun getWallpaperImages(
        @Query("limit") limit: Int = 100
    ): List<PicsumItem>?
}