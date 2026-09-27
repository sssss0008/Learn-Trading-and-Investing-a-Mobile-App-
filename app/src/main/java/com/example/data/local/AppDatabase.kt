package com.example.data.local

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "user_progress")
data class UserProgressEntity(
    @PrimaryKey val id: Int = 1,
    val learnerName: String = "Awiskar Acharya",
    val xp: Int = 2450,
    val streakDays: Int = 14,
    val completedCourses: Int = 12,
    val marketSkillLevel: String = "Intermediate",
    val completedLessonIds: String = "foundations_1,foundations_2,ta_1,ta_2",
    val savedWatchlist: String = "AAPL,MSFT,NVDA,SPY,BTC",
    val themeMode: String = "DARK"
)

@Entity(tableName = "paper_orders")
data class PaperOrderEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val symbol: String,
    val assetName: String,
    val action: String, // BUY, SELL
    val orderType: String, // MARKET, LIMIT, STOP_LOSS
    val quantity: Double,
    val price: Double,
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "journal_entries")
data class JournalEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val entryType: String, // TRADING or INVESTMENT
    val symbol: String,
    val date: String,
    val setup: String,
    val entryPrice: Double,
    val exitPrice: Double? = null,
    val emotion: String,
    val mistake: String,
    val lesson: String,
    val timestamp: Long = System.currentTimeMillis()
)

@Dao
interface ProgressDao {
    @Query("SELECT * FROM user_progress WHERE id = 1")
    fun getProgress(): Flow<UserProgressEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveProgress(progress: UserProgressEntity)
}

@Dao
interface PaperOrderDao {
    @Query("SELECT * FROM paper_orders ORDER BY timestamp DESC")
    fun getAllOrders(): Flow<List<PaperOrderEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrder(order: PaperOrderEntity)

    @Query("DELETE FROM paper_orders")
    suspend fun clearOrders()
}

@Dao
interface JournalDao {
    @Query("SELECT * FROM journal_entries ORDER BY timestamp DESC")
    fun getAllEntries(): Flow<List<JournalEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEntry(entry: JournalEntity)

    @Query("DELETE FROM journal_entries WHERE id = :id")
    suspend fun deleteEntry(id: Long)
}

@Database(
    entities = [
        UserProgressEntity::class,
        PaperOrderEntity::class,
        JournalEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun progressDao(): ProgressDao
    abstract fun paperOrderDao(): PaperOrderDao
    abstract fun journalDao(): JournalDao
}
