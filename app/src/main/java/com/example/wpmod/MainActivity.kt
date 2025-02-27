package com.example.wpmod

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.wpmod.model.RenderPatternType
import com.example.wpmod.ui.theme.WpmodTheme
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Checkbox

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WpmodTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(modifier = Modifier.padding(innerPadding)) {
                        Greeting(name = "Android")
                        PatternSelector()
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    WpmodTheme {
        Greeting("Android")
    }
}

@Composable
fun PatternSelector() {
    val context = LocalContext.current
    val sharedPref = context.getSharedPreferences("wallpaper_prefs", Context.MODE_PRIVATE)
    val selectedPattern = remember {
        mutableStateOf(
            RenderPatternType.valueOf(
                sharedPref.getString("selected_pattern", RenderPatternType.VORTEX.name)
                    ?: RenderPatternType.VORTEX.name
            )
        )
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        RenderPatternType.values().forEach { patternType ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = selectedPattern.value == patternType,
                    onCheckedChange = { isChecked ->
                        if (isChecked) {
                            selectedPattern.value = patternType
                            with(sharedPref.edit()) {
                                putString("selected_pattern", patternType.name)
                                apply()
                            }
                        }
                    }
                )
                Text(text = patternType.name)
            }
        }
        Text(text = "Selected Pattern: ${selectedPattern.value}")
    }
}