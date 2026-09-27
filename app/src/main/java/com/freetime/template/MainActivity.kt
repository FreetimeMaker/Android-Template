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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.freetime.browser.FreetimeBrowser
import com.freetime.core.FreetimeCore
import com.freetime.design.AppTheme
import com.freetime.design.FloatingBottomNavigationBar
import com.freetime.design.FloatingBottomNavigationGlassRoot
import com.freetime.design.FloatingBottomNavigationItem
import com.freetime.design.ThemeMode
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
    var selectedTab by remember { mutableIntStateOf(0) }
    val warning = rememberFreetimeWarnState(
        context = context,
        appName = "Template",
        versionCode = 1L,
    )

    AppTheme(
        themeMode = ThemeMode.AUTO_TIME,
        lightHour = 7,
        darkHour = 19,
        floatingBottomNavigationGlassEnabled = true,
    ) {
        FloatingBottomNavigationGlassRoot(
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
                bottomBar = {
                    val items = listOf(
                        FloatingBottomNavigationItem(
                            label = "Home",
                            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                        ),
                        FloatingBottomNavigationItem(
                            label = "Settings",
                            icon = { Icon(Icons.Default.Settings, contentDescription = "Settings") },
                        ),
                        FloatingBottomNavigationItem(
                            label = "Donate",
                            icon = { Icon(Icons.Default.Add, contentDescription = "Donate") },
                        ),
                    )

                    FloatingBottomNavigationBar(
                        items = items,
                        selectedItemIndex = when (selectedTab) {
                            0 -> 0
                            1 -> 1
                            3 -> 2
                            else -> 0
                        },
                        onItemSelected = { index ->
                            selectedTab = when (index) {
                                0 -> 0
                                1 -> 1
                                else -> 3
                            }
                        },
                        searchItem = FloatingBottomNavigationItem(
                            label = "Browser",
                            icon = { Icon(Icons.Default.Search, contentDescription = "Browser") },
                        ),
                        searchSelected = selectedTab == 2,
                        onSearchSelected = { selectedTab = 2 },
                    )
                },
            ) { innerPadding ->
                when (selectedTab) {
                    0 -> HomeScreen(Modifier.padding(innerPadding))
                    1 -> SettingsScreen(Modifier.padding(innerPadding))
                    2 -> BrowserScreen(Modifier.padding(innerPadding))
                    else -> DonationScreen(Modifier.padding(innerPadding))
                }
            }
        }

        FreetimeWarn(
            state = warning,
            onLearnMore = {
                FreetimeBrowser.openExternal(
                    context,
                    "https://github.com/FreetimeMaker/Freetime-Core",
                )
            },
        )
    }
}

@Composable
private fun HomeScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Text("Freetime Core Template", style = MaterialTheme.typography.headlineMedium)
        Text(
            text = "SDK: ${FreetimeCore.SDK_VERSION}",
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(
                Modifier.padding(18.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Text("Material 3 Expressive", style = MaterialTheme.typography.titleMedium)
                Text("Normal content uses Material 3 Expressive and Material You.")
                Text("Liquid Glass is reserved for the floating bottom navigation.")
            }
        }
    }
}

@Composable
private fun SettingsScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Text("Settings", style = MaterialTheme.typography.headlineMedium)
        Text("AUTO_TIME: light from 07:00, dark from 19:00.")
        Text("Floating bottom navigation Liquid Glass is enabled.")
    }
}

@Composable
private fun BrowserScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Text("Browser", style = MaterialTheme.typography.headlineMedium)
        Button(
            onClick = { FreetimeBrowser.openExternal(context, "https://free-time.me") },
        ) {
            Text("Open website")
        }
    }
}

@Composable
private fun DonationScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    FreetimeDonationScreen(
        targets = listOf(
            DonationTarget.Link(
                label = "OpenCollective",
                url = "https://opencollective.com/freetimemaker",
            ),
        ),
        onLinkClick = { FreetimeBrowser.openExternal(context, it.url) },
        modifier = modifier.fillMaxSize(),
    )
}
