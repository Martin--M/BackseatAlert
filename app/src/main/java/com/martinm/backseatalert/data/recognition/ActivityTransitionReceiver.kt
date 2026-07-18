package com.martinm.backseatalert.data.recognition

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.google.android.gms.location.ActivityTransition
import com.google.android.gms.location.ActivityTransitionResult
import com.google.android.gms.location.DetectedActivity
import com.martinm.backseatalert.utils.AlertManager

class ActivityTransitionReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == ACTION_PROCESS_TRANSITIONS) {
            if (ActivityTransitionResult.hasResult(intent)) {
                val result = ActivityTransitionResult.extractResult(intent) ?: return
                for (event in result.transitionEvents) {
                    if (event.activityType == DetectedActivity.IN_VEHICLE &&
                        event.transitionType == ActivityTransition.ACTIVITY_TRANSITION_EXIT) {
                        AlertManager.triggerAlarm(context)
                        break
                    }
                }
            } else {
                // Fallback for manual/testing triggers when no transition extra is present
                AlertManager.triggerAlarm(context)
            }
        }
    }

    companion object {
        const val ACTION_PROCESS_TRANSITIONS = "com.martinm.backseatalert.ACTION_PROCESS_ACTIVITY_TRANSITIONS"
    }
}