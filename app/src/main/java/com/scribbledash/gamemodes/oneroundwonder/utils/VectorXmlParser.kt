package com.scribbledash.gamemodes.oneroundwonder.utils

import android.content.Context
import android.util.Xml
import androidx.annotation.RawRes
import androidx.compose.ui.geometry.Offset
import androidx.core.graphics.PathParser
import com.scribbledash.gamemodes.oneroundwonder.model.ScribbleDashPath
import com.scribbledash.gamemodes.oneroundwonder.model.computeBounds
import org.xmlpull.v1.XmlPullParser

object VectorXmlParser {

    data class ViewportSize(val width: Float, val height: Float)

    fun loadPathsFromRawXml(context: Context, @RawRes resId: Int): List<ScribbleDashPath> {
        val paths = mutableListOf<ScribbleDashPath>()
        val inputStream = context.resources.openRawResource(resId)

        val parser = Xml.newPullParser()
        parser.setInput(inputStream, "UTF-8")

        while (parser.eventType != XmlPullParser.END_DOCUMENT) {
            if (parser.eventType == XmlPullParser.START_TAG && parser.name == "path") {
                val pathData = parser.getAttributeValue(null, "pathData")
                if (!pathData.isNullOrBlank()) {
                    val path = PathParser.createPathFromPathData(pathData)
                    val bounds = path.computeBounds()
                    paths.add(ScribbleDashPath(path = path, bounds = bounds))
                }
            }
            parser.next()
        }

        return paths
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
