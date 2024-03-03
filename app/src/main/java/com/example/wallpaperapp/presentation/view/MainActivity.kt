package com.example.wallpaperapp.presentation.view

import android.app.WallpaperManager
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.wallpaperapp.presentation.adapter.ImagesRecyclerViewAdapter
import com.example.wallpaperapp.databinding.ActivityMainBinding
import com.example.wallpaperapp.domain.entity.WallpaperLink
import com.example.wallpaperapp.presentation.WallPaperUiState
import com.example.wallpaperapp.presentation.adapter.ItemOnClickistener
import com.example.wallpaperapp.presentation.viewmodel.WallPaperViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.net.URL


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val wallPaperViewModel: WallPaperViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

       //1.setting views
        //2.collect the state (coroutines)
        //3.pdate the wallpaper from rest api
        //4.updtae the ui
        setUpViews()
        collectUitate()
        wallPaperViewModel.fetchwallpapers()

    }

    private fun setUpViews() {
        binding.imagesrecyclerview.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(this@MainActivity, 2)
        }
    }

    private fun collectUitate() {
       lifecycleScope.launch (Dispatchers.Main){
           wallPaperViewModel.wallpaperList.collect(){wallpaperUiState->

               when (wallpaperUiState){

                   is WallPaperUiState.Loading ->{
                       binding.progressBar.visibility = View.VISIBLE
                       Toast.makeText(this@MainActivity,"wallpapers are currently loading",Toast.LENGTH_SHORT).show()
                   }

                   is WallPaperUiState.EmptyList -> {
                       binding.progressBar.visibility = View.VISIBLE
                       Toast.makeText(this@MainActivity,"wallpapers are currently Empty",Toast.LENGTH_SHORT).show()

                   }
                   is WallPaperUiState.Success -> {
                       binding.progressBar.visibility = View.GONE
                       populateDataInRecyclerView(wallpaperUiState.data)

                   }
                   is WallPaperUiState.Error-> {

                       Toast.makeText(this@MainActivity,"error occured",Toast.LENGTH_SHORT).show()

                   }

               }

            }

       }
    }

    fun populateDataInRecyclerView(list: List<WallpaperLink>) {

        val wallpaperAdapter = ImagesRecyclerViewAdapter(list,this::onClickImage)
        binding.imagesrecyclerview.adapter = wallpaperAdapter

    }

    fun onClickImage(wallpaperUrl: String) {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                // Download the image from the URL
                val inputStream = URL(wallpaperUrl).openStream()
                val bitmap = BitmapFactory.decodeStream(inputStream)

                // Set the downloaded bitmap as wallpaper
                val wallpaperManager = WallpaperManager.getInstance(this@MainActivity)
                wallpaperManager.setBitmap(bitmap)

                // Notify the user that the wallpaper has been set successfully
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@MainActivity,
                        "Wallpaper set successfully",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } catch (e: IOException) {
                // Handle any errors that occur during the download process
                e.printStackTrace()
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@MainActivity,
                        "Failed to set wallpaper",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

}