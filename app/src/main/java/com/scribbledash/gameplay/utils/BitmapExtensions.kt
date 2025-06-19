package com.scribbledash.gameplay.utils

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import java.io.IOException
import androidx.core.graphics.createBitmap
import androidx.core.graphics.get
import androidx.core.graphics.set

class BitmapExtensions(private val context: Context) {

    fun saveToGallery(
        bitmap: Bitmap,
        fileName: String,
        format: Bitmap.CompressFormat = Bitmap.CompressFormat.PNG,
        quality: Int = 100,
    ): Uri? {
        val resolver = context.contentResolver
        val contentValues = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
            put(
                MediaStore.Images.Media.MIME_TYPE, when (format) {
                    Bitmap.CompressFormat.JPEG -> "image/jpeg"
                    Bitmap.CompressFormat.PNG -> "image/png"
                    Bitmap.CompressFormat.WEBP -> "image/webp"
                    else -> "image/png"
                }
            )
            put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
            put(MediaStore.Images.Media.IS_PENDING, 1)
        }

        return try {
            val uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
            uri?.let {
                resolver.openOutputStream(it)?.use { outputStream ->
                    bitmap.compress(format, quality, outputStream)
                }

                contentValues.clear()
                contentValues.put(MediaStore.Images.Media.IS_PENDING, 0)
                resolver.update(it, contentValues, null, null)
            }
            uri
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

    fun compareBitmaps(
        drawing: Bitmap,
        reference: Bitmap,
        fuzzyRadius: Int = 1,
    ): Float {
        val width = drawing.width
        val height = drawing.height

        val comparisonBitmap = createBitmap(width, height)
        var matchingPixels = 0
        var visibleUserPixels = 0

        for (y in 0 until height) {
            for (x in 0 until width) {
                val userPixel = drawing[x, y]
                val referencePixel = reference[x, y]

                val userVisible = Color.alpha(userPixel) > 0
                val referenceVisible = Color.alpha(referencePixel) > 0

                var matchFound = false

                if (userVisible) {
                    visibleUserPixels++

                    loop@ for (dy in -fuzzyRadius..fuzzyRadius) {
                        for (dx in -fuzzyRadius..fuzzyRadius) {
                            val nx = x + dx
                            val ny = y + dy
                            if (nx in 0 until width && ny in 0 until height) {
                                if (Color.alpha(reference[nx, ny]) > 0) {
                                    matchFound = true
                                    break@loop
                                }
                            }
                        }
                    }

                    if (matchFound) {
                        matchingPixels++
                        comparisonBitmap[x, y] = Color.GREEN
                    } else {
                        comparisonBitmap[x, y] = Color.RED
                    }
                } else if (referenceVisible) {
                    comparisonBitmap[x, y] = Color.YELLOW
                }
            }
        }

        // TODO: testing
        saveToGallery(
            bitmap = comparisonBitmap,
            fileName = "comparisonBitmap.png"
        )
        saveToGallery(
            bitmap = drawing,
            fileName = "drawing.png"
        )
        saveToGallery(
            bitmap = reference,
            fileName = "reference.png"
        )

        if (visibleUserPixels == 0) return 0f
        val coveragePercentage = (matchingPixels.toFloat() / visibleUserPixels) * 100f

        return coveragePercentage
    }
}
