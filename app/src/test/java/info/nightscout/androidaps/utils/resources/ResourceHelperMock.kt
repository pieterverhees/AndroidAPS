package info.nightscout.androidaps.utils.resources

import java.util.*

object ResourceHelperMock : ResourceHelper {
    var defaultValue = "SomeString"
    var intData = HashMap<Int, Any>()

    fun set(id: Int, value: String) {
        intData.put(id, value)
    }

    override fun gs(id: Int): String? {
        return intData[id] as String? ?: defaultValue
    }

    override fun gs(id: Int, vararg args: Any?): String? {
        return intData[id] as String? ?: defaultValue
    }

    override fun gq(id: Int, quantity: Int, vararg args: Any?): String? {
        return intData[id] as String? ?: defaultValue
    }

    override fun gc(id: Int): Int? {
        return intData[id] as Int? ?: 0
    }
}