package com.example.ui.theme

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Original TradeInvest Academy Vector Logo:
 * Geometric combination of Rising Trend Graph, Abstract 'T' (crossbar + pillar),
 * Abstract 'I' (pillar), and Learning Cap/Quill upward arrow node.
 */
@Composable
fun TradeInvestLogo(
    modifier: Modifier = Modifier,
    size: Dp = 36.dp,
    showText: Boolean = false,
    textColor: Color = Color.Unspecified
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(size)
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(Color(0xFF0F2B3E), Color(0xFF091422))
                    ),
                    shape = RoundedCornerShape(size * 0.25f)
                )
                .padding(size * 0.12f),
            contentAlignment = Alignment.Center
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val w = this.size.width
                val h = this.size.height

                // Rising Trend Base Beam (Cyan to Emerald)
                val trendPath = Path().apply {
                    moveTo(w * 0.15f, h * 0.85f)
                    lineTo(w * 0.45f, h * 0.55f)
                    lineTo(w * 0.65f, h * 0.68f)
                    lineTo(w * 0.90f, h * 0.20f)
                }
                drawPath(
                    path = trendPath,
                    color = CyanAccent,
                    style = Stroke(width = w * 0.14f, cap = StrokeCap.Round, join = StrokeJoin.Round)
                )

                // Abstract "T" Crossbar & Vertical Stem
                drawLine(
                    color = Color.White,
                    start = Offset(w * 0.20f, h * 0.26f),
                    end = Offset(w * 0.58f, h * 0.26f),
                    strokeWidth = w * 0.12f,
                    cap = StrokeCap.Round
                )
                drawLine(
                    color = Color.White,
                    start = Offset(w * 0.39f, h * 0.26f),
                    end = Offset(w * 0.39f, h * 0.65f),
                    strokeWidth = w * 0.12f,
                    cap = StrokeCap.Round
                )

                // Abstract "I" Stem (Emerald)
                drawLine(
                    color = EmeraldBullish,
                    start = Offset(w * 0.72f, h * 0.38f),
                    end = Offset(w * 0.72f, h * 0.82f),
                    strokeWidth = w * 0.12f,
                    cap = StrokeCap.Round
                )

                // Learning Node (Upward Peak / Diamond Node at top right)
                drawCircle(
                    color = AmberGold,
                    radius = w * 0.11f,
                    center = Offset(w * 0.90f, h * 0.20f)
                )
            }
        }

        if (showText) {
            Spacer(modifier = Modifier.width(10.dp))
            androidx.compose.foundation.layout.Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Trade",
                        fontWeight = FontWeight.Bold,
                        fontSize = (size.value * 0.44f).sp,
                        color = if (textColor != Color.Unspecified) textColor else Color.White
                    )
                    Text(
                        text = "Invest",
                        fontWeight = FontWeight.Bold,
                        fontSize = (size.value * 0.44f).sp,
                        color = CyanAccent
                    )
                }
                Text(
                    text = "ACADEMY",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = (size.value * 0.24f).sp,
                    letterSpacing = 2.sp,
                    color = AmberGold
                )
            }
        }
    }
}

/**
 * Custom Vector Financial Icons
 */
