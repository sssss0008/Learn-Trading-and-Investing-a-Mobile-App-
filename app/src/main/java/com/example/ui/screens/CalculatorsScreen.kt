package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.EducationalDisclaimerCard
import com.example.ui.components.SectionHeader
import com.example.ui.theme.AmberGold
import com.example.ui.theme.CyanAccent
import com.example.ui.theme.EmeraldBullish
import com.example.ui.theme.LocalFinancialColors
import java.util.Locale
import kotlin.math.max
import kotlin.math.pow

@Composable
fun CalculatorsScreen(modifier: Modifier = Modifier) {
    val finColors = LocalFinancialColors.current
    var selectedTab by remember { mutableIntStateOf(0) }
    val calcTabs = listOf("Compounding", "CAGR", "Dividend & DRIP", "Inflation", "Risk/Reward")

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        ScrollableTabRow(
            selectedTabIndex = selectedTab,
            containerColor = MaterialTheme.colorScheme.background,
            contentColor = CyanAccent,
            edgePadding = 16.dp,
            indicator = { tabPositions ->
                TabRowDefaults.SecondaryIndicator(
                    Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                    color = CyanAccent,
                    height = 2.5.dp
                )
            }
        ) {
            calcTabs.forEachIndexed { idx, title ->
                Tab(
                    selected = selectedTab == idx,
                    onClick = { selectedTab = idx },
                    text = {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = if (selectedTab == idx) FontWeight.Bold else FontWeight.Medium,
                            color = if (selectedTab == idx) CyanAccent else finColors.textMuted
                        )
                    }
                )
            }
        }

        EducationalDisclaimerCard(
            text = "Financial Calculators are Educational Tools • Returns are Hypothetical"
        )

        when (selectedTab) {
            0 -> CompoundingCalculator()
            1 -> CagrCalculator()
            2 -> DividendCalculator()
            3 -> InflationCalculator()
            4 -> RiskRewardCalculator()
        }
    }
}

