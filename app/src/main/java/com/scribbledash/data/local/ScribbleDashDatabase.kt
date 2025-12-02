package com.scribbledash.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.scribbledash.core.presentation.utils.GameType
import com.scribbledash.core.presentation.utils.StatisticsType
import com.scribbledash.data.local.converter.UShortConverter
import com.scribbledash.data.local.dao.StatisticDao
import com.scribbledash.data.local.entity.StatisticEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [StatisticEntity::class], version = 1, exportSchema = false)
@TypeConverters(UShortConverter::class)
abstract class ScribbleDashDatabase : RoomDatabase() {

    abstract fun statisticDao(): StatisticDao

    companion object {
        @Volatile
        private var INSTANCE: ScribbleDashDatabase? = null

        fun getDatabase(context: Context): ScribbleDashDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ScribbleDashDatabase::class.java,
                    "scribbledash_database"
                )
                .addCallback(ScribbleDashDatabaseCallback(context))
                .build()
                INSTANCE = instance
                instance
            }
        }

        private class ScribbleDashDatabaseCallback(
            private val context: Context
        ) : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let {
                    CoroutineScope(Dispatchers.IO).launch {
                        prePopulateDatabase(it.statisticDao())
                    }
                }
            }

            suspend fun prePopulateDatabase(statisticDao: StatisticDao) {
                val statistics = listOf(
                    StatisticEntity(
                        gameMode = GameType.SPEED_DRAW.name,
                        type = StatisticsType.ACCURACY.name,
                        descriptionResName = "speed_draw_accuracy_description",
                        value = 0f,
                        displayOrder = 0u,
                        iconResName = "ic_hourglass"
                    ),
                    StatisticEntity(
                        gameMode = GameType.SPEED_DRAW.name,
                        type = StatisticsType.COUNT.name,
                        descriptionResName = "speed_draw_count_description",
                        value = 0f,
                        displayOrder = 1u,
                        iconResName = "ic_bolt"
                    ),
                    StatisticEntity(
                        gameMode = GameType.ENDLESS.name,
                        type = StatisticsType.ACCURACY.name,
                        descriptionResName = "endless_accuracy_description",
                        value = 0f,
                        displayOrder = 2u,
                        iconResName = "ic_high_score"
                    ),
                    StatisticEntity(
                        gameMode = GameType.ENDLESS.name,
                        type = StatisticsType.COUNT.name,
                        descriptionResName = "endless_count_description",
                        value = 0f,
                        displayOrder = 3u,
                        iconResName = "ic_palette"
                    )
                )
                statisticDao.insertAll(statistics)
            }
        }
    }
}
