# BackseatAlert

BackseatAlert is a lightweight Android application designed to prevent drivers from forgetting passengers or babies in the backseat by alerting them immediately upon exiting a vehicle.

## 🚀 Key Features

* **Automatic Exit Detection:** Utilizes the Google Play Services Activity Recognition API to listen for vehicle entry/exit transitions.
* **Persistent Alarms:** Sounds a high-priority, recurring notification alarm immediately upon exiting a vehicle.
* **Retrigger Loop:** Automatically schedules an exact system alarm to retrigger the notification every 30 seconds until the user explicitly dismisses the alert.
* **Doze Mode Safeguard:** Built-in checks to request exclusion from Android battery optimization constraints, ensuring background receivers are not suspended by the OS.
* **Manual Testing Suite:** Exposes an on-demand test button in the UI and custom intent fallback filters to simulate transitions via ADB.

---

## 🛠️ Architecture & Tech Stack

* **Language:** Kotlin
* **UI Framework:** Jetpack Compose (Material 3)
* **Dependency Injection:** Hilt
* **Data Storage:** Preferences DataStore (stores enable/disable state)
* **System Services:** `ActivityRecognitionClient` (Play Services Location), `AlarmManager`, and `NotificationManager`.

---

## 💻 Local Setup & Development

### Prerequisites
* JDK 17
* Android SDK (API level 36)
* Running Android Emulator or connected physical device

### Building and Installing
To build and install the debug APK on your connected device or emulator:
```bash
./gradlew installDebug
```

---

## 🧪 Testing in the Emulator (using ADB)

Because activity recognition relies on device sensor fusion (accelerometer, gyroscope) to identify driving transitions, testing physical movement in an emulator is not natively possible. 

To bypass this and mock a vehicle exit transition, use the custom intent broadcast fallback configured in the receiver:

### 1. Grant Permissions
Launch the app on the emulator and grant the requested **Activity Recognition** and **Post Notifications** permissions (or use ADB):
```bash
adb shell pm grant com.martinm.backseatalert android.permission.POST_NOTIFICATIONS
adb shell pm grant com.martinm.backseatalert android.permission.ACTIVITY_RECOGNITION
```

### 2. Trigger the Safety Alert (Start Alarm)
Send the broadcast intent to the `ActivityTransitionReceiver`. Since there is no Google Play Services payload attached, the receiver will run its manual fallback:
```bash
adb shell am broadcast \
  -a com.martinm.backseatalert.ACTION_PROCESS_ACTIVITY_TRANSITIONS \
  -n com.martinm.backseatalert/.data.recognition.ActivityTransitionReceiver
```

### 3. Dismiss the Alert (Stop Alarm)
To simulate the user clicking the "Dismiss Alert" button on the notification (which cancels the retrigger loop and cleans up the active state):
```bash
adb shell am broadcast \
  -a com.martinm.backseatalert.ACTION_DISMISS_ALARM \
  -n com.martinm.backseatalert/.data.recognition.AlarmReceiver
```

### 4. Retrigger the Alert (Optional)
To manually fire a retrigger alarm (only works if the alarm state is already active):
```bash
adb shell am broadcast \
  -a com.martinm.backseatalert.ACTION_RETRIGGER_ALARM \
  -n com.martinm.backseatalert/.data.recognition.AlarmReceiver
```

---

## 📦 CI/CD Workflow

The repository is equipped with a GitHub Actions workflow (`.github/workflows/ci.yml`) that automates builds:
* **Pull Requests & Pushes:** Compiles the app and runs a debug build, archiving `app-debug.apk`.
* **Tags (`v*`):** Builds the release unsigned APK, signs it using a base64-decoded keystore, and exports the signed production artifact.

### Required GitHub Secrets for Releases:
To build signed releases, configure the following secrets in your GitHub Repository settings:
1. `ANDROID_KEY_BASE64` - The base64-encoded keystore file.
2. `ANDROID_KEY_ALIAS` - The keystore key alias.
3. `ANDROID_KEY_PASSWORD` - The password for both the keystore and key alias.
