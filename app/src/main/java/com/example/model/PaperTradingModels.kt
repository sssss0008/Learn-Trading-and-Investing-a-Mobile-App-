package com.example.model

enum class OrderType {
    MARKET,
    LIMIT,
    STOP_LOSS
}

enum class OrderAction {
    BUY,
    SELL
}

data class PaperOrder(
    val id: String,
    val symbol: String,
    val assetName: String,
    val action: OrderAction,
    val orderType: OrderType,
    val quantity: Double,
    val price: Double,
    val targetLimitPrice: Double? = null,
    val timestamp: Long,
    val status: String = "EXECUTED"
)

data class PaperPosition(
    val symbol: String,
    val assetName: String,
    val quantity: Double,
    val averageBuyPrice: Double,
    val currentPrice: Double
) {
    val totalCost: Double get() = quantity * averageBuyPrice
    val currentValue: Double get() = quantity * currentPrice
    val unrealizedPnl: Double get() = currentValue - totalCost
    val unrealizedPnlPercent: Double get() = if (totalCost > 0) (unrealizedPnl / totalCost) * 100 else 0.0
}

data class TradeJournalEntry(
    val id: String,
    val assetSymbol: String,
    val date: String,
    val setup: String,           // e.g. "Breakout above 200 SMA", "Oversold RSI Rebound"
    val entryPrice: Double,
    val exitPrice: Double?,
    val resultPnl: Double?,
    val emotion: String,         // e.g. "Disciplined", "FOMO", "Anxious", "Confident"
    val mistake: String,
    val lessonLearned: String
)

data class InvestmentThesis(
    val id: String,
    val assetSymbol: String,
    val companyName: String,
    val businessModel: String,
    val competitiveAdvantage: String,
    val growthDrivers: String,
    val keyRisks: String,
    val fairValueEstimate: Double,
    val timeHorizonYears: Int,
    val reviewDate: String
)

data class PortfolioAllocation(
    val assetClass: String,
    val currentWeightPercent: Double,
    val targetWeightPercent: Double,
    val colorHex: Long
)
