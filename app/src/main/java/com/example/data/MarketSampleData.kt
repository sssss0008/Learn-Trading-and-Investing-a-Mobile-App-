package com.example.data

import com.example.model.Asset
import com.example.model.AssetCategory
import com.example.model.BalanceSheetData
import com.example.model.Candle
import com.example.model.CashFlowData
import com.example.model.EconomicIndicator
import com.example.model.EtfHolding
import com.example.model.FinancialStatement

object MarketSampleData {

    private fun generateCandles(basePrice: Float, count: Int = 30): List<Candle> {
        val candles = mutableListOf<Candle>()
        var current = basePrice
        val now = System.currentTimeMillis()
        val step = 86400000L // 1 day

        val trendFactors = listOf(
            0.008f, 0.012f, -0.005f, 0.015f, -0.008f, 0.010f, 0.022f, -0.012f,
            0.005f, 0.018f, -0.004f, 0.011f, 0.009f, -0.015f, 0.014f, 0.008f,
            -0.006f, 0.019f, 0.012f, -0.010f, 0.025f, -0.008f, 0.014f, 0.011f,
            -0.003f, 0.016f, -0.009f, 0.013f, 0.017f, 0.005f
        )

        for (i in 0 until count) {
            val deltaPercent = trendFactors[i % trendFactors.size]
            val open = current
            val close = open * (1f + deltaPercent)
            val high = maxOf(open, close) * (1f + kotlin.math.abs(deltaPercent) * 0.45f)
            val low = minOf(open, close) * (1f - kotlin.math.abs(deltaPercent) * 0.45f)
            val volume = 20_000_000f + (i * 1_200_000f)

            candles.add(
                Candle(
                    timestamp = now - (count - i) * step,
                    open = open,
                    high = high,
                    low = low,
                    close = close,
                    volume = volume
                )
            )
            current = close
        }
        return candles
    }

