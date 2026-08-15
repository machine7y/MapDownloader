package machine7y.mapdownloader.data.source

import android.content.Context
import android.util.Xml
import dagger.hilt.android.qualifiers.ApplicationContext
import machine7y.mapdownloader.domain.entity.Region
import machine7y.mapdownloader.domain.source.RegionSource
import org.xmlpull.v1.XmlPullParser
import javax.inject.Inject

private const val RESOURCE_FILE = "regions.xml"
private const val ENCODING = "UTF-8"

private const val TAG_REGION = "region"
private const val ATTR_NAME = "name"
private const val ATTR_TYPE = "type"
private const val TYPE_CONTINENT = "continent"

class RegionSourceImpl @Inject constructor(
    @param:ApplicationContext private val context: Context,
) : RegionSource {

    override suspend fun getRegionList(): List<Region> {
        val regions = mutableListOf<Region>()

        context.assets.open(RESOURCE_FILE).use { input ->
            val parser = Xml.newPullParser()
            parser.setInput(input, ENCODING)

            var depth = 0
            var eventType = parser.eventType
            while (eventType != XmlPullParser.END_DOCUMENT) {
                when (eventType) {
                    XmlPullParser.START_TAG -> if (parser.name == TAG_REGION) {
                        depth++
                        val name = parser.getAttributeValue(null, ATTR_NAME)
                        val type = parser.getAttributeValue(null, ATTR_TYPE)
                        if (name != null) {
                            when {
                                depth == 1 && type == TYPE_CONTINENT -> regions.add(
                                    Region.Continent(
                                        id = regions.size,
                                        name = name,
                                    )
                                )
                                depth == 2 -> regions.add(Region.Country(id = regions.size, name = name))
                            }
                        }
                    }
                    XmlPullParser.END_TAG -> if (parser.name == TAG_REGION) {
                        depth--
                    }
                }
                eventType = parser.next()
            }
        }

        return regions
    }
}
