package com.dbad.justintime.f_profile.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class ProfileViewModel() : ViewModel() {
    private val _state = MutableStateFlow(ProfileState())
    val state = _state.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000L),
        ProfileState()
    )

    fun onEvent() {}
}