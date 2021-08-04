package com.example.zenserapp.ui.explore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.zenserapp.MainActivity
import com.example.zenserapp.databinding.FragmentExploreBinding

class ExploreFragment : Fragment() {
    private var _binding: FragmentExploreBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var exploreViewModel: ExploreViewModel

    override fun onCreateView( inflater: LayoutInflater,
                                container: ViewGroup?,
                               savedInstanceState: Bundle?): View? {
        exploreViewModel = ViewModelProvider(this).get(ExploreViewModel::class.java)
        _binding = FragmentExploreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //the example of searching iphone results
        val iphoneSearch = arrayOf("iphone", "iphone xs", "iphone xs case", "iphone cover", "iphone 12"
            , "iphone 12 mini", "iphone 11", "iphone 12 pro", "iphone 11 pro"
            , "iphone 11 pro max", "iphone case")
        val iphoneAdapter: ArrayAdapter<String> = ArrayAdapter(
            activity as MainActivity, android.R.layout.simple_list_item_1, iphoneSearch
        )

        binding.lvSearchResultList.adapter = iphoneAdapter

        binding.expSearchingField.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.expSearchingField.clearFocus()
                if(iphoneSearch.contains(query)){
                    iphoneAdapter.filter.filter(query)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                iphoneAdapter.filter.filter(newText)
                return false
            }

        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}