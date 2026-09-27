package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import com.example.model.OrderAction
import com.example.model.OrderType
import com.example.model.PaperOrder
import com.example.model.PaperPosition
import com.example.model.TradeJournalEntry
import com.example.ui.components.EducationalDisclaimerCard
import com.example.ui.components.MetricStatCard
import com.example.ui.components.SectionHeader
import com.example.ui.theme.AmberGold
import com.example.ui.theme.CyanAccent
import com.example.ui.theme.EmeraldBullish
import com.example.ui.theme.IndigoBlue
import com.example.ui.theme.LocalFinancialColors
import com.example.ui.theme.RoseBearish
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun PracticeScreen(
    modifier: Modifier = Modifier,
    cashBalance: Double,
    positions: Map<String, PaperPosition>,
    orders: List<PaperOrder>,
    journalEntries: List<TradeJournalEntry>,
    assets: List<Asset>,
    onExecuteOrder: (Asset, OrderAction, OrderType, Double) -> Unit,
    onAddJournalEntry: (TradeJournalEntry) -> Unit
) {
    val finColors = LocalFinancialColors.current
    var selectedTab by remember { mutableIntStateOf(0) }
    val practiceTabs = listOf("Paper Portfolio", "Simulate Trade", "Trading Journal", "Allocation & Rebalance", "Historical Simulator")

    var showOrderDialog by remember { mutableStateOf(false) }

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
            practiceTabs.forEachIndexed { idx, title ->
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
            text = "100% Simulated Paper Environment • Zero Real Money Risk • Learning Sandbox"
        )

        when (selectedTab) {
            0 -> PaperPortfolioTab(
                cash = cashBalance,
                positions = positions.values.toList(),
                orders = orders,
                onOpenTrade = { showOrderDialog = true }
            )
            1 -> SimulateOrderTab(
                assets = assets,
                cash = cashBalance,
                onExecute = onExecuteOrder
            )
            2 -> TradingJournalTab(
                entries = journalEntries,
                onAddEntry = onAddJournalEntry
            )
            3 -> AllocationAndRebalanceTab(
                cash = cashBalance,
                positions = positions.values.toList()
            )
            4 -> HistoricalSimulatorTab()
        }
    }
}

