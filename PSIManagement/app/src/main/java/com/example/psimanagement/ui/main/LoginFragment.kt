package com.example.psimanagement.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.psimanagement.R
import com.example.psimanagement.databinding.LoginFragmentBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class LoginFragment : Fragment() {

    companion object {
        fun newInstance() = LoginFragment()
    }
    private val viewModel: MainViewModel by activityViewModels()

    private var binding: LoginFragmentBinding? = null



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LoginFragmentBinding.inflate(inflater, container, false)
        binding!!.btnLogin.setOnClickListener {

//            viewModel.setUser(binding!!.etTest.text.toString())
            findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
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
            mainViewModel = viewModel
            loginFragment = this@LoginFragment
        }


    }

    fun goToMainFragment(){
        findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
    }




}