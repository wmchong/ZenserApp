package com.example.zenserapp.ui.me

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.zenserapp.R
import com.example.zenserapp.databinding.FragmentMeBinding
import android.util.Log

class MeFragment : Fragment(R.layout.fragment_me) {

    private var _binding: FragmentMeBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: MeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.searchIB.setOnClickListener {
            //send text to backend
            //sql here
            val text = binding.searchET.text.toString()
            Log.i("look here",text)
        }
    }

    //prevent memory leaks
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}