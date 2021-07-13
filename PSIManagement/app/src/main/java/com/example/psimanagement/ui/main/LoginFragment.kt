package com.example.psimanagement.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.psimanagement.R
import com.example.psimanagement.databinding.LoginFragmentBinding


class LoginFragment : Fragment() {

    companion object {
        fun newInstance() = LoginFragment()
    }

    private lateinit var viewModel: MainViewModel
//Binding1
//    GRADLE有+這個
//buildFeatures{
//        viewBinding = true
//    }

//Binding2
        private lateinit var binding: LoginFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//Binding3
        binding = LoginFragmentBinding.inflate(inflater, container, false)
        binding.btnLogin.setOnClickListener {
            //cupcake navigation4

            viewModel.setUser(binding.etTest.text.toString())
            findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
            Log.d("IANIAN","loginFragment43 viewModel.getUser().toString():"+viewModel.getUser().toString())
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        // TODO: Use the ViewModel
    }



}