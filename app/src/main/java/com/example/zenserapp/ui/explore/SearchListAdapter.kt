//package com.example.zenserapp.ui.explore
//
//import android.content.Intent
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.TextView
//import androidx.recyclerview.widget.RecyclerView
//import com.example.zenserapp.R
//import com.squareup.picasso.Picasso
//
//class SearchListAdapter(var searchList: List<SearchModel>): RecyclerView.Adapter<SearchListAdapter.SearchListViewHolder>(){
//    class SearchListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
//        fun bind(searchModel: SearchModel){
//            var title : TextView =itemView.findViewById(R.id.tvSearchResult)
//        }
//    }
//
//    override fun onCreateViewHolder(
//        parent: ViewGroup,
//        viewType: Int
//    ): SearchListAdapter.SearchListViewHolder {
//        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_results, parent, false)
//        return SearchListViewHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: SearchListAdapter.SearchListViewHolder, position: Int) {
//        holder.bind(searchList[position])
//    }
//
//    override fun getItemCount(): Int {
//        return searchList.size
//    }
//
//}

package com.example.zenserapp.ui.explore

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.zenserapp.R
import com.example.zenserapp.ui.explore.SearchListAdapter.SearchListAdapterVH

class SearchListAdapter (var clickListener: ClickListener) : RecyclerView.Adapter<SearchListAdapterVH>(), Filterable{

    var searchModelList = ArrayList<SearchModel>()
    var searchModelListFilter = ArrayList<SearchModel>()

    fun setData(searchModelList: ArrayList<SearchModel>){
        this.searchModelList = searchModelList
        this.searchModelListFilter = searchModelList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchListAdapterVH {
        return SearchListAdapterVH(LayoutInflater.from(parent.context).inflate(R.layout.row_results, parent, false))
    }

    override fun onBindViewHolder(holder: SearchListAdapterVH, position: Int) {
        val searchModel = searchModelList[position]
        holder.title.text = searchModel.title

        holder.itemView.setOnClickListener{
            clickListener.ClickedItem(searchModel)
        }
    }

    override fun getItemCount(): Int {
        return searchModelList.size
    }

    class SearchListAdapterVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView =itemView.findViewById(R.id.tvSearchResult)
    }

    interface ClickListener{
        fun ClickedItem(searchModel: SearchModel)
    }

    override fun getFilter(): Filter {
        return object: Filter(){
            override fun performFiltering(charsequence: CharSequence?): FilterResults {
                val filterResults = FilterResults()
                if(charsequence == null ||charsequence.length <0){
                    filterResults.count = searchModelListFilter.size
                    filterResults.values = searchModelListFilter
                }else{
                    val searchChr = charsequence.toString().lowercase()

                    val searchModal= ArrayList<SearchModel>()

                    for (item in searchModelListFilter){
                        if(item.title!!.contains(searchChr)){
                            searchModal.add(item)
                        }
                    }

                    filterResults.count = searchModal.size
                    filterResults.values = searchModal

                }
                return filterResults
            }

            override fun publishResults(p0: CharSequence?, filterResults: FilterResults?) {
                searchModelList = filterResults!!.values as ArrayList<SearchModel>
                notifyDataSetChanged()

            }

        }
    }
}