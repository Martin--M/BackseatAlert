# BackseatAlert

## v0.1.0

### Features
* **Vehicle Exit Detection:** Detects when a user exits a vehicle using the Google Play Services Activity Recognition API.
* **Persistent Reminders:** Triggers a high-priority, ongoing notification reminder when a vehicle exit transition is detected.
* **Automatic Retrigger Loop:** Schedules exact alarms to sound/vibrate the reminder alert every 30 seconds until the user manually dismisses the notification.
* **Battery Optimization Management:** Integrates battery optimization status detection and a deep link to request Doze Mode exclusion (unrestricted battery permission) to ensure background detection reliability.
* **Manual Testing Trigger:** Provides a "Test Alert Notification" button in the UI to instantly trigger the alert workflow.
* **ADB Simulation Fallback:** Supports manual testing in the emulator by broadcasting the transition intent via ADB.

### Internal
* **CI/CD Integration:** Configured a GitHub Actions workflow to build debug builds on push/pull requests and build/sign release APKs on tag releases.
