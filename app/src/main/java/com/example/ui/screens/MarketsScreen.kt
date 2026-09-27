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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.GridOn
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.Asset
import com.example.model.AssetCategory
import com.example.ui.components.EducationalDisclaimerCard
import com.example.ui.components.InteractiveCandleChart
import com.example.ui.components.MetricStatCard
import com.example.ui.components.SectionHeader
import com.example.ui.theme.AmberGold
import com.example.ui.theme.CyanAccent
import com.example.ui.theme.EmeraldBullish
import com.example.ui.theme.LocalFinancialColors
import com.example.ui.theme.RoseBearish
import java.util.Locale

@Composable
fun MarketsScreen(
    modifier: Modifier = Modifier,
    assets: List<Asset>,
    selectedAssetSymbol: String? = null,
    onSelectAsset: (String?) -> Unit = {},
    onSimulateOrder: (Asset) -> Unit = {}
) {
    val finColors = LocalFinancialColors.current
    var selectedCategory by remember { mutableStateOf<AssetCategory?>(null) }
    var searchQuery by remember { mutableStateOf("") }
    var isHeatmapMode by remember { mutableStateOf(false) }

    val activeAsset = assets.find { it.symbol == selectedAssetSymbol }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        if (activeAsset == null) {
            // Main Markets Explorer
            EducationalDisclaimerCard(
                text = "Market Data is Informational & Educational • Simulated Feed"
            )

            // Search Bar & Heatmap Toggle
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    modifier = Modifier
                        .weight(1f)
                        .testTag("search_markets"),
                    placeholder = { Text("Search tickers, indices, crypto...") },
                    leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = null, tint = finColors.textMuted) },
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = CyanAccent,
                        unfocusedBorderColor = finColors.cardBorder
                    ),
                    singleLine = true
                )

                Spacer(modifier = Modifier.width(8.dp))

                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = if (isHeatmapMode) CyanAccent.copy(alpha = 0.2f) else finColors.cardBackground,
                    border = androidx.compose.foundation.BorderStroke(1.dp, if (isHeatmapMode) CyanAccent else finColors.cardBorder),
                    modifier = Modifier.clickable { isHeatmapMode = !isHeatmapMode }
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = if (isHeatmapMode) Icons.Default.List else Icons.Default.GridOn,
                            contentDescription = "Toggle Heatmap",
                            tint = if (isHeatmapMode) CyanAccent else finColors.textSecondary,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = if (isHeatmapMode) "List" else "Heatmap",
                            style = MaterialTheme.typography.labelSmall,
                            color = if (isHeatmapMode) CyanAccent else finColors.textSecondary
                        )
                    }
                }
            }

            // Category Filter Chips
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp, vertical = 6.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                val isAllSelected = selectedCategory == null
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = if (isAllSelected) CyanAccent else finColors.cardBackground,
                    border = androidx.compose.foundation.BorderStroke(1.dp, if (isAllSelected) CyanAccent else finColors.cardBorder),
                    modifier = Modifier.clickable { selectedCategory = null }
                ) {
                    Text(
                        text = "ALL",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = if (isAllSelected) Color.Black else finColors.textSecondary,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                    )
                }

                AssetCategory.values().forEach { cat ->
                    val isSelected = selectedCategory == cat
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = if (isSelected) CyanAccent else finColors.cardBackground,
                        border = androidx.compose.foundation.BorderStroke(1.dp, if (isSelected) CyanAccent else finColors.cardBorder),
                        modifier = Modifier.clickable { selectedCategory = cat }
                    ) {
                        Text(
                            text = cat.name,
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                            color = if (isSelected) Color.Black else finColors.textSecondary,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                        )
                    }
                }
            }

            val filteredAssets = remember(selectedCategory, searchQuery, assets) {
                assets.filter { asset ->
                    (selectedCategory == null || asset.category == selectedCategory) &&
                    (searchQuery.isBlank() || asset.symbol.contains(searchQuery, ignoreCase = true) || asset.name.contains(searchQuery, ignoreCase = true))
                }
            }

            if (!isHeatmapMode) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                ) {
                    items(filteredAssets) { asset ->
                        AssetRowItem(asset = asset, onClick = { onSelectAsset(asset.symbol) })
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                    item {
                        Spacer(modifier = Modifier.height(30.dp))
                    }
                }
            } else {
                // Sector / Asset Heatmap Grid
                HeatmapView(assets = filteredAssets, onSelectAsset = onSelectAsset)
            }
        } else {
            // Detailed Asset Workspace Screen
            AssetWorkspaceDetail(
                asset = activeAsset,
                onBack = { onSelectAsset(null) },
                onSimulateOrder = { onSimulateOrder(activeAsset) }
            )
        }
    }
}

