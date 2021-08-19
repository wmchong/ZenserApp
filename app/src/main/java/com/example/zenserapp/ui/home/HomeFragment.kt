package com.example.zenserapp.ui.home

import android.content.Intent
import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.zenserapp.R
import com.example.zenserapp.databinding.FragmentHomeBinding
import com.example.zenserapp.ui.categories.*

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        _binding = FragmentHomeBinding.inflate(inflater, container, false)



        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.cvService.setOnClickListener {
            val intent = Intent(context, ServicesHome::class.java)
            context?.startActivity(intent)
        }

        binding.cvComputer.setOnClickListener {
            val intent = Intent(context, ComputerAndTechHome::class.java)
            context?.startActivity(intent)
        }

        binding.cvVouchers.setOnClickListener {
            val intent = Intent(context, VoucherHome::class.java)
            context?.startActivity(intent)
        }
        binding.cvEducation.setOnClickListener {
            val intent = Intent(context, EducationHome::class.java)
            context?.startActivity(intent)
        }
        binding.cvWomenFashion.setOnClickListener {
            val intent = Intent(context, WomenFashionHome::class.java)
            context?.startActivity(intent)
        }
        binding.cvMenFashion.setOnClickListener {
            val intent = Intent(context, MenFashionHome::class.java)
            context?.startActivity(intent)
        }
        binding.ivBlackfriday.setOnClickListener {
           val intent = Intent(context, DealsPageHome::class.java)
           context?.startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
