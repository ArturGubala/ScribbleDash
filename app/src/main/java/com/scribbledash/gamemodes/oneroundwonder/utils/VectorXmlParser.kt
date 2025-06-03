package com.scribbledash.gamemodes.oneroundwonder.utils

import android.content.Context
import android.graphics.Path
import android.graphics.PathMeasure
import android.util.Xml
import androidx.annotation.RawRes
import androidx.compose.ui.geometry.Offset
//import androidx.compose.ui.graphics.Path
import androidx.core.graphics.PathParser
import com.scribbledash.gamemodes.oneroundwonder.model.PathData
import org.xmlpull.v1.XmlPullParser

object VectorXmlParser {

    data class ViewportSize(val width: Float, val height: Float)

    fun parseVectorDrawable(context: Context, xmlResourceId: Int): List<Path> {
        val inputStream = context.resources.openRawResource(xmlResourceId)
        val parser = Xml.newPullParser()
        parser.setInput(inputStream, "UTF-8")

        val paths = mutableListOf<Path>()

        try {
            var eventType = parser.next()

            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG && parser.name == "path") {
//                    val pathString = parser.getAttributeValue(null, "android:pathData")
                    val pathString = parser.getAttributeValue(0)
                    if (pathString != null) {
                        val path = PathParser.createPathFromPathData(pathString)
                        paths.add(path)
                    }
                }
                eventType = parser.next()
            }
        } finally {
            inputStream.close()
        }

        return paths
    }


    fun loadPathsFromRawXml(context: Context, @RawRes resId: Int): PathData {
        val paths = mutableListOf<List<Offset>>()
        val inputStream = context.resources.openRawResource(resId)

        val parser = Xml.newPullParser()
        parser.setInput(inputStream, "UTF-8")

        while (parser.eventType != XmlPullParser.END_DOCUMENT) {
            if (parser.eventType == XmlPullParser.START_TAG && parser.name == "path") {
                val pathData = parser.getAttributeValue(null, "pathData")
                if (!pathData.isNullOrBlank()) {
                    val androidPath = PathParser.createPathFromPathData(pathData)
                    val sampled = samplePathToOffsets(androidPath, precision = 1f)
                    paths.add(sampled)
                }
            }
            parser.next()
        }

        return PathData(paths[0])
    }


    fun parseVectorDrawableAsPathData(context: Context, xmlResourceId: Int): PathData? {
        val inputStream = context.resources.openRawResource(xmlResourceId)
        val parser = Xml.newPullParser()
        parser.setInput(inputStream, "UTF-8")

        val offsetList = mutableListOf<Offset>()

        try {
            var eventType = parser.next()

            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG && parser.name == "path") {
//                    val pathString = parser.getAttributeValue(null, "android:pathData")
                    val pathString = parser.getAttributeValue(0)
                    if (pathString != null) {
                        val path = PathParser.createPathFromPathData(pathString)
                        offsetList.addAll(pathToOffsets(path))
                    }
                }
                eventType = parser.next()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        } finally {
            inputStream.close()
        }

        return PathData(offsetList)
    }

    fun getViewportSize(context: Context, xmlResourceId: Int): ViewportSize? {
        val inputStream = context.resources.openRawResource(xmlResourceId)
        val parser = Xml.newPullParser()
        parser.setInput(inputStream, null)

        return try {
            var eventType = parser.eventType
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG && parser.name == "vector") {
                    val width = parser.getAttributeValue("http://schemas.android.com/apk/res/android", "viewportWidth")?.toFloatOrNull()
                    val height = parser.getAttributeValue("http://schemas.android.com/apk/res/android", "viewportHeight")?.toFloatOrNull()
                    if (width != null && height != null) {
                        return ViewportSize(width, height)
                    }
                }
                eventType = parser.next()
            }
            null
        } catch (e: Exception) {
            e.printStackTrace()
            null
        } finally {
            inputStream.close()
        }
    }

    private fun pathToOffsets(path: Path, steps: Int = 100): List<Offset> {
        val pathMeasure = PathMeasure(path, false)
        val length = pathMeasure.length
        val points = mutableListOf<Offset>()

        for (i in 0 until steps) {
            val pos = FloatArray(2)
            val distance = (i / (steps - 1).toFloat()) * length
            if (pathMeasure.getPosTan(distance, pos, null)) {
                points.add(Offset(pos[0], pos[1]))
            }
        }

        return points
    }

    fun samplePathToOffsets(path: android.graphics.Path, precision: Float): List<Offset> {
        val pm = android.graphics.PathMeasure(path, false)
        val length = pm.length
        val points = mutableListOf<Offset>()

        var distance = 0f
        val pos = FloatArray(2)

        while (distance < length) {
            if (pm.getPosTan(distance, pos, null)) {
                points.add(Offset(pos[0], pos[1]))
            }
            distance += precision
        }

        return points
    }
}
