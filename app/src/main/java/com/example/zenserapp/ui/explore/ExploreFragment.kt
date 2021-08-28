package com.example.zenserapp.ui.explore

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.zenserapp.SearchResultPage
import com.example.zenserapp.databinding.FragmentExploreBinding
import com.google.firebase.database.*

class ExploreFragment : Fragment(), SearchListAdapter.ClickListener  {
    private var _binding: FragmentExploreBinding? = null
    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!
    private lateinit var exploreViewModel: ExploreViewModel

    private lateinit var dbref:DatabaseReference
    val searchList = ArrayList<SearchModel>()
    var searchListAdapter: SearchListAdapter? = null;
    var keyWord:String?=""

    //a command
    override fun onCreateView( inflater: LayoutInflater,
                                container: ViewGroup?,
                               savedInstanceState: Bundle?): View? {

        exploreViewModel = ViewModelProvider(this).get(ExploreViewModel::class.java)
        _binding = FragmentExploreBinding.inflate(inflater, container, false)

        dbref=FirebaseDatabase.getInstance().getReference("/searchListings")
        getData()

        searchListAdapter = SearchListAdapter(this)
        searchListAdapter!!.setData(searchList)
        binding.rvSearchView.layoutManager = LinearLayoutManager(context)
        binding.rvSearchView.setHasFixedSize(true)
        binding.rvSearchView.adapter = searchListAdapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.searchField.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.rvSearchView.clearFocus()
                val speItemModal = query?.let { SearchModel(title= it) }
                keyWord = query
                if(searchList.contains(speItemModal)){
                    searchListAdapter!!.filter.filter(query)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                searchListAdapter!!.filter.filter(newText)
                keyWord = newText
                return false
            }
        })

        binding.btnSearch.setOnClickListener {
            Log.e("Search keyword", "$keyWord")
            val intent = Intent(context, SearchResultPage::class.java)
            intent.putExtra("KeyWord",keyWord)
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun ClickedItem(searchModel: SearchModel) {
        Log.e("TAG", "$searchModel.title")
        val keyWord = searchModel.title
        val intent = Intent(context, SearchResultPage::class.java)
        intent.putExtra("KeyWord",keyWord)
        startActivity(intent)
    }

    //get title from firebase
    private fun getData(){
        dbref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(nameSnapshot in snapshot.children){
                        Log.e("snapshot children", "$nameSnapshot")
                        val title= nameSnapshot.child("title").getValue(String::class.java)!!.lowercase()
                        Log.e("snapshot children title", "$title")
                        val searchmodel = SearchModel(title = "$title")
                        searchList.add(searchmodel)
                        Log.e("search list size", "${searchList.size}")
                        Log.e("search list","$searchList")
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.d("Error message",error.message)
            }
        })

    }
}