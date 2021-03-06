package com.example.zenserapp.ui.categories

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
import com.google.firebase.database.*
import com.squareup.picasso.Picasso

class MyAdapter(private val context: Context, private val productList:ArrayList<Product>) :
    RecyclerView.Adapter<MyAdapter.MyViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView=LayoutInflater.from(parent.context).inflate(R.layout.product,parent,false)
        return  MyViewHolder(itemView)
    }
    private val uid = FirebaseAuth.getInstance().uid
    private val productInWishlist: ArrayList<String> = ArrayList<String>()

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = productList[position]
        holder.title.text = currentItem.title
        holder.price.text = currentItem.price
        holder.username.text = currentItem.username
        //  Picasso.get().load(productList[position].image).into(holder.image?.findViewById(R.id.iv_productImage))
        //  Glide.with(context).load(currentItem.imageUrl).into(holder.image)
        Picasso.get().load(currentItem.imageUrl).into(holder.image)

        //to make heart icon in red if that listing is in the wishlist already
        val titleOnThePage = holder.title.text.toString()
        val query: Query = FirebaseDatabase.getInstance().getReference("/wishlist/$uid/")
        query.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(productSnapshot in snapshot.children){
                        val product=productSnapshot.getValue(Product::class.java)
                        val title =  product!!.title
                        if(title.equals(titleOnThePage)){
                            //the listing is in your wishlist
                            holder.favourites.setBackgroundResource(R.drawable.ic_baseline_favorite_selected_24)
                            productInWishlist.add(title.toString())
                            Log.d("two titles equal","$title, $titleOnThePage")
                        }else{
                            Log.d("two titles not equal","$title, $titleOnThePage")
                        }
                    }
                    Log.d("List of listings that are in wishlist","$productInWishlist")
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.d("Error message",error.message)
            }
        })

        holder.itemView.setOnClickListener {
            val intent = Intent(context, ProductDetails::class.java)
            intent.putExtra("Title", currentItem.title)
            intent.putExtra("Price", currentItem.price)
            intent.putExtra("Username", currentItem.username)
            intent.putExtra("Image", currentItem.imageUrl)
            intent.putExtra("Description", currentItem.desc)
            intent.putExtra("Condition", currentItem.condition)
            intent.putExtra("Method", currentItem.methodDelivery)
            intent.putExtra("UID",currentItem.uid)
            context.startActivity(intent)

        }

            holder.favourites.setOnClickListener {
                var addToWishlistState = true
                for(lst in productInWishlist){
                    if(lst.equals("${currentItem.title}")){
                        //meaning user want to remove that listing from the wishlist
                        val ref =
                            FirebaseDatabase.getInstance().getReference("/wishlist/$uid/${currentItem.title}").removeValue()
                        holder.favourites.setBackgroundResource(R.drawable.ic_baseline_favorite_24)
                        productInWishlist.remove(lst)
                        Log.d("Update the list of listings that are in wishlist: delete","$productInWishlist")
                        addToWishlistState = false
                        break
                    }else{
                    }
                }
                //meaning user wants to add the listing to the wishlist
                if(addToWishlistState) {
                    holder.favourites.setBackgroundResource(R.drawable.ic_baseline_favorite_selected_24)
                    Toast.makeText(context, currentItem.title, Toast.LENGTH_SHORT).show()
                    val ref =
                        FirebaseDatabase.getInstance()
                            .getReference("/wishlist/$uid/${currentItem.title}")
                    val fav = Wishlist(
                        currentItem.title,
                        currentItem.imageUrl,
                        currentItem.price,
                        currentItem.username
                    )
                    ref.setValue(fav)
                        .addOnSuccessListener {
                            Log.d("Added to wish list", "product saved to database")
                        }
                    productInWishlist.add("${currentItem.title}")
                    Log.d("Update the list of listings that are in wishlist: delete","$productInWishlist")
                }
            }
        }

    override fun getItemCount(): Int {
       return productList.size
    }
    class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        var image: ImageView =itemView.findViewById(R.id.iv_productImage)
        var title: TextView=itemView.findViewById(R.id.tv_title)
        var price: TextView=itemView.findViewById(R.id.tv_price)
        var username: TextView=itemView.findViewById(R.id.tv_username)
        var favourites:ImageButton=itemView.findViewById(R.id.b_favourite)

    }

}
class Wishlist(val title:String?="",val imageUrl:String?="",val price:String?="",val username:String?="")