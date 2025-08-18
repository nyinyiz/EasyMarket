package com.nnzapp.easymarket.presentation.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.nnzapp.easymarket.data.config.ApiConfig

@Composable
fun ConfigScreen(onConfigSelected: (Boolean, String) -> Unit) {
    var useMockApi by remember { mutableStateOf(false) }
    var selectedApiUrl by remember { mutableStateOf(ApiConfig.predefinedRoutes.first().url) }
    var customApiUrl by remember { mutableStateOf("") }
    var isDropdownExpanded by remember { mutableStateOf(false) }
    var selectedRoute by remember { mutableStateOf(ApiConfig.predefinedRoutes.first()) }
    var useCustomUrl by remember { mutableStateOf(false) }

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = "EasyMarket Configuration",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
        )

        Spacer(modifier = Modifier.height(32.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            colors =
                CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                ),
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
            ) {
                Text(
                    text = "API Configuration",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.SemiBold,
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Choose your data source:",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Live API Option
                Row(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .selectable(
                                selected = !useMockApi,
                                onClick = { useMockApi = false },
                            ).padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    RadioButton(
                        selected = !useMockApi,
                        onClick = { useMockApi = false },
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = "Live API",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Medium,
                        )
                        Text(
                            text = "Real server data (may have rate limits)",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                }

                // Mock API Option
                Row(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .selectable(
                                selected = useMockApi,
                                onClick = { useMockApi = true },
                            ).padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    RadioButton(
                        selected = useMockApi,
                        onClick = { useMockApi = true },
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = "Mock API",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Medium,
                        )
                        Text(
                            text = "Local mock data (always available)",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                }

                if (!useMockApi) {
                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        text = "API Endpoint:",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Medium,
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Predefined routes dropdown
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                    ) {
                        Row(
                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        isDropdownExpanded = !isDropdownExpanded
                                    }.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = if (useCustomUrl) "Custom URL" else selectedRoute.name,
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Medium,
                                )
                                Text(
                                    text =
                                        if (useCustomUrl) {
                                            customApiUrl.takeIf { it.isNotEmpty() }
                                                ?: "Enter custom URL"
                                        } else {
                                            selectedRoute.url
                                        },
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                )
                            }
                            IconButton(
                                onClick = { isDropdownExpanded = !isDropdownExpanded },
                            ) {
                                Icon(
                                    imageVector = if (isDropdownExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                                    contentDescription = "Expand",
                                )
                            }
                        }

                        DropdownMenu(
                            expanded = isDropdownExpanded,
                            onDismissRequest = { isDropdownExpanded = false },
                        ) {
                            ApiConfig.predefinedRoutes.forEach { route ->
                                DropdownMenuItem(
                                    contentPadding = PaddingValues(8.dp),
                                    text = {
                                        Column {
                                            Text(
                                                text = route.name,
                                                style = MaterialTheme.typography.bodySmall,
                                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                            )
                                            Text(
                                                text = route.url,
                                                style = MaterialTheme.typography.titleSmall,
                                                color = Color.Blue,
                                            )
                                        }
                                    },
                                    onClick = {
                                        selectedRoute = route
                                        selectedApiUrl = route.url
                                        useCustomUrl = false
                                        isDropdownExpanded = false
                                    },
                                )
                                HorizontalDivider()
                            }
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = "Custom URL",
                                        style = MaterialTheme.typography.titleSmall,
                                        fontWeight = FontWeight.Medium,
                                    )
                                },
                                onClick = {
                                    useCustomUrl = true
                                    isDropdownExpanded = false
                                },
                            )
                        }
                    }

                    if (useCustomUrl) {
                        Spacer(modifier = Modifier.height(12.dp))
                        OutlinedTextField(
                            value = customApiUrl,
                            onValueChange = { customApiUrl = it },
                            label = { Text("Custom API URL") },
                            placeholder = { Text("https://your-api-endpoint.com") },
                            modifier = Modifier.fillMaxWidth(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Uri),
                            shape = RoundedCornerShape(12.dp),
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        ElevatedButton(
            onClick = {
                val finalUrl =
                    if (useMockApi) {
                        ""
                    } else if (useCustomUrl) {
                        customApiUrl
                    } else {
                        selectedApiUrl
                    }
                onConfigSelected(useMockApi, finalUrl)
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(),
            enabled = useMockApi || (!useCustomUrl || customApiUrl.isNotEmpty()),
        ) {
            Text(
                text = "Continue to Store",
                style = MaterialTheme.typography.titleMedium,
            )
        }
    }
}