@Composable
private fun CompoundingCalculator() {
    val finColors = LocalFinancialColors.current

    var initialDeposit by remember { mutableFloatStateOf(5000f) }
    var monthlyContribution by remember { mutableFloatStateOf(500f) }
    var annualReturn by remember { mutableFloatStateOf(9f) } // %
    var years by remember { mutableFloatStateOf(20f) }

    val r = (annualReturn / 100.0) / 12.0
    val n = (years * 12).toInt()

    val fvInitial = initialDeposit * (1.0 + r).pow(n.toDouble())
    val fvMonthly = if (r > 0) monthlyContribution * (((1.0 + r).pow(n.toDouble()) - 1.0) / r) else (monthlyContribution * n).toDouble()
    val totalFutureValue = fvInitial + fvMonthly
    val totalInvested = initialDeposit + (monthlyContribution * n)
    val totalInterest = max(totalFutureValue - totalInvested, 0.0)

    LazyColumn(modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp)) {
        item {
            Spacer(modifier = Modifier.height(10.dp))
            SectionHeader(title = "Compound Interest Visualizer", subtitle = "See the exponential growth of regular, long-term contributions")

            Card(
                modifier = Modifier.fillMaxWidth().border(1.dp, CyanAccent.copy(alpha = 0.4f), RoundedCornerShape(16.dp)),
                colors = CardDefaults.cardColors(containerColor = finColors.cardBackground)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "TOTAL ESTIMATED FUTURE VALUE", style = MaterialTheme.typography.labelSmall, color = finColors.textMuted, fontWeight = FontWeight.Bold)
                    Text(text = "$${String.format(Locale.US, "%,.2f", totalFutureValue)}", style = MaterialTheme.typography.displayMedium.copy(fontFamily = FontFamily.Monospace), fontWeight = FontWeight.Bold, color = CyanAccent)
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Column {
                            Text(text = "Total Principal Deposited", style = MaterialTheme.typography.labelSmall, color = finColors.textMuted)
                            Text(text = "$${String.format(Locale.US, "%,.2f", totalInvested)}", style = MaterialTheme.typography.titleMedium.copy(fontFamily = FontFamily.Monospace), fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onBackground)
                        }
                        Column(horizontalAlignment = androidx.compose.ui.Alignment.End) {
                            Text(text = "Total Growth / Interest", style = MaterialTheme.typography.labelSmall, color = finColors.textMuted)
                            Text(text = "$${String.format(Locale.US, "%,.2f", totalInterest)}", style = MaterialTheme.typography.titleMedium.copy(fontFamily = FontFamily.Monospace), fontWeight = FontWeight.SemiBold, color = EmeraldBullish)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            SliderRow("Initial Deposit ($)", initialDeposit, 500f, 50000f) { initialDeposit = it }
            SliderRow("Monthly Contribution ($)", monthlyContribution, 50f, 3000f) { monthlyContribution = it }
            SliderRow("Estimated Annual Return (%)", annualReturn, 3f, 15f) { annualReturn = it }
            SliderRow("Investment Duration (Years)", years, 1f, 40f) { years = it }
            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

@Composable
private fun CagrCalculator() {
    val finColors = LocalFinancialColors.current
    var initialVal by remember { mutableFloatStateOf(10000f) }
    var endingVal by remember { mutableFloatStateOf(25000f) }
    var years by remember { mutableFloatStateOf(5f) }

    val cagr = if (initialVal > 0 && years > 0) {
        ((endingVal / initialVal).toDouble().pow(1.0 / years.toDouble()) - 1.0) * 100.0
    } else 0.0

    LazyColumn(modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp)) {
        item {
            Spacer(modifier = Modifier.height(10.dp))
            SectionHeader(title = "CAGR (Compound Annual Growth Rate)", subtitle = "Determine the true geometric annualized rate of return")

            Card(
                modifier = Modifier.fillMaxWidth().border(1.dp, EmeraldBullish.copy(alpha = 0.4f), RoundedCornerShape(16.dp)),
                colors = CardDefaults.cardColors(containerColor = finColors.cardBackground)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "COMPOUND ANNUAL GROWTH RATE", style = MaterialTheme.typography.labelSmall, color = finColors.textMuted, fontWeight = FontWeight.Bold)
                    Text(text = "${String.format(Locale.US, "%.2f", cagr)}% / year", style = MaterialTheme.typography.displayMedium.copy(fontFamily = FontFamily.Monospace), fontWeight = FontWeight.Bold, color = EmeraldBullish)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            SliderRow("Beginning Value ($)", initialVal, 1000f, 100000f) { initialVal = it }
            SliderRow("Ending Value ($)", endingVal, 1000f, 250000f) { endingVal = it }
            SliderRow("Duration (Years)", years, 1f, 30f) { years = it }
            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

@Composable
private fun DividendCalculator() {
    val finColors = LocalFinancialColors.current
    var shareCount by remember { mutableFloatStateOf(200f) }
    var divPerShare by remember { mutableFloatStateOf(3.50f) }

    val annualDiv = shareCount * divPerShare
    val monthlyDiv = annualDiv / 12.0

    LazyColumn(modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp)) {
        item {
            Spacer(modifier = Modifier.height(10.dp))
            SectionHeader(title = "Dividend & Passive Income Calculator", subtitle = "Project cash flow generated from income-producing shares")

            Card(
                modifier = Modifier.fillMaxWidth().border(1.dp, AmberGold.copy(alpha = 0.4f), RoundedCornerShape(16.dp)),
                colors = CardDefaults.cardColors(containerColor = finColors.cardBackground)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "ANNUAL DIVIDEND CASH FLOW", style = MaterialTheme.typography.labelSmall, color = finColors.textMuted, fontWeight = FontWeight.Bold)
                    Text(text = "$${String.format(Locale.US, "%,.2f", annualDiv)}", style = MaterialTheme.typography.displayMedium.copy(fontFamily = FontFamily.Monospace), fontWeight = FontWeight.Bold, color = AmberGold)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = "Equivalent Monthly Income: $${String.format(Locale.US, "%.2f", monthlyDiv)} / mo", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onBackground)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            SliderRow("Number of Shares Owned", shareCount, 10f, 2000f) { shareCount = it }
            SliderRow("Annual Dividend per Share ($)", divPerShare, 0.5f, 15f) { divPerShare = it }
            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

@Composable
private fun InflationCalculator() {
    val finColors = LocalFinancialColors.current
    var currentCash by remember { mutableFloatStateOf(10000f) }
    var inflationRate by remember { mutableFloatStateOf(3.2f) }
    var years by remember { mutableFloatStateOf(15f) }

    val futurePurchasingPower = currentCash / ((1.0 + (inflationRate / 100.0)).pow(years.toDouble()))
    val lossPercentage = ((currentCash - futurePurchasingPower) / currentCash) * 100.0

    LazyColumn(modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp)) {
        item {
            Spacer(modifier = Modifier.height(10.dp))
            SectionHeader(title = "Inflation & Purchasing Power", subtitle = "Calculate how inflation devalues static cash over time")

            Card(
                modifier = Modifier.fillMaxWidth().border(1.dp, CyanAccent.copy(alpha = 0.4f), RoundedCornerShape(16.dp)),
                colors = CardDefaults.cardColors(containerColor = finColors.cardBackground)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "FUTURE PURCHASING POWER", style = MaterialTheme.typography.labelSmall, color = finColors.textMuted, fontWeight = FontWeight.Bold)
                    Text(text = "$${String.format(Locale.US, "%,.2f", futurePurchasingPower)}", style = MaterialTheme.typography.displayMedium.copy(fontFamily = FontFamily.Monospace), fontWeight = FontWeight.Bold, color = CyanAccent)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = "Total Purchasing Power Lost: -${String.format(Locale.US, "%.1f", lossPercentage)}%", style = MaterialTheme.typography.bodyMedium, color = finColors.textSecondary)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            SliderRow("Current Cash Amount ($)", currentCash, 1000f, 100000f) { currentCash = it }
            SliderRow("Average Inflation Rate (%)", inflationRate, 1f, 8f) { inflationRate = it }
            SliderRow("Years in the Future", years, 1f, 30f) { years = it }
            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

@Composable
private fun RiskRewardCalculator() {
    val finColors = LocalFinancialColors.current
    var entry by remember { mutableFloatStateOf(100f) }
    var stop by remember { mutableFloatStateOf(95f) }
    var target by remember { mutableFloatStateOf(115f) }

    val risk = max(entry - stop, 0.1f)
    val reward = max(target - entry, 0.1f)
    val ratio = reward / risk

    LazyColumn(modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp)) {
        item {
            Spacer(modifier = Modifier.height(10.dp))
            SectionHeader(title = "Risk to Reward (R:R) Ratio", subtitle = "Quantify upside potential versus defined downside risk")

            Card(
                modifier = Modifier.fillMaxWidth().border(1.dp, if (ratio >= 2.0) EmeraldBullish else AmberGold, RoundedCornerShape(16.dp)),
                colors = CardDefaults.cardColors(containerColor = finColors.cardBackground)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "RISK / REWARD RATIO", style = MaterialTheme.typography.labelSmall, color = finColors.textMuted, fontWeight = FontWeight.Bold)
                    Text(text = "1 : ${String.format(Locale.US, "%.1f", ratio)}", style = MaterialTheme.typography.displayMedium.copy(fontFamily = FontFamily.Monospace), fontWeight = FontWeight.Bold, color = if (ratio >= 2.0) EmeraldBullish else AmberGold)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = if (ratio >= 2.0) "✓ Favorable: Expected reward exceeds 2x the capital risked." else "⚠️ Caution: High-risk setup. Target at least 2:1 for positive expectancy.", style = MaterialTheme.typography.bodySmall, color = if (ratio >= 2.0) EmeraldBullish else AmberGold)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            SliderRow("Entry Price ($)", entry, 10f, 300f) { entry = it }
            SliderRow("Stop-Loss Price ($)", kotlin.math.min(stop, entry - 1f), 5f, 290f) { stop = it }
            SliderRow("Profit Target Price ($)", kotlin.math.max(target, entry + 1f), 15f, 400f) { target = it }
            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

@Composable
private fun SliderRow(label: String, value: Float, min: Float, max: Float, onChange: (Float) -> Unit) {
    val finColors = LocalFinancialColors.current
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = label, style = MaterialTheme.typography.bodySmall, color = finColors.textSecondary)
            Text(text = String.format(Locale.US, "%.1f", value), style = MaterialTheme.typography.bodySmall.copy(fontFamily = FontFamily.Monospace), fontWeight = FontWeight.Bold)
        }
        Slider(
            value = value,
            onValueChange = onChange,
            valueRange = min..max,
            colors = SliderDefaults.colors(thumbColor = CyanAccent, activeTrackColor = CyanAccent, inactiveTrackColor = finColors.cardBorder)
        )
    }
}
