package com.scribbledash.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.scribbledash.R
import com.scribbledash.core.presentation.utils.GameType
import com.scribbledash.core.presentation.utils.ShopItemTier
import com.scribbledash.core.presentation.utils.ShopItemType
import com.scribbledash.core.presentation.utils.StatisticsType
import com.scribbledash.data.local.converter.UShortConverter
import com.scribbledash.data.local.dao.ShopItemDao
import com.scribbledash.data.local.dao.StatisticDao
import com.scribbledash.data.local.dao.WalletDao
import com.scribbledash.data.local.entity.ShopItemEntity
import com.scribbledash.data.local.entity.StatisticsEntity
import com.scribbledash.data.local.entity.WalletEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [
        StatisticsEntity::class,
        WalletEntity::class,
        ShopItemEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(UShortConverter::class)
abstract class ScribbleDashDatabase : RoomDatabase() {

    abstract fun statisticDao(): StatisticDao
    abstract fun walletDao(): WalletDao
    abstract fun shopItemDao(): ShopItemDao

    companion object {
        @Volatile
        private var INSTANCE: ScribbleDashDatabase? = null

        fun getDatabase(context: Context): ScribbleDashDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ScribbleDashDatabase::class.java,
                    "scribbledash_database"
                ).addCallback(ScribbleDashDatabaseCallback(CoroutineScope(Dispatchers.IO)))
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private class ScribbleDashDatabaseCallback(
            private val scope: CoroutineScope,
        ) : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                scope.launch {
                    INSTANCE?.let { database ->
                        prePopulateDatabase(
                            database.statisticDao(),
                            database.walletDao(),
                            database.shopItemDao()  // ✅ Add shop items
                        )
                    }
                }
            }

            suspend fun prePopulateDatabase(
                statisticDao: StatisticDao,
                walletDao: WalletDao,
                shopItemDao: ShopItemDao  // ✅ Add parameter
            ) {
                // Existing statistics
                val statistics = listOf(
                    StatisticsEntity(
                        gameMode = GameType.SPEED_DRAW.name,
                        type = StatisticsType.ACCURACY.name,
                        descriptionResName = "speed_draw_accuracy_description",
                        value = 0f,
                        displayOrder = 0u,
                        iconResName = "ic_hourglass"
                    ),
                    StatisticsEntity(
                        gameMode = GameType.SPEED_DRAW.name,
                        type = StatisticsType.COUNT.name,
                        descriptionResName = "speed_draw_count_description",
                        value = 0f,
                        displayOrder = 1u,
                        iconResName = "ic_bolt"
                    ),
                    StatisticsEntity(
                        gameMode = GameType.ENDLESS.name,
                        type = StatisticsType.ACCURACY.name,
                        descriptionResName = "endless_accuracy_description",
                        value = 0f,
                        displayOrder = 2u,
                        iconResName = "ic_high_score"
                    ),
                    StatisticsEntity(
                        gameMode = GameType.ENDLESS.name,
                        type = StatisticsType.COUNT.name,
                        descriptionResName = "endless_count_description",
                        value = 0f,
                        displayOrder = 3u,
                        iconResName = "ic_palette"
                    )
                )
                statisticDao.insertAll(statistics)

                // Existing wallet
                walletDao.insertWallet(WalletEntity(id = 1, coins = 0))

                // ✅ NEW: Pre-populate shop items
                val shopItems = createDefaultShopItems()
                shopItemDao.insertAll(shopItems)
            }

            /**
             * Creates all default shop items (29 items total)
             */
            private fun createDefaultShopItems(): List<ShopItemEntity> {
                val items = mutableListOf<ShopItemEntity>()

                // ========== PEN COLORS ==========

                // Basic Pens (20 coins each)
                items.add(
                    ShopItemEntity(
                        id = "pen_midnight_black",
                        name = "Midnight Black",
                        type = ShopItemType.PEN_COLOR.name,
                        tier = ShopItemTier.BASIC.name,
                        colorHex = "#101820",
                        cost = 0,
                        isDefault = true,
                        isPurchased = true,
                        isSelected = true
                    )
                )
                items.add(
                    ShopItemEntity(
                        id = "pen_crimson_red",
                        name = "Crimson Red",
                        type = ShopItemType.PEN_COLOR.name,
                        tier = ShopItemTier.BASIC.name,
                        colorHex = "#B22234",
                        cost = 20,
                        isDefault = false,
                        isPurchased = false,
                        isSelected = false
                    )
                )
                items.add(
                    ShopItemEntity(
                        id = "pen_sunshine_yellow",
                        name = "Sunshine Yellow",
                        type = ShopItemType.PEN_COLOR.name,
                        tier = ShopItemTier.BASIC.name,
                        colorHex = "#F9D85D",
                        cost = 20,
                        isDefault = false,
                        isPurchased = false,
                        isSelected = false
                    )
                )
                items.add(
                    ShopItemEntity(
                        id = "pen_ocean_blue",
                        name = "Ocean Blue",
                        type = ShopItemType.PEN_COLOR.name,
                        tier = ShopItemTier.BASIC.name,
                        colorHex = "#1D4E89",
                        cost = 20,
                        isDefault = false,
                        isPurchased = false,
                        isSelected = false
                    )
                )
                items.add(
                    ShopItemEntity(
                        id = "pen_emerald_green",
                        name = "Emerald Green",
                        type = ShopItemType.PEN_COLOR.name,
                        tier = ShopItemTier.BASIC.name,
                        colorHex = "#4CAF50",
                        cost = 20,
                        isDefault = false,
                        isPurchased = false,
                        isSelected = false
                    )
                )
                items.add(
                    ShopItemEntity(
                        id = "pen_flame_orange",
                        name = "Flame Orange",
                        type = ShopItemType.PEN_COLOR.name,
                        tier = ShopItemTier.BASIC.name,
                        colorHex = "#F57F20",
                        cost = 20,
                        isDefault = false,
                        isPurchased = false,
                        isSelected = false
                    )
                )

                // Premium Pens (120 coins each)
                items.add(
                    ShopItemEntity(
                        id = "pen_rose_quartz",
                        name = "Rose Quartz",
                        type = ShopItemType.PEN_COLOR.name,
                        tier = ShopItemTier.PREMIUM.name,
                        colorHex = "#F4A6B8",
                        cost = 120,
                        isDefault = false,
                        isPurchased = false,
                        isSelected = false
                    )
                )
                items.add(
                    ShopItemEntity(
                        id = "pen_royal_purple",
                        name = "Royal Purple",
                        type = ShopItemType.PEN_COLOR.name,
                        tier = ShopItemTier.PREMIUM.name,
                        colorHex = "#6A0FAB",
                        cost = 120,
                        isDefault = false,
                        isPurchased = false,
                        isSelected = false
                    )
                )
                items.add(
                    ShopItemEntity(
                        id = "pen_teal_dream",
                        name = "Teal Dream",
                        type = ShopItemType.PEN_COLOR.name,
                        tier = ShopItemTier.PREMIUM.name,
                        colorHex = "#008C92",
                        cost = 120,
                        isDefault = false,
                        isPurchased = false,
                        isSelected = false
                    )
                )
                items.add(
                    ShopItemEntity(
                        id = "pen_golden_glow",
                        name = "Golden Glow",
                        type = ShopItemType.PEN_COLOR.name,
                        tier = ShopItemTier.PREMIUM.name,
                        colorHex = "#FFD700",
                        cost = 120,
                        isDefault = false,
                        isPurchased = false,
                        isSelected = false
                    )
                )
                items.add(
                    ShopItemEntity(
                        id = "pen_coral_reef",
                        name = "Coral Reef",
                        type = ShopItemType.PEN_COLOR.name,
                        tier = ShopItemTier.PREMIUM.name,
                        colorHex = "#FF6F61",
                        cost = 120,
                        isDefault = false,
                        isPurchased = false,
                        isSelected = false
                    )
                )
                items.add(
                    ShopItemEntity(
                        id = "pen_majestic_indigo",
                        name = "Majestic Indigo",
                        type = ShopItemType.PEN_COLOR.name,
                        tier = ShopItemTier.PREMIUM.name,
                        colorHex = "#4B0082",
                        cost = 120,
                        isDefault = false,
                        isPurchased = false,
                        isSelected = false
                    )
                )
                items.add(
                    ShopItemEntity(
                        id = "pen_copper_aura",
                        name = "Copper Aura",
                        type = ShopItemType.PEN_COLOR.name,
                        tier = ShopItemTier.PREMIUM.name,
                        colorHex = "#B87333",
                        cost = 120,
                        isDefault = false,
                        isPurchased = false,
                        isSelected = false
                    )
                )

                items.add(
                    ShopItemEntity(
                        id = "pen_rainbow",
                        name = "Rainbow Pen",
                        type = ShopItemType.PEN_COLOR.name,
                        tier = ShopItemTier.LEGENDARY.name,
                        colorHex = "#FF0000",
                        cost = 999,
                        isDefault = false,
                        isPurchased = false,
                        isSelected = false,
                        isGradient = true,
                        gradientColors = "#FF0000,#FFA500,#FFFF00,#008000,#00EEFF,#0000FF,#FB02FB"
                    )
                )

                items.add(
                    ShopItemEntity(
                        id = "canvas_white",
                        name = "White",
                        type = ShopItemType.CANVAS_BACKGROUND.name,
                        tier = ShopItemTier.BASIC.name,
                        colorHex = "#FFFFFF",
                        cost = 0,
                        isDefault = true,
                        isPurchased = true,
                        isSelected = true
                    )
                )
                items.add(
                    ShopItemEntity(
                        id = "canvas_light_gray",
                        name = "Light Gray",
                        type = ShopItemType.CANVAS_BACKGROUND.name,
                        tier = ShopItemTier.BASIC.name,
                        colorHex = "#E0E0E0",
                        cost = 80,
                        isDefault = false,
                        isPurchased = false,
                        isSelected = false
                    )
                )
                items.add(
                    ShopItemEntity(
                        id = "canvas_pale_beige",
                        name = "Pale Beige",
                        type = ShopItemType.CANVAS_BACKGROUND.name,
                        tier = ShopItemTier.BASIC.name,
                        colorHex = "#F5F5DC",
                        cost = 80,
                        isDefault = false,
                        isPurchased = false,
                        isSelected = false
                    )
                )
                items.add(
                    ShopItemEntity(
                        id = "canvas_powder_blue",
                        name = "Soft Powder Blue",
                        type = ShopItemType.CANVAS_BACKGROUND.name,
                        tier = ShopItemTier.BASIC.name,
                        colorHex = "#B0C4DE",
                        cost = 80,
                        isDefault = false,
                        isPurchased = false,
                        isSelected = false
                    )
                )
                items.add(
                    ShopItemEntity(
                        id = "canvas_sage_green",
                        name = "Light Sage Green",
                        type = ShopItemType.CANVAS_BACKGROUND.name,
                        tier = ShopItemTier.BASIC.name,
                        colorHex = "#D3E8D2",
                        cost = 80,
                        isDefault = false,
                        isPurchased = false,
                        isSelected = false
                    )
                )
                items.add(
                    ShopItemEntity(
                        id = "canvas_pale_peach",
                        name = "Pale Peach",
                        type = ShopItemType.CANVAS_BACKGROUND.name,
                        tier = ShopItemTier.BASIC.name,
                        colorHex = "#F4E1D9",
                        cost = 80,
                        isDefault = false,
                        isPurchased = false,
                        isSelected = false
                    )
                )
                items.add(
                    ShopItemEntity(
                        id = "canvas_soft_lavender",
                        name = "Soft Lavender",
                        type = ShopItemType.CANVAS_BACKGROUND.name,
                        tier = ShopItemTier.BASIC.name,
                        colorHex = "#E7D8E9",
                        cost = 80,
                        isDefault = false,
                        isPurchased = false,
                        isSelected = false
                    )
                )

                // Premium Canvas (150 coins each)
                items.add(
                    ShopItemEntity(
                        id = "canvas_faded_olive",
                        name = "Faded Olive",
                        type = ShopItemType.CANVAS_BACKGROUND.name,
                        tier = ShopItemTier.PREMIUM.name,
                        colorHex = "#B8CBB8",
                        cost = 150,
                        isDefault = false,
                        isPurchased = false,
                        isSelected = false
                    )
                )
                items.add(
                    ShopItemEntity(
                        id = "canvas_muted_mauve",
                        name = "Muted Mauve",
                        type = ShopItemType.CANVAS_BACKGROUND.name,
                        tier = ShopItemTier.PREMIUM.name,
                        colorHex = "#D1B2C1",
                        cost = 150,
                        isDefault = false,
                        isPurchased = false,
                        isSelected = false
                    )
                )
                items.add(
                    ShopItemEntity(
                        id = "canvas_dusty_blue",
                        name = "Dusty Blue",
                        type = ShopItemType.CANVAS_BACKGROUND.name,
                        tier = ShopItemTier.PREMIUM.name,
                        colorHex = "#A3BFD9",
                        cost = 150,
                        isDefault = false,
                        isPurchased = false,
                        isSelected = false
                    )
                )
                items.add(
                    ShopItemEntity(
                        id = "canvas_soft_khaki",
                        name = "Soft Khaki",
                        type = ShopItemType.CANVAS_BACKGROUND.name,
                        tier = ShopItemTier.PREMIUM.name,
                        colorHex = "#D8D6C1",
                        cost = 150,
                        isDefault = false,
                        isPurchased = false,
                        isSelected = false
                    )
                )
                items.add(
                    ShopItemEntity(
                        id = "canvas_muted_coral",
                        name = "Muted Coral",
                        type = ShopItemType.CANVAS_BACKGROUND.name,
                        tier = ShopItemTier.PREMIUM.name,
                        colorHex = "#F2C5C3",
                        cost = 150,
                        isDefault = false,
                        isPurchased = false,
                        isSelected = false
                    )
                )
                items.add(
                    ShopItemEntity(
                        id = "canvas_pale_mint",
                        name = "Pale Mint",
                        type = ShopItemType.CANVAS_BACKGROUND.name,
                        tier = ShopItemTier.PREMIUM.name,
                        colorHex = "#D9EDE1",
                        cost = 150,
                        isDefault = false,
                        isPurchased = false,
                        isSelected = false
                    )
                )
                items.add(
                    ShopItemEntity(
                        id = "canvas_soft_lilac",
                        name = "Soft Lilac",
                        type = ShopItemType.CANVAS_BACKGROUND.name,
                        tier = ShopItemTier.PREMIUM.name,
                        colorHex = "#E2D3E8",
                        cost = 150,
                        isDefault = false,
                        isPurchased = false,
                        isSelected = false
                    )
                )

                // Legendary Canvas (250 coins each)
                items.add(
                    ShopItemEntity(
                        id = "canvas_wood_texture",
                        name = "Wood Texture",
                        type = ShopItemType.CANVAS_BACKGROUND.name,
                        tier = ShopItemTier.LEGENDARY.name,
                        colorHex = "#8B7355",
                        cost = 250,
                        isDefault = false,
                        isPurchased = false,
                        isSelected = false,
                        previewBackgroundImageRes = R.drawable.canvas_wood_texture
                    )
                )
                items.add(
                    ShopItemEntity(
                        id = "canvas_vintage_notebook",
                        name = "Vintage Notebook",
                        type = ShopItemType.CANVAS_BACKGROUND.name,
                        tier = ShopItemTier.LEGENDARY.name,
                        colorHex = "#F4E8D0",
                        cost = 250,
                        isDefault = false,
                        isPurchased = false,
                        isSelected = false,
                        previewBackgroundImageRes = R.drawable.canvas_paper_texture
                    )
                )

                return items
            }
        }
    }
}