@Composable
fun FinancialVectorIcon(
    iconType: FinancialIconType,
    modifier: Modifier = Modifier,
    tint: Color = CyanAccent,
    size: Dp = 24.dp
) {
    Canvas(modifier = modifier.size(size)) {
        val w = this.size.width
        val h = this.size.height
        when (iconType) {
            FinancialIconType.CANDLESTICK -> drawCandlestickIcon(w, h, tint)
            FinancialIconType.CHART_LINE -> drawChartLineIcon(w, h, tint)
            FinancialIconType.STOCKS -> drawStockIcon(w, h, tint)
            FinancialIconType.ETF -> drawEtfIcon(w, h, tint)
            FinancialIconType.BONDS -> drawBondIcon(w, h, tint)
            FinancialIconType.CRYPTO -> drawCryptoIcon(w, h, tint)
            FinancialIconType.FOREX -> drawForexIcon(w, h, tint)
            FinancialIconType.COMMODITIES -> drawCommoditiesIcon(w, h, tint)
            FinancialIconType.PORTFOLIO -> drawPortfolioIcon(w, h, tint)
            FinancialIconType.CALCULATOR -> drawCalculatorIcon(w, h, tint)
            FinancialIconType.BALANCE_SHEET -> drawBalanceSheetIcon(w, h, tint)
            FinancialIconType.INCOME_STATEMENT -> drawIncomeStatementIcon(w, h, tint)
            FinancialIconType.CASH_FLOW -> drawCashFlowIcon(w, h, tint)
            FinancialIconType.RISK_SHIELD -> drawRiskShieldIcon(w, h, tint)
            FinancialIconType.COMPOUND_GROWTH -> drawCompoundGrowthIcon(w, h, tint)
            FinancialIconType.DIVIDEND -> drawDividendIcon(w, h, tint)
            FinancialIconType.PAPER_TRADING -> drawPaperTradingIcon(w, h, tint)
            FinancialIconType.AI_TUTOR -> drawAiTutorIcon(w, h, tint)
            FinancialIconType.JOURNAL -> drawJournalIcon(w, h, tint)
            FinancialIconType.CERTIFICATE -> drawCertificateIcon(w, h, tint)
            FinancialIconType.ROADMAP -> drawRoadmapIcon(w, h, tint)
            FinancialIconType.FLASHCARDS -> drawFlashcardsIcon(w, h, tint)
            FinancialIconType.BULL_MARKET -> drawBullIcon(w, h, tint)
            FinancialIconType.BEAR_MARKET -> drawBearIcon(w, h, tint)
        }
    }
}

enum class FinancialIconType {
    CANDLESTICK,
    CHART_LINE,
    STOCKS,
    ETF,
    BONDS,
    CRYPTO,
    FOREX,
    COMMODITIES,
    PORTFOLIO,
    CALCULATOR,
    BALANCE_SHEET,
    INCOME_STATEMENT,
    CASH_FLOW,
    RISK_SHIELD,
    COMPOUND_GROWTH,
    DIVIDEND,
    PAPER_TRADING,
    AI_TUTOR,
    JOURNAL,
    CERTIFICATE,
    ROADMAP,
    FLASHCARDS,
    BULL_MARKET,
    BEAR_MARKET
}

private fun DrawScope.drawCandlestickIcon(w: Float, h: Float, color: Color) {
    val sw = w * 0.08f
    // Candle 1 (Bullish green)
    drawLine(color = color, start = Offset(w * 0.32f, h * 0.15f), end = Offset(w * 0.32f, h * 0.85f), strokeWidth = sw, cap = StrokeCap.Round)
    drawRoundRect(
        color = color,
        topLeft = Offset(w * 0.20f, h * 0.35f),
        size = Size(w * 0.24f, h * 0.35f),
        cornerRadius = CornerRadius(w * 0.04f)
    )
    // Candle 2 (Bearish/offset)
    val secondColor = color.copy(alpha = 0.85f)
    drawLine(color = secondColor, start = Offset(w * 0.68f, h * 0.25f), end = Offset(w * 0.68f, h * 0.90f), strokeWidth = sw, cap = StrokeCap.Round)
    drawRoundRect(
        color = secondColor,
        topLeft = Offset(w * 0.56f, h * 0.40f),
        size = Size(w * 0.24f, h * 0.32f),
        cornerRadius = CornerRadius(w * 0.04f)
    )
}

private fun DrawScope.drawChartLineIcon(w: Float, h: Float, color: Color) {
    val p = Path().apply {
        moveTo(w * 0.15f, h * 0.78f)
        lineTo(w * 0.40f, h * 0.52f)
        lineTo(w * 0.62f, h * 0.64f)
        lineTo(w * 0.88f, h * 0.22f)
    }
    drawPath(path = p, color = color, style = Stroke(width = w * 0.11f, cap = StrokeCap.Round, join = StrokeJoin.Round))
    drawCircle(color = color, radius = w * 0.08f, center = Offset(w * 0.88f, h * 0.22f))
    // Bottom baseline
    drawLine(color = color.copy(alpha = 0.4f), start = Offset(w * 0.12f, h * 0.88f), end = Offset(w * 0.88f, h * 0.88f), strokeWidth = w * 0.06f, cap = StrokeCap.Round)
}

