package info.nightscout.androidaps.utils.textValidator

import android.content.Context
import android.text.InputType
import androidx.preference.EditTextPreference

class ValidatingEditTextPreference(context: Context) : EditTextPreference(context) {
    override fun setOnBindEditTextListener(onBindEditTextListener: OnBindEditTextListener?) {
        super.setOnBindEditTextListener {editText ->
        editText.inputType = InputType.TYPE_CLASS_NUMBER
    }
}