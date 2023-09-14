package com.example.ch9_jetpack

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ch9_jetpack.databinding.FragmentThreeBinding
import com.example.ch9_jetpack.databinding.FragmentTwoBinding


class ThreeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FragmentThreeBinding.inflate(inflater, container, false).root
    }
}