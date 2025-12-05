package com.scribbledash.gameplay.utils

import com.scribbledash.core.presentation.utils.DifficultyLevel
import kotlin.math.roundToInt

enum class CoinRewardHelper(val range: IntRange, val amount: Int) {
    OOPS(0..39, 1),
    MEH(40..69, 2),
    GOOD(70..79, 2),
    GREAT(80..89, 4),
    WOOHOO(90..100, 6);

    companion object Companion {
        fun fromScoreAndDifficulty(score: Int, gameDifficultyLevel: DifficultyLevel): Int {
            if (score in OOPS.range) {
                return 1
            }

            return when(gameDifficultyLevel) {
                DifficultyLevel.BEGINNER -> {
                    (CoinRewardHelper.entries.first { score in it.range }.amount * 0.5).roundToInt()
                }
                DifficultyLevel.CHALLENGING -> {
                    CoinRewardHelper.entries.first { score in it.range }.amount
                }
                DifficultyLevel.MASTER -> {
                    (CoinRewardHelper.entries.first { score in it.range }.amount * 1.75).roundToInt()
                }
            }
        }
    }
}