@Composable
private fun PaperPortfolioTab(
    cash: Double,
    positions: List<PaperPosition>,
    orders: List<PaperOrder>,
    onOpenTrade: () -> Unit
) {
    val finColors = LocalFinancialColors.current
    val totalPositionsValue = positions.sumOf { it.currentValue }
    val totalNetWorth = cash + totalPositionsValue
    val totalUnrealizedPnl = positions.sumOf { it.unrealizedPnl }

    LazyColumn(modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp)) {
        item {
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Total Simulated Net Worth",
                        style = MaterialTheme.typography.labelSmall,
                        color = finColors.textMuted,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "$${String.format(Locale.US, "%,.2f", totalNetWorth)}",
                        style = MaterialTheme.typography.displayMedium.copy(fontFamily = FontFamily.Monospace),
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }

                Button(
                    onClick = onOpenTrade,
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = CyanAccent, contentColor = Color.Black)
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("New Trade", fontWeight = FontWeight.Bold)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                MetricStatCard(
                    modifier = Modifier.weight(1f),
                    title = "Virtual Cash",
                    value = "$${String.format(Locale.US, "%,.0f", cash)}",
                    accentColor = CyanAccent
                )
                MetricStatCard(
                    modifier = Modifier.weight(1f),
                    title = "Unrealized P&L",
                    value = "${if (totalUnrealizedPnl >= 0) "+" else ""}$${String.format(Locale.US, "%,.2f", totalUnrealizedPnl)}",
                    accentColor = if (totalUnrealizedPnl >= 0) EmeraldBullish else RoseBearish
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
            SectionHeader(
                title = "Open Simulated Positions (${positions.size})",
                subtitle = "Mark-to-market performance"
            )
        }

        if (positions.isEmpty()) {
            item {
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = finColors.cardBackground,
                    border = androidx.compose.foundation.BorderStroke(1.dp, finColors.cardBorder),
                    modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("No open positions", fontWeight = FontWeight.Bold, color = finColors.textSecondary)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("Simulate an order to start practicing", style = MaterialTheme.typography.bodySmall, color = finColors.textMuted)
                    }
                }
            }
        } else {
            items(positions) { pos ->
                val isPos = pos.unrealizedPnl >= 0
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 5.dp)
                        .border(1.dp, finColors.cardBorder, RoundedCornerShape(14.dp)),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = finColors.cardBackground)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(text = pos.symbol, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
                                Text(text = "${pos.quantity.toInt()} shares • Avg $${String.format(Locale.US, "%.2f", pos.averageBuyPrice)}", style = MaterialTheme.typography.bodySmall, color = finColors.textMuted)
                            }
                            Column(horizontalAlignment = Alignment.End) {
                                Text(text = "$${String.format(Locale.US, "%,.2f", pos.currentValue)}", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium.copy(fontFamily = FontFamily.Monospace))
                                Surface(
                                    shape = RoundedCornerShape(4.dp),
                                    color = if (isPos) EmeraldBullish.copy(alpha = 0.18f) else RoseBearish.copy(alpha = 0.18f)
                                ) {
                                    Text(
                                        text = "${if (isPos) "+" else ""}$${String.format(Locale.US, "%.2f", pos.unrealizedPnl)} (${String.format(Locale.US, "%.2f", pos.unrealizedPnlPercent)}%)",
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = if (isPos) EmeraldBullish else RoseBearish,
                                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
            SectionHeader(title = "Recent Simulated Orders (${orders.size})")
        }

        items(orders) { ord ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                    .border(1.dp, finColors.cardBorder, RoundedCornerShape(12.dp)),
                colors = CardDefaults.cardColors(containerColor = finColors.cardBackground)
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Surface(
                                shape = RoundedCornerShape(4.dp),
                                color = if (ord.action == OrderAction.BUY) EmeraldBullish.copy(alpha = 0.2f) else RoseBearish.copy(alpha = 0.2f)
                            ) {
                                Text(
                                    text = ord.action.name,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 11.sp,
                                    color = if (ord.action == OrderAction.BUY) EmeraldBullish else RoseBearish,
                                    modifier = Modifier.padding(horizontal = 5.dp, vertical = 1.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(text = ord.symbol, fontWeight = FontWeight.Bold)
                        }
                        Text(
                            text = SimpleDateFormat("MMM dd, HH:mm", Locale.US).format(Date(ord.timestamp)),
                            style = MaterialTheme.typography.labelSmall,
                            color = finColors.textMuted
                        )
                    }

                    Column(horizontalAlignment = Alignment.End) {
                        Text(
                            text = "${ord.quantity.toInt()} @ $${String.format(Locale.US, "%.2f", ord.price)}",
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(text = "Total $${String.format(Locale.US, "%,.2f", ord.quantity * ord.price)}", style = MaterialTheme.typography.labelSmall, color = finColors.textSecondary)
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
private fun SimulateOrderTab(
    assets: List<Asset>,
    cash: Double,
    onExecute: (Asset, OrderAction, OrderType, Double) -> Unit
) {
    val finColors = LocalFinancialColors.current
    var selectedAsset by remember { mutableStateOf(assets.first()) }
    var selectedAction by remember { mutableStateOf(OrderAction.BUY) }
    var selectedOrderType by remember { mutableStateOf(OrderType.MARKET) }
    var quantityText by remember { mutableStateOf("10") }
    var orderExecutedSuccess by remember { mutableStateOf(false) }

    val qty = quantityText.toDoubleOrNull() ?: 0.0
    val totalCost = qty * selectedAsset.currentPrice

    LazyColumn(modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp)) {
        item {
            Spacer(modifier = Modifier.height(10.dp))
            SectionHeader(title = "Simulate Order Execution", subtitle = "Test trade execution mechanics with zero financial consequence")

            Card(
                modifier = Modifier.fillMaxWidth().border(1.dp, finColors.cardBorder, RoundedCornerShape(16.dp)),
                colors = CardDefaults.cardColors(containerColor = finColors.cardBackground)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "Select Asset", style = MaterialTheme.typography.labelSmall, color = finColors.textMuted)
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(1.dp, finColors.cardBorder, RoundedCornerShape(10.dp))
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "${selectedAsset.symbol} - ${selectedAsset.name}", fontWeight = FontWeight.Bold)
                        Text(text = "$${String.format(Locale.US, "%.2f", selectedAsset.currentPrice)}", fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold, color = CyanAccent)
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Buy vs Sell
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        Button(
                            onClick = { selectedAction = OrderAction.BUY },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (selectedAction == OrderAction.BUY) EmeraldBullish else finColors.cardBorder
                            )
                        ) {
                            Text("BUY / LONG", fontWeight = FontWeight.Bold, color = if (selectedAction == OrderAction.BUY) Color.Black else finColors.textSecondary)
                        }

                        Button(
                            onClick = { selectedAction = OrderAction.SELL },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (selectedAction == OrderAction.SELL) RoseBearish else finColors.cardBorder
                            )
                        ) {
                            Text("SELL / CLOSE", fontWeight = FontWeight.Bold, color = if (selectedAction == OrderAction.SELL) Color.White else finColors.textSecondary)
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    OutlinedTextField(
                        value = quantityText,
                        onValueChange = { quantityText = it },
                        label = { Text("Quantity (Shares/Units)") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = finColors.cardBorder.copy(alpha = 0.35f),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text("Estimated Total Order Value", style = MaterialTheme.typography.bodySmall, color = finColors.textSecondary)
                                Text("$${String.format(Locale.US, "%,.2f", totalCost)}", fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold)
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text("Virtual Cash Available", style = MaterialTheme.typography.bodySmall, color = finColors.textMuted)
                                Text("$${String.format(Locale.US, "%,.2f", cash)}", fontFamily = FontFamily.Monospace, color = finColors.textMuted)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            if (qty > 0) {
                                onExecute(selectedAsset, selectedAction, selectedOrderType, qty)
                                orderExecutedSuccess = true
                            }
                        },
                        enabled = qty > 0 && (selectedAction == OrderAction.SELL || totalCost <= cash),
                        modifier = Modifier.fillMaxWidth().testTag("btn_execute_order"),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = CyanAccent, contentColor = Color.Black)
                    ) {
                        Text("Execute Simulated Order", fontWeight = FontWeight.Bold)
                    }

                    if (orderExecutedSuccess) {
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "✓ Simulated Order Executed Successfully!",
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.Bold,
                            color = EmeraldBullish
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

@Composable
private fun TradingJournalTab(
    entries: List<TradeJournalEntry>,
    onAddEntry: (TradeJournalEntry) -> Unit
) {
    val finColors = LocalFinancialColors.current
    var showDialog by remember { mutableStateOf(false) }

    var assetSymbol by remember { mutableStateOf("AAPL") }
    var setupText by remember { mutableStateOf("Breakout above 200 SMA") }
    var entryPriceText by remember { mutableStateOf("225.00") }
    var emotionText by remember { mutableStateOf("Disciplined") }
    var mistakeText by remember { mutableStateOf("None") }
    var lessonText by remember { mutableStateOf("Followed position sizing protocol perfectly") }

    LazyColumn(modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp)) {
        item {
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                SectionHeader(title = "Trading & Investing Journal", subtitle = "Track setups, emotions, mistakes, and lessons learned")
                Button(
                    onClick = { showDialog = true },
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = CyanAccent, contentColor = Color.Black)
                ) {
                    Text("+ Log Entry", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                }
            }
        }

        items(entries) { entry ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
                    .border(1.dp, finColors.cardBorder, RoundedCornerShape(14.dp)),
                colors = CardDefaults.cardColors(containerColor = finColors.cardBackground)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "${entry.assetSymbol} • ${entry.date}", fontWeight = FontWeight.Bold, color = CyanAccent)
                        Surface(shape = RoundedCornerShape(4.dp), color = AmberGold.copy(alpha = 0.15f)) {
                            Text(text = "Emotion: ${entry.emotion}", style = MaterialTheme.typography.labelSmall, color = AmberGold, modifier = Modifier.padding(horizontal = 5.dp, vertical = 2.dp))
                        }
                    }

                    Spacer(modifier = Modifier.height(6.dp))
                    Text(text = "Setup: ${entry.setup}", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.SemiBold)
                    Text(text = "Entry: $${entry.entryPrice}${if (entry.exitPrice != null) " • Exit: $${entry.exitPrice}" else ""}", style = MaterialTheme.typography.labelSmall, color = finColors.textMuted)

                    if (entry.lessonLearned.isNotBlank()) {
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = "💡 Lesson: ${entry.lessonLearned}", style = MaterialTheme.typography.bodySmall, color = EmeraldBullish)
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(30.dp))
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Log New Trade / Reflection") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(value = assetSymbol, onValueChange = { assetSymbol = it }, label = { Text("Asset Ticker") }, singleLine = true)
                    OutlinedTextField(value = setupText, onValueChange = { setupText = it }, label = { Text("Technical / Fundamental Setup") }, singleLine = true)
                    OutlinedTextField(value = entryPriceText, onValueChange = { entryPriceText = it }, label = { Text("Entry Price ($)") }, singleLine = true)
                    OutlinedTextField(value = emotionText, onValueChange = { emotionText = it }, label = { Text("Emotional State (e.g. Disciplined, FOMO)") }, singleLine = true)
                    OutlinedTextField(value = lessonText, onValueChange = { lessonText = it }, label = { Text("Lesson Learned") }, singleLine = true)
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        onAddEntry(
                            TradeJournalEntry(
                                id = System.currentTimeMillis().toString(),
                                assetSymbol = assetSymbol,
                                date = "Sep 27, 2026",
                                setup = setupText,
                                entryPrice = entryPriceText.toDoubleOrNull() ?: 100.0,
                                exitPrice = null,
                                resultPnl = null,
                                emotion = emotionText,
                                mistake = mistakeText,
                                lessonLearned = lessonText
                            )
                        )
                        showDialog = false
                    }
                ) { Text("Save Journal") }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) { Text("Cancel") }
            }
        )
    }
}

