package com.martinm.backseatalert.ui.main

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.PowerManager
import android.provider.Settings
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.martinm.backseatalert.data.recognition.ActivityRecognitionManager
import com.martinm.backseatalert.data.repository.SettingsRepository
import com.martinm.backseatalert.utils.AlertManager
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val repository: SettingsRepository,
    private val activityRecognitionManager: ActivityRecognitionManager
) : ViewModel() {

    private val _isBatteryOptimizing = MutableStateFlow(checkBatteryOptimizing())
    val isBatteryOptimizing: StateFlow<Boolean> = _isBatteryOptimizing

    val uiState: StateFlow<MainUiState> = repository.isEnabled
        .combine(isBatteryOptimizing) { enabled, batteryOptimizing ->
            MainUiState(
                isEnabled = enabled,
                isBatteryOptimizing = batteryOptimizing
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = MainUiState(isEnabled = false, isBatteryOptimizing = true)
        )

    fun setEnabled(enabled: Boolean) {
        viewModelScope.launch {
            repository.setEnabled(enabled)
            if (enabled) {
                activityRecognitionManager.start()
            } else {
                activityRecognitionManager.stop()
            }
        }
    }

    fun checkBatteryOptimizing(): Boolean {
        val powerManager = context.getSystemService(Context.POWER_SERVICE) as PowerManager
        return !powerManager.isIgnoringBatteryOptimizations(context.packageName)
    }

    fun refreshBatteryStatus() {
        _isBatteryOptimizing.value = checkBatteryOptimizing()
    }

    fun requestIgnoreBatteryOptimizations(context: Context) {
        val intent = Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS).apply {
            data = Uri.parse("package:${context.packageName}")
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        context.startActivity(intent)
    }

    fun triggerTestAlarm() {
        AlertManager.triggerAlarm(context)
    }
}