@Composable
private fun AssetRowItem(
    asset: Asset,
    onClick: () -> Unit
) {
    val finColors = LocalFinancialColors.current
    val isPositive = asset.changePercent >= 0

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .clickable { onClick() }
            .border(1.dp, finColors.cardBorder, RoundedCornerShape(14.dp))
            .testTag("asset_row_${asset.symbol}"),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = finColors.cardBackground)
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = asset.symbol,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Surface(
                        shape = RoundedCornerShape(4.dp),
                        color = finColors.cardBorder.copy(alpha = 0.5f)
                    ) {
                        Text(
                            text = asset.category.name,
                            style = MaterialTheme.typography.labelSmall,
                            color = finColors.textMuted,
                            modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = asset.name,
                    style = MaterialTheme.typography.bodySmall,
                    color = finColors.textSecondary,
                    maxLines = 1
                )
            }

            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = "$${String.format(Locale.US, "%.2f", asset.currentPrice)}",
                    style = MaterialTheme.typography.titleMedium.copy(fontFamily = FontFamily.Monospace),
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(2.dp))
                Surface(
                    shape = RoundedCornerShape(4.dp),
                    color = if (isPositive) EmeraldBullish.copy(alpha = 0.18f) else RoseBearish.copy(alpha = 0.18f)
                ) {
                    Text(
                        text = "${if (isPositive) "+" else ""}${String.format(Locale.US, "%.2f", asset.changePercent)}%",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = if (isPositive) EmeraldBullish else RoseBearish,
                        modifier = Modifier.padding(horizontal = 5.dp, vertical = 2.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun HeatmapView(
    assets: List<Asset>,
    onSelectAsset: (String) -> Unit
) {
    val finColors = LocalFinancialColors.current

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        item {
            Text(
                text = "Market Performance Heatmap",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = "Multi-signal tile representation based on price change",
                style = MaterialTheme.typography.bodySmall,
                color = finColors.textMuted
            )
            Spacer(modifier = Modifier.height(14.dp))
        }

        item {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                assets.chunked(2).forEach { rowAssets ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        rowAssets.forEach { asset ->
                            val isPos = asset.changePercent >= 0
                            val tileColor = if (isPos) {
                                EmeraldBullish.copy(alpha = (0.25f + (asset.changePercent / 6.0).toFloat().coerceIn(0.1f, 0.6f)))
                            } else {
                                RoseBearish.copy(alpha = (0.25f + (kotlin.math.abs(asset.changePercent) / 6.0).toFloat().coerceIn(0.1f, 0.6f)))
                            }

                            Surface(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(95.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .clickable { onSelectAsset(asset.symbol) }
                                    .border(1.dp, finColors.cardBorder, RoundedCornerShape(12.dp)),
                                color = tileColor
                            ) {
                                Column(
                                    modifier = Modifier.padding(10.dp),
                                    verticalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(text = asset.symbol, fontWeight = FontWeight.Bold, color = Color.White)
                                        Text(text = "${if (isPos) "+" else ""}${String.format(Locale.US, "%.2f", asset.changePercent)}%", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = Color.White)
                                    }
                                    Text(text = "$${String.format(Locale.US, "%.2f", asset.currentPrice)}", fontFamily = FontFamily.Monospace, fontWeight = FontWeight.SemiBold, fontSize = 14.sp, color = Color.White)
                                }
                            }
                        }
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

@Composable
private fun AssetWorkspaceDetail(
    asset: Asset,
    onBack: () -> Unit,
    onSimulateOrder: () -> Unit
) {
    val finColors = LocalFinancialColors.current
    var selectedTab by remember { mutableIntStateOf(0) }
    val detailTabs = listOf("Chart", "Financials", "Valuation", "Overview")

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack) {
                    Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = CyanAccent)
                }

                Button(
                    onClick = onSimulateOrder,
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = CyanAccent, contentColor = Color.Black)
                ) {
                    Text("Simulate Order", fontWeight = FontWeight.Bold)
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Asset Hero Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column {
                    Text(
                        text = "${asset.symbol} • ${asset.name}",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Text(
                        text = "${asset.category.name} • Market Cap: ${asset.marketCap}",
                        style = MaterialTheme.typography.bodySmall,
                        color = finColors.textMuted
                    )
                }

                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "$${String.format(Locale.US, "%.2f", asset.currentPrice)}",
                        style = MaterialTheme.typography.titleLarge.copy(fontFamily = FontFamily.Monospace),
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    val isPositive = asset.changePercent >= 0
                    Text(
                        text = "${if (isPositive) "+" else ""}${String.format(Locale.US, "%.2f", asset.changePercent)}%",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = if (isPositive) EmeraldBullish else RoseBearish
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Sub tabs
            ScrollableTabRow(
                selectedTabIndex = selectedTab,
                containerColor = MaterialTheme.colorScheme.background,
                contentColor = CyanAccent,
                edgePadding = 0.dp,
                indicator = { tabPositions ->
                    TabRowDefaults.SecondaryIndicator(
                        Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                        color = CyanAccent,
                        height = 2.dp
                    )
                }
            ) {
                detailTabs.forEachIndexed { i, t ->
                    Tab(
                        selected = selectedTab == i,
                        onClick = { selectedTab = i },
                        text = {
                            Text(
                                text = t,
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = if (selectedTab == i) FontWeight.Bold else FontWeight.Medium,
                                color = if (selectedTab == i) CyanAccent else finColors.textMuted
                            )
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))
        }

        when (selectedTab) {
            0 -> {
                // Interactive Candlestick Chart View
                item {
                    InteractiveCandleChart(
                        candles = asset.candles,
                        symbol = asset.symbol,
                        assetName = asset.name
                    )
                }
            }
            1 -> {
                // Financial Statements View (Income, Balance, Cash Flow)
                item {
                    FinancialStatementsTab(asset = asset)
                }
            }
            2 -> {
                // Valuation Multiples
                item {
                    ValuationMetricsTab(asset = asset)
                }
            }
            3 -> {
                // Overview & Description
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(1.dp, finColors.cardBorder, RoundedCornerShape(14.dp)),
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = finColors.cardBackground)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "About ${asset.name}",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = CyanAccent
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = asset.description,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onBackground,
                                lineHeight = 22.sp
                            )
                        }
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

@Composable
private fun FinancialStatementsTab(asset: Asset) {
    val finColors = LocalFinancialColors.current
    val inc = asset.incomeStatement
    val bal = asset.balanceSheet
    val cf = asset.cashFlow

    Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
        if (inc != null) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, finColors.cardBorder, RoundedCornerShape(14.dp)),
                colors = CardDefaults.cardColors(containerColor = finColors.cardBackground)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Income Statement (${inc.year})",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = CyanAccent
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    StatementRow("Revenue", "$${inc.revenue}B")
                    StatementRow("Cost of Goods Sold (COGS)", "-$${inc.cogs}B")
                    StatementRow("Gross Profit", "$${inc.grossProfit}B", isBold = true)
                    StatementRow("Operating Expenses", "-$${inc.operatingExpenses}B")
                    StatementRow("Operating Income", "$${inc.operatingIncome}B", isBold = true)
                    StatementRow("Net Income", "$${inc.netIncome}B", isBold = true, highlight = EmeraldBullish)
                    StatementRow("Net Profit Margin", "${inc.profitMarginPercent}%")
                }
            }
        }

        if (bal != null) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, finColors.cardBorder, RoundedCornerShape(14.dp)),
                colors = CardDefaults.cardColors(containerColor = finColors.cardBackground)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Balance Sheet (${bal.year})",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = AmberGold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    StatementRow("Cash & Equivalents", "$${bal.cashAndEquivalents}B")
                    StatementRow("Total Current Assets", "$${bal.totalCurrentAssets}B")
                    StatementRow("Total Assets", "$${bal.totalAssets}B", isBold = true)
                    StatementRow("Total Current Liabilities", "$${bal.totalCurrentLiabilities}B")
                    StatementRow("Long-Term Debt", "$${bal.longTermDebt}B")
                    StatementRow("Total Shareholder Equity", "$${bal.totalShareholderEquity}B", isBold = true, highlight = CyanAccent)
                }
            }
        }

        if (cf != null) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, finColors.cardBorder, RoundedCornerShape(14.dp)),
                colors = CardDefaults.cardColors(containerColor = finColors.cardBackground)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Cash Flow Statement (${cf.year})",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = EmeraldBullish
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    StatementRow("Operating Cash Flow", "$${cf.operatingCashFlow}B", isBold = true)
                    StatementRow("Capital Expenditures (Capex)", "-$${cf.capitalExpenditures}B")
                    StatementRow("Free Cash Flow (FCF)", "$${cf.freeCashFlow}B", isBold = true, highlight = EmeraldBullish)
                    StatementRow("Financing Cash Flow", "$${cf.financingCashFlow}B")
                }
            }
        }
    }
}

