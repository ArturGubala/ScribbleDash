package com.scribbledash.core.domain.model

sealed class GameModeType(val mode: String = "") {
//    data object Undefined : GameModeType()
    data object OneRoundWonder : GameModeType(mode = "OneRoundWonder")
}
