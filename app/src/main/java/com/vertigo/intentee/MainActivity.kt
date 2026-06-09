package com.vertigo.intentee

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.vertigo.intentee.ui.theme.IntenteeTheme

/**
 * LESSON: UNDERSTANDING ANDROID INTENTS
 *
 * An Intent is a "messaging object" you use to request an action from another app component.
 * Think of it as a letter: you define where it should go and what it should contain.
 *
 * There are two primary types of Intents:
 * 1. EXPLICIT: You name the exact component (class) to start. Used for internal navigation.
 * 2. IMPLICIT: You name an ACTION (e.g., "Open a website") and let Android find the best app.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // enableEdgeToEdge() allows the app to draw under the status/navigation bars
        enableEdgeToEdge()
        
        setContent {
            IntenteeTheme {
                // Scaffold provides the basic Material Design layout structure
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    // We pass the padding to our screen to avoid drawing under the system bars
                    IntentLessonScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun IntentLessonScreen(modifier: Modifier = Modifier) {
    // LocalContext.current provides the Activity context needed to call startActivity()
    val context = LocalContext.current

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "Android Intents Lesson",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
        }

        // --- LESSON 1: EXPLICIT INTENT ---
        item {
            IntentCard(
                title = "1. Explicit Intent (Internal)",
                description = "Navigates to a specific screen within your app. Note how we pass data using 'putExtra'.",
                buttonText = "Go to Second Activity",
                onClick = {
                    // Create an Intent by specifying the 'context' and the 'target class'
                    val intent = Intent(context, SecondActivity::class.java).apply {
                        // Key-Value pairs passed to the next activity
                        putExtra("EXTRA_DATA", "Hello from the first screen!")
                    }
                    context.startActivity(intent)
                }
            )
        }

        // --- LESSON 2: IMPLICIT INTENT (WEB) ---
        item {
            IntentCard(
                title = "2. Implicit Intent: View Web",
                description = "We don't specify an app; we specify the action ACTION_VIEW and a URL. The system opens the browser.",
                buttonText = "Open Browser",
                onClick = {
                    // Define the ACTION and the DATA (Uri)
                    val intent = Intent(Intent.ACTION_VIEW).apply {
                        data = Uri.parse("https://developer.android.com")
                    }
                    try {
                        context.startActivity(intent)
                    } catch (e: ActivityNotFoundException) {
                        // Always handle cases where no app can handle your intent
                        Toast.makeText(context, "No browser found on device", Toast.LENGTH_SHORT).show()
                    }
                }
            )
        }

        // --- LESSON 3: IMPLICIT INTENT (SHARE) ---
        item {
            IntentCard(
                title = "3. Implicit Intent: Share",
                description = "This uses ACTION_SEND. We use 'createChooser' to force Android to show the app selection list.",
                buttonText = "Share Text Message",
                onClick = {
                    val sendIntent = Intent().apply {
                        action = Intent.ACTION_SEND
                        type = "text/plain"
                        putExtra(Intent.EXTRA_TEXT, "Learning Intents is fun!")
                    }
                    
                    // A 'Chooser' ensures the user picks an app instead of using a default
                    val shareIntent = Intent.createChooser(sendIntent, "Send message via:")
                    context.startActivity(shareIntent)
                }
            )
        }

        // --- LESSON 4: IMPLICIT INTENT (MAPS) ---
        item {
            IntentCard(
                title = "4. Implicit Intent: Location",
                description = "Uses a 'geo:' URI scheme. We also use 'setPackage' to try and target Google Maps specifically.",
                buttonText = "Open Maps",
                onClick = {
                    // Coordinates for Googleplex
                    val gmmIntentUri = Uri.parse("geo:37.422160,-122.084270?q=Googleplex")
                    val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                    
                    // setPackage is optional; it attempts to bypass the chooser if the app is installed
                    mapIntent.setPackage("com.google.android.apps.maps")
                    
                    try {
                        context.startActivity(mapIntent)
                    } catch (e: ActivityNotFoundException) {
                        Toast.makeText(context, "Google Maps not installed", Toast.LENGTH_SHORT).show()
                    }
                }
            )
        }
    }
}

/**
 * A reusable UI component for each lesson item.
 */
@Composable
fun IntentCard(
    title: String,
    description: String,
    buttonText: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = title, style = MaterialTheme.typography.titleLarge)
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            Button(
                onClick = onClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = buttonText)
            }
        }
    }
}
