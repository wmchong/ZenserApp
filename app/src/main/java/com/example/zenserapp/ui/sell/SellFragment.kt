package com.example.zenserapp.ui.sell

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.zenserapp.databinding.FragmentSellBinding
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.zenserapp.MainActivity
import com.example.zenserapp.R

class SellFragment : Fragment(), SellRecyclerAdapter.OnItemClickListener {
    private var _binding: FragmentSellBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSellBinding.inflate(inflater, container, false)
        val categoryList = generateCategoryList()

        binding.categoriesRV.adapter = SellRecyclerAdapter(categoryList, this)
        binding.categoriesRV.layoutManager = LinearLayoutManager(context)
        binding.categoriesRV.setHasFixedSize(true)

        return binding.root
    }

    override fun onItemClick(position: Int) {
        Toast.makeText(context,"Item $position clicked", Toast.LENGTH_SHORT).show()
    }

    private fun generateCategoryList(): List<Category> {
        val list = ArrayList<Category>()
        val item1 = Category(R.drawable.computer, "Computers & Tech")
        val item2 = Category(R.drawable.education, "Learning & Enrichment")
        val item3 = Category(R.drawable.men_fashion, "Men's Fashion")
        val item4 = Category(R.drawable.women_fashion, "Women's Fashion")
        val item5 = Category(R.drawable.service, "Services")
        val item6 = Category(R.drawable.tickets_vouchers, "Tickets & Vouchers")
        list.add(item1)
        list.add(item2)
        list.add(item3)
        list.add(item4)
        list.add(item5)
        list.add(item6)
        return list

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}