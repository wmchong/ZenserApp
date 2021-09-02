package com.example.zenserapp.ui.me

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.zenserapp.R
import com.example.zenserapp.databinding.FragmentMeBinding
import android.util.Log
import com.example.zenserapp.LoginPage
import com.example.zenserapp.SettingPage
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import android.content.DialogInterface
import android.graphics.Color
import android.net.Uri
import android.os.Parcelable
import android.widget.ImageView
import android.widget.Toast
import androidx.core.net.toUri
import com.example.zenserapp.ui.sell.Product
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.parcel.Parcelize
import java.util.*
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso


class MeFragment : Fragment(R.layout.fragment_me) {

    private var _binding: FragmentMeBinding? = null
    private val binding get() = _binding!!
    private lateinit var db:DatabaseReference
    private lateinit var uid:String
    // request code to pick image
    private val PICK_IMAGES_CODE = 0

    var displayString = ""

    private var selectedPhotoUri:Uri?=null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        uid=FirebaseAuth.getInstance().currentUser?.uid.toString()

        _binding = FragmentMeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        db=FirebaseDatabase.getInstance().getReference("users")
        uid=FirebaseAuth.getInstance().currentUser?.uid.toString()

        if(uid.isNotEmpty()){

            getUserData()
            getUserDisplay()

        }
        else{
            Log.d("Chat","Failed")
        }

        val tabLayout = binding.tabLayout
        val viewPager2 = binding.viewPager2
        val adapter = ViewPagerAdapter(childFragmentManager, lifecycle)
        viewPager2.adapter=adapter

        TabLayoutMediator(tabLayout,viewPager2){tab,position->
            when(position){
                0->{
                    tab.text="Listings"
                }
                1->{
                    tab.text="Wishlists"
                }
                2->{
                    tab.text="Purchases"
                }
            }
        }.attach()

        binding.settingIB.setOnClickListener {
            val intent = Intent(context, SettingPage::class.java)
            context?.startActivity(intent)
        }

        binding.logoutIB.setOnClickListener {
            showAlertDialog()
        }

        binding.displayIS.setFactory { ImageView(context) }

        binding.displayIS.setOnClickListener{
            pickImageIntent()
        }
    }

    fun showAlertDialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(activity)
        builder.setTitle("Logout")
        builder.setMessage("Are you sure you want to log out of this account?")
        builder.setPositiveButton("Logout",
            DialogInterface.OnClickListener { dialog, id ->
                val intent = Intent(context,LoginPage::class.java)
                startActivity(intent)
            })
        builder.setNegativeButton("Cancel",
            DialogInterface.OnClickListener { dialog, id ->
                dialog.dismiss()
            })
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    //prevent memory leaks
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    fun getUserData(){
        db.child(uid).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val user_name=snapshot.child("username").getValue(String::class.java).toString()
                Log.d("meFrag",user_name)
                binding.usernameTV.text=user_name
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("Database error",error.toString())
            }

        })
    }

    private fun pickImageIntent() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGES_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGES_CODE) {
            if (data != null) {
                // picked single image
                val imageUri = data.data

                //set function to save to firebase
                selectedPhotoUri = imageUri
                saveUserDisplayToFirebaseDatabase()
            }
        }
        else{
            Toast.makeText(context,"Please choose an image",Toast.LENGTH_SHORT).show()
        }
        }
    

    fun getUserDisplay(){
        db= FirebaseDatabase.getInstance().getReference("display")
        db.child(uid).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val dpUri=snapshot.child("imageUri").getValue(String::class.java).toString()
                displayString = dpUri

                Log.d("displayString",displayString)

                if (displayString.contains("https://firebasestorage.googleapis.com")){
                    val temp = displayString.toUri()
                    Log.d("temp",temp.toString())
                    Picasso.get().load(temp).into(binding.displayIV)
                    binding.displayIV.setImageResource(android.R.color.transparent)
                } else {
                    binding.displayIV.setImageResource(R.drawable.anon)
                }

            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("Database error",error.toString())
            }
        })
    }

    private fun saveUserDisplayToFirebaseDatabase(){
        val uid =FirebaseAuth.getInstance().uid
        val filename= UUID.randomUUID().toString()
        val ref=FirebaseStorage.getInstance().getReference("/display/$uid/$filename")

        Log.d("displayString",displayString)
        Log.d("selectedPhotoUri",selectedPhotoUri.toString())
        if (selectedPhotoUri != null) {
            ref.putFile(selectedPhotoUri!!)
                .addOnSuccessListener {
                    Log.d("Upload","Image uploaded")
                    ref.downloadUrl.addOnSuccessListener {
                        it.toString()
                        Log.d("upload","File location: $it")
                        handleSelection(it.toString())
                        showSnackbar("New display picture saved")
                    }
                        .addOnFailureListener{

                        }

                }
        }
    }

    private fun handleSelection(imageUrl:String) {
        val ref =
            FirebaseDatabase.getInstance().getReference("/display/$uid")
        val user = UserDisplay(uid,imageUrl)
        ref.setValue(user)
            .addOnSuccessListener {
                Log.d("Insert Activity", "image saved to database")
            }
    }

    private fun showSnackbar(msg: String) {
        val snackBar = Snackbar.make(binding.root, msg, Snackbar.LENGTH_SHORT)
        snackBar.view.setBackgroundColor(Color.parseColor("#EDEAEA"))
        snackBar.setTextColor(Color.parseColor("#000000"))
        snackBar.show()
    }
}
class UserDisplay(val uid:String ,val imageUri:String) {
    constructor() : this("","")
}