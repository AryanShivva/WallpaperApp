package com.example.wallpaperapp.presentation

import com.example.wallpaperapp.domain.entity.WallpaperLink

sealed class WallPaperUiState{
    object Loading : WallPaperUiState()
    object EmptyList : WallPaperUiState()
    data class Success(val data: List<WallpaperLink>) : WallPaperUiState()
    data class Error(val message:String) : WallPaperUiState()

}