private fun DrawScope.drawStockIcon(w: Float, h: Float, color: Color) {
    drawRoundRect(
        color = color,
        topLeft = Offset(w * 0.16f, h * 0.60f),
        size = Size(w * 0.18f, h * 0.28f),
        cornerRadius = CornerRadius(w * 0.03f)
    )
    drawRoundRect(
        color = color,
        topLeft = Offset(w * 0.41f, h * 0.42f),
        size = Size(w * 0.18f, h * 0.46f),
        cornerRadius = CornerRadius(w * 0.03f)
    )
    drawRoundRect(
        color = color,
        topLeft = Offset(w * 0.66f, h * 0.22f),
        size = Size(w * 0.18f, h * 0.66f),
        cornerRadius = CornerRadius(w * 0.03f)
    )
}

private fun DrawScope.drawEtfIcon(w: Float, h: Float, color: Color) {
    // 3 overlapping index layers
    val sw = w * 0.08f
    drawRoundRect(color = color.copy(alpha = 0.5f), topLeft = Offset(w * 0.20f, h * 0.18f), size = Size(w * 0.60f, h * 0.20f), cornerRadius = CornerRadius(w * 0.04f))
    drawRoundRect(color = color.copy(alpha = 0.75f), topLeft = Offset(w * 0.20f, h * 0.44f), size = Size(w * 0.60f, h * 0.20f), cornerRadius = CornerRadius(w * 0.04f))
    drawRoundRect(color = color, topLeft = Offset(w * 0.20f, h * 0.70f), size = Size(w * 0.60f, h * 0.20f), cornerRadius = CornerRadius(w * 0.04f))
}

private fun DrawScope.drawBondIcon(w: Float, h: Float, color: Color) {
    drawRoundRect(
        color = color,
        topLeft = Offset(w * 0.15f, h * 0.20f),
        size = Size(w * 0.70f, h * 0.60f),
        cornerRadius = CornerRadius(w * 0.08f),
        style = Stroke(width = w * 0.09f)
    )
    // Certificate ribbon lines
    drawLine(color = color, start = Offset(w * 0.28f, h * 0.42f), end = Offset(w * 0.72f, h * 0.42f), strokeWidth = w * 0.07f, cap = StrokeCap.Round)
    drawLine(color = color, start = Offset(w * 0.28f, h * 0.58f), end = Offset(w * 0.56f, h * 0.58f), strokeWidth = w * 0.07f, cap = StrokeCap.Round)
}

private fun DrawScope.drawCryptoIcon(w: Float, h: Float, color: Color) {
    drawCircle(color = color, radius = w * 0.38f, center = Offset(w * 0.50f, h * 0.50f), style = Stroke(width = w * 0.09f))
    // Abstract decentralized block/nodes
    drawCircle(color = color, radius = w * 0.08f, center = Offset(w * 0.50f, h * 0.32f))
    drawCircle(color = color, radius = w * 0.08f, center = Offset(w * 0.35f, h * 0.60f))
    drawCircle(color = color, radius = w * 0.08f, center = Offset(w * 0.65f, h * 0.60f))
    drawLine(color = color, start = Offset(w * 0.50f, h * 0.32f), end = Offset(w * 0.35f, h * 0.60f), strokeWidth = w * 0.05f)
    drawLine(color = color, start = Offset(w * 0.50f, h * 0.32f), end = Offset(w * 0.65f, h * 0.60f), strokeWidth = w * 0.05f)
    drawLine(color = color, start = Offset(w * 0.35f, h * 0.60f), end = Offset(w * 0.65f, h * 0.60f), strokeWidth = w * 0.05f)
}

