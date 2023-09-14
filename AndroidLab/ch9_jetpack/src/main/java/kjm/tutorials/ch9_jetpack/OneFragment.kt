package com.example.ch9_jetpack

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ch9_jetpack.databinding.FragmentOneBinding
import com.example.ch9_jetpack.databinding.ItemRecyclerviewBinding

//항목을 구성하기 위한 뷰 객체를 가지는 역할..
//코틀린에서는 생성자 매개변수에 val, var 을 추가하면.. 그 자체가 클래스 멤버변수가 된다..
//단 주생성자만 가능.. 보조생성자의 매개변수를 클래스 멤버 변수로 만들수는 없다..
class MyViewHolder(val binding: ItemRecyclerviewBinding): RecyclerView.ViewHolder(binding.root)

//항목 구성 역할...
class MyAdapter(val datas: MutableList<String>): RecyclerView.Adapter<MyViewHolder>(){
    //항목 갯수 판단위해 자동 호출..
    override fun getItemCount(): Int {
        return datas.size
    }
    //항목을 구성하는 뷰를 가지는 ViewHolder 를 결정하기 위해서 자동 호출..
    //항목이 100개가 있고, 한 화면에 5개씩 출력된다면..
    //100번 호출하지는 않는다..화면에 같이 나오는 항목 갯수만큼만 호출하고 내부적으로 재사용된다..
    //6번 호출된다..
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(ItemRecyclerviewBinding.inflate(LayoutInflater.from(
            parent.context
        ), parent, false))
    }
    //각 항목을 구성하기 위해서 호출...
    //항목을 구성하기 위한 ViewHolder 객체는 전달..
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.itemData.text = datas[position]
    }
}

class MyDecoration(val context: Context): RecyclerView.ItemDecoration() {
    //모든 항목이 출력된후 마지막 한번 꾸미기 작업..
    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)
        //뷰(RecyclerView) 의 사이즈 획득..
        val width = parent.width
        val height = parent.height
        //그리고자 하는 이미지의 사이즈 획득..
        val drawable = ResourcesCompat.getDrawable(context.resources, R.drawable.kbo, null)
        val drWidth = drawable?.intrinsicWidth
        val drHeight = drawable?.intrinsicHeight
        //이미지 그리기 위한 좌표 계산..
        val left = width/2 - drWidth?.div(2) as Int
        val top = height/2 - drHeight?.div(2) as Int
        //이미지 그리기..
        c.drawBitmap(BitmapFactory.decodeResource(context.resources, R.drawable.kbo),
            left.toFloat(), top.toFloat(), null)
    }
    //각 항목을 꾸미기위해서..
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        //항목의 index..
        val index = parent.getChildAdapterPosition(view)+1//+1 은 계산 편의성 때문에..
        if(index % 3 == 0)
            outRect.set(10, 10, 10, 60)
        else
            outRect.set(10, 10, 10 , 0)

        view.setBackgroundColor(Color.parseColor("#28A0FF"))
        ViewCompat.setElevation(view, 20.0f)
    }
}

class OneFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding =  FragmentOneBinding.inflate(inflater, container, false)

        //항목 구성 데이터 준비..
        val datas = mutableListOf<String>()
        //코틀린의 for 문은 자바 스타일은 제공하지 않는다..
        //for( ; ; ) 은 불가..
        //in 범위 연산자.. 1부터 20까지...
        for(i in 1..20){
            datas.add("Item $i")
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(activity)
        binding.recyclerView.adapter = MyAdapter(datas)
        binding.recyclerView.addItemDecoration(MyDecoration(activity as Context))

        return binding.root
    }
}