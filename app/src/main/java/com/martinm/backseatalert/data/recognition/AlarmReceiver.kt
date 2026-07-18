package com.martinm.backseatalert.data.recognition

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.martinm.backseatalert.utils.AlertManager

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action) {
            ACTION_DISMISS_ALARM -> {
                AlertManager.dismissAlarm(context)
            }
            ACTION_RETRIGGER_ALARM -> {
                AlertManager.retriggerAlarm(context)
            }
        }
    }

    companion object {
        const val ACTION_DISMISS_ALARM = "com.martinm.backseatalert.ACTION_DISMISS_ALARM"
        const val ACTION_RETRIGGER_ALARM = "com.martinm.backseatalert.ACTION_RETRIGGER_ALARM"
    }
}