private fun DrawScope.drawForexIcon(w: Float, h: Float, color: Color) {
    // Two circular exchange arrows
    val p1 = Path().apply {
        moveTo(w * 0.25f, h * 0.45f)
        lineTo(w * 0.70f, h * 0.45f)
        lineTo(w * 0.55f, h * 0.30f)
    }
    drawPath(path = p1, color = color, style = Stroke(width = w * 0.09f, cap = StrokeCap.Round, join = StrokeJoin.Round))
    val p2 = Path().apply {
        moveTo(w * 0.75f, h * 0.60f)
        lineTo(w * 0.30f, h * 0.60f)
        lineTo(w * 0.45f, h * 0.75f)
    }
    drawPath(path = p2, color = color.copy(alpha = 0.85f), style = Stroke(width = w * 0.09f, cap = StrokeCap.Round, join = StrokeJoin.Round))
}

private fun DrawScope.drawCommoditiesIcon(w: Float, h: Float, color: Color) {
    // Gold Ingot / Barrel representation
    val p = Path().apply {
        moveTo(w * 0.25f, h * 0.35f)
        lineTo(w * 0.75f, h * 0.35f)
        lineTo(w * 0.88f, h * 0.75f)
        lineTo(w * 0.12f, h * 0.75f)
        close()
    }
    drawPath(path = p, color = color, style = Stroke(width = w * 0.09f, join = StrokeJoin.Round))
    drawLine(color = color, start = Offset(w * 0.32f, h * 0.55f), end = Offset(w * 0.68f, h * 0.55f), strokeWidth = w * 0.07f, cap = StrokeCap.Round)
}

private fun DrawScope.drawPortfolioIcon(w: Float, h: Float, color: Color) {
    // Briefcase + pie chart slice
    drawRoundRect(
        color = color,
        topLeft = Offset(w * 0.15f, h * 0.32f),
        size = Size(w * 0.70f, h * 0.54f),
        cornerRadius = CornerRadius(w * 0.08f),
        style = Stroke(width = w * 0.09f)
    )
    // Handle
    val hp = Path().apply {
        moveTo(w * 0.36f, h * 0.32f)
        lineTo(w * 0.36f, h * 0.18f)
        lineTo(w * 0.64f, h * 0.18f)
        lineTo(w * 0.64f, h * 0.32f)
    }
    drawPath(path = hp, color = color, style = Stroke(width = w * 0.08f, cap = StrokeCap.Round, join = StrokeJoin.Round))
    // Center lock/shield
    drawCircle(color = color, radius = w * 0.08f, center = Offset(w * 0.50f, h * 0.56f))
}

private fun DrawScope.drawCalculatorIcon(w: Float, h: Float, color: Color) {
    drawRoundRect(
        color = color,
        topLeft = Offset(w * 0.20f, h * 0.15f),
        size = Size(w * 0.60f, h * 0.72f),
        cornerRadius = CornerRadius(w * 0.08f),
        style = Stroke(width = w * 0.08f)
    )
    // Screen
    drawRoundRect(
        color = color.copy(alpha = 0.35f),
        topLeft = Offset(w * 0.28f, h * 0.24f),
        size = Size(w * 0.44f, h * 0.18f),
        cornerRadius = CornerRadius(w * 0.03f)
    )
    // Keypad dots
    for (row in 0..2) {
        for (col in 0..2) {
            drawCircle(
                color = color,
                radius = w * 0.04f,
                center = Offset(w * (0.34f + col * 0.16f), h * (0.52f + row * 0.13f))
            )
        }
    }
}

