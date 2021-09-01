package com.example.zenserapp.ui.sell

import android.app.Activity
import android.content.Intent
import android.media.Image
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
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
import com.example.zenserapp.ui.chat.ChatPage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.util.*
import kotlin.collections.ArrayList

class SellFragment : Fragment(), SellRecyclerAdapter.OnItemClickListener {
    private var _binding: FragmentSellBinding? = null
    private val binding get() = _binding!!
    private var images: ArrayList<Uri?>? = null
    private var position = 0
    private lateinit var list:ArrayList<Category>

    var selectedPhotoUri:Uri?=null

    // request code to pick image(s)
    private val PICK_IMAGES_CODE = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSellBinding.inflate(inflater, container, false)

        images = ArrayList()
        binding.saveImageIS.setFactory { ImageView(context) }

        binding.pickImagesBtn.setOnClickListener {
            pickImagesIntent()
        }

        binding.nextImageBtn.setOnClickListener {
            if (position < images!!.size-1) {
                position++
                binding.saveImageIS.setImageURI(images!![position])
            }
            else {
                // no more images
                Toast.makeText(context, "No more images...", Toast.LENGTH_SHORT).show()
            }
        }

        binding.prevImageBtn.setOnClickListener {
            if (position > 0) {
                position--
                binding.saveImageIS.setImageURI(images!![position])
            }
            else {
                // no more images
                Toast.makeText(context, "No more images...", Toast.LENGTH_SHORT).show()
            }
        }

        // category list recyclerview
        val categoryList = generateCategoryList()
        binding.categoriesRV.adapter = SellRecyclerAdapter(categoryList, this)
        binding.categoriesRV.layoutManager = LinearLayoutManager(context)
        binding.categoriesRV.setHasFixedSize(true)

        return binding.root
    }

    private fun pickImagesIntent() {
        val intent = Intent()
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Image(s)"), PICK_IMAGES_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGES_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                if (data!!.clipData != null) {
                    // picked multiple times
                    // get number of picked images
                    val count = data.clipData!!.itemCount
                    for (i in 0 until count) {
                        val imageUri = data.clipData!!.getItemAt(i).uri
                        // add images to list
                        images!!.add(imageUri)
                    }
                    // set first image from list to image switcher
                    binding.saveImageIS.setImageURI(images!![0])
                    position == 0
                }
                else {
                    // picked single image
                    val imageUri = data.data
                    selectedPhotoUri=imageUri
                    // set image to image switcher
                    binding.saveImageIS.setImageURI(imageUri)
                    position == 0

                }
            }
        }
    }
    override fun onItemClick(position: Int) {
        val intent = Intent(context, ListingDetails::class.java)
        Log.d("SELL PAGE",list[position].text1)
        Log.d("SELL PAGE",selectedPhotoUri.toString())
        intent.putExtra("Category",list[position].text1)
        intent.putExtra("PhotoUrl",selectedPhotoUri.toString())
        if(selectedPhotoUri==null){
            Toast.makeText(context,"Please select an image of the product before proceeding",Toast.LENGTH_SHORT).show()
        }
        else{
            context?.startActivity(intent)
        }
    }

    private fun generateCategoryList(): List<Category> {
         list = ArrayList<Category>()
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