@Composable
private fun AllocationAndRebalanceTab(
    cash: Double,
    positions: List<PaperPosition>
) {
    val finColors = LocalFinancialColors.current

    LazyColumn(modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp)) {
        item {
            Spacer(modifier = Modifier.height(10.dp))
            SectionHeader(
                title = "Asset Allocation & Rebalancing Lab",
                subtitle = "Compare Current portfolio weightings against Target allocations"
            )

            Card(
                modifier = Modifier.fillMaxWidth().border(1.dp, finColors.cardBorder, RoundedCornerShape(16.dp)),
                colors = CardDefaults.cardColors(containerColor = finColors.cardBackground)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "Current vs Target Allocation", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(10.dp))

                    AllocationRow("US Equities (Stocks & Index ETFs)", 65.0, 60.0, CyanAccent)
                    AllocationRow("Fixed Income (Bonds)", 15.0, 25.0, EmeraldBullish)
                    AllocationRow("Cash & Equivalents", 15.0, 10.0, AmberGold)
                    AllocationRow("Commodities & Gold", 5.0, 5.0, IndigoBlue)

                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "💡 Rebalancing Insight: Your equity weighting has drifted to 65% (target: 60%). A disciplined rebalance trims 5% of equities to allocate towards undervalued fixed income assets.",
                        style = MaterialTheme.typography.bodySmall,
                        color = finColors.textSecondary
                    )
                }
            }

            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

