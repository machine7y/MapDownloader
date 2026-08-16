package machine7y.mapdownloader.data.local.source

import android.content.Context
import android.util.Xml
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import machine7y.mapdownloader.domain.entity.RegionNode
import machine7y.mapdownloader.domain.source.RegionSource
import org.xmlpull.v1.XmlPullParser
import javax.inject.Inject
import javax.inject.Singleton

private const val RESOURCE_FILE = "regions.xml"
private const val ENCODING = "UTF-8"

private const val TAG_REGION = "region"
private const val ATTR_NAME = "name"
private const val ATTR_TYPE = "type"
private const val ATTR_MAP = "map"
private const val VALUE_YES = "yes"

@Singleton
class RegionSourceImpl @Inject constructor(
    @param:ApplicationContext private val context: Context,
) : RegionSource {

    private val mutex = Mutex()
    private var cachedRegionList: List<RegionNode>? = null
    private var nextLocalRegionId = 0

    override suspend fun getRegionList(): List<RegionNode> {
        cachedRegionList?.let { return it }
        return mutex.withLock {
            cachedRegionList ?: parseRegionList().also { cachedRegionList = it }
        }
    }

    override suspend fun getRegion(localRegionId: Int): RegionNode {
        fun find(nodes: List<RegionNode>): RegionNode? {
            for (node in nodes) {
                if (node.localRegionId == localRegionId) return node
                find(node.children)?.let { return it }
            }
            return null
        }

        //TODO handle error
        return find(getRegionList()) ?: error("Region with localRegionId=$localRegionId not found")
    }

    private fun parseRegionList(): List<RegionNode> =
        context.assets.open(RESOURCE_FILE).use { input ->
            nextLocalRegionId = 0
            val parser = Xml.newPullParser()
            parser.setInput(input, ENCODING)
            parseChildren(parser)
        }

    private fun parseChildren(parser: XmlPullParser): List<RegionNode> {
        val children = mutableListOf<RegionNode>()
        var eventType = parser.eventType
        while (eventType != XmlPullParser.END_DOCUMENT) {
            if (eventType == XmlPullParser.START_TAG && parser.name == TAG_REGION) {
                children.add(parseNode(parser))
            }
            eventType = parser.next()
        }
        return children
    }

    private fun parseNode(parser: XmlPullParser): RegionNode {
        val localRegionId = nextLocalRegionId++
        val name = parser.getAttributeValue(null, ATTR_NAME).orEmpty()
        val type = parser.getAttributeValue(null, ATTR_TYPE).orEmpty()
        val isMap = parser.getAttributeValue(null, ATTR_MAP) == VALUE_YES

        val children = mutableListOf<RegionNode>()
        var eventType = parser.next()
        while (!(eventType == XmlPullParser.END_TAG && parser.name == TAG_REGION)) {
            if (eventType == XmlPullParser.START_TAG && parser.name == TAG_REGION) {
                children.add(parseNode(parser))
            }
            eventType = parser.next()
        }

        return RegionNode(localRegionId = localRegionId, name = name, type = type, isMap = isMap, children = children)
    }
}
