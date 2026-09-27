package com.example.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.EducationalDisclaimerCard
import com.example.ui.components.SectionHeader
import com.example.ui.theme.AmberGold
import com.example.ui.theme.CyanAccent
import com.example.ui.theme.EmeraldBullish
import com.example.ui.theme.IndigoBlue
import com.example.ui.theme.LocalFinancialColors
import com.example.ui.theme.RoseBearish
import java.util.Locale
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min
import kotlin.math.pow

@Composable
fun InteractiveLabsScreen(
    modifier: Modifier = Modifier,
    initialLab: String = "candlestick",
    onBack: (() -> Unit)? = null
) {
    val finColors = LocalFinancialColors.current
    var selectedLabIndex by remember {
        mutableIntStateOf(
            when (initialLab) {
                "dcf" -> 1
                "ma" -> 2
                "rsi" -> 3
                "risk" -> 4
                else -> 0
            }
        )
    }

    val labs = listOf("Candlestick Lab", "DCF Valuation", "Moving Averages", "RSI Momentum", "Position Sizing")

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        if (onBack != null) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack) {
                    Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = CyanAccent)
                }
                Text("Back to Dashboard", style = MaterialTheme.typography.titleSmall, color = CyanAccent)
            }
        }

        ScrollableTabRow(
            selectedTabIndex = selectedLabIndex,
            containerColor = MaterialTheme.colorScheme.background,
            contentColor = CyanAccent,
            edgePadding = 16.dp,
            indicator = { tabPositions ->
                TabRowDefaults.SecondaryIndicator(
                    Modifier.tabIndicatorOffset(tabPositions[selectedLabIndex]),
                    color = CyanAccent,
                    height = 2.5.dp
                )
            }
        ) {
            labs.forEachIndexed { idx, title ->
                Tab(
                    selected = selectedLabIndex == idx,
                    onClick = { selectedLabIndex = idx },
                    text = {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = if (selectedLabIndex == idx) FontWeight.Bold else FontWeight.Normal,
                            color = if (selectedLabIndex == idx) CyanAccent else finColors.textMuted
                        )
                    }
                )
            }
        }

        EducationalDisclaimerCard(
            text = "Interactive Laboratory • Manipulate Parameters to Observe Market Mechanics"
        )

        when (selectedLabIndex) {
            0 -> CandlestickLab()
            1 -> DcfValuationLab()
            2 -> MovingAverageLab()
            3 -> RsiMomentumLab()
            4 -> PositionSizingLab()
        }
    }
}

