package com.example.model

enum class AssetCategory {
    INDICES,
    STOCKS,
    ETFS,
    BONDS,
    COMMODITIES,
    FOREX,
    CRYPTO
}

data class Candle(
    val timestamp: Long,
    val open: Float,
    val high: Float,
    val low: Float,
    val close: Float,
    val volume: Float
)

data class Asset(
    val symbol: String,
    val name: String,
    val category: AssetCategory,
    val currentPrice: Double,
    val changePercent: Double,
    val changeAmount: Double,
    val dayHigh: Double,
    val dayLow: Double,
    val yearHigh: Double,
    val yearLow: Double,
    val marketCap: String, // e.g. "$3.24T"
    val peRatio: Double? = null,
    val eps: Double? = null,
    val dividendYield: Double? = null,
    val beta: Double? = null,
    val volume: String = "45.2M",
    val description: String = "",
    val sparkline: List<Float> = emptyList(),
    val candles: List<Candle> = emptyList(),
    val incomeStatement: FinancialStatement? = null,
    val balanceSheet: BalanceSheetData? = null,
    val cashFlow: CashFlowData? = null,
    val etfHoldings: List<EtfHolding> = emptyList()
)

data class FinancialStatement(
    val year: String,
    val revenue: Double,          // in Billions
    val cogs: Double,
    val grossProfit: Double,
    val operatingExpenses: Double,
    val operatingIncome: Double,
    val interestExpense: Double,
    val taxExpense: Double,
    val netIncome: Double,
    val profitMarginPercent: Double
)

data class BalanceSheetData(
    val year: String,
    val cashAndEquivalents: Double,
    val accountsReceivable: Double,
    val inventory: Double,
    val totalCurrentAssets: Double,
    val propertyPlantEquipment: Double,
    val totalAssets: Double,
    val shortTermDebt: Double,
    val accountsPayable: Double,
    val totalCurrentLiabilities: Double,
    val longTermDebt: Double,
    val totalLiabilities: Double,
    val totalShareholderEquity: Double
)

data class CashFlowData(
    val year: String,
    val operatingCashFlow: Double,
    val capitalExpenditures: Double,
    val freeCashFlow: Double,
    val investingCashFlow: Double,
    val financingCashFlow: Double,
    val netChangeInCash: Double
)

data class EtfHolding(
    val assetSymbol: String,
    val assetName: String,
    val weightPercent: Double
)

data class EconomicIndicator(
    val name: String,
    val region: String,
    val currentVal: String,
    val previousVal: String,
    val forecastVal: String,
    val impact: String, // High, Medium, Low
    val frequency: String,
    val educationalNote: String
)