@Composable
private fun StatementRow(
    label: String,
    value: String,
    isBold: Boolean = false,
    highlight: Color? = null
) {
    val finColors = LocalFinancialColors.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            fontWeight = if (isBold) FontWeight.Bold else FontWeight.Normal,
            color = if (isBold) MaterialTheme.colorScheme.onBackground else finColors.textSecondary
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodySmall.copy(fontFamily = FontFamily.Monospace),
            fontWeight = if (isBold) FontWeight.Bold else FontWeight.Normal,
            color = highlight ?: (if (isBold) MaterialTheme.colorScheme.onBackground else finColors.textSecondary)
        )
    }
}

@Composable
private fun ValuationMetricsTab(asset: Asset) {
    val finColors = LocalFinancialColors.current

    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            MetricStatCard(
                modifier = Modifier.weight(1f),
                title = "P/E Ratio",
                value = if (asset.peRatio != null) "${asset.peRatio}x" else "N/A",
                subValue = "Trailing 12 Months",
                accentColor = CyanAccent
            )
            MetricStatCard(
                modifier = Modifier.weight(1f),
                title = "EPS",
                value = if (asset.eps != null) "$${asset.eps}" else "N/A",
                subValue = "Diluted Per Share",
                accentColor = EmeraldBullish
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            MetricStatCard(
                modifier = Modifier.weight(1f),
                title = "Dividend Yield",
                value = if (asset.dividendYield != null) "${asset.dividendYield}%" else "None",
                subValue = "Annualized Distribution",
                accentColor = AmberGold
            )
            MetricStatCard(
                modifier = Modifier.weight(1f),
                title = "Beta (Volatility)",
                value = if (asset.beta != null) "${asset.beta}" else "1.00",
                subValue = "Relative to S&P 500",
                accentColor = finColors.textMuted
            )
        }
    }
}
