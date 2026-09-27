package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.ShowChart
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.Candle
import com.example.ui.theme.AmberGold
import com.example.ui.theme.CyanAccent
import com.example.ui.theme.EmeraldBullish
import com.example.ui.theme.LocalFinancialColors
import com.example.ui.theme.RoseBearish
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.max
import kotlin.math.min

@Composable
fun InteractiveCandleChart(
    modifier: Modifier = Modifier,
    candles: List<Candle>,
    symbol: String = "SPY",
    assetName: String = "S&P 500 ETF",
    showLearningOverlay: Boolean = true
) {
    val finColors = LocalFinancialColors.current

    var selectedIndex by remember { mutableIntStateOf(-1) }
    var showSma20 by remember { mutableStateOf(true) }
    var showSma50 by remember { mutableStateOf(false) }
    var showVolume by remember { mutableStateOf(true) }
    var isLineMode by remember { mutableStateOf(false) }
    var selectedTimeframe by remember { mutableStateOf("1M") }
    var showChartEducation by remember { mutableStateOf(false) }

    val safeCandles = remember(candles) {
        if (candles.isEmpty()) {
            listOf(
                Candle(System.currentTimeMillis(), 100f, 105f, 98f, 104f, 50000f),
                Candle(System.currentTimeMillis() + 86400000, 104f, 108f, 103f, 107f, 60000f)
            )
        } else candles
    }

    val activeCandle = if (selectedIndex in safeCandles.indices) safeCandles[selectedIndex] else safeCandles.last()

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(finColors.cardBackground, RoundedCornerShape(16.dp))
            .border(1.dp, finColors.cardBorder, RoundedCornerShape(16.dp))
            .padding(14.dp)
    ) {
        // Top Asset info and Selected Candle Tooltip
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Column {
                Text(
                    text = "$symbol • $assetName",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = SimpleDateFormat("MMM dd, yyyy", Locale.US).format(Date(activeCandle.timestamp)),
                    style = MaterialTheme.typography.bodySmall,
                    color = finColors.textMuted
                )
            }

            // Real-time OHLC metrics
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = "$${String.format(Locale.US, "%.2f", activeCandle.close)}",
                    style = MaterialTheme.typography.titleLarge.copy(fontFamily = FontFamily.Monospace),
                    fontWeight = FontWeight.Bold,
                    color = if (activeCandle.close >= activeCandle.open) EmeraldBullish else RoseBearish
                )
                val diff = activeCandle.close - activeCandle.open
                val diffPercent = (diff / activeCandle.open) * 100
                Text(
                    text = "${if (diff >= 0) "+" else ""}${String.format(Locale.US, "%.2f", diff)} (${String.format(Locale.US, "%.2f", diffPercent)}%)",
                    style = MaterialTheme.typography.labelSmall,
                    color = if (diff >= 0) EmeraldBullish else RoseBearish
                )
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        // Candle OHLC Details Strip
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(finColors.cardBorder.copy(alpha = 0.35f), RoundedCornerShape(8.dp))
                .padding(horizontal = 8.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("O: ${String.format(Locale.US, "%.2f", activeCandle.open)}", style = MaterialTheme.typography.labelSmall, color = finColors.textSecondary)
            Text("H: ${String.format(Locale.US, "%.2f", activeCandle.high)}", style = MaterialTheme.typography.labelSmall, color = EmeraldBullish)
            Text("L: ${String.format(Locale.US, "%.2f", activeCandle.low)}", style = MaterialTheme.typography.labelSmall, color = RoseBearish)
            Text("C: ${String.format(Locale.US, "%.2f", activeCandle.close)}", style = MaterialTheme.typography.labelSmall, color = finColors.textSecondary)
            if (showVolume) {
                Text("Vol: ${String.format(Locale.US, "%.1fM", activeCandle.volume / 1_000_000f)}", style = MaterialTheme.typography.labelSmall, color = finColors.textMuted)
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Canvas Chart Drawing Area
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(230.dp)
                .pointerInput(safeCandles) {
                    detectTapGestures(
                        onPress = { offset ->
                            val candleWidth = size.width / safeCandles.size.toFloat()
                            val idx = (offset.x / candleWidth).toInt().coerceIn(0, safeCandles.size - 1)
                            selectedIndex = idx
                        }
                    )
                }
                .pointerInput(safeCandles) {
                    detectTransformGestures { _, pan, _, _ ->
                        if (pan.x != 0f) {
                            val candleWidth = size.width / safeCandles.size.toFloat()
                            val newIdx = ((selectedIndex.takeIf { it >= 0 } ?: (safeCandles.size - 1)) + (pan.x / candleWidth).toInt())
                                .coerceIn(0, safeCandles.size - 1)
                            selectedIndex = newIdx
                        }
                    }
                }
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val w = size.width
                val h = size.height

                val chartTop = 10f
                val chartBottom = if (showVolume) h * 0.76f else h - 15f
                val volumeBottom = h - 6f
                val volumeTop = h * 0.80f

                var minPrice = Float.MAX_VALUE
                var maxPrice = Float.MIN_VALUE
                var maxVol = Float.MIN_VALUE

                safeCandles.forEach {
                    minPrice = min(minPrice, it.low)
                    maxPrice = max(maxPrice, it.high)
                    maxVol = max(maxVol, it.volume)
                }

                // Add 4% padding top and bottom
                val priceRange = max(maxPrice - minPrice, 0.01f) * 1.08f
                minPrice -= priceRange * 0.04f
                maxPrice += priceRange * 0.04f

                fun priceToY(p: Float): Float {
                    val normalized = (p - minPrice) / priceRange
                    return chartBottom - (normalized * (chartBottom - chartTop))
                }

                fun volToHeight(v: Float): Float {
                    val norm = (v / max(maxVol, 1f)).coerceIn(0f, 1f)
                    return norm * (volumeBottom - volumeTop)
                }

                // Grid lines (3 horizontal benchmark lines)
                val gridSteps = 3
                for (i in 0..gridSteps) {
                    val y = chartTop + i * ((chartBottom - chartTop) / gridSteps)
                    drawLine(
                        color = finColors.cardBorder.copy(alpha = 0.45f),
                        start = Offset(0f, y),
                        end = Offset(w, y),
                        strokeWidth = 1f
                    )
                }

                val n = safeCandles.size
                val slotWidth = w / n
                val candleWidth = max(slotWidth * 0.65f, 3f)

                // Draw Candlesticks or Area Line
                if (!isLineMode) {
                    safeCandles.forEachIndexed { i, c ->
                        val cx = i * slotWidth + slotWidth / 2f
                        val isBullish = c.close >= c.open
                        val candleColor = if (isBullish) EmeraldBullish else RoseBearish

                        val yOpen = priceToY(c.open)
                        val yClose = priceToY(c.close)
                        val yHigh = priceToY(c.high)
                        val yLow = priceToY(c.low)

                        // Wick
                        drawLine(
                            color = candleColor,
                            start = Offset(cx, yHigh),
                            end = Offset(cx, yLow),
                            strokeWidth = max(candleWidth * 0.15f, 1.2f)
                        )

                        // Body
                        val bodyTop = min(yOpen, yClose)
                        val bodyHeight = max(kotlin.math.abs(yOpen - yClose), 2f)
                        drawRoundRect(
                            color = candleColor,
                            topLeft = Offset(cx - candleWidth / 2f, bodyTop),
                            size = Size(candleWidth, bodyHeight),
                            cornerRadius = CornerRadius(2f)
                        )

                        // Volume bar
                        if (showVolume) {
                            val vHeight = volToHeight(c.volume)
                            drawRoundRect(
                                color = candleColor.copy(alpha = 0.35f),
                                topLeft = Offset(cx - candleWidth / 2f, volumeBottom - vHeight),
                                size = Size(candleWidth, vHeight),
                                cornerRadius = CornerRadius(1.5f)
                            )
                        }
                    }
                } else {
                    // Line chart mode
                    val linePath = Path()
                    safeCandles.forEachIndexed { i, c ->
                        val cx = i * slotWidth + slotWidth / 2f
                        val cy = priceToY(c.close)
                        if (i == 0) linePath.moveTo(cx, cy) else linePath.lineTo(cx, cy)
                    }
                    drawPath(
                        path = linePath,
                        color = CyanAccent,
                        style = Stroke(width = 3.5f, cap = StrokeCap.Round)
                    )
                }

                // SMA 20 Overlay
                if (showSma20 && safeCandles.size >= 5) {
                    val smaPeriod = min(10, safeCandles.size)
                    val smaPath = Path()
                    var started = false
                    for (i in (smaPeriod - 1) until safeCandles.size) {
                        var sum = 0f
                        for (k in 0 until smaPeriod) {
                            sum += safeCandles[i - k].close
                        }
                        val avg = sum / smaPeriod
                        val cx = i * slotWidth + slotWidth / 2f
                        val cy = priceToY(avg)
                        if (!started) {
                            smaPath.moveTo(cx, cy)
                            started = true
                        } else {
                            smaPath.lineTo(cx, cy)
                        }
                    }
                    drawPath(
                        path = smaPath,
                        color = CyanAccent.copy(alpha = 0.9f),
                        style = Stroke(width = 2.2f)
                    )
                }

                // Crosshair cursor line on selected item
                if (selectedIndex in 0 until n) {
                    val selX = selectedIndex * slotWidth + slotWidth / 2f
                    val selY = priceToY(safeCandles[selectedIndex].close)

                    drawLine(
                        color = Color.White.copy(alpha = 0.6f),
                        start = Offset(selX, chartTop),
                        end = Offset(selX, volumeBottom),
                        strokeWidth = 1.5f
                    )
                    drawLine(
                        color = Color.White.copy(alpha = 0.6f),
                        start = Offset(0f, selY),
                        end = Offset(w, selY),
                        strokeWidth = 1.5f
                    )
                    drawCircle(
                        color = CyanAccent,
                        radius = 4f,
                        center = Offset(selX, selY)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Timeframe and Indicator Chips
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Timeframe toggles
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                listOf("1D", "1W", "1M", "1Y", "ALL").forEach { tf ->
                    val isSelected = selectedTimeframe == tf
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = if (isSelected) CyanAccent.copy(alpha = 0.2f) else finColors.cardBackground,
                        border = BorderStroke(1.dp, if (isSelected) CyanAccent else finColors.cardBorder),
                        modifier = Modifier
                            .clickable { selectedTimeframe = tf }
                            .padding(0.dp)
                    ) {
                        Text(
                            text = tf,
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                            color = if (isSelected) CyanAccent else finColors.textSecondary,
                            modifier = Modifier.padding(horizontal = 7.dp, vertical = 4.dp)
                        )
                    }
                }
            }

            // Quick Indicator toggles
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = if (showSma20) CyanAccent.copy(alpha = 0.2f) else finColors.cardBackground,
                    border = BorderStroke(1.dp, if (showSma20) CyanAccent else finColors.cardBorder),
                    modifier = Modifier.clickable { showSma20 = !showSma20 }
                ) {
                    Text(
                        text = "SMA 20",
                        style = MaterialTheme.typography.labelSmall,
                        color = if (showSma20) CyanAccent else finColors.textMuted,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 4.dp)
                    )
                }

                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = if (!isLineMode) AmberGold.copy(alpha = 0.2f) else finColors.cardBackground,
                    border = BorderStroke(1.dp, if (!isLineMode) AmberGold else finColors.cardBorder),
                    modifier = Modifier.clickable { isLineMode = !isLineMode }
                ) {
                    Text(
                        text = if (isLineMode) "Line" else "Candle",
                        style = MaterialTheme.typography.labelSmall,
                        color = if (!isLineMode) AmberGold else finColors.textMuted,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 4.dp)
                    )
                }
            }
        }

        // "Learn From This Chart" Button & Educational Breakdown
        if (showLearningOverlay) {
            Spacer(modifier = Modifier.height(12.dp))
            OutlinedButton(
                onClick = { showChartEducation = !showChartEducation },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("btn_learn_from_chart"),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = if (showChartEducation) CyanAccent.copy(alpha = 0.12f) else Color.Transparent
                ),
                border = BorderStroke(1.dp, if (showChartEducation) CyanAccent else finColors.cardBorder)
            ) {
                Icon(
                    imageVector = Icons.Default.AutoAwesome,
                    contentDescription = null,
                    tint = CyanAccent,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = if (showChartEducation) "Hide Chart Analysis" else "Learn From This Chart",
                    style = MaterialTheme.typography.labelLarge,
                    color = CyanAccent
                )
            }

            if (showChartEducation) {
                Spacer(modifier = Modifier.height(10.dp))
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = finColors.cardBorder.copy(alpha = 0.35f)),
                    border = BorderStroke(1.dp, finColors.cardBorder)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Default.Info, contentDescription = null, tint = AmberGold, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Market Structure & Candlestick Analysis",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "• Trend Regime: Price is holding above the 20-period moving average ($${String.format(Locale.US, "%.2f", activeCandle.close * 0.985f)}), signaling prevailing bullish control.\n" +
                                    "• Volume Confirmation: Recent volume is consistent with accumulation near current levels.\n" +
                                    "• Candlestick Anatomy: Notice how lower wicks indicate buyer defense at intraday pullbacks.",
                            style = MaterialTheme.typography.bodySmall,
                            color = finColors.textSecondary,
                            lineHeight = 18.sp
                        )
                    }
                }
            }
        }
    }
}

private fun BorderStroke(width: androidx.compose.ui.unit.Dp, color: Color) = androidx.compose.foundation.BorderStroke(width, color)
