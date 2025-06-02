package com.scribbledash.core.presentation.utils

import com.scribbledash.R
import com.scribbledash.core.domain.model.Drawings

fun getDrawableRawIdForDrawing(drawing: Drawings): Int {
    return when (drawing) {
        Drawings.ALIEN -> R.raw.img_alien
        Drawings.BICYCLE -> R.raw.img_bicycle
        Drawings.BOAT -> R.raw.img_boat
        Drawings.BOOK -> R.raw.img_book
        Drawings.BUTTERFLY -> R.raw.img_butterfly
        Drawings.CAMERA -> R.raw.img_camera
        Drawings.CAR -> R.raw.img_car
        Drawings.CASTLE -> R.raw.img_castle
        Drawings.CAT -> R.raw.img_cat
        Drawings.CLOCK -> R.raw.img_clock
        Drawings.CROWN -> R.raw.img_crown
        Drawings.CUP -> R.raw.img_cup
        Drawings.DOG -> R.raw.img_dog
        Drawings.ENVELOPE -> R.raw.img_envelope
        Drawings.EYE -> R.raw.img_eye
        Drawings.FISH -> R.raw.img_fish
        Drawings.FLOWER -> R.raw.img_flower
        Drawings.FOOTBALL_FIELD -> R.raw.img_football_field
        Drawings.FROG -> R.raw.img_frog
        Drawings.GLASSES -> R.raw.img_glasses
        Drawings.HEART -> R.raw.img_heart
        Drawings.HELICOTPER -> R.raw.img_helicotper
        Drawings.HOTAIRBALLOON -> R.raw.img_hotairballoon
        Drawings.HOUSE -> R.raw.img_house
        Drawings.MOON -> R.raw.img_moon
        Drawings.MOUNTAINS -> R.raw.img_mountains
        Drawings.ROBOT -> R.raw.img_robot
        Drawings.ROCKET -> R.raw.img_rocket
        Drawings.SMILEY -> R.raw.img_smiley
        Drawings.SNOWFLAKE -> R.raw.img_snowflake
        Drawings.SOFA -> R.raw.img_sofa
        Drawings.TRAIN -> R.raw.img_train
        Drawings.UMBRELLA -> R.raw.img_umbrella
        Drawings.WHALE -> R.raw.img_whale
    }}
