package info.nightscout.androidaps.plugins.insulin

import info.nightscout.androidaps.R
import info.nightscout.androidaps.interfaces.InsulinInterface
import info.nightscout.androidaps.utils.resources.ResourceHelperMock
import info.nightscout.androidaps.utils.sharedPreferences.SPMock
import org.junit.Assert.assertEquals
import org.junit.Test

class InsulinOrefFreePeakPluginTest {

    private val insulinOrefFreePeakPlugin = InsulinOrefFreePeakPlugin(
        SPMock, ResourceHelperMock
    )

    @Test
    fun getId() {
        assertEquals(InsulinInterface.OREF_FREE_PEAK, insulinOrefFreePeakPlugin.id)
    }

    @Test
    fun getFriendlyName() {
        ResourceHelperMock.set(R.string.free_peak_oref, "Free-Peak Oref")
        assertEquals("Free-Peak Oref", insulinOrefFreePeakPlugin.friendlyName)
    }

    @Test
    fun commentStandardText() {
        ResourceHelperMock.set(R.string.insulin_peak_time, "Peak Time [min]")
        assertEquals("Peak Time [min]: 75", insulinOrefFreePeakPlugin.commentStandardText())
    }

    @Test
    fun getPeak() {
        // default
        assertEquals(75, insulinOrefFreePeakPlugin.peak)
        // customized
        SPMock.putInt(R.string.key_insulin_oref_peak, 70)
        assertEquals(70, insulinOrefFreePeakPlugin.peak)
        SPMock.putInt(R.string.key_insulin_oref_peak, 75)
    }
}