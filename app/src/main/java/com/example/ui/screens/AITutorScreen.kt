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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ai.AiTutorService
import com.example.ui.components.EducationalDisclaimerCard
import com.example.ui.theme.CyanAccent
import com.example.ui.theme.EmeraldBullish
import com.example.ui.theme.LocalFinancialColors
import kotlinx.coroutines.launch

data class ChatMessage(
    val sender: String, // "Learner" or "AI Tutor"
    val content: String,
    val timestamp: Long = System.currentTimeMillis()
)

@Composable
fun AITutorScreen(
    modifier: Modifier = Modifier,
    onBack: (() -> Unit)? = null
) {
    val finColors = LocalFinancialColors.current
    val scope = rememberCoroutineScope()
    val listState = rememberLazyListState()

    var inputPrompt by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    val messages = remember {
        mutableStateListOf(
            ChatMessage(
                sender = "AI Tutor",
                content = "👋 Hello! I am your AI Financial Tutor at TradeInvest Academy (created by Awiskar Acharya).\n\n" +
                        "I can explain financial statements, candlestick price action, valuation ratios, risk management, and macroeconomics.\n\n" +
                        "Tap any prompt chip below or type your question!"
            )
        )
    }

    val quickChips = listOf(
        "Explain P/E Ratio",
        "How to read Candlesticks",
        "DCF Valuation Steps",
        "1% Risk Rule Formula",
        "Portfolio Rebalancing"
    )

    fun sendQuestion(text: String) {
        if (text.isBlank() || isLoading) return
        val userMsg = text.trim()
        inputPrompt = ""
        messages.add(ChatMessage("Learner", userMsg))
        isLoading = true

        scope.launch {
            val answer = AiTutorService.askTutor(userMsg)
            messages.add(ChatMessage("AI Tutor", answer))
            isLoading = false
            listState.animateScrollToItem(messages.size - 1)
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (onBack != null) {
                IconButton(onClick = onBack) {
                    Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = CyanAccent)
                }
            }

            Box(
                modifier = Modifier
                    .size(34.dp)
                    .background(CyanAccent.copy(alpha = 0.15f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(imageVector = Icons.Default.AutoAwesome, contentDescription = null, tint = CyanAccent, modifier = Modifier.size(18.dp))
            }

            Spacer(modifier = Modifier.width(10.dp))

            Column {
                Text(text = "AI Financial Learning Tutor", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Text(text = "Powered by Gemini 3.5 Flash • Educational Tutor", style = MaterialTheme.typography.labelSmall, color = finColors.textMuted)
            }
        }

        EducationalDisclaimerCard(
            text = "AI Tutor provides educational explanations • Not investment recommendations"
        )

        // Quick Topic Chips
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            quickChips.forEach { chip ->
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = finColors.cardBackground,
                    border = androidx.compose.foundation.BorderStroke(1.dp, finColors.cardBorder),
                    modifier = Modifier.clickable { sendQuestion(chip) }
                ) {
                    Text(
                        text = chip,
                        style = MaterialTheme.typography.labelSmall,
                        color = CyanAccent,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                    )
                }
            }
        }

        // Messages List
        LazyColumn(
            state = listState,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(messages) { msg ->
                val isUser = msg.sender == "Learner"

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = if (isUser) Arrangement.End else Arrangement.Start
                ) {
                    Surface(
                        shape = RoundedCornerShape(14.dp),
                        color = if (isUser) CyanAccent else finColors.cardBackground,
                        border = if (!isUser) androidx.compose.foundation.BorderStroke(1.dp, finColors.cardBorder) else null,
                        modifier = Modifier.fillMaxWidth(if (isUser) 0.8f else 0.95f)
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Text(
                                text = msg.sender,
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = if (isUser) Color.Black else CyanAccent
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = msg.content,
                                style = MaterialTheme.typography.bodySmall,
                                color = if (isUser) Color.Black else MaterialTheme.colorScheme.onBackground,
                                lineHeight = 20.sp
                            )
                        }
                    }
                }
            }

            if (isLoading) {
                item {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        CircularProgressIndicator(modifier = Modifier.size(18.dp), color = CyanAccent, strokeWidth = 2.dp)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "AI Tutor analyzing concept...", style = MaterialTheme.typography.bodySmall, color = finColors.textMuted)
                    }
                }
            }
        }

        // Input Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = inputPrompt,
                onValueChange = { inputPrompt = it },
                modifier = Modifier
                    .weight(1f)
                    .testTag("ai_tutor_input"),
                placeholder = { Text("Ask about any market concept, formula, ratio...") },
                shape = RoundedCornerShape(20.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = CyanAccent,
                    unfocusedBorderColor = finColors.cardBorder
                ),
                maxLines = 3
            )

            Spacer(modifier = Modifier.width(8.dp))

            IconButton(
                onClick = { sendQuestion(inputPrompt) },
                modifier = Modifier
                    .size(44.dp)
                    .background(CyanAccent, CircleShape)
                    .testTag("btn_send_ai_tutor")
            ) {
                Icon(imageVector = Icons.AutoMirrored.Filled.Send, contentDescription = "Send", tint = Color.Black)
            }
        }
    }
}
