package kjm.tutorials.ch7_database

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import kjm.tutorials.ch7_database.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding
    // 코틀린에서는 List, Set, Map을 두가지 타입으로 구분함
    // mutable - immutable
    // list, set, map - immutable
    // mutablelist, mutableset, mutablemap - mutable
    var datas : MutableList<String> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = DbHelper(this).readableDatabase
        val cursor = db.rawQuery("select * from todo_tb", null)
        while (cursor.moveToNext()){
            datas.add(cursor.getString(1))
        }
        db.close()
        // 화면에 출력
        var result = ""
        // it - 람다함수내에서만 예약어..
        datas.forEach {
            result += "$it \n"
        }
        binding.mainTextView.text = result

        // addactivity 실행시키고.. 되돌아 왔을 때 사후처리하는 객체..
        val launcher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult() // 요청 처리자.. 다른 activity 실행
        ){
            // callback..
            // addactivity가 넘긴 데이터를 획득하고..
            // ?. - 멤버접근연산자
            // !! - npe 발생
            it.data!!.getStringExtra("result")?.let {
                datas.add(it)
                var result = ""
                // it - 람다함수내에서만 예약어..
                datas.forEach {
                    result += "$it \n"
                }
                binding.mainTextView.text = result
            }
        }

        binding.mainButton.setOnClickListener {
            // reflection - reference type
            // Class<*> <== A.class (java)
            // Class<*> <== A::class.java (kotlin)
            // KClass<*> <== A::class (kotlin)
            val intent = Intent(this, AddActivity::class.java)
            launcher.launch(intent)
        }
    }
}