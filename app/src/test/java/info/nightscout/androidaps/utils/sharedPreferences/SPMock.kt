package info.nightscout.androidaps.utils.sharedPreferences

import androidx.annotation.IntegerRes
import androidx.annotation.StringRes
import java.util.*

object SPMock : SP {

    private var stringData = HashMap<String, Any>()
    private var intData = HashMap<Int, Any>()

    override fun getAll(): Map<String, *> = stringData

    override fun clear() = stringData.clear()

    override fun contains(key: String): Boolean = stringData.contains(key)

    override fun contains(@IntegerRes resourceId: Int): Boolean = intData.containsKey(resourceId)

    override fun remove(@IntegerRes resourceID: Int) {
        intData.remove(resourceID)
    }

    override fun remove(key: String) {
        stringData.remove(key)
    }

    override fun getString(@StringRes resourceID: Int, defaultValue: String): String = intData[resourceID] as String?
        ?: defaultValue

    override fun getString(key: String, defaultValue: String): String = stringData[key] as String?
        ?: defaultValue

    override fun getBoolean(@StringRes resourceID: Int, defaultValue: Boolean): Boolean = intData[resourceID] as Boolean?
        ?: defaultValue

    override fun getBoolean(key: String, defaultValue: Boolean): Boolean = stringData[key] as Boolean?
        ?: defaultValue

    override fun getDouble(@StringRes resourceID: Int, defaultValue: Double): Double = intData[resourceID] as Double?
        ?: defaultValue

    override fun getDouble(key: String, defaultValue: Double): Double = stringData[key] as Double?
        ?: defaultValue

    override fun getInt(@StringRes resourceID: Int, defaultValue: Int): Int = intData[resourceID] as Int?
        ?: defaultValue

    override fun getInt(key: String, defaultValue: Int): Int = stringData[key] as Int?
        ?: defaultValue

    override fun getLong(@StringRes resourceID: Int, defaultValue: Long): Long = intData[resourceID] as Long?
        ?: defaultValue

    override fun getLong(key: String, defaultValue: Long): Long = stringData[key] as Long?
        ?: defaultValue

    override fun putBoolean(key: String, value: Boolean) {
        stringData[key] = value
    }

    override fun putBoolean(@StringRes resourceID: Int, value: Boolean) {
        intData[resourceID] = value
    }

    override fun putDouble(key: String, value: Double) {
        stringData[key] = value
    }

    override fun putLong(key: String, value: Long) {
        stringData[key] = value
    }

    override fun putLong(@StringRes resourceID: Int, value: Long) {
        intData[resourceID] = value
    }

    override fun putInt(key: String, value: Int) {
        stringData[key] = value
    }

    override fun putInt(@StringRes resourceID: Int, value: Int) {
        intData[resourceID] = value
    }

    override fun incInt(@StringRes resourceID: Int) {
        var value: Int = intData[resourceID] as Int
        value++
        intData[resourceID] = value
    }

    override fun putString(@StringRes resourceID: Int, value: String) {
        intData[resourceID] = value
    }

    override fun putString(key: String, value: String) {
        stringData[key] = value
    }
}