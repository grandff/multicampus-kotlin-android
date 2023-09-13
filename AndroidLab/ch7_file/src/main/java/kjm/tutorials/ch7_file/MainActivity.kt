package kjm.tutorials.ch7_file

import android.content.ContentUris
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kjm.tutorials.ch7_file.databinding.ActivityMainBinding
import java.io.File

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button1.setOnClickListener {
            // 내장 파일 ...
            // 내장 루트의 test.txt - 원한다면 마음껏.. 서브 폴더.. 절대경로..
            val file = File(filesDir, "test.txt")
            val writeStream = file.writer()
            writeStream.write("hello world - internal")
            writeStream.flush()
            val readStream = file.reader().buffered()
            readStream.forEachLine {
                binding.textView.text = it
            }
        }

        binding.button2.setOnClickListener {
            // 외장 앱별...
            val file = File(getExternalFilesDir(null), "test.txt")
            val writeStream = file.writer()
            writeStream.write("hello world - external")
            writeStream.flush()

            val readStream = file.reader().buffered()
            readStream.forEachLine {
                binding.textView.text = it
            }
        }

        // 퍼미션 요청 다이얼로그 띄우고.. 사후처리..
        val launcher = registerForActivityResult(
            // 단일 퍼미션 조정 - RequestPermission()
            // 여러 퍼미션을 한꺼번에 조정 - RequestMultiplePermissions()
            ActivityResultContracts.RequestMultiplePermissions()
        ) {
            // callback...
            if(it.all {
                permission -> permission.value
                }){
                // do something..
                testFile()
            }else{
                Toast.makeText(this, "permission denied..", Toast.LENGTH_SHORT)
            }
        }

        binding.button3.setOnClickListener {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if(ContextCompat.checkSelfPermission(this, "android.permission.READ_MEDIA_IMAGES") == PackageManager.PERMISSION_GRANTED){
                    // do somtehing...
                    testFile()
                }else{
                    launcher.launch(arrayOf("android.permission.READ_MEDIA_IMAGES"))
                }
            }else{

            }
        }
    }

    fun testFile() {
        val projection = arrayOf(MediaStore.Images.Media._ID, MediaStore.Images.Media.DISPLAY_NAME)
        val cursor = contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            projection,
            null,
            null,
            null
        )

        // 코틀린의 기초 함수.. - 매개변수를 함수를 받아들이고.. 그 함수를 실행시키는 함수..
        cursor?.run {
            while (moveToNext()){
                val uri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, getLong(0))
                // uri로 식별되는 사진을 읽을 수 있는 stream을 제공
                // 원한다면 stream 을 얻지 않고.. 사진 경로 문자열을 얻을 수도 있음..
                applicationContext.contentResolver.openInputStream(uri).use {
                    // 카메라로 촬영한 사진 이미지다.. 사이즈 너무 크다.. 그냥 읽으면 OOM 발생 가능
                    // 데이터 사이즈를 줄여서 로딩해야함
                    val option = BitmapFactory.Options()
                    option.inSampleSize = 10    // 10분의 1로 줄여서 처리
                    val bitmap = BitmapFactory.decodeStream(it, null, option)
                    binding.imageView.setImageBitmap(bitmap)
                }
            }
        }
    }
}