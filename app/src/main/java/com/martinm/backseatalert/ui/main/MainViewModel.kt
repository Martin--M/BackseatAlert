package com.martinm.backseatalert.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.martinm.backseatalert.data.repository.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: SettingsRepository
) : ViewModel() {
    val uiState: StateFlow<MainUiState> = repository.isEnabled
        .map { MainUiState(it) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), MainUiState(false))
}