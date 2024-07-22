package com.psbskb.myrecipe.modules.recipe

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class RecipeVideoPlayerModel : ViewModel() {
    private val _isVideoPlaying = mutableStateOf(false)
    val isVideoPlaying: State<Boolean> = _isVideoPlaying

    init {
        _isVideoPlaying.value = false;
    }

    fun changeVideState() {
        _isVideoPlaying.value = !_isVideoPlaying.value
    }
}