@Composable
private fun AllocationRow(name: String, currentPct: Double, targetPct: Double, color: Color) {
    val finColors = LocalFinancialColors.current
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = name, style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.SemiBold)
            Text(text = "Current: ${currentPct.toInt()}% (Target: ${targetPct.toInt()}%)", style = MaterialTheme.typography.labelSmall, color = finColors.textMuted)
        }
    }
}

@Composable
private fun HistoricalSimulatorTab() {
    val finColors = LocalFinancialColors.current

    val scenarios = listOf(
        Pair("The 2008 Great Financial Crisis", "Subprime contagion and global credit freeze. Major indices drew down 50%+. How would your risk rules perform?"),
        Pair("The March 2020 Liquidity Shock", "Rapid 35% crash followed by aggressive fiscal and central bank monetary intervention."),
        Pair("The 2022 Inflation & Rate Hiking Cycle", "Central banks aggressively raised benchmark rates from 0% to 5.25%, re-pricing both stocks and long-duration bonds.")
    )

    LazyColumn(modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp)) {
        item {
            Spacer(modifier = Modifier.height(10.dp))
            SectionHeader(
                title = "Historical Market Crisis Simulator",
                subtitle = "Study historical black swans and test risk management resilience"
            )
        }

        items(scenarios) { sc ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
                    .border(1.dp, finColors.cardBorder, RoundedCornerShape(14.dp)),
                colors = CardDefaults.cardColors(containerColor = finColors.cardBackground)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = sc.first, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium, color = AmberGold)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = sc.second, style = MaterialTheme.typography.bodySmall, color = finColors.textSecondary, lineHeight = 20.sp)
                    Spacer(modifier = Modifier.height(10.dp))
                    Button(
                        onClick = {},
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = CyanAccent, contentColor = Color.Black)
                    ) {
                        Text("Simulate Scenario", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}
