package com.example.ui.screens

import android.content.Intent
import android.net.Uri
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Launch
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.CertificateInfo
import com.example.ui.components.EducationalDisclaimerCard
import com.example.ui.components.SectionHeader
import com.example.ui.theme.AmberGold
import com.example.ui.theme.AppThemeMode
import com.example.ui.theme.CyanAccent
import com.example.ui.theme.EmeraldBullish
import com.example.ui.theme.FinancialIconType
import com.example.ui.theme.FinancialVectorIcon
import com.example.ui.theme.IndigoBlue
import com.example.ui.theme.LocalFinancialColors
import com.example.ui.theme.TradeInvestLogo

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    certificates: List<CertificateInfo>,
    currentThemeMode: AppThemeMode,
    onChangeThemeMode: (AppThemeMode) -> Unit
) {
    val context = LocalContext.current
    val finColors = LocalFinancialColors.current

    var selectedCertificate by remember { mutableStateOf<CertificateInfo?>(null) }
    var showCareerCenter by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(14.dp))

            // Profile Header Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, finColors.cardBorder, RoundedCornerShape(20.dp)),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = finColors.cardBackground)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        TradeInvestLogo(size = 52.dp)
                        Spacer(modifier = Modifier.width(14.dp))
                        Column {
                            Text(
                                text = "Awiskar Acharya",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                            Text(
                                text = "TradeInvest Academy Creator & Market Student",
                                style = MaterialTheme.typography.bodySmall,
                                color = CyanAccent
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Stats row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        ProfileStatItem("XP EARNED", "2,450 XP", EmeraldBullish)
                        ProfileStatItem("STREAK", "14 Days", AmberGold)
                        ProfileStatItem("TRACKS", "12 Done", CyanAccent)
                        ProfileStatItem("LEVEL", "Intermediate", IndigoBlue)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Creator Attribution & LinkedIn Section
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, CyanAccent.copy(alpha = 0.4f), RoundedCornerShape(16.dp)),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = finColors.cardBackground)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Created by Awiskar Acharya",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = CyanAccent
                        )
                        IconButton(
                            onClick = {
                                try {
                                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/in/awiskaracharya/"))
                                    context.startActivity(intent)
                                } catch (_: Exception) {}
                            }
                        ) {
                            Icon(imageVector = Icons.Default.Launch, contentDescription = "LinkedIn", tint = CyanAccent, modifier = Modifier.size(18.dp))
                        }
                    }

                    Text(
                        text = "TradeInvest Academy was designed to bridge the gap between academic finance theory, professional trading terminals, and real-world capital risk management. Learn. Analyze. Practice. Invest Smarter.",
                        style = MaterialTheme.typography.bodySmall,
                        color = finColors.textSecondary,
                        lineHeight = 18.sp
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Button(
                        onClick = {
                            try {
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/in/awiskaracharya/"))
                                context.startActivity(intent)
                            } catch (_: Exception) {}
                        },
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = CyanAccent, contentColor = Color.Black)
                    ) {
                        Text("Connect on LinkedIn", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Theme Mode Selector
            SectionHeader(title = "Display Theme Settings")
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, finColors.cardBorder, RoundedCornerShape(16.dp)),
                colors = CardDefaults.cardColors(containerColor = finColors.cardBackground)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(text = "Theme Palette", style = MaterialTheme.typography.labelSmall, color = finColors.textMuted)
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        ThemeOptionButton("Dark", currentThemeMode == AppThemeMode.DARK) { onChangeThemeMode(AppThemeMode.DARK) }
                        ThemeOptionButton("Light", currentThemeMode == AppThemeMode.LIGHT) { onChangeThemeMode(AppThemeMode.LIGHT) }
                        ThemeOptionButton("AMOLED", currentThemeMode == AppThemeMode.AMOLED) { onChangeThemeMode(AppThemeMode.AMOLED) }
                        ThemeOptionButton("Contrast", currentThemeMode == AppThemeMode.HIGH_CONTRAST) { onChangeThemeMode(AppThemeMode.HIGH_CONTRAST) }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Earned Certificates (Prompt Section 133)
            SectionHeader(
                title = "Verified Certificates",
                subtitle = "Proof of completed financial education curricula"
            )
        }

        items(certificates) { cert ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 5.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .clickable { selectedCertificate = cert }
                    .border(1.dp, if (cert.isUnlocked) EmeraldBullish.copy(alpha = 0.5f) else finColors.cardBorder, RoundedCornerShape(14.dp)),
                colors = CardDefaults.cardColors(containerColor = finColors.cardBackground)
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .background(if (cert.isUnlocked) EmeraldBullish.copy(alpha = 0.15f) else finColors.cardBorder, RoundedCornerShape(10.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        FinancialVectorIcon(
                            iconType = FinancialIconType.CERTIFICATE,
                            tint = if (cert.isUnlocked) EmeraldBullish else finColors.textMuted,
                            size = 20.dp
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = cert.title, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleSmall)
                        Text(text = "${cert.category} • ${if (cert.isUnlocked) "Issued: ${cert.issueDate}" else "Locked (${cert.requiredProgress}% required)"}", style = MaterialTheme.typography.labelSmall, color = finColors.textMuted)
                    }

                    if (cert.isUnlocked) {
                        Surface(shape = RoundedCornerShape(4.dp), color = EmeraldBullish.copy(alpha = 0.15f)) {
                            Text(text = "VERIFIED", fontWeight = FontWeight.Bold, fontSize = 10.sp, color = EmeraldBullish, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
                        }
                    }
                }
            }
        }

        // Finance Career Center & Interview Preparation
        item {
            Spacer(modifier = Modifier.height(16.dp))
            SectionHeader(
                title = "Career Center & Interview Prep",
                subtitle = "Master financial analyst and quant interview questions"
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .clickable { showCareerCenter = !showCareerCenter }
                    .border(1.dp, IndigoBlue.copy(alpha = 0.4f), RoundedCornerShape(14.dp)),
                colors = CardDefaults.cardColors(containerColor = finColors.cardBackground)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Default.Work, contentDescription = null, tint = IndigoBlue, modifier = Modifier.size(20.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = "Finance Analyst Interview Questions", fontWeight = FontWeight.Bold)
                        }
                        Text(text = if (showCareerCenter) "Hide" else "Explore", color = CyanAccent, fontWeight = FontWeight.SemiBold, fontSize = 12.sp)
                    }

                    if (showCareerCenter) {
                        Spacer(modifier = Modifier.height(12.dp))
                        InterviewQnaItem(
                            question = "Q: If Enterprise Value is $100M, Debt is $20M, and Cash is $5M, what is Equity Value?",
                            answer = "Equity Value = Enterprise Value - Total Debt + Cash = $100M - $20M + $5M = $85M."
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        InterviewQnaItem(
                            question = "Q: How does a $10 increase in depreciation affect the three statements (tax rate 20%)?",
                            answer = "Income Statement: Operating Income drops by $10, Net Income drops by $8 ($10 - $2 tax). Cash Flow: Net income -$8 + non-cash depreciation +$10 = Net cash increases by $2. Balance sheet: Cash up $2, PP&E down $10, Equity down $8."
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(30.dp))
        }
    }

    // Certificate Viewer Dialog
    if (selectedCertificate != null) {
        val cert = selectedCertificate!!
        AlertDialog(
            onDismissRequest = { selectedCertificate = null },
            title = {
                Text(text = "Official Certificate of Completion", fontWeight = FontWeight.Bold)
            },
            text = {
                Card(
                    modifier = Modifier.fillMaxWidth().border(2.dp, AmberGold, RoundedCornerShape(16.dp)),
                    colors = CardDefaults.cardColors(containerColor = finColors.cardBackground)
                ) {
                    Column(
                        modifier = Modifier.padding(18.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        TradeInvestLogo(size = 40.dp)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "TRADEINVEST ACADEMY", fontWeight = FontWeight.Bold, letterSpacing = 2.sp, fontSize = 12.sp, color = AmberGold)
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(text = "This certifies that", style = MaterialTheme.typography.bodySmall, color = finColors.textMuted)
                        Text(text = "Awiskar Acharya", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = CyanAccent)
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(text = "has successfully demonstrated practical mastery in", style = MaterialTheme.typography.bodySmall, color = finColors.textMuted)
                        Text(text = cert.title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(text = "Issued: ${cert.issueDate} • Instructor: Awiskar Acharya", style = MaterialTheme.typography.labelSmall, color = finColors.textSecondary)
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = { selectedCertificate = null },
                    colors = ButtonDefaults.buttonColors(containerColor = CyanAccent, contentColor = Color.Black)
                ) {
                    Text("Close")
                }
            }
        )
    }
}

@Composable
private fun ProfileStatItem(label: String, value: String, color: Color) {
    val finColors = LocalFinancialColors.current
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = label, style = MaterialTheme.typography.labelSmall, color = finColors.textMuted, fontSize = 9.sp)
        Spacer(modifier = Modifier.height(2.dp))
        Text(text = value, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = color)
    }
}

@Composable
private fun ThemeOptionButton(label: String, isSelected: Boolean, onClick: () -> Unit) {
    val finColors = LocalFinancialColors.current
    Surface(
        shape = RoundedCornerShape(8.dp),
        color = if (isSelected) CyanAccent else finColors.cardBackground,
        border = androidx.compose.foundation.BorderStroke(1.dp, if (isSelected) CyanAccent else finColors.cardBorder),
        modifier = Modifier.clickable { onClick() }
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
            color = if (isSelected) Color.Black else finColors.textSecondary,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
        )
    }
}

@Composable
private fun InterviewQnaItem(question: String, answer: String) {
    val finColors = LocalFinancialColors.current
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Text(text = question, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodySmall, color = CyanAccent)
        Spacer(modifier = Modifier.height(2.dp))
        Text(text = answer, style = MaterialTheme.typography.bodySmall, color = finColors.textSecondary, lineHeight = 18.sp)
    }
}
