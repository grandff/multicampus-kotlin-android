package kjm.tutorials.ch7_database

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kjm.tutorials.ch7_database.databinding.ActivityAddBinding

class AddActivity : AppCompatActivity() {

    //
    lateinit var binding : ActivityAddBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.addButton.setOnClickListener {
            val inputData = binding.addEditView.text.toString()

            val db = DbHelper(this).writableDatabase

            // 코틀린에서는 배열 타입을 표현할때 []을 사용하지 않음
            // 물론 배열 데이터에 접급할 때는 [0]이 가능함
            // 코틀린에서 모든 것은 객체임으로 배열도 객체이다.. array 타입의 객체임
            // array 클래스의 객체를 생성해서 배열을 표현하던가
            // 직접 배열 값을 주면서 배열 객체를 만들고자 할때는 arrayOf 사용
            db.execSQL("insert into todo_tb (todo) values (?)", arrayOf(inputData))
            db.close()

            // 자동으로 이전 화면으로 화면 전환.. 결과 데이터를 넘기면서..
            intent.putExtra("result", inputData)
            setResult(RESULT_OK, intent)    // 상태 표현
            // 자신을 종료시켜서 시스템에 의해 자동으로 이전화면이 나오게 처리
            finish()
        }
    }
}