package kjm.tutorials.ch7_preference

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.preference.EditTextPreference
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat

class MySettings : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        // 아래 코드로.. 설정화면, 설정내용 저장까지 자동
        setPreferencesFromResource(R.xml.settings, rootKey)

        // 설정 객체 직접 획득 가능.. summary 조정, event
        val idPreference: EditTextPreference? = findPreference("id")
        val colorPreference : ListPreference? = findPreference("color")

        // summary를 동적으로
        // 유저가 설정한 값을 그대로 summary에 출력
        colorPreference?.summaryProvider = ListPreference.SimpleSummaryProvider.getInstance()

        // 알고리즘에 의한 summary 설정 ..
        idPreference?.summaryProvider = Preference.SummaryProvider<EditTextPreference> {
            // 유저 설정 값 획득
            val text = it.text
            if(TextUtils.isEmpty(text)) {
                "설정이 안됨"
            }else{
                "설정한 ID는 $text 라능"
            }
        }

        // change listener로 확인
        idPreference?.setOnPreferenceChangeListener {
            preference, newValue ->
            Log.d("kkang", "key : $preference, value : $newValue")
            true
        }
    }
}