package com.example.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.ShowChart
import androidx.compose.material.icons.filled.Science
import androidx.compose.material.icons.outlined.Calculate
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.School
import androidx.compose.material.icons.outlined.ShowChart
import androidx.compose.material.icons.outlined.Science
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.ui.theme.CyanAccent
import com.example.ui.theme.LocalFinancialColors

enum class AppDestination(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val tag: String
) {
    HOME("Home", Icons.Filled.Home, Icons.Outlined.Home, "nav_home"),
    LEARN("Learn", Icons.Filled.School, Icons.Outlined.School, "nav_learn"),
    MARKETS("Markets", Icons.Filled.ShowChart, Icons.Outlined.ShowChart, "nav_markets"),
    LABS("Labs", Icons.Filled.Science, Icons.Outlined.Science, "nav_labs"),
    CALCULATORS("Calculators", Icons.Filled.Calculate, Icons.Outlined.Calculate, "nav_calculators"),
    PRACTICE("Practice", Icons.Filled.ShowChart, Icons.Outlined.ShowChart, "nav_practice"),
    PROFILE("Profile", Icons.Filled.Person, Icons.Outlined.Person, "nav_profile")
}

@Composable
fun TradeInvestBottomBar(
    currentDestination: AppDestination,
    onNavigate: (AppDestination) -> Unit,
    modifier: Modifier = Modifier
) {
    val finColors = LocalFinancialColors.current

    val mainDestinations = listOf(
        AppDestination.HOME,
        AppDestination.LEARN,
        AppDestination.MARKETS,
        AppDestination.LABS,
        AppDestination.PRACTICE,
        AppDestination.PROFILE
    )

    NavigationBar(
        modifier = modifier.navigationBarsPadding(),
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 6.dp
    ) {
        mainDestinations.forEach { dest ->
            val isSelected = currentDestination == dest
            NavigationBarItem(
                selected = isSelected,
                onClick = { onNavigate(dest) },
                icon = {
                    Icon(
                        imageVector = if (isSelected) dest.selectedIcon else dest.unselectedIcon,
                        contentDescription = dest.title,
                        modifier = Modifier.size(22.dp)
                    )
                },
                label = {
                    Text(
                        text = dest.title,
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    selectedTextColor = CyanAccent,
                    indicatorColor = CyanAccent.copy(alpha = 0.22f),
                    unselectedIconColor = finColors.textMuted,
                    unselectedTextColor = finColors.textMuted
                ),
                modifier = Modifier.testTag(dest.tag)
            )
        }
    }
}