@Composable
private fun CandlestickLab() {
    val finColors = LocalFinancialColors.current

    var openVal by remember { mutableFloatStateOf(100f) }
    var highVal by remember { mutableFloatStateOf(120f) }
    var lowVal by remember { mutableFloatStateOf(85f) }
    var closeVal by remember { mutableFloatStateOf(115f) }

    // Ensure valid bounds
    val safeHigh = max(highVal, max(openVal, closeVal))
    val safeLow = min(lowVal, min(openVal, closeVal))

    val isBullish = closeVal >= openVal
    val bodyHeight = abs(closeVal - openVal)
    val upperWick = safeHigh - max(openVal, closeVal)
    val lowerWick = min(openVal, closeVal) - safeLow
    val totalRange = max(safeHigh - safeLow, 0.1f)

    // Identify Pattern
    val detectedPattern = when {
        bodyHeight / totalRange < 0.08f -> "Doji (Indecision / Equilibrium)"
        isBullish && lowerWick >= bodyHeight * 2f && upperWick <= bodyHeight * 0.5f -> "Bullish Hammer (Buyer Rejection at Lows)"
        !isBullish && upperWick >= bodyHeight * 2f && lowerWick <= bodyHeight * 0.5f -> "Shooting Star (Bearish Rejection at Highs)"
        isBullish && bodyHeight / totalRange > 0.85f -> "Bullish Marubozu (Total Buyer Dominance)"
        !isBullish && bodyHeight / totalRange > 0.85f -> "Bearish Marubozu (Total Seller Dominance)"
        isBullish -> "Standard Bullish Candle"
        else -> "Standard Bearish Candle"
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(10.dp))
            SectionHeader(
                title = "Interactive Candlestick Laboratory",
                subtitle = "Drag the OHLC sliders to observe live candle construction & patterns"
            )

            // Visual Candle Canvas Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
                    .border(1.dp, finColors.cardBorder, RoundedCornerShape(16.dp)),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = finColors.cardBackground)
            ) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Canvas(modifier = Modifier.size(160.dp, 200.dp)) {
                        val w = size.width
                        val h = size.height

                        val candleColor = if (isBullish) EmeraldBullish else RoseBearish

                        fun pToY(p: Float): Float {
                            val norm = (p - 70f) / 70f
                            return h - (norm.coerceIn(0f, 1f) * h)
                        }

                        val cx = w / 2f
                        val yH = pToY(safeHigh)
                        val yL = pToY(safeLow)
                        val yO = pToY(openVal)
                        val yC = pToY(closeVal)

                        // Wick
                        drawLine(
                            color = candleColor,
                            start = Offset(cx, yH),
                            end = Offset(cx, yL),
                            strokeWidth = 4f,
                            cap = StrokeCap.Round
                        )

                        // Body
                        val bTop = min(yO, yC)
                        val bH = max(abs(yO - yC), 4f)
                        drawRoundRect(
                            color = candleColor,
                            topLeft = Offset(cx - 30f, bTop),
                            size = Size(60f, bH),
                            cornerRadius = CornerRadius(4f)
                        )
                    }

                    // Badge top right
                    Surface(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(12.dp),
                        shape = RoundedCornerShape(8.dp),
                        color = (if (isBullish) EmeraldBullish else RoseBearish).copy(alpha = 0.18f)
                    ) {
                        Text(
                            text = if (isBullish) "BULLISH (Close ≥ Open)" else "BEARISH (Close < Open)",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = if (isBullish) EmeraldBullish else RoseBearish,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Identified Pattern Result Box
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = CyanAccent.copy(alpha = 0.12f),
                border = androidx.compose.foundation.BorderStroke(1.dp, CyanAccent.copy(alpha = 0.4f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = "DETECTED PATTERN:",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = CyanAccent
                    )
                    Text(
                        text = detectedPattern,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Slider Controls
            CandleSliderControl("Open Price", openVal, 75f, 135f) { openVal = it }
            CandleSliderControl("High Price", safeHigh, 80f, 140f) { highVal = it }
            CandleSliderControl("Low Price", safeLow, 70f, 130f) { lowVal = it }
            CandleSliderControl("Close Price", closeVal, 75f, 135f) { closeVal = it }

            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

@Composable
private fun CandleSliderControl(
    label: String,
    value: Float,
    min: Float,
    max: Float,
    onValueChange: (Float) -> Unit
) {
    val finColors = LocalFinancialColors.current
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = label, style = MaterialTheme.typography.bodySmall, color = finColors.textSecondary)
            Text(text = "$${String.format(Locale.US, "%.1f", value)}", style = MaterialTheme.typography.bodySmall.copy(fontFamily = FontFamily.Monospace), fontWeight = FontWeight.Bold)
        }
        Slider(
            value = value,
            onValueChange = onValueChange,
            valueRange = min..max,
            colors = SliderDefaults.colors(
                thumbColor = CyanAccent,
                activeTrackColor = CyanAccent,
                inactiveTrackColor = finColors.cardBorder
            )
        )
    }
}

@Composable
private fun DcfValuationLab() {
    val finColors = LocalFinancialColors.current

    var baseFcf by remember { mutableFloatStateOf(10f) } // in Billions
    var growthRate by remember { mutableFloatStateOf(12f) } // %
    var discountRate by remember { mutableFloatStateOf(9f) } // WACC %
    var terminalRate by remember { mutableFloatStateOf(2.5f) } // %
    var sharesOutstanding by remember { mutableFloatStateOf(1.5f) } // Billions of shares

    // DCF Calculation
    val g = growthRate / 100.0
    val r = discountRate / 100.0
    val t = terminalRate / 100.0

    var pvFcfSum = 0.0
    var currentFcf = baseFcf.toDouble()
    for (year in 1..5) {
        currentFcf *= (1.0 + g)
        val discounted = currentFcf / ((1.0 + r).pow(year.toDouble()))
        pvFcfSum += discounted
    }

    // Terminal Value
    val year5Fcf = currentFcf
    val terminalValue = (year5Fcf * (1.0 + t)) / max(r - t, 0.005)
    val pvTerminalValue = terminalValue / ((1.0 + r).pow(5.0))

    val enterpriseValue = pvFcfSum + pvTerminalValue
    val fairValuePerShare = enterpriseValue / sharesOutstanding

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(10.dp))
            SectionHeader(
                title = "Discounted Cash Flow (DCF) Model",
                subtitle = "Institutional intrinsic value estimation based on projected cash flows"
            )

            // Result Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, CyanAccent.copy(alpha = 0.4f), RoundedCornerShape(16.dp)),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = finColors.cardBackground)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "ESTIMATED FAIR VALUE PER SHARE",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = finColors.textMuted
                    )
                    Text(
                        text = "$${String.format(Locale.US, "%.2f", fairValuePerShare)}",
                        style = MaterialTheme.typography.displayMedium.copy(fontFamily = FontFamily.Monospace),
                        fontWeight = FontWeight.Bold,
                        color = CyanAccent
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Enterprise Value: $${String.format(Locale.US, "%.1f", enterpriseValue)}B (PV of 5-Yr Cash: $${String.format(Locale.US, "%.1f", pvFcfSum)}B + PV of Terminal Value: $${String.format(Locale.US, "%.1f", pvTerminalValue)}B)",
                        style = MaterialTheme.typography.bodySmall,
                        color = finColors.textSecondary
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Parameter Sliders
            CandleSliderControl("Current Free Cash Flow (FCF)", baseFcf, 1f, 50f) { baseFcf = it }
            CandleSliderControl("5-Year Annual FCF Growth Rate (%)", growthRate, 2f, 35f) { growthRate = it }
            CandleSliderControl("Discount Rate WACC (%)", discountRate, 6f, 15f) { discountRate = it }
            CandleSliderControl("Terminal Growth Rate (%)", terminalRate, 1f, 4f) { terminalRate = it }

            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

@Composable
private fun MovingAverageLab() {
    val finColors = LocalFinancialColors.current
    var fastPeriod by remember { mutableFloatStateOf(20f) }
    var slowPeriod by remember { mutableFloatStateOf(50f) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(10.dp))
            SectionHeader(
                title = "Moving Average & Crossover Dynamics",
                subtitle = "Study lag, smoothing, and Golden Cross / Death Cross signals"
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, finColors.cardBorder, RoundedCornerShape(16.dp)),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = finColors.cardBackground)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Fast Period: ${fastPeriod.toInt()} • Slow Period: ${slowPeriod.toInt()}",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "• Golden Cross: When the shorter moving average (${fastPeriod.toInt()}) crosses above the slower average (${slowPeriod.toInt()}), signaling increasing medium-term bullish momentum.\n" +
                                "• Death Cross: When the fast average crosses below the slow average, confirming bearish regime breakdown.",
                        style = MaterialTheme.typography.bodySmall,
                        color = finColors.textSecondary,
                        lineHeight = 20.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            CandleSliderControl("Fast Moving Average Period", fastPeriod, 5f, 50f) { fastPeriod = it }
            CandleSliderControl("Slow Moving Average Period", slowPeriod, 50f, 200f) { slowPeriod = it }

            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

@Composable
private fun RsiMomentumLab() {
    val finColors = LocalFinancialColors.current
    var rsiVal by remember { mutableFloatStateOf(65f) }

    val zone = when {
        rsiVal >= 70 -> "Overbought (> 70)"
        rsiVal <= 30 -> "Oversold (< 30)"
        else -> "Neutral Zone (30 - 70)"
    }
    val zoneColor = when {
        rsiVal >= 70 -> RoseBearish
        rsiVal <= 30 -> EmeraldBullish
        else -> CyanAccent
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(10.dp))
            SectionHeader(
                title = "RSI Momentum & Mean Reversion Lab",
                subtitle = "Relative Strength Index velocity and divergence concepts"
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, zoneColor.copy(alpha = 0.4f), RoundedCornerShape(16.dp)),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = finColors.cardBackground)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "CURRENT RSI VALUE",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = finColors.textMuted
                    )
                    Text(
                        text = "${rsiVal.toInt()}",
                        style = MaterialTheme.typography.displayMedium.copy(fontFamily = FontFamily.Monospace),
                        fontWeight = FontWeight.Bold,
                        color = zoneColor
                    )
                    Text(
                        text = "Regime: $zone",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.SemiBold,
                        color = zoneColor
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "⚠️ Educational Rule: An overbought RSI does not mean 'sell immediately'. In strong uptrends, RSI can remain pegged above 70 for weeks while prices continue surging.",
                        style = MaterialTheme.typography.bodySmall,
                        color = finColors.textSecondary
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            CandleSliderControl("RSI Indicator Level", rsiVal, 5f, 95f) { rsiVal = it }

            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

@Composable
private fun PositionSizingLab() {
    val finColors = LocalFinancialColors.current

    var accountEquity by remember { mutableFloatStateOf(50000f) }
    var riskPercent by remember { mutableFloatStateOf(1f) }
    var entryPrice by remember { mutableFloatStateOf(100f) }
    var stopPrice by remember { mutableFloatStateOf(95f) }

    val dollarRisk = accountEquity * (riskPercent / 100f)
    val priceRisk = max(entryPrice - stopPrice, 0.1f)
    val maxShares = (dollarRisk / priceRisk).toInt()
    val totalCost = maxShares * entryPrice

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(10.dp))
            SectionHeader(
                title = "Position Sizing & 1% Capital Protection",
                subtitle = "Calculate exact share allocation based on mathematical risk tolerance"
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, EmeraldBullish.copy(alpha = 0.4f), RoundedCornerShape(16.dp)),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = finColors.cardBackground)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "RECOMMENDED POSITION SIZE",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = finColors.textMuted
                    )
                    Text(
                        text = "$maxShares Shares",
                        style = MaterialTheme.typography.displayMedium.copy(fontFamily = FontFamily.Monospace),
                        fontWeight = FontWeight.Bold,
                        color = EmeraldBullish
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Total Investment: $${String.format(Locale.US, "%,.2f", totalCost)} • Max Risk Loss: $${String.format(Locale.US, "%,.2f", dollarRisk)}",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            CandleSliderControl("Total Account Size ($)", accountEquity, 5000f, 200000f) { accountEquity = it }
            CandleSliderControl("Max Risk per Trade (%)", riskPercent, 0.5f, 3f) { riskPercent = it }
            CandleSliderControl("Target Entry Price ($)", entryPrice, 10f, 500f) { entryPrice = it }
            CandleSliderControl("Stop-Loss Price ($)", min(stopPrice, entryPrice - 1f), 5f, 490f) { stopPrice = it }

            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}
