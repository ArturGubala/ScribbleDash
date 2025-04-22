package com.scribbledash.core.presentation.utils

import com.scribbledash.R
import com.scribbledash.core.domain.model.Drawings

fun getDrawableIdForDrawing(drawing: Drawings): Int {
    return when (drawing) {
        Drawings.ALIEN -> R.drawable.img_alien
        Drawings.BICYCLE -> R.drawable.img_bicycle
        Drawings.BOAT -> R.drawable.img_boat
        Drawings.BOOK -> R.drawable.img_book
        Drawings.BUTTERFLY -> R.drawable.img_butterfly
        Drawings.CAMERA -> R.drawable.img_camera
        Drawings.CAR -> R.drawable.img_car
        Drawings.CASTLE -> R.drawable.img_castle
        Drawings.CAT -> R.drawable.img_cat
        Drawings.CLOCK -> R.drawable.img_clock
        Drawings.CROWN -> R.drawable.img_crown
        Drawings.CUP -> R.drawable.img_cup
        Drawings.DOG -> R.drawable.img_dog
        Drawings.ENVELOPE -> R.drawable.img_envelope
        Drawings.EYE -> R.drawable.img_eye
        Drawings.FISH -> R.drawable.img_fish
        Drawings.FLOWER -> R.drawable.img_flower
        Drawings.FOOTBALL_FIELD -> R.drawable.img_football_field
        Drawings.FROG -> R.drawable.img_frog
        Drawings.GLASSES -> R.drawable.img_alien
        Drawings.HEART -> R.drawable.img_heart
        Drawings.HELICOTPER -> R.drawable.img_helicotper
        Drawings.HOTAIRBALLOON -> R.drawable.img_hotairballoon
        Drawings.HOUSE -> R.drawable.img_house
        Drawings.MOON -> R.drawable.img_moon
        Drawings.MOUNTAINS -> R.drawable.img_mountains
        Drawings.ROBOT -> R.drawable.img_robot
        Drawings.ROCKET -> R.drawable.img_rocket
        Drawings.SMILEY -> R.drawable.img_smiley
        Drawings.SNOWFLAKE -> R.drawable.img_snowflake
        Drawings.SOFA -> R.drawable.img_sofa
        Drawings.TRAIN -> R.drawable.img_train
        Drawings.UMBRELLA -> R.drawable.img_umbrella
        Drawings.WHALE -> R.drawable.img_whale
    }}
