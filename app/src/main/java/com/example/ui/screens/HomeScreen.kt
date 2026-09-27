package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.Asset
import com.example.model.Course
import com.example.model.DailyChallenge
import com.example.ui.components.EducationalDisclaimerCard
import com.example.ui.components.MetricStatCard
import com.example.ui.components.SectionHeader
import com.example.ui.theme.AmberGold
import com.example.ui.theme.CyanAccent
import com.example.ui.theme.CyanAccentDark
import com.example.ui.theme.EmeraldBullish
import com.example.ui.theme.FinancialIconType
import com.example.ui.theme.FinancialVectorIcon
import com.example.ui.theme.IndigoBlue
import com.example.ui.theme.LocalFinancialColors
import com.example.ui.theme.RoseBearish
import java.util.Locale

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    courses: List<Course>,
    assets: List<Asset>,
    dailyChallenge: DailyChallenge,
    onNavigateToCourse: (String) -> Unit,
    onNavigateToAsset: (String) -> Unit,
    onNavigateToRoadmap: () -> Unit,
    onNavigateToLab: (String) -> Unit,
    onNavigateToPaperTrading: () -> Unit,
    onNavigateToAiTutor: () -> Unit
) {
    val finColors = LocalFinancialColors.current

    var challengeSelectedOption by remember { mutableIntStateOf(-1) }
    var challengeSubmitted by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Educational Disclaimer banner
        item {
            EducationalDisclaimerCard(
                text = "Educational Platform • Simulated Market Examples • Created by Awiskar Acharya"
            )
        }

        // Top 4 Metrics Grid (Prompt Section 9)
        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    MetricStatCard(
                        modifier = Modifier.weight(1f),
                        title = "Learning Progress",
                        value = "72%",
                        subValue = "18 of 25 Lessons",
                        accentColor = CyanAccent,
                        icon = Icons.Default.School
                    )
                    MetricStatCard(
                        modifier = Modifier.weight(1f),
                        title = "Practice Streak",
                        value = "14 Days",
                        subValue = "Top 5% Learner",
                        accentColor = AmberGold,
                        icon = Icons.Default.LocalFireDepartment
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    MetricStatCard(
                        modifier = Modifier.weight(1f),
                        title = "Courses Completed",
                        value = "12",
                        subValue = "4 Certificates",
                        accentColor = EmeraldBullish,
                        icon = Icons.Default.EmojiEvents
                    )
                    MetricStatCard(
                        modifier = Modifier.weight(1f),
                        title = "Market Skills",
                        value = "Intermediate",
                        subValue = "2,450 XP",
                        accentColor = IndigoBlue,
                        icon = Icons.Default.TrendingUp
                    )
                }
            }
        }

        // Market Snapshot Carousel (Prompt Section 10)
        item {
            SectionHeader(
                title = "Educational Market Snapshot",
                subtitle = "Informational benchmark prices & trends",
                actionLabel = "All Markets",
                onActionClick = { onNavigateToAsset("SPY") }
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                assets.take(6).forEach { asset ->
                    MarketSnapshotItem(
                        asset = asset,
                        onClick = { onNavigateToAsset(asset.symbol) }
                    )
                }
            }
        }

        // Hero Learning Card (Prompt Section 11)
        item {
            Spacer(modifier = Modifier.height(16.dp))
            Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, finColors.cardBorder, RoundedCornerShape(20.dp)),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = finColors.cardBackground)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                Brush.horizontalGradient(
                                    colors = listOf(
                                        CyanAccent.copy(alpha = 0.08f),
                                        IndigoBlue.copy(alpha = 0.04f)
                                    )
                                )
                            )
                            .padding(18.dp)
                    ) {
                        Column {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Surface(
                                    shape = RoundedCornerShape(6.dp),
                                    color = CyanAccent.copy(alpha = 0.2f)
                                ) {
                                    Text(
                                        text = "CONTINUE LEARNING",
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = CyanAccent,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                    )
                                }

                                Text(
                                    text = "68% Completed",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.SemiBold,
                                    color = CyanAccent
                                )
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            Text(
                                text = "Technical Analysis Fundamentals",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onBackground
                            )

                            Text(
                                text = "Master candlestick anatomy, support/resistance polarity, and volume confirmation.",
                                style = MaterialTheme.typography.bodySmall,
                                color = finColors.textSecondary,
                                modifier = Modifier.padding(top = 4.dp)
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            LinearProgressIndicator(
                                progress = { 0.68f },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(6.dp)
                                    .clip(RoundedCornerShape(3.dp)),
                                color = CyanAccent,
                                trackColor = finColors.cardBorder
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                                Button(
                                    onClick = { onNavigateToCourse("technical_analysis") },
                                    modifier = Modifier.testTag("btn_continue_course"),
                                    shape = RoundedCornerShape(10.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = CyanAccent,
                                        contentColor = Color.Black
                                    )
                                ) {
                                    Icon(imageVector = Icons.Default.PlayArrow, contentDescription = null, modifier = Modifier.size(18.dp))
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text("Continue Lesson", fontWeight = FontWeight.Bold)
                                }

                                OutlinedButton(
                                    onClick = { onNavigateToCourse("technical_analysis") },
                                    shape = RoundedCornerShape(10.dp)
                                ) {
                                    Text("View Course", color = finColors.textSecondary)
                                }
                            }
                        }
                    }
                }
            }
        }

        // Daily Market Lesson Card (Prompt Section 12)
        item {
            Spacer(modifier = Modifier.height(16.dp))
            SectionHeader(
                title = "Today's Market Lesson",
                subtitle = "Bite-sized daily financial concept"
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .border(1.dp, finColors.cardBorder, RoundedCornerShape(16.dp)),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = finColors.cardBackground)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .background(EmeraldBullish.copy(alpha = 0.15f), RoundedCornerShape(12.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        FinancialVectorIcon(
                            iconType = FinancialIconType.BULL_MARKET,
                            tint = EmeraldBullish,
                            size = 28.dp
                        )
                    }

                    Spacer(modifier = Modifier.width(14.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "What Is a Bull Market?",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "Beginner • 4 min read",
                                style = MaterialTheme.typography.labelSmall,
                                color = finColors.textMuted
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "• Objective: Cycle Recognition",
                                style = MaterialTheme.typography.labelSmall,
                                color = CyanAccent
                            )
                        }
                    }

                    Button(
                        onClick = { onNavigateToCourse("foundations") },
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = EmeraldBullish,
                            contentColor = Color.Black
                        )
                    ) {
                        Text("Start", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    }
                }
            }
        }

        // Daily Educational Challenge Widget (Prompt Section 13)
        item {
            Spacer(modifier = Modifier.height(16.dp))
            SectionHeader(
                title = "Daily Challenge",
                subtitle = "Earn +${dailyChallenge.xpReward} XP by solving this market scenario"
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .border(1.dp, AmberGold.copy(alpha = 0.35f), RoundedCornerShape(16.dp)),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = finColors.cardBackground)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = dailyChallenge.title,
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = AmberGold
                        )
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = AmberGold.copy(alpha = 0.15f)
                        ) {
                            Text(
                                text = "+${dailyChallenge.xpReward} XP",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = AmberGold,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = dailyChallenge.scenario,
                        style = MaterialTheme.typography.bodySmall,
                        color = finColors.textSecondary,
                        lineHeight = 18.sp
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = dailyChallenge.question,
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onBackground
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // Options
                    dailyChallenge.options.forEachIndexed { index, option ->
                        val isSelected = challengeSelectedOption == index
                        val isCorrect = index == dailyChallenge.correctIndex
                        val optionBg = when {
                            challengeSubmitted && isCorrect -> EmeraldBullish.copy(alpha = 0.18f)
                            challengeSubmitted && isSelected && !isCorrect -> RoseBearish.copy(alpha = 0.18f)
                            isSelected -> CyanAccent.copy(alpha = 0.15f)
                            else -> finColors.cardBorder.copy(alpha = 0.3f)
                        }
                        val optionBorder = when {
                            challengeSubmitted && isCorrect -> EmeraldBullish
                            challengeSubmitted && isSelected && !isCorrect -> RoseBearish
                            isSelected -> CyanAccent
                            else -> finColors.cardBorder
                        }

                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .border(1.dp, optionBorder, RoundedCornerShape(8.dp))
                                .clickable(enabled = !challengeSubmitted) {
                                    challengeSelectedOption = index
                                },
                            color = optionBg
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(20.dp)
                                        .background(
                                            if (isSelected) CyanAccent else finColors.cardBorder,
                                            CircleShape
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    if (challengeSubmitted && isCorrect) {
                                        Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null, tint = EmeraldBullish, modifier = Modifier.size(16.dp))
                                    } else {
                                        Text(
                                            text = ('A' + index).toString(),
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = if (isSelected) Color.Black else finColors.textSecondary
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.width(10.dp))
                                Text(
                                    text = option,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    if (!challengeSubmitted) {
                        Button(
                            onClick = { challengeSubmitted = true },
                            enabled = challengeSelectedOption != -1,
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = CyanAccent, contentColor = Color.Black)
                        ) {
                            Text("Submit Answer", fontWeight = FontWeight.Bold)
                        }
                    } else {
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = finColors.cardBorder.copy(alpha = 0.5f),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(10.dp)) {
                                Text(
                                    text = if (challengeSelectedOption == dailyChallenge.correctIndex) "✓ Correct! +50 XP" else "Review Explanation:",
                                    fontWeight = FontWeight.Bold,
                                    style = MaterialTheme.typography.labelMedium,
                                    color = if (challengeSelectedOption == dailyChallenge.correctIndex) EmeraldBullish else AmberGold
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = dailyChallenge.explanation,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = finColors.textSecondary
                                )
                            }
                        }
                    }
                }
            }
        }

        // Quick Interactive Labs & Simulators (Prompt Sections 31-40, 50-65)
        item {
            Spacer(modifier = Modifier.height(16.dp))
            SectionHeader(
                title = "Interactive Financial Labs",
                subtitle = "Hands-on visual laboratories"
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                QuickLabCard("Candlestick Lab", "OHLC Sliders & Pattern Detector", FinancialIconType.CANDLESTICK) {
                    onNavigateToLab("candlestick")
                }
                QuickLabCard("DCF Valuation Lab", "Cash Flow & Fair Value Model", FinancialIconType.BALANCE_SHEET) {
                    onNavigateToLab("dcf")
                }
                QuickLabCard("Moving Average Lab", "SMA vs EMA & Golden Cross", FinancialIconType.CHART_LINE) {
                    onNavigateToLab("ma")
                }
                QuickLabCard("Risk / Reward Lab", "1% Rule & Position Sizing", FinancialIconType.RISK_SHIELD) {
                    onNavigateToLab("risk")
                }
                QuickLabCard("Paper Trading", "$100,000 Virtual Simulator", FinancialIconType.PAPER_TRADING) {
                    onNavigateToPaperTrading()
                }
            }
        }

        // Visual Learning Roadmap banner (Prompt Section 14)
        item {
            Spacer(modifier = Modifier.height(16.dp))
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .clickable { onNavigateToRoadmap() }
                    .border(1.dp, IndigoBlue.copy(alpha = 0.4f), RoundedCornerShape(16.dp)),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = finColors.cardBackground)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .background(IndigoBlue.copy(alpha = 0.15f), RoundedCornerShape(12.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        FinancialVectorIcon(iconType = FinancialIconType.ROADMAP, tint = IndigoBlue, size = 26.dp)
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Financial Mastery Roadmap",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Text(
                            text = "Foundations → Technical → Fundamental → Portfolio",
                            style = MaterialTheme.typography.bodySmall,
                            color = finColors.textSecondary
                        )
                    }

                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = "Open Roadmap",
                        tint = CyanAccent,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

