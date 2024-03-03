package com.example.wallpaperapp.domain.repository

import com.bumptech.glide.load.engine.Resource
import com.example.wallpaperapp.data.api.model.PicsumItem
import com.example.wallpaperapp.domain.entity.WallpaperLink
import kotlinx.coroutines.flow.Flow


interface WallpaperRepository {
    fun getImages(): Flow<com.example.wallpaperapp.utils.Resource<List<WallpaperLink>>>
}