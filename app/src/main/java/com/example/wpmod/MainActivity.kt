package com.example.wpmod

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.wpmod.ui.theme.WpmodTheme
import com.example.wpmod.renderer.PatternRenderer
import com.example.wpmod.model.RenderPatternType
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Checkbox
import androidx.compose.foundation.layout.Row

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WpmodTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(modifier = Modifier.padding(innerPadding)) {
                        Greeting(name = "Android")
                        PatternSelector(renderer = PatternRenderer())
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
fun PatternSelector(renderer: PatternRenderer) {
    val selectedPattern = remember { mutableStateOf(RenderPatternType.VORTEX) }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        RenderPatternType.values().forEach { patternType ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = selectedPattern.value == patternType,
                    onCheckedChange = {
                        if (it) {
                            selectedPattern.value = patternType
                            renderer.loadPattern(patternType)
                        }
                    }
                )
                Text(text = patternType.name)
            }
        }
        Text(text = "Selected Pattern: ${selectedPattern.value}")
    }
}