package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.data.AcademyRepository
import com.example.model.Asset
import com.example.model.OrderAction
import com.example.model.OrderType
import com.example.model.PaperOrder
import com.example.model.PaperPosition
import com.example.model.TradeJournalEntry
import com.example.ui.components.AppDestination
import com.example.ui.components.TradeInvestBottomBar
import com.example.ui.components.TradeInvestTopHeader
import com.example.ui.screens.AITutorScreen
import com.example.ui.screens.CalculatorsScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.InteractiveLabsScreen
import com.example.ui.screens.LearnScreen
import com.example.ui.screens.MarketsScreen
import com.example.ui.screens.PracticeScreen
import com.example.ui.screens.ProfileScreen
import com.example.ui.theme.AppThemeMode
import com.example.ui.theme.TradeInvestTheme
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(private val repository: AcademyRepository) : ViewModel() {
    val cashBalance: StateFlow<Double> = repository.simulatedCash
    val positions: StateFlow<Map<String, PaperPosition>> = repository.positions
    val courses = repository.courses
    val assets = repository.sampleAssets
    val dailyChallenge = repository.dailyChallenges.first()
    val flashcards = repository.flashcards
    val glossary = repository.glossary
    val certificates = repository.certificates
    val skillTreeNodes = repository.skillTree

    suspend fun executeOrder(asset: Asset, action: OrderAction, type: OrderType, qty: Double) {
        repository.executePaperOrder(asset, action, type, qty)
    }

    suspend fun addJournal(entry: TradeJournalEntry) {
        repository.addJournalEntry(entry)
    }
}

class MainActivity : ComponentActivity() {

    private lateinit var repository: AcademyRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        repository = AcademyRepository(this)

        setContent {
            var themeMode by remember { mutableStateOf(AppThemeMode.DARK) }

            TradeInvestTheme(themeMode = themeMode) {
                MainAppContainer(
                    repository = repository,
                    currentThemeMode = themeMode,
                    onThemeModeChange = { themeMode = it }
                )
            }
        }
    }
}

@Composable
fun MainAppContainer(
    repository: AcademyRepository,
    currentThemeMode: AppThemeMode,
    onThemeModeChange: (AppThemeMode) -> Unit
) {
    val scope = rememberCoroutineScope()

    var currentDestination by remember { mutableStateOf(AppDestination.HOME) }
    var selectedCourseId by remember { mutableStateOf<String?>(null) }
    var selectedAssetSymbol by remember { mutableStateOf<String?>(null) }
    var selectedLab by remember { mutableStateOf("candlestick") }
    var showAiTutorScreen by remember { mutableStateOf(false) }

    val cash by repository.simulatedCash.collectAsState()
    val positions by repository.positions.collectAsState()
    val orders by repository.paperOrders.collectAsState(initial = emptyList())
    val journalEntries by repository.journalEntries.collectAsState(initial = emptyList())

    // Back handling protocol
    BackHandler(
        enabled = showAiTutorScreen || selectedCourseId != null || selectedAssetSymbol != null || currentDestination != AppDestination.HOME
    ) {
        when {
            showAiTutorScreen -> showAiTutorScreen = false
            selectedCourseId != null -> selectedCourseId = null
            selectedAssetSymbol != null -> selectedAssetSymbol = null
            currentDestination != AppDestination.HOME -> currentDestination = AppDestination.HOME
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            if (!showAiTutorScreen) {
                TradeInvestTopHeader(
                    modifier = Modifier.statusBarsPadding(),
                    learnerName = "Awiskar",
                    onOpenAiTutor = { showAiTutorScreen = true },
                    onToggleTheme = {
                        val nextTheme = when (currentThemeMode) {
                            AppThemeMode.DARK -> AppThemeMode.LIGHT
                            AppThemeMode.LIGHT -> AppThemeMode.AMOLED
                            AppThemeMode.AMOLED -> AppThemeMode.HIGH_CONTRAST
                            AppThemeMode.HIGH_CONTRAST -> AppThemeMode.DARK
                        }
                        onThemeModeChange(nextTheme)
                    },
                    isDark = currentThemeMode != AppThemeMode.LIGHT
                )
            }
        },
        bottomBar = {
            if (!showAiTutorScreen) {
                TradeInvestBottomBar(
                    currentDestination = currentDestination,
                    onNavigate = { dest ->
                        currentDestination = dest
                        selectedCourseId = null
                        selectedAssetSymbol = null
                    }
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.background)
        ) {
            if (showAiTutorScreen) {
                AITutorScreen(
                    onBack = { showAiTutorScreen = false }
                )
            } else {
                when (currentDestination) {
                    AppDestination.HOME -> {
                        HomeScreen(
                            courses = repository.courses,
                            assets = repository.sampleAssets,
                            dailyChallenge = repository.dailyChallenges.first(),
                            onNavigateToCourse = { cId ->
                                selectedCourseId = cId
                                currentDestination = AppDestination.LEARN
                            },
                            onNavigateToAsset = { sym ->
                                selectedAssetSymbol = sym
                                currentDestination = AppDestination.MARKETS
                            },
                            onNavigateToRoadmap = {
                                currentDestination = AppDestination.LEARN
                            },
                            onNavigateToLab = { labName ->
                                selectedLab = labName
                                currentDestination = AppDestination.LABS
                            },
                            onNavigateToPaperTrading = {
                                currentDestination = AppDestination.PRACTICE
                            },
                            onNavigateToAiTutor = {
                                showAiTutorScreen = true
                            }
                        )
                    }

                    AppDestination.LEARN -> {
                        LearnScreen(
                            courses = repository.courses,
                            flashcards = repository.flashcards,
                            glossary = repository.glossary,
                            skillTreeNodes = repository.skillTree,
                            selectedCourseId = selectedCourseId,
                            onSelectCourse = { selectedCourseId = it }
                        )
                    }

                    AppDestination.MARKETS -> {
                        MarketsScreen(
                            assets = repository.sampleAssets,
                            selectedAssetSymbol = selectedAssetSymbol,
                            onSelectAsset = { selectedAssetSymbol = it },
                            onSimulateOrder = { asset ->
                                currentDestination = AppDestination.PRACTICE
                            }
                        )
                    }

                    AppDestination.LABS -> {
                        InteractiveLabsScreen(
                            initialLab = selectedLab,
                            onBack = { currentDestination = AppDestination.HOME }
                        )
                    }

                    AppDestination.CALCULATORS -> {
                        CalculatorsScreen()
                    }

                    AppDestination.PRACTICE -> {
                        PracticeScreen(
                            cashBalance = cash,
                            positions = positions,
                            orders = orders,
                            journalEntries = journalEntries,
                            assets = repository.sampleAssets,
                            onExecuteOrder = { asset, action, type, qty ->
                                scope.launch {
                                    repository.executePaperOrder(asset, action, type, qty)
                                }
                            },
                            onAddJournalEntry = { entry ->
                                scope.launch {
                                    repository.addJournalEntry(entry)
                                }
                            }
                        )
                    }

                    AppDestination.PROFILE -> {
                        ProfileScreen(
                            certificates = repository.certificates,
                            currentThemeMode = currentThemeMode,
                            onChangeThemeMode = onThemeModeChange
                        )
                    }
                }
            }
        }
    }
}
