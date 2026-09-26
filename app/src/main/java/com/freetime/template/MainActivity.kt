package com.freetime.template

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.freetime.browser.FreetimeBrowser
import com.freetime.core.FreetimeCore
import com.freetime.design.AppTheme
import com.freetime.design.LiquidGlassRoot
import com.freetime.design.ThemeMode
import com.freetime.design.liquidGlass
import com.freetime.design.liquidGlassCapsule
import com.freetime.donations.DonationTarget
import com.freetime.donations.FreetimeDonationScreen
import com.freetime.warn.FreetimeWarn
import com.freetime.warn.rememberFreetimeWarnState

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { TemplateApp() }
    }
}

@Composable
private fun TemplateApp() {
    val context = LocalContext.current
    val warning = rememberFreetimeWarnState(
        context = context,
        appName = "Template",
        versionCode = 1L,
    )

    AppTheme(
        themeMode = ThemeMode.AUTO_TIME,
        liquidGlassEnabled = true,
    ) {
        LiquidGlassRoot(
            modifier = Modifier.fillMaxSize(),
            source = {
                Box(
                    Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                listOf(
                                    MaterialTheme.colorScheme.background,
                                    MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.35f),
                                    MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.25f),
                                ),
                            ),
                        ),
                )
            },
        ) {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                containerColor = Color.Transparent,
            ) { innerPadding ->
                TemplateContent(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                )
            }
        }

        FreetimeWarn(
            state = warning,
            onLearnMore = {
                FreetimeBrowser.openExternal(context, "https://github.com/FreetimeMaker/Freetime-Core")
            },
        )
    }
}

@Composable
private fun TemplateContent(modifier: Modifier = Modifier) {
    val context = LocalContext.current

    Column(
        modifier = modifier.padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Text(
            text = "Freetime Core Template",
            style = MaterialTheme.typography.headlineMedium,
        )
        Text(
            text = "Freetime Core ${FreetimeCore.SDK_VERSION}",
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .liquidGlass(
                    shape = MaterialTheme.shapes.largeIncreased,
                    interactive = false,
                ),
            colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        ) {
            Column(
                Modifier.padding(18.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Text("Core + Design", style = MaterialTheme.typography.titleMedium)
                Text("Material 3 Expressive, Material You and Liquid Glass are ready to use.")
            }
        }

        Button(
            onClick = {
                FreetimeBrowser.openExternal(context, "https://free-time.me")
            },
            modifier = Modifier.liquidGlassCapsule(),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
        ) {
            Text("Open website")
        }

        DonationExample()
    }
}

@Composable
private fun DonationExample() {
    val context = LocalContext.current
    val targets = listOf(
        DonationTarget.Link(
            label = "OpenCollective",
            url = "https://opencollective.com/freetimemaker",
        ),
    )

    FreetimeDonationScreen(
        targets = targets,
        onLinkClick = { target ->
            FreetimeBrowser.openExternal(context, target.url)
        },
    )
}

@Preview(showBackground = true)
@Composable
private fun TemplatePreview() {
    AppTheme {
        Text(
            text = "Freetime Core 2.0.0",
            modifier = Modifier.padding(24.dp),
        )
    }
}
