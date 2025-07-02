package com.martinm.backseatalert.data.recognition

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ActivityRecognitionManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun start() { /* register transitions here */ }
    fun stop() { /* unregister */ }
}