    val sampleAssets: List<Asset> = listOf(
        Asset(
            symbol = "SPY",
            name = "SPDR S&P 500 ETF Trust",
            category = AssetCategory.INDICES,
            currentPrice = 584.20,
            changePercent = 0.85,
            changeAmount = 4.92,
            dayHigh = 586.10,
            dayLow = 582.40,
            yearHigh = 590.00,
            yearLow = 410.20,
            marketCap = "$540B AUM",
            peRatio = 26.4,
            dividendYield = 1.25,
            beta = 1.00,
            volume = "58.4M",
            description = "The gold standard benchmark tracking 500 of the largest publicly traded American corporations across all 11 sectors.",
            sparkline = listOf(570f, 572f, 571f, 575f, 574f, 578f, 581f, 584f),
            candles = generateCandles(560f),
            etfHoldings = listOf(
                EtfHolding("MSFT", "Microsoft Corp.", 7.1),
                EtfHolding("AAPL", "Apple Inc.", 6.9),
                EtfHolding("NVDA", "NVIDIA Corp.", 6.4),
                EtfHolding("AMZN", "Amazon.com Inc.", 3.8),
                EtfHolding("GOOGL", "Alphabet Inc.", 3.6)
            )
        ),
        Asset(
            symbol = "QQQ",
            name = "Invesco QQQ Trust (NASDAQ 100)",
            category = AssetCategory.ETFS,
            currentPrice = 492.60,
            changePercent = 1.42,
            changeAmount = 6.90,
            dayHigh = 494.50,
            dayLow = 488.20,
            yearHigh = 498.00,
            yearLow = 345.00,
            marketCap = "$290B AUM",
            peRatio = 31.8,
            dividendYield = 0.58,
            beta = 1.18,
            volume = "42.1M",
            description = "Tracks the 100 largest non-financial corporations on NASDAQ, heavily focused on technology, AI, and cloud compute.",
            sparkline = listOf(475f, 478f, 476f, 482f, 485f, 489f, 492f),
            candles = generateCandles(470f)
        ),
        Asset(
            symbol = "AAPL",
            name = "Apple Inc.",
            category = AssetCategory.STOCKS,
            currentPrice = 228.50,
            changePercent = 0.92,
            changeAmount = 2.08,
            dayHigh = 229.80,
            dayLow = 226.90,
            yearHigh = 237.23,
            yearLow = 164.08,
            marketCap = "$3.48T",
            peRatio = 34.2,
            eps = 6.68,
            dividendYield = 0.44,
            beta = 1.05,
            volume = "46.8M",
            description = "Global consumer electronics and software ecosystem with unmatched brand loyalty, services revenue, and capital returns.",
            sparkline = listOf(220f, 222f, 221f, 225f, 224f, 227f, 228.5f),
            candles = generateCandles(218f),
            incomeStatement = FinancialStatement(
                year = "2025",
                revenue = 391.0,
                cogs = 210.4,
                grossProfit = 180.6,
                operatingExpenses = 57.4,
                operatingIncome = 123.2,
                interestExpense = 3.9,
                taxExpense = 19.3,
                netIncome = 100.0,
                profitMarginPercent = 25.57
            ),
            balanceSheet = BalanceSheetData(
                year = "2025",
                cashAndEquivalents = 65.2,
                accountsReceivable = 30.1,
                inventory = 6.5,
                totalCurrentAssets = 143.6,
                propertyPlantEquipment = 45.3,
                totalAssets = 352.6,
                shortTermDebt = 15.0,
                accountsPayable = 58.2,
                totalCurrentLiabilities = 145.3,
                longTermDebt = 95.0,
                totalLiabilities = 290.4,
                totalShareholderEquity = 62.2
            ),
            cashFlow = CashFlowData(
                year = "2025",
                operatingCashFlow = 118.2,
                capitalExpenditures = 11.5,
                freeCashFlow = 106.7,
                investingCashFlow = -15.8,
                financingCashFlow = -100.2,
                netChangeInCash = 2.2
            )
        ),
        Asset(
            symbol = "MSFT",
            name = "Microsoft Corporation",
            category = AssetCategory.STOCKS,
            currentPrice = 432.10,
            changePercent = 1.15,
            changeAmount = 4.90,
            dayHigh = 434.00,
            dayLow = 428.50,
            yearHigh = 468.35,
            yearLow = 309.45,
            marketCap = "$3.21T",
            peRatio = 36.1,
            eps = 11.96,
            dividendYield = 0.72,
            beta = 0.92,
            volume = "22.4M",
            description = "Enterprise cloud infrastructure, Azure AI copilot platforms, productivity software, and enterprise solutions.",
            sparkline = listOf(415f, 418f, 422f, 420f, 428f, 432f),
            candles = generateCandles(410f),
            incomeStatement = FinancialStatement(
                year = "2025",
                revenue = 245.1,
                cogs = 74.2,
                grossProfit = 170.9,
                operatingExpenses = 61.4,
                operatingIncome = 109.5,
                interestExpense = 2.4,
                taxExpense = 19.1,
                netIncome = 88.0,
                profitMarginPercent = 35.9
            )
        ),
        Asset(
            symbol = "NVDA",
            name = "NVIDIA Corporation",
            category = AssetCategory.STOCKS,
            currentPrice = 124.80,
            changePercent = 2.65,
            changeAmount = 3.22,
            dayHigh = 126.50,
            dayLow = 122.10,
            yearHigh = 140.76,
            yearLow = 40.50,
            marketCap = "$3.07T",
            peRatio = 44.8,
            eps = 2.78,
            dividendYield = 0.03,
            beta = 1.68,
            volume = "65.3M",
            description = "Pioneer in accelerated computing and GPU architectures powering AI model training and inferencing globally.",
            sparkline = listOf(114f, 116f, 120f, 118f, 122f, 124.8f),
            candles = generateCandles(112f)
        ),
        Asset(
            symbol = "XAUUSD",
            name = "Gold Spot ($ / Troy Ounce)",
            category = AssetCategory.COMMODITIES,
            currentPrice = 2658.40,
            changePercent = 0.32,
            changeAmount = 8.50,
            dayHigh = 2665.00,
            dayLow = 2645.20,
            yearHigh = 2685.00,
            yearLow = 1980.00,
            marketCap = "$17.5T World Est.",
            volume = "18.2B Oz",
            description = "The historic monetary metal and premier hedge against fiat debasement, sovereign debt risk, and geopolitical turmoil.",
            sparkline = listOf(2610f, 2625f, 2635f, 2640f, 2658f),
            candles = generateCandles(2600f)
        ),
        Asset(
            symbol = "US10Y",
            name = "US 10-Year Treasury Yield",
            category = AssetCategory.BONDS,
            currentPrice = 3.98,
            changePercent = -0.04,
            changeAmount = -0.02,
            dayHigh = 4.02,
            dayLow = 3.96,
            yearHigh = 4.99,
            yearLow = 3.65,
            marketCap = "Sovereign Debt",
            volume = "Federal Reserve",
            description = "The world's risk-free benchmark interest rate determining mortgage rates, corporate borrowing costs, and equity valuations.",
            sparkline = listOf(4.15f, 4.10f, 4.05f, 4.00f, 3.98f),
            candles = generateCandles(4.12f)
        ),
        Asset(
            symbol = "EURUSD",
            name = "Euro / US Dollar",
            category = AssetCategory.FOREX,
            currentPrice = 1.1145,
            changePercent = 0.18,
            changeAmount = 0.0020,
            dayHigh = 1.1160,
            dayLow = 1.1120,
            yearHigh = 1.1275,
            yearLow = 1.0450,
            marketCap = "Global FX",
            volume = "$6.6T Daily Market",
            description = "The most liquid currency pair in the world, reflecting economic parity and rate differentials between the ECB and Fed.",
            sparkline = listOf(1.108f, 1.110f, 1.112f, 1.1145f),
            candles = generateCandles(1.105f)
        ),
        Asset(
            symbol = "BTC",
            name = "Bitcoin",
            category = AssetCategory.CRYPTO,
            currentPrice = 64280.0,
            changePercent = 1.84,
            changeAmount = 1160.0,
            dayHigh = 64950.0,
            dayLow = 62800.0,
            yearHigh = 73750.0,
            yearLow = 26500.0,
            marketCap = "$1.27T",
            volume = "$28.4B 24h",
            description = "Decentralized digital commodity featuring algorithmic scarcity, fixed 21M supply, and peer-to-peer settlement.",
            sparkline = listOf(61000f, 62500f, 62000f, 63800f, 64280f),
            candles = generateCandles(61000f)
        )
    )

    val economicCalendar: List<EconomicIndicator> = listOf(
        EconomicIndicator(
            name = "US Core CPI Inflation (YoY)",
            region = "United States",
            currentVal = "3.2%",
            previousVal = "3.3%",
            forecastVal = "3.2%",
            impact = "High",
            frequency = "Monthly",
            educationalNote = "Measures price changes excluding volatile food and energy. Primary metric monitored by the Federal Reserve for rate cuts."
        ),
        EconomicIndicator(
            name = "Federal Funds Rate Target",
            region = "United States",
            currentVal = "5.00%",
            previousVal = "5.25%",
            forecastVal = "5.00%",
            impact = "High",
            frequency = "FOMC 6-8 weeks",
            educationalNote = "Baseline interest rate charged by commercial banks for overnight lending. Governs liquidity throughout global capital markets."
        ),
        EconomicIndicator(
            name = "Non-Farm Payrolls (NFP)",
            region = "United States",
            currentVal = "+142K",
            previousVal = "+114K",
            forecastVal = "+160K",
            impact = "High",
            frequency = "Monthly (First Friday)",
            educationalNote = "Measures monthly employment creation outside agricultural sectors. Strong labor markets sustain consumer spending."
        )
    )
}
