# Android Intents Lesson Project


This project serves as a practical guide to understanding and implementing **Intents** in Android using Jetpack Compose.

## What is an Intent?
An `Intent` is a messaging object used to request an action from another app component. It facilitates communication between components like Activities, Services, and Broadcast Receivers.

---

## 1. Explicit Intents
Used to start a specific component within your own application.
- **Usage**: Navigating between your own screens.
- **Example**: `Intent(context, SecondActivity::class.java)`

## 2. Implicit Intents
Used when you have an action you want to perform (like "view a map" or "send an email") but don't care which app handles it.
- **Common Actions**:
    - `ACTION_VIEW`: View data (URLs, Files, Locations).
    - `ACTION_SEND`: Share data.
    - `ACTION_DIAL`: Open the phone dialer.

## 3. Intent Extras
Data can be passed between activities using "Extras" (Key-Value pairs).
- **Sending**: `intent.putExtra("KEY", value)`
- **Receiving**: `intent.getStringExtra("KEY")`

## 4. Choosers
When multiple apps can handle an implicit intent, a **Chooser** forces the user to pick an app every time (common for Sharing).
- **Example**: `Intent.createChooser(intent, "Title")`

---

## Project Structure
- `MainActivity.kt`: The main dashboard containing all intent examples.
- `SecondActivity.kt`: A target activity for the Explicit Intent example.
- `AndroidManifest.xml`: Where activities are registered and queries are declared.

## Key Snippet (Implicit Intent)
```kotlin
val intent = Intent(Intent.ACTION_VIEW).apply {
    data = Uri.parse("https://developer.android.com")
}
context.startActivity(intent)
```

## Setup Note
If you are targeting Android 11 (API 30) or higher, you must declare `<queries>` in your `AndroidManifest.xml` for any external apps you want to interact with (e.g., Browsers or Maps).
