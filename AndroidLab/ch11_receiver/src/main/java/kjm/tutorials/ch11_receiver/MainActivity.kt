package kjm.tutorials.ch11_receiver

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import kjm.tutorials.ch11_receiver.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val launcher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ){
            if(it.all{
                        p -> p.value == true
                }){
                noti()
            }else{
                Toast.makeText(this, "permission denied...", Toast.LENGTH_SHORT).show()
            }
        }

        binding.button.setOnClickListener {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if(ContextCompat.checkSelfPermission(
                        this,
                        "android.permission.POST_NOTIFICATIONS"
                ) == PackageManager.PERMISSION_GRANTED){
                    noti()
                }else{
                    launcher.launch(arrayOf("android.permission.POST_NOTIFICATIONS"))
                }
            }else{
                noti()
            }
        }
    }

    fun noti() {
        val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val builder: NotificationCompat.Builder
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // channel 개념을 대입해서 builder 준비 ..
            val channel = NotificationChannel(
                "oneId",
            "Channel Name",
                NotificationManager.IMPORTANCE_LOW
            )
            channel.description = "Channel Description"
            // 만들어진 채널을 시스템에 등록..
            manager.createNotificationChannel(channel)
            // 시스템에 등록된 채널을 적용해서 builder를 준비..
            builder = NotificationCompat.Builder(this, "oneId")
        }else{
            builder = NotificationCompat.Builder(this)
        }

        builder.setSmallIcon(android.R.drawable.ic_notification_overlay)
        builder.setWhen(System.currentTimeMillis())
        builder.setContentTitle("message")
        builder.setContentText("hello world")
        // 확장 컨텐츠 클릭 이벤트 처리
        // 인텐트 준비.. 의뢰..
        val intent = Intent(this, MyReceiver::class.java)
        val pIntent = PendingIntent.getBroadcast(
            this, 10, intent, PendingIntent.FLAG_IMMUTABLE
        )
        builder.setContentIntent(pIntent)
        // noti 발생..
        manager.notify(11, builder.build())
    }
}