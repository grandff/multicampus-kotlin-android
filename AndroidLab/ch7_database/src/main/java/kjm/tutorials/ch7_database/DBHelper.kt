package com.example.ch7_database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

//db 관리적인 코드 추상화..
//코틀린에서는 생성자를 constructor 예약어로 선언하는 함수를 이용하며..
//생성자는 primary constructor(클래스 선언위치), 하나만..
// secondary constructor(클래스 바디) 로 구분된다.. 여러개..

//SQLiteOpenHelper() - 상위 클래스 명시 () 은 상위 클래스의 생성자 호출..
class DBHelper (context: Context) : SQLiteOpenHelper(context, "testdb", null, 1){
    //앱 인스톨후 최초 한번..
    //null safety 지원..
    //타입... nullable, non-null 을 구분해서..
    //val user1: User - non -null
    //val user2: User? - nullable
    //nullable 로 선언한 객체의 맴버 접근할 때는 꼭 null safety operator활용해야..
    //user1.some() //ok
    //user2.some() //error
    //user2?.some() //ok
    override fun onCreate(p0: SQLiteDatabase?) {
        p0?.execSQL("create table todo_tb (" +
                "_id integer primary key autoincrement," +
                "todo not null)")
    }
    //db version 변경시마다..
    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

    }
}