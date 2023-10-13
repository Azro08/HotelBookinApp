package com.example.hotelsapp.presentation.auth

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.hotelsapp.R
import com.example.hotelsapp.databinding.FragmentLoginBinding
import com.example.hotelsapp.helper.AuthManager
import com.example.hotelsapp.presentation.MainActivity
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    @Inject
    lateinit var authManager: AuthManager
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.textViewSignup.setOnClickListener{
            findNavController().navigate(R.id.nav_to_signup)
        }

        binding.buttonLogin.setOnClickListener{
            login()
        }
    }

    private fun login() = with(binding){
        val email = editTextLoginEmail.text.toString()
        val password = editTextLoginPassword.text.toString()

        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {task ->
                if (task.isSuccessful){
                    authManager.saveUer(email)
                    Toast.makeText(
                        requireContext(),
                        "Logged in",
                        Toast.LENGTH_LONG
                    ).show()
                    requireActivity().startActivity(Intent(requireContext(), MainActivity::class.java))
                    requireActivity().finish()
                } else{
                    Toast.makeText(
                        requireContext(),
                        task.exception?.message.toString(),
                        Toast.LENGTH_LONG
                    ).show()
                }

            }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}