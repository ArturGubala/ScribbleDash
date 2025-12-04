package com.scribbledash.gameplay.utils

import com.scribbledash.R

enum class ScoreCategory(val range: IntRange, val headline: String) {
    OOPS(0..39, "Oops"),
    MEH(40..69, "Meh"),
    GOOD(70..79, "Good"),
    GREAT(80..89, "Great"),
    WOOHOO(90..100, "Woohoo!");

    companion object {
        fun fromScore(score: Int): ScoreCategory {
            return entries.first { score in it.range }
        }
    }
}

object ScoreFeedback {
    private val oopsFeedbackList = listOf(
        R.string.feedback_oops_1,
        R.string.feedback_oops_2,
        R.string.feedback_oops_3,
        R.string.feedback_oops_4,
        R.string.feedback_oops_5,
        R.string.feedback_oops_6,
        R.string.feedback_oops_7,
        R.string.feedback_oops_8,
        R.string.feedback_oops_9,
        R.string.feedback_oops_10,
    )

    private val goodFeedbackList = listOf(
        R.string.feedback_good_1,
        R.string.feedback_good_2,
        R.string.feedback_good_3,
        R.string.feedback_good_4,
        R.string.feedback_good_5,
        R.string.feedback_good_6,
        R.string.feedback_good_7,
        R.string.feedback_good_8,
        R.string.feedback_good_9,
        R.string.feedback_good_10,
    )

    private val woohooFeedbackList = listOf(
        R.string.feedback_woohoo_1,
        R.string.feedback_woohoo_2,
        R.string.feedback_woohoo_3,
        R.string.feedback_woohoo_4,
        R.string.feedback_woohoo_5,
        R.string.feedback_woohoo_6,
        R.string.feedback_woohoo_7,
        R.string.feedback_woohoo_8,
        R.string.feedback_woohoo_9,
        R.string.feedback_woohoo_10,
    )

    fun getRandomFeedback(score: Int): Int {
        val feedbackList = when {
            score < 70 -> oopsFeedbackList
            score < 90 -> goodFeedbackList
            else -> woohooFeedbackList
        }
        return feedbackList.random()
    }

    fun getScoreHeadline(score: Int): String {
        return ScoreCategory.fromScore(score).headline
    }

    fun getScoreCategory(score: Int): ScoreCategory {
        return ScoreCategory.fromScore(score)
    }
}