private fun DrawScope.drawBalanceSheetIcon(w: Float, h: Float, color: Color) {
    // Balanced Scale
    drawLine(color = color, start = Offset(w * 0.50f, h * 0.20f), end = Offset(w * 0.50f, h * 0.85f), strokeWidth = w * 0.08f, cap = StrokeCap.Round)
    drawLine(color = color, start = Offset(w * 0.20f, h * 0.32f), end = Offset(w * 0.80f, h * 0.32f), strokeWidth = w * 0.08f, cap = StrokeCap.Round)
    // Left Pan
    drawLine(color = color.copy(alpha = 0.7f), start = Offset(w * 0.20f, h * 0.32f), end = Offset(w * 0.15f, h * 0.55f), strokeWidth = w * 0.05f)
    drawLine(color = color.copy(alpha = 0.7f), start = Offset(w * 0.20f, h * 0.32f), end = Offset(w * 0.32f, h * 0.55f), strokeWidth = w * 0.05f)
    drawArc(color = color, startAngle = 0f, sweepAngle = 180f, useCenter = false, topLeft = Offset(w * 0.12f, h * 0.50f), size = Size(w * 0.22f, h * 0.14f), style = Stroke(width = w * 0.06f))
    // Right Pan
    drawLine(color = color.copy(alpha = 0.7f), start = Offset(w * 0.80f, h * 0.32f), end = Offset(w * 0.68f, h * 0.55f), strokeWidth = w * 0.05f)
    drawLine(color = color.copy(alpha = 0.7f), start = Offset(w * 0.80f, h * 0.32f), end = Offset(w * 0.85f, h * 0.55f), strokeWidth = w * 0.05f)
    drawArc(color = color, startAngle = 0f, sweepAngle = 180f, useCenter = false, topLeft = Offset(w * 0.66f, h * 0.50f), size = Size(w * 0.22f, h * 0.14f), style = Stroke(width = w * 0.06f))
}

private fun DrawScope.drawIncomeStatementIcon(w: Float, h: Float, color: Color) {
    // Waterfall bars (Revenue -> Expenses -> Net Income)
    drawRoundRect(color = EmeraldBullish, topLeft = Offset(w * 0.18f, h * 0.22f), size = Size(w * 0.18f, h * 0.60f), cornerRadius = CornerRadius(w * 0.03f))
    drawRoundRect(color = RoseBearish, topLeft = Offset(w * 0.41f, h * 0.40f), size = Size(w * 0.18f, h * 0.42f), cornerRadius = CornerRadius(w * 0.03f))
    drawRoundRect(color = CyanAccent, topLeft = Offset(w * 0.64f, h * 0.54f), size = Size(w * 0.18f, h * 0.28f), cornerRadius = CornerRadius(w * 0.03f))
}

private fun DrawScope.drawCashFlowIcon(w: Float, h: Float, color: Color) {
    // Flow stream with water drop / dollar wave
    val p = Path().apply {
        moveTo(w * 0.15f, h * 0.45f)
        cubicTo(w * 0.35f, h * 0.20f, w * 0.65f, h * 0.70f, w * 0.85f, h * 0.45f)
    }
    drawPath(path = p, color = color, style = Stroke(width = w * 0.10f, cap = StrokeCap.Round))
    val p2 = Path().apply {
        moveTo(w * 0.15f, h * 0.65f)
        cubicTo(w * 0.35f, h * 0.40f, w * 0.65f, h * 0.90f, w * 0.85f, h * 0.65f)
    }
    drawPath(path = p2, color = color.copy(alpha = 0.5f), style = Stroke(width = w * 0.08f, cap = StrokeCap.Round))
}

private fun DrawScope.drawRiskShieldIcon(w: Float, h: Float, color: Color) {
    val p = Path().apply {
        moveTo(w * 0.50f, h * 0.15f)
        lineTo(w * 0.82f, h * 0.28f)
        lineTo(w * 0.82f, h * 0.58f)
        cubicTo(w * 0.82f, h * 0.78f, w * 0.50f, h * 0.90f, w * 0.50f, h * 0.90f)
        cubicTo(w * 0.50f, h * 0.90f, w * 0.18f, h * 0.78f, w * 0.18f, h * 0.58f)
        lineTo(w * 0.18f, h * 0.28f)
        close()
    }
    drawPath(path = p, color = color, style = Stroke(width = w * 0.08f, join = StrokeJoin.Round))
    // Checkmark or center dot inside
    drawCircle(color = color, radius = w * 0.10f, center = Offset(w * 0.50f, h * 0.52f))
}

