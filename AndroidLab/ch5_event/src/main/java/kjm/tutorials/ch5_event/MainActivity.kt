package kjm.tutorials.ch5_event

import android.os.Bundle
import android.os.SystemClock
import android.view.KeyEvent
import android.view.View
import android.view.View.OnClickListener
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kjm.tutorials.ch5_event.databinding.ActivityMainBinding

// kotlin은 extends, implements 예약어가 없고, 타입으로 본다
// 클래스 선언하면서 : 뒤에 옴
// 상위 클래스, 인터페이스를 나열함
class MainActivity : AppCompatActivity() {

    // back button이 눌린 시간 저장
    var initTime = 0L
    // chronometer 측정 시간 저장
    var pauseTime = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // binding view setting
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // start button add click event
        // 익명클래스 사용
        // object {}
        // object: A() {} - A를 상속받은 익명 클래스
        // object: A {} - A 인터페이스를 구현한 익명 클래스
        binding.startButton.setOnClickListener(object : OnClickListener {
            override fun onClick(p0: View?) {
                binding.chronometer.base = SystemClock.elapsedRealtime() + pauseTime
                binding.chronometer.start()
                // 버튼조정
                buttonStateChange(binding,true)
            }
        })

        // stop button add click event
        // 함수의 마지막 매개변수가 함수타입이라면 ... 그 함수를 () 밖에 선언이 가능함
        binding.stopButton.setOnClickListener{
            // 현재 시간 저장
            pauseTime = binding.chronometer.base - SystemClock.elapsedRealtime()
            binding.chronometer.stop()
            // 버튼조정
            buttonStateChange(binding,false)
        }

        // reset button add click event
        binding.resetButton.setOnClickListener {
            pauseTime = 0L
            binding.chronometer.base = SystemClock.elapsedRealtime()
            binding.chronometer.stop()

            buttonStateChange(binding, false)
        }
    }

    // 뒤로가기버튼 이벤트 추가
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            // 3초 이내 두번 클릭
            if(System.currentTimeMillis() - initTime > 3000){
                Toast.makeText(this, "종료하려면 한번 더 누르세요.", Toast.LENGTH_SHORT).show()
                initTime = System.currentTimeMillis()
                return true
            }
        }

        return super.onKeyDown(keyCode, event)
    }

    fun buttonStateChange(binding: ActivityMainBinding, isStart: Boolean ){
        // 버튼조정
        binding.startButton.isEnabled = !isStart
        binding.stopButton.isEnabled = isStart
        binding.resetButton.isEnabled = isStart
    }
}