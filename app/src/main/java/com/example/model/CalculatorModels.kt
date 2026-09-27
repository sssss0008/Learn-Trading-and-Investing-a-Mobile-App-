package com.example.model

data class CompoundingResult(
    val totalPrincipal: Double,
    val totalInterestEarned: Double,
    val futureValue: Double,
    val yearlyBreakdown: List<YearlyGrowthPoint>
)

data class YearlyGrowthPoint(
    val year: Int,
    val principalDeposited: Double,
    val totalValue: Double
)

data class PositionSizeResult(
    val riskAmountDollars: Double,
    val priceRiskPerShare: Double,
    val maxSharesToBuy: Int,
    val totalPositionValue: Double,
    val percentageOfAccount: Double
)

data class RiskRewardResult(
    val riskPerShare: Double,
    val rewardPerShare: Double,
    val ratio: Double, // e.g. 3.0 (1:3)
    val isFavorable: Boolean
)

data class DcfResult(
    val enterpriseValue: Double,
    val equityValue: Double,
    val estimatedFairValuePerShare: Double,
    val projectedFreeCashFlows: List<Double>
)

data class GoalPlannerResult(
    val targetAmount: Double,
    val requiredMonthlySavings: Double,
    val totalEstimatedInterest: Double
)
