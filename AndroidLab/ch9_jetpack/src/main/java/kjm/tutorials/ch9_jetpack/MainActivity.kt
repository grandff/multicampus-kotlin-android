package com.example.ch9_jetpack

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.ch9_jetpack.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    //ViewPager Adapter...
    class MyPagerAdapter(acttivity: FragmentActivity): FragmentStateAdapter(acttivity){
        val fragments: List<Fragment>
        //주생성자.. 클래스 선언위치..
        //생성자 호출시 실행될 로직을 {} 로 처리할 수 없다..
        //init 블락.. 생성자 호출시에 자동 호출되는 영역..
        //주로.. 주 생성자의 실행코드를 담기 위해서 이용..
        init {
            fragments = listOf(OneFragment(), TwoFragment(), ThreeFragment())
        }
        //항목 갯수를 판단하기 위해서 자동 호출..
        override fun getItemCount(): Int {
            return fragments.size
        }
        //각 항목을 위한 Fragment 를 결정하기 위해서 호출..
        override fun createFragment(position: Int): Fragment {
            return fragments[position]
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //viewpager..................
        binding.viewPager.adapter = MyPagerAdapter(this)
    }
}