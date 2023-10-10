package com.example.hotelsapp.presentation.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.hotelsapp.R
import com.example.hotelsapp.databinding.FragmentSignUpBinding
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SignUpFragment : Fragment() {
    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var firebaseAuth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignUpBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.buttonSignup.setOnClickListener {
            signup()
        }
    }

    private fun signup() = with(binding) {
        val email = editTextSignupEmail.text.toString()
        val password = editTextSignupPassword.text.toString()
        val rePassword = editTextSignupRepPassword.text.toString()

        if (password != rePassword) {
            editTextSignupPassword.error = "check password"
            editTextSignupRepPassword.error = "check password"
        } else {
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(
                            requireContext(),
                            "account created",
                            Toast.LENGTH_LONG
                        ).show()
                        findNavController().navigate(R.id.nav_to_login)
                    } else {
                        Toast.makeText(
                            requireContext(),
                            task.exception?.message.toString(),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}