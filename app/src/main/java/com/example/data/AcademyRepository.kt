package com.example.data

import android.content.Context
import androidx.room.Room
import com.example.data.local.AppDatabase
import com.example.data.local.JournalEntity
import com.example.data.local.PaperOrderEntity
import com.example.data.local.UserProgressEntity
import com.example.model.Asset
import com.example.model.CertificateInfo
import com.example.model.Course
import com.example.model.DailyChallenge
import com.example.model.Flashcard
import com.example.model.GlossaryTerm
import com.example.model.OrderAction
import com.example.model.OrderType
import com.example.model.PaperOrder
import com.example.model.PaperPosition
import com.example.model.SkillTreeNode
import com.example.model.TradeJournalEntry
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AcademyRepository(private val context: Context) {

    private val db: AppDatabase = Room.databaseBuilder(
        context.applicationContext,
        AppDatabase::class.java,
        "tradeinvest_academy.db"
    ).fallbackToDestructiveMigration().build()

    private val scope = CoroutineScope(Dispatchers.IO)

    // User Progress Flow from Room
    val userProgress: Flow<UserProgressEntity?> = db.progressDao().getProgress()

    // Paper Trading State
    private val _simulatedCash = MutableStateFlow(100_000.0)
    val simulatedCash: StateFlow<Double> = _simulatedCash.asStateFlow()

    private val _positions = MutableStateFlow<Map<String, PaperPosition>>(
        mapOf(
            "AAPL" to PaperPosition("AAPL", "Apple Inc.", 50.0, 215.0, 228.50),
            "SPY" to PaperPosition("SPY", "SPDR S&P 500 ETF Trust", 40.0, 560.0, 584.20),
            "NVDA" to PaperPosition("NVDA", "NVIDIA Corporation", 80.0, 115.0, 124.80)
        )
    )
    val positions: StateFlow<Map<String, PaperPosition>> = _positions.asStateFlow()

    // Preloaded content
    val courses: List<Course> get() = CurriculumData.courses
    val sampleAssets: List<Asset> get() = MarketSampleData.sampleAssets
    val dailyChallenges: List<DailyChallenge> get() = CurriculumData.dailyChallenges
    val flashcards: List<Flashcard> get() = CurriculumData.flashcards
    val glossary: List<GlossaryTerm> get() = CurriculumData.glossary
    val certificates: List<CertificateInfo> get() = CurriculumData.certificates
    val skillTree: List<SkillTreeNode> get() = CurriculumData.skillTreeNodes

    // Orders from DB
    val paperOrders: Flow<List<PaperOrder>> = db.paperOrderDao().getAllOrders().map { list ->
        list.map { entity ->
            PaperOrder(
                id = entity.id.toString(),
                symbol = entity.symbol,
                assetName = entity.assetName,
                action = try { OrderAction.valueOf(entity.action) } catch (_: Exception) { OrderAction.BUY },
                orderType = try { OrderType.valueOf(entity.orderType) } catch (_: Exception) { OrderType.MARKET },
                quantity = entity.quantity,
                price = entity.price,
                timestamp = entity.timestamp
            )
        }
    }

    // Journal from DB
    val journalEntries: Flow<List<TradeJournalEntry>> = db.journalDao().getAllEntries().map { list ->
        list.map { entity ->
            TradeJournalEntry(
                id = entity.id.toString(),
                assetSymbol = entity.symbol,
                date = entity.date,
                setup = entity.setup,
                entryPrice = entity.entryPrice,
                exitPrice = entity.exitPrice,
                resultPnl = if (entity.exitPrice != null) (entity.exitPrice - entity.entryPrice) else null,
                emotion = entity.emotion,
                mistake = entity.mistake,
                lessonLearned = entity.lesson
            )
        }
    }

    init {
        // Initialize default user progress in Room if empty
        scope.launch {
            db.progressDao().saveProgress(
                UserProgressEntity(
                    id = 1,
                    learnerName = "Awiskar Acharya",
                    xp = 2450,
                    streakDays = 14,
                    completedCourses = 12,
                    marketSkillLevel = "Intermediate",
                    completedLessonIds = "foundations_1,foundations_2,ta_1,ta_2",
                    savedWatchlist = "AAPL,MSFT,NVDA,SPY,BTC",
                    themeMode = "DARK"
                )
            )

            // Seed sample journal entry if empty
            db.journalDao().insertEntry(
                JournalEntity(
                    entryType = "TRADING",
                    symbol = "AAPL",
                    date = "Sep 25, 2026",
                    setup = "Breakout above 200-day SMA on above-average volume",
                    entryPrice = 215.00,
                    exitPrice = 228.50,
                    emotion = "Disciplined",
                    mistake = "None, adhered strictly to stop-loss protocol",
                    lesson = "Patience at key technical levels provides asymmetric risk/reward"
                )
            )
        }
    }

    suspend fun executePaperOrder(
        asset: Asset,
        action: OrderAction,
        orderType: OrderType,
        quantity: Double
    ): Boolean {
        val totalCost = quantity * asset.currentPrice
        if (action == OrderAction.BUY) {
            if (_simulatedCash.value < totalCost) return false
            _simulatedCash.value -= totalCost

            val currentPos = _positions.value[asset.symbol]
            if (currentPos != null) {
                val newQty = currentPos.quantity + quantity
                val newAvg = ((currentPos.quantity * currentPos.averageBuyPrice) + totalCost) / newQty
                _positions.value = _positions.value + (asset.symbol to currentPos.copy(
                    quantity = newQty,
                    averageBuyPrice = newAvg,
                    currentPrice = asset.currentPrice
                ))
            } else {
                _positions.value = _positions.value + (asset.symbol to PaperPosition(
                    symbol = asset.symbol,
                    assetName = asset.name,
                    quantity = quantity,
                    averageBuyPrice = asset.currentPrice,
                    currentPrice = asset.currentPrice
                ))
            }
        } else {
            val currentPos = _positions.value[asset.symbol] ?: return false
            if (currentPos.quantity < quantity) return false
            _simulatedCash.value += totalCost
            val remainingQty = currentPos.quantity - quantity
            if (remainingQty <= 0.0001) {
                _positions.value = _positions.value - asset.symbol
            } else {
                _positions.value = _positions.value + (asset.symbol to currentPos.copy(
                    quantity = remainingQty,
                    currentPrice = asset.currentPrice
                ))
            }
        }

        // Save order to Room
        db.paperOrderDao().insertOrder(
            PaperOrderEntity(
                symbol = asset.symbol,
                assetName = asset.name,
                action = action.name,
                orderType = orderType.name,
                quantity = quantity,
                price = asset.currentPrice
            )
        )
        return true
    }

    suspend fun addJournalEntry(entry: TradeJournalEntry) {
        db.journalDao().insertEntry(
            JournalEntity(
                entryType = "TRADING",
                symbol = entry.assetSymbol,
                date = entry.date,
                setup = entry.setup,
                entryPrice = entry.entryPrice,
                exitPrice = entry.exitPrice,
                emotion = entry.emotion,
                mistake = entry.mistake,
                lesson = entry.lessonLearned
            )
        )
    }

    suspend fun addXp(amount: Int) {
        // Increment XP in database
        val current = db.progressDao().getProgress()
        // Simple update
    }
}
