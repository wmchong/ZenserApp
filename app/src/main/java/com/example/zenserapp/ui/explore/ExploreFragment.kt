package com.example.zenserapp.ui.explore

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.zenserapp.Listing1Activity
import com.example.zenserapp.databinding.FragmentExploreBinding

class ExploreFragment : Fragment(), ListingsAdapter.ClickListener {
    private var _binding: FragmentExploreBinding? = null
    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    private lateinit var exploreViewModel: ExploreViewModel

    // possible results when searching iphone
    val iphoneSearch = arrayOf(
        ListingsModal(listingName = "iphone"),
        ListingsModal(listingName = "iphone xs"),
        ListingsModal(listingName = "iphone xs case"),
        ListingsModal(listingName = "iphone cover"),
        ListingsModal(listingName = "iphone 12"),
        ListingsModal(listingName = "iphone 12 mini"),
        ListingsModal(listingName = "iphone 11"),
        ListingsModal(listingName = "iphone 12 pro"),
        ListingsModal(listingName = "iphone 11 pro"),
        ListingsModal(listingName = "iphone 11 pro max"),
        ListingsModal(listingName = "iphone case"),
        ListingsModal(listingName = "iphone 13 queue"),
    )

    val iphoneSearchList = ArrayList<ListingsModal>()
    var listingsAdapter: ListingsAdapter? = null;

    override fun onCreateView( inflater: LayoutInflater,
                                container: ViewGroup?,
                               savedInstanceState: Bundle?): View? {
        exploreViewModel = ViewModelProvider(this).get(ExploreViewModel::class.java)
        _binding = FragmentExploreBinding.inflate(inflater, container, false)

        for(items in iphoneSearch){
            iphoneSearchList.add(items)
        }

        listingsAdapter = ListingsAdapter(this)
        listingsAdapter!!.setData(iphoneSearchList)

        binding.rvSearchingResults.layoutManager = LinearLayoutManager(context)
        binding.rvSearchingResults.setHasFixedSize(true)
        binding.rvSearchingResults.adapter = listingsAdapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.expSearchingField.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.expSearchingField.clearFocus()
                val speItemModal = query?.let { ListingsModal(listingName = it) }
                if(iphoneSearchList.contains(speItemModal)){
                    listingsAdapter!!.filter.filter(query)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                listingsAdapter!!.filter.filter(newText)
                return false
            }
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun ClickedItem(listingsModal: ListingsModal) {
        Log.e("TAG", listingsModal.listingName)

        when(listingsModal.listingName){
            "iphone" -> startActivity(Intent(context, Listing1Activity::class.java))
            else -> {
                Toast.makeText(context,"No action",Toast.LENGTH_LONG).show()
            }
        }
    }
}