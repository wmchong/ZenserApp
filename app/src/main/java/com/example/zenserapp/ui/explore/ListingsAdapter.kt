package com.example.zenserapp.ui.explore

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.zenserapp.R
import com.example.zenserapp.ui.explore.ListingsAdapter.ListingsAdapterVH

class ListingsAdapter
    (var clickListener: ClickListener)
    : RecyclerView.Adapter<ListingsAdapterVH>(), Filterable{

    var listingModalList = ArrayList<ListingsModal>()
    var listingModalListFilter = ArrayList<ListingsModal>()

    fun setData(listingModalList: ArrayList<ListingsModal>){
        this.listingModalList = listingModalList
        this.listingModalListFilter = listingModalList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListingsAdapterVH {
        return ListingsAdapterVH(LayoutInflater.from(parent.context).inflate(R.layout.row_results, parent, false))
    }

    override fun onBindViewHolder(holder: ListingsAdapterVH, position: Int) {
        val listingsModal = listingModalList[position]
        holder.listingName.text = listingsModal.listingName

        holder.itemView.setOnClickListener{
            clickListener.ClickedItem(listingsModal)
        }
    }

    override fun getItemCount(): Int {
        return listingModalList.size
    }

    class ListingsAdapterVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val listingName: TextView =itemView.findViewById(R.id.tvSearchingListing)
    }

    interface ClickListener{
        fun ClickedItem(listingsModal: ListingsModal)
    }

    override fun getFilter(): Filter {
        return object: Filter(){
            override fun performFiltering(charsequence: CharSequence?): FilterResults {
                val filterResults = FilterResults()
                if(charsequence == null ||charsequence.length <0){
                    filterResults.count = listingModalListFilter.size
                    filterResults.values = listingModalListFilter
                }else{
                    val searchChr = charsequence.toString().lowercase()

                    val listingModal= ArrayList<ListingsModal>()

                    for (item in listingModalListFilter){
                        if(item.listingName.contains(searchChr)){
                            listingModal.add(item)
                        }
                    }

                    filterResults.count = listingModal.size
                    filterResults.values = listingModal

                }
                return filterResults
            }

            override fun publishResults(p0: CharSequence?, filterResults: FilterResults?) {
                listingModalList = filterResults!!.values as ArrayList<ListingsModal>
                notifyDataSetChanged()

            }

        }
    }
}