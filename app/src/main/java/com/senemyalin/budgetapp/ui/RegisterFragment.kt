package com.senemyalin.budgetapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.senemyalin.budgetapp.R
import com.senemyalin.budgetapp.common.click
import com.senemyalin.budgetapp.common.toastMessage
import com.senemyalin.budgetapp.common.viewBinding
import com.senemyalin.budgetapp.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment(R.layout.fragment_register) {

    private val binding by viewBinding(FragmentRegisterBinding::bind)
    private lateinit var auth : FirebaseAuth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = Firebase.auth

        if(auth.currentUser != null){
            findNavController().navigate(R.id.action_registerFragment_to_incomeFragment2)
        }

        with(binding){
            btnRegister.click {
                val getEmail = etEmail.text.toString()
                val getPassword = etPassword.text.toString()

                if (getEmail.isNotEmpty() && getPassword.isNotEmpty()) {
                    register(getEmail,getPassword)
                }
                else requireContext().toastMessage("Try again.")
            }

            tvLogin.click {
                findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
            }
        }
    }

    private fun register(email:String, password:String){
        auth.createUserWithEmailAndPassword(email,password).addOnSuccessListener {
            findNavController().navigate(R.id.action_registerFragment_to_incomeFragment2)
            requireContext().toastMessage("Sign up successfully!")
        }.addOnFailureListener {
            requireContext().toastMessage(it.message.orEmpty())
        }
    }
}