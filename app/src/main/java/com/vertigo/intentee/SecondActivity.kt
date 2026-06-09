package com.vertigo.intentee

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vertigo.intentee.ui.theme.IntenteeTheme

/**
 * SECOND ACTIVITY: THE TARGET COMPONENT
 * 
 * This Activity is the destination for our Explicit Intent lesson.
 * Its purpose is to show how to receive data sent from another Activity.
 */
class SecondActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        
        /**
         * RECEIVING DATA:
         * We use the 'intent' property (which is available in any Activity) to access
         * the intent that started this component.
         *
         * We use 'getStringExtra' because we sent a String in MainActivity.
         * The key ("EXTRA_DATA") MUST match exactly what was used in the sender.
         */
        val receivedData = intent.getStringExtra("EXTRA_DATA") ?: "No data found!"

        setContent {
            IntenteeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                            .padding(16.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Explicit Intent Received!",
                            style = MaterialTheme.typography.headlineMedium
                        )
                        
                        Text(
                            text = "Message from first screen:",
                            modifier = Modifier.padding(top = 24.dp),
                            style = MaterialTheme.typography.labelLarge
                        )
                        
                        // Display the data we extracted from the intent extras
                        Text(
                            text = receivedData,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }
    }
}
