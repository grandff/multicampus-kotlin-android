package com.example.ch8_menu

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.SearchView.OnQueryTextListener

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        //SearchView 객체 획득.. 적용된 MenuItem 객체를 먼저 획득하고.. 그곳에 연결된 ActionView 객체 획득
        val menuItem = menu?.findItem(R.id.menu3)
        //코틀린의 명시적 캐스팅 연산자 as
        //코틀린의 타입 체크 연산자 is
        val searchView = menuItem?.actionView as SearchView
        searchView.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                return  true
            }
            //검색어를 입력하기 위해서 올라온 키보드의 검색 버튼을 누른 순간..
            override fun onQueryTextSubmit(query: String?): Boolean {
                //inner 클래스내에서 outer 클래스의 객체를 지정할때..
                //java - MainActivity.this
                //kotlin - this@OuterClass
                //@aaa - kotlin 의 label 명시..
                Toast.makeText(this@MainActivity, query, Toast.LENGTH_SHORT).show()
                searchView.setQuery("", false)
                searchView.isIconified = true
                return true
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //kotlin 에서 자바의 switch - case 제공하지 않는다.
        //when 으로 대체하고.. when 의 능력이 많아서.. switch-case 보다 이용비율이 높다..
        //when(data) 의 조건을 정수 값 이외의 다양한 타입의 값 대입 가능..
        //값이 아니라 타입으로 조건이 가능하고..
        //범위로도 조건 가능하고..
        //when 에 값을 명시하지 않을 수도 있고..
        when(item.itemId){
            R.id.menu1 -> Log.d("kkang", "menu1 click")
            R.id.menu2 -> Log.d("kkang", "menu2 click")
        }
        return super.onOptionsItemSelected(item)
    }
}