@Composable
private fun MarketSnapshotItem(
    asset: Asset,
    onClick: () -> Unit
) {
    val finColors = LocalFinancialColors.current
    val isPositive = asset.changePercent >= 0

    Surface(
        modifier = Modifier
            .width(145.dp)
            .clip(RoundedCornerShape(14.dp))
            .clickable { onClick() }
            .border(1.dp, finColors.cardBorder, RoundedCornerShape(14.dp)),
        color = finColors.cardBackground,
        shape = RoundedCornerShape(14.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = asset.symbol,
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = asset.category.name.take(3),
                    style = MaterialTheme.typography.labelSmall,
                    color = finColors.textMuted
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "$${String.format(Locale.US, "%.2f", asset.currentPrice)}",
                style = MaterialTheme.typography.titleMedium.copy(fontFamily = FontFamily.Monospace),
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(4.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(
                    shape = RoundedCornerShape(4.dp),
                    color = if (isPositive) EmeraldBullish.copy(alpha = 0.18f) else RoseBearish.copy(alpha = 0.18f)
                ) {
                    Text(
                        text = "${if (isPositive) "+" else ""}${String.format(Locale.US, "%.2f", asset.changePercent)}%",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = if (isPositive) EmeraldBullish else RoseBearish,
                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun QuickLabCard(
    title: String,
    subtitle: String,
    iconType: FinancialIconType,
    onClick: () -> Unit
) {
    val finColors = LocalFinancialColors.current

    Surface(
        modifier = Modifier
            .width(170.dp)
            .clip(RoundedCornerShape(14.dp))
            .clickable { onClick() }
            .border(1.dp, finColors.cardBorder, RoundedCornerShape(14.dp)),
        color = finColors.cardBackground,
        shape = RoundedCornerShape(14.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .background(CyanAccent.copy(alpha = 0.12f), RoundedCornerShape(10.dp)),
                contentAlignment = Alignment.Center
            ) {
                FinancialVectorIcon(iconType = iconType, tint = CyanAccent, size = 20.dp)
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = subtitle,
                style = MaterialTheme.typography.labelSmall,
                color = finColors.textSecondary,
                lineHeight = 14.sp
            )
        }
    }
}
