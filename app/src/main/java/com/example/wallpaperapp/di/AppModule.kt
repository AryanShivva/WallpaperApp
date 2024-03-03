package com.example.wallpaperapp.di

import com.example.wallpaperapp.data.api.PicsumAPI
import com.example.wallpaperapp.data.api.WallpaperRepositoryImpl
import com.example.wallpaperapp.data.api.model.PicsumItem
import com.example.wallpaperapp.domain.repository.WallpaperRepository
import com.example.wallpaperapp.utils.constants.BASE_URL
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
interface AppModule {

    companion object {

    @Provides
    @Singleton
    fun provideRetrofitApi(

    ): PicsumAPI {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(PicsumAPI::class.java)

    }
}

    @Binds
    @Singleton
    fun provideWallpaperRepository(repository: WallpaperRepositoryImpl):WallpaperRepository

}