private fun DrawScope.drawCompoundGrowthIcon(w: Float, h: Float, color: Color) {
    // Exponential curve
    val p = Path().apply {
        moveTo(w * 0.15f, h * 0.82f)
        cubicTo(w * 0.45f, h * 0.80f, w * 0.68f, h * 0.60f, w * 0.85f, h * 0.18f)
    }
    drawPath(path = p, color = color, style = Stroke(width = w * 0.11f, cap = StrokeCap.Round))
    // Arrow head
    drawLine(color = color, start = Offset(w * 0.85f, h * 0.18f), end = Offset(w * 0.65f, h * 0.18f), strokeWidth = w * 0.09f, cap = StrokeCap.Round)
    drawLine(color = color, start = Offset(w * 0.85f, h * 0.18f), end = Offset(w * 0.85f, h * 0.38f), strokeWidth = w * 0.09f, cap = StrokeCap.Round)
}

private fun DrawScope.drawDividendIcon(w: Float, h: Float, color: Color) {
    // Coin + sprout/payout
    drawCircle(color = color, radius = w * 0.32f, center = Offset(w * 0.50f, h * 0.60f), style = Stroke(width = w * 0.08f))
    drawLine(color = color, start = Offset(w * 0.50f, h * 0.48f), end = Offset(w * 0.50f, h * 0.72f), strokeWidth = w * 0.08f, cap = StrokeCap.Round)
    // Sprout leaves
    val p = Path().apply {
        moveTo(w * 0.50f, h * 0.36f)
        cubicTo(w * 0.35f, h * 0.20f, w * 0.20f, h * 0.32f, w * 0.50f, h * 0.36f)
        cubicTo(w * 0.65f, h * 0.20f, w * 0.80f, h * 0.32f, w * 0.50f, h * 0.36f)
    }
    drawPath(path = p, color = EmeraldBullish, style = Fill)
}

private fun DrawScope.drawPaperTradingIcon(w: Float, h: Float, color: Color) {
    // Simulator sheet with toggle
    drawRoundRect(
        color = color,
        topLeft = Offset(w * 0.20f, h * 0.16f),
        size = Size(w * 0.60f, h * 0.72f),
        cornerRadius = CornerRadius(w * 0.06f),
        style = Stroke(width = w * 0.08f)
    )
    drawLine(color = color, start = Offset(w * 0.32f, h * 0.35f), end = Offset(w * 0.68f, h * 0.35f), strokeWidth = w * 0.07f, cap = StrokeCap.Round)
    drawLine(color = color, start = Offset(w * 0.32f, h * 0.50f), end = Offset(w * 0.60f, h * 0.50f), strokeWidth = w * 0.07f, cap = StrokeCap.Round)
    drawCircle(color = CyanAccent, radius = w * 0.08f, center = Offset(w * 0.65f, h * 0.68f))
}

private fun DrawScope.drawAiTutorIcon(w: Float, h: Float, color: Color) {
    // Sparkle / Neural star geometry
    val p = Path().apply {
        moveTo(w * 0.50f, h * 0.12f)
        cubicTo(w * 0.50f, h * 0.38f, w * 0.62f, h * 0.50f, w * 0.88f, h * 0.50f)
        cubicTo(w * 0.62f, h * 0.50f, w * 0.50f, h * 0.62f, w * 0.50f, h * 0.88f)
        cubicTo(w * 0.50f, h * 0.62f, w * 0.38f, h * 0.50f, w * 0.12f, h * 0.50f)
        cubicTo(w * 0.38f, h * 0.50f, w * 0.50f, h * 0.38f, w * 0.50f, h * 0.12f)
        close()
    }
    drawPath(path = p, color = color)
}

private fun DrawScope.drawJournalIcon(w: Float, h: Float, color: Color) {
    drawRoundRect(
        color = color,
        topLeft = Offset(w * 0.22f, h * 0.16f),
        size = Size(w * 0.56f, h * 0.72f),
        cornerRadius = CornerRadius(w * 0.06f),
        style = Stroke(width = w * 0.08f)
    )
    drawLine(color = color, start = Offset(w * 0.34f, h * 0.32f), end = Offset(w * 0.66f, h * 0.32f), strokeWidth = w * 0.06f, cap = StrokeCap.Round)
    drawLine(color = color, start = Offset(w * 0.34f, h * 0.46f), end = Offset(w * 0.66f, h * 0.46f), strokeWidth = w * 0.06f, cap = StrokeCap.Round)
    drawLine(color = color, start = Offset(w * 0.34f, h * 0.60f), end = Offset(w * 0.54f, h * 0.60f), strokeWidth = w * 0.06f, cap = StrokeCap.Round)
}

