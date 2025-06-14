package com.scribbledash.gameplay.utils

import android.content.Context
import android.graphics.Path
import android.graphics.RectF
import android.util.Xml
import androidx.annotation.RawRes
import androidx.core.graphics.PathParser
import com.scribbledash.gameplay.model.ScribbleDashPath
import com.scribbledash.gameplay.model.computeBounds
import org.xmlpull.v1.XmlPullParser

class VectorXmlParser(private val context: Context) {

    fun loadPathsFromRawXml(@RawRes resId: Int): ScribbleDashPath {
        val parser = Xml.newPullParser()
        val path = Path()
        val bounds: RectF

        context.resources.openRawResource(resId).use { inputStream ->
            parser.setInput(inputStream, "UTF-8")

            while (parser.eventType != XmlPullParser.END_DOCUMENT) {
                if (parser.eventType == XmlPullParser.START_TAG && parser.name == "path") {
                    val pathData = parser.getAttributeValue(null, "pathData")
                    if (!pathData.isNullOrBlank()) {
                        val parsedPath = PathParser.createPathFromPathData(pathData)
                        path.addPath(parsedPath)
                    }
                }
                parser.next()
            }

            bounds = path.computeBounds()
        }

        return ScribbleDashPath(path = path, bounds = bounds)
    }
}
