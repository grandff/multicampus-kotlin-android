package kjm.tutorials.ch3_view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kjm.tutorials.ch3_view.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    // old source
//    lateinit var button: Button
//    lateinit var editView: EditText
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        // 화면 출력
//        setContentView(R.layout.activity_main)
//
//        // 필요한 뷰 객체 획득
//        button = findViewById(R.id.buttonTest)
//        editView = findViewById(R.id.editViewTest)
//
//        // 버튼 이벤트 등록
//        button.setOnClickListener{
//            val data = editView.text.toString()
//            Log.d("kkang", data) // 태그가 들어가있는 걸로만 logcat에서 검색할 수 있으니까 잘 활용
//        }
//    }

    // view binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonTest.setOnClickListener{
            val data = binding.editViewTest.text.toString()
            Log.d("kkang", data) // 태그가 들어가있는 걸로만 logcat에서 검색할 수 있으니까 잘 활용
        }
    }
}