private fun DrawScope.drawCertificateIcon(w: Float, h: Float, color: Color) {
    drawRoundRect(
        color = color,
        topLeft = Offset(w * 0.14f, h * 0.22f),
        size = Size(w * 0.72f, h * 0.56f),
        cornerRadius = CornerRadius(w * 0.08f),
        style = Stroke(width = w * 0.08f)
    )
    drawCircle(color = AmberGold, radius = w * 0.10f, center = Offset(w * 0.50f, h * 0.50f))
}

private fun DrawScope.drawRoadmapIcon(w: Float, h: Float, color: Color) {
    drawLine(color = color, start = Offset(w * 0.25f, h * 0.25f), end = Offset(w * 0.75f, h * 0.50f), strokeWidth = w * 0.07f, cap = StrokeCap.Round)
    drawLine(color = color, start = Offset(w * 0.75f, h * 0.50f), end = Offset(w * 0.35f, h * 0.78f), strokeWidth = w * 0.07f, cap = StrokeCap.Round)
    drawCircle(color = CyanAccent, radius = w * 0.09f, center = Offset(w * 0.25f, h * 0.25f))
    drawCircle(color = EmeraldBullish, radius = w * 0.09f, center = Offset(w * 0.75f, h * 0.50f))
    drawCircle(color = AmberGold, radius = w * 0.09f, center = Offset(w * 0.35f, h * 0.78f))
}

private fun DrawScope.drawFlashcardsIcon(w: Float, h: Float, color: Color) {
    drawRoundRect(
        color = color.copy(alpha = 0.45f),
        topLeft = Offset(w * 0.28f, h * 0.15f),
        size = Size(w * 0.54f, h * 0.64f),
        cornerRadius = CornerRadius(w * 0.06f)
    )
    drawRoundRect(
        color = color,
        topLeft = Offset(w * 0.18f, h * 0.25f),
        size = Size(w * 0.54f, h * 0.64f),
        cornerRadius = CornerRadius(w * 0.06f),
        style = Stroke(width = w * 0.08f)
    )
}

private fun DrawScope.drawBullIcon(w: Float, h: Float, color: Color) {
    // Upward charging arrow + horns motif
    val p = Path().apply {
        moveTo(w * 0.20f, h * 0.75f)
        lineTo(w * 0.80f, h * 0.25f)
    }
    drawPath(path = p, color = color, style = Stroke(width = w * 0.12f, cap = StrokeCap.Round))
    val arrow = Path().apply {
        moveTo(w * 0.55f, h * 0.25f)
        lineTo(w * 0.80f, h * 0.25f)
        lineTo(w * 0.80f, h * 0.50f)
    }
    drawPath(path = arrow, color = color, style = Stroke(width = w * 0.12f, cap = StrokeCap.Round, join = StrokeJoin.Round))
}

private fun DrawScope.drawBearIcon(w: Float, h: Float, color: Color) {
    // Downward claw / trend arrow
    val p = Path().apply {
        moveTo(w * 0.20f, h * 0.25f)
        lineTo(w * 0.80f, h * 0.75f)
    }
    drawPath(path = p, color = color, style = Stroke(width = w * 0.12f, cap = StrokeCap.Round))
    val arrow = Path().apply {
        moveTo(w * 0.55f, h * 0.75f)
        lineTo(w * 0.80f, h * 0.75f)
        lineTo(w * 0.80f, h * 0.50f)
    }
    drawPath(path = arrow, color = color, style = Stroke(width = w * 0.12f, cap = StrokeCap.Round, join = StrokeJoin.Round))
}
