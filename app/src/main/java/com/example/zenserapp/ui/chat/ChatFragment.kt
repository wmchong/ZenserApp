package com.example.zenserapp.ui.chat

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.zenserapp.R
import com.example.zenserapp.databinding.FragmentChatBinding


class ChatFragment : Fragment() {

    private var _binding: FragmentChatBinding? = null
    private val binding get() = _binding!!

    private var titlesList = mutableListOf<String>()
    private var descList = mutableListOf<String>()
    private var imagesList = mutableListOf<Int>()

    val users: Array<String> = arrayOf("Jim", "Anne", "Bob")


    companion object {
       fun newInstance() = ChatFragment()
    }

    private lateinit var viewModel: ChatViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChatBinding.inflate(inflater,container, false)


        postToList()

        binding.rvRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.rvRecyclerView.adapter =
            context?.let { RecyclerAdapter(it, titlesList, descList, imagesList) }

        return binding.root

    } // random comment

//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProvider(this).get(ChatViewModel::class.java)
//        // TODO: Use the ViewModel
//    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun addToList(title: String, description: String, image: Int) {
        titlesList.add(title)
        descList.add(description)
        imagesList.add(image)
    }

    private fun postToList() {
        for (i in 1..3){
            addToList(users[i-1], "Description $i", R.mipmap.ic_launcher_round)
        }
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        binding.rvRecyclerView.setOnClickListener {
//            val intent = Intent(context, ChatPage::class.java)
//            context?.startActivity(intent)
//        }
//    }

}