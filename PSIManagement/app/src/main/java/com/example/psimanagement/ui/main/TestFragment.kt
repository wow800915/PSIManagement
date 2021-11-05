package com.example.psimanagement.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.psimanagement.MainActivity.Companion.testFragment
import com.example.psimanagement.PSIManagamentApplication
import com.example.psimanagement.R
import com.example.psimanagement.databinding.TestFragmentBinding
import com.google.firebase.database.FirebaseDatabase


class TestFragment : Fragment() {

    companion object {
        fun newInstance() = TestFragment()
    }

    //20210906這邊room開始有改
//    private val viewModel: MainViewModel by activityViewModels()
    private val viewModel: MainViewModel by activityViewModels {
        MainViewModelFactory(
                (activity?.application as PSIManagamentApplication).inventoryItemDatabase.inventoryItemDao(),
                (activity?.application as PSIManagamentApplication).salesItemDatabase.salesItemDao(),
                (activity?.application as PSIManagamentApplication).purchaseItemDatabase.purchaseItemDao(),
                (activity?.application as PSIManagamentApplication).scrapItemDatabase.scrapItemDao()
        )
    }

    private var binding: TestFragmentBinding? = null

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        activity?.setTitle(R.string.title_purchase)
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("message")
        binding = TestFragmentBinding.inflate(inflater, container, false)
        binding!!.btnLogin.setOnClickListener {
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
            testFragment = this@TestFragment
        }


    }

    fun goToMainFragment() {
        findNavController().navigate(R.id.action_testFragment_to_salesFragment)
    }


}