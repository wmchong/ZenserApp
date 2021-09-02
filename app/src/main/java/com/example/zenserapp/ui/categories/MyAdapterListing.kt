package com.example.zenserapp.ui.categories

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.zenserapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso

class MyAdapterListing(private val context: Context, private val productList:ArrayList<Product>) :
    RecyclerView.Adapter<MyAdapterListing.MyViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView= LayoutInflater.from(parent.context).inflate(R.layout.listing,parent,false)
        return  MyViewHolder(itemView)
    }


    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = productList[position]
        holder.title.text = currentItem.title
        holder.price.text = currentItem.price
        holder.username.text = currentItem.username
        //  Picasso.get().load(productList[position].image).into(holder.image?.findViewById(R.id.iv_productImage))
        //  Glide.with(context).load(currentItem.imageUrl).into(holder.image)
        Picasso.get().load(currentItem.imageUrl).into(holder.image)

        holder.itemView.setOnClickListener {
            val intent = Intent(context, ProductDetails::class.java)
            intent.putExtra("Title", currentItem.title)
            intent.putExtra("Price", currentItem.price)
            intent.putExtra("Username", currentItem.username)
            intent.putExtra("Image", currentItem.imageUrl)
            intent.putExtra("Description", currentItem.desc)
            intent.putExtra("Condition", currentItem.condition)
            intent.putExtra("Method", currentItem.methodDelivery)
            context.startActivity(intent)
            notifyDataSetChanged()

        }
        holder.remove.setOnClickListener {
            val builder = AlertDialog.Builder(context)
            val uid=FirebaseAuth.getInstance().uid
            builder.setMessage("Are you sure you want to Delete?")
                .setCancelable(false)
                .setPositiveButton("Yes") { dialog, id ->

                    FirebaseDatabase.getInstance().getReference("/listing/$uid/${currentItem.title}").removeValue()
                    FirebaseDatabase.getInstance().getReference("/products/Computers & Tech/${currentItem.title}").child("uid").parent?.removeValue()
                    FirebaseDatabase.getInstance().getReference("/products/Learning & Enrichment/${currentItem.title}").child("uid").parent?.removeValue()
                    FirebaseDatabase.getInstance().getReference("/products/Men's Fashion/${currentItem.title}").child("uid").parent?.removeValue()
                    FirebaseDatabase.getInstance().getReference("/products/Services/${currentItem.title}").child("uid").parent?.removeValue()
                    FirebaseDatabase.getInstance().getReference("/products/Tickets & Vouchers/${currentItem.title}").child("uid").parent?.removeValue()
                    FirebaseDatabase.getInstance().getReference("/products/Women's Fashion/${currentItem.title}").child("uid").parent?.removeValue()
                    FirebaseDatabase.getInstance().getReference("/searchListings/${currentItem.title}").child("uid").parent?.removeValue()

                    productList.remove(currentItem)
                    notifyDataSetChanged()

                }
                .setNegativeButton("No") { dialog, id ->
                    dialog.dismiss()
                }
            val alert = builder.create()
            alert.show()
        }

    }



    override fun getItemCount(): Int {
        return productList.size
    }
    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var image: ImageView =itemView.findViewById(R.id.iv_productImage)
        var title: TextView =itemView.findViewById(R.id.tv_title)
        var price: TextView =itemView.findViewById(R.id.tv_price)
        var username: TextView =itemView.findViewById(R.id.tv_username)
        var remove: ImageView=itemView.findViewById(R.id.iv_remove)

    }

}
