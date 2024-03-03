package com.example.wallpaperapp.data.api

import com.bumptech.glide.load.engine.Resource
import com.example.wallpaperapp.domain.entity.WallpaperLink
import com.example.wallpaperapp.domain.repository.WallpaperRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class WallpaperRepositoryImpl @Inject constructor(val picSumApi : PicsumAPI): WallpaperRepository
{


    override fun getImages(): Flow<com.example.wallpaperapp.utils.Resource<List<WallpaperLink>>> = flow {

        try {

            val response = picSumApi.getWallpaperImages()

            response?.let {
                val wallpaperlinks : List<WallpaperLink> = response.map {
                    WallpaperLink(it.downloadUrl)
                }
                emit(com.example.wallpaperapp.utils.Resource.Success(wallpaperlinks))
            }

        }
        catch (e : Exception){
            var errorOutput = ""
            if(e.message!=null){
                errorOutput = e.message!!
            }
            else
            {
                errorOutput = "unknown error"
            }
            emit(com.example.wallpaperapp.utils.Resource.Error(null,errorOutput))

        }
    }


}


