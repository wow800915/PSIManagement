package com.example.psimanagement.ui.main

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.psimanagement.R
import com.example.psimanagement.PSIManagamentApplication
import com.example.psimanagement.databinding.LoginFragmentBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class LoginFragment : Fragment() {

    companion object {
        fun newInstance() = LoginFragment()
    }
    //20210906這邊room開始有改
//    private val viewModel: MainViewModel by activityViewModels()
    private val viewModel: MainViewModel by activityViewModels {
        MainViewModelFactory(
            (activity?.application as PSIManagamentApplication).database.inventoryDao()
        )
    }
    private var binding: LoginFragmentBinding? = null



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("message")
        binding = LoginFragmentBinding.inflate(inflater, container, false)
        binding!!.btnLogin.setOnClickListener {
            viewModel.addNewItem()
            // Write a message to the database
            // Write a message to the database
//            myRef.setValue("Hello, World!534564")
//            myRef.addValueEventListener(object : ValueEventListener {
//                override fun onDataChange(dataSnapshot: DataSnapshot) {
//                    // This method is called once with the initial value and again
//                    // whenever data at this location is updated.
//                    val value = dataSnapshot.getValue(String::class.java)
//                    Log.d(TAG, "Value is: $value")
//                }
//
//                override fun onCancelled(error: DatabaseError) {
//                    // Failed to read value
//                    Log.w(TAG, "Failed to read value.", error.toException())
//                }
//            })
//            val database = Firebase.database
//            val myRef = database.getReference("message")

//            myRef.setValue("Hello, World!")
//            viewModel.setUser(binding!!.etTest.text.toString())
//            findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
//            Log.d("IANIAN","loginFragment43 viewModel.getUser().toString():"+viewModel.getUser().toString())
//            Log.d("IANIAN", "viewModel.backingPropertyTest: ${viewModel.backingPropertyTest} ");
        }
//        binding!!.title = "标题LOGINFRAGMENT"
//
//        repeat(3) {
//            GlobalScope.launch {
//                println("Hi from ${Thread.currentThread()}")
//            }
//        }




        return binding!!.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        // TODO: Use the ViewModel
        binding?.apply {
//            lifecycleOwner = viewLifecycleOwner
            //20210906這邊room開始有改
//            mainViewModel = viewModel
            loginFragment = this@LoginFragment
        }


    }

    fun goToMainFragment(){
        findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
    }




}