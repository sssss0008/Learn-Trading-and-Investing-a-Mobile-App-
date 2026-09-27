package com.example.ui.components

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.AmberGold
import com.example.ui.theme.CyanAccent
import com.example.ui.theme.EmeraldBullish
import com.example.ui.theme.LocalFinancialColors
import com.example.ui.theme.RoseBearish
import com.example.ui.theme.TradeInvestLogo

@Composable
fun TradeInvestTopHeader(
    modifier: Modifier = Modifier,
    learnerName: String = "Awiskar",
    xp: Int = 2450,
    streak: Int = 14,
    onOpenSearch: () -> Unit = {},
    onOpenAiTutor: () -> Unit = {},
    onToggleTheme: () -> Unit = {},
    isDark: Boolean = true
) {
    val context = LocalContext.current
    val finColors = LocalFinancialColors.current

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp, vertical = 10.dp)
    ) {
        // Creator Badge Bar & Top Utility Controls
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Creator attribution badge (clickable to LinkedIn)
            Surface(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .border(1.dp, finColors.cardBorder, RoundedCornerShape(20.dp))
                    .clickable {
                        try {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/in/awiskaracharya/"))
                            context.startActivity(intent)
                        } catch (_: Exception) {}
                    }
                    .testTag("creator_badge"),
                color = finColors.cardBackground.copy(alpha = 0.8f)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(7.dp)
                            .background(CyanAccent, CircleShape)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Created by Awiskar Acharya",
                        style = MaterialTheme.typography.labelSmall,
                        color = finColors.textSecondary,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            // Quick Tools: AI Tutor, Theme Toggle, Search
            Row(verticalAlignment = Alignment.CenterVertically) {
                // Streak Pill
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = AmberGold.copy(alpha = 0.15f),
                    modifier = Modifier.padding(end = 6.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.LocalFireDepartment,
                            contentDescription = "Streak",
                            tint = AmberGold,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(3.dp))
                        Text(
                            text = "${streak}d",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = AmberGold
                        )
                    }
                }

                // AI Tutor Trigger
                IconButton(
                    onClick = onOpenAiTutor,
                    modifier = Modifier
                        .size(36.dp)
                        .background(CyanAccent.copy(alpha = 0.12f), CircleShape)
                        .testTag("btn_ai_tutor")
                ) {
                    Icon(
                        imageVector = Icons.Default.AutoAwesome,
                        contentDescription = "AI Finance Tutor",
                        tint = CyanAccent,
                        modifier = Modifier.size(18.dp)
                    )
                }

                Spacer(modifier = Modifier.width(6.dp))

                // Theme switch
                IconButton(
                    onClick = onToggleTheme,
                    modifier = Modifier
                        .size(36.dp)
                        .background(finColors.cardBackground, CircleShape)
                        .border(1.dp, finColors.cardBorder, CircleShape)
                        .testTag("btn_toggle_theme")
                ) {
                    Icon(
                        imageVector = if (isDark) Icons.Default.LightMode else Icons.Default.DarkMode,
                        contentDescription = "Toggle Theme",
                        tint = finColors.textSecondary,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Branding & Learner Welcome
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Good morning, $learnerName",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = "Continue building your market knowledge.",
                    style = MaterialTheme.typography.bodySmall,
                    color = finColors.textSecondary
                )
            }

            TradeInvestLogo(size = 40.dp)
        }
    }
}

@Composable
fun MetricStatCard(
    modifier: Modifier = Modifier,
    title: String,
    value: String,
    subValue: String? = null,
    deltaText: String? = null,
    isPositive: Boolean? = null,
    icon: ImageVector? = null,
    accentColor: Color = CyanAccent
) {
    val finColors = LocalFinancialColors.current

    Card(
        modifier = modifier
            .border(1.dp, finColors.cardBorder, RoundedCornerShape(14.dp)),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = finColors.cardBackground)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title.uppercase(),
                    style = MaterialTheme.typography.labelSmall,
                    color = finColors.textMuted,
                    letterSpacing = 1.sp,
                    fontWeight = FontWeight.SemiBold
                )
                if (icon != null) {
                    Box(
                        modifier = Modifier
                            .size(26.dp)
                            .background(accentColor.copy(alpha = 0.15f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = icon,
                            contentDescription = null,
                            tint = accentColor,
                            modifier = Modifier.size(14.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = value,
                style = MaterialTheme.typography.titleLarge.copy(fontFamily = FontFamily.Monospace),
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )

            if (subValue != null || deltaText != null) {
                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (deltaText != null) {
                        val pillBg = when (isPositive) {
                            true -> EmeraldBullish.copy(alpha = 0.18f)
                            false -> RoseBearish.copy(alpha = 0.18f)
                            else -> finColors.textMuted.copy(alpha = 0.18f)
                        }
                        val pillColor = when (isPositive) {
                            true -> EmeraldBullish
                            false -> RoseBearish
                            else -> finColors.textMuted
                        }
                        Surface(
                            shape = RoundedCornerShape(4.dp),
                            color = pillBg,
                            modifier = Modifier.padding(end = 6.dp)
                        ) {
                            Text(
                                text = deltaText,
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = pillColor,
                                modifier = Modifier.padding(horizontal = 5.dp, vertical = 2.dp)
                            )
                        }
                    }
                    if (subValue != null) {
                        Text(
                            text = subValue,
                            style = MaterialTheme.typography.labelSmall,
                            color = finColors.textMuted
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SectionHeader(
    title: String,
    subtitle: String? = null,
    actionLabel: String? = null,
    onActionClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val finColors = LocalFinancialColors.current

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            if (subtitle != null) {
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = finColors.textSecondary
                )
            }
        }

        if (actionLabel != null) {
            Text(
                text = actionLabel,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.SemiBold,
                color = CyanAccent,
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .clickable { onActionClick() }
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            )
        }
    }
}

@Composable
fun EducationalDisclaimerCard(
    modifier: Modifier = Modifier,
    text: String = "Educational Example • Simulated Model • Not Personalized Financial Advice"
) {
    val finColors = LocalFinancialColors.current

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .border(1.dp, finColors.cardBorder.copy(alpha = 0.5f), RoundedCornerShape(10.dp)),
        color = finColors.cardBackground.copy(alpha = 0.6f),
        shape = RoundedCornerShape(10.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = null,
                tint = AmberGold,
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = text,
                style = MaterialTheme.typography.labelSmall,
                color = finColors.textSecondary,
                lineHeight = 14.sp
            )
        }
    }
}
