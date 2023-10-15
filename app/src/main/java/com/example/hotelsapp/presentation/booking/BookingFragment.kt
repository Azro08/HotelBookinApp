package com.example.hotelsapp.presentation.booking

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.hotelsapp.data.dto.hotel_booking.BookingDetails
import com.example.hotelsapp.data.dto.hotel_booking.CardDetails
import com.example.hotelsapp.databinding.FragmentBookingBinding
import com.example.hotelsapp.helper.Constants
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class BookingFragment : Fragment() {
    private var _binding: FragmentBookingBinding? = null
    private val binding get() = _binding!!
    private val viewModel: BookingViewModel by viewModels()

    @Inject
    lateinit var firebaseAuth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBookingBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val hotelName = arguments?.getString(Constants.HOTEL_NAME_KEY)
        hotelName?.let {
            binding.textViewHotelName.text = hotelName
        }
        binding.buttonFinish.setOnClickListener {
            if (areAllFieldsFilled()) {
                bookHotel()
                Toast.makeText(requireContext(), "Booked", Toast.LENGTH_LONG).show()
                findNavController().popBackStack()
            } else Toast.makeText(requireContext(), "Please fill all the fields", Toast.LENGTH_LONG)
                .show()
        }

    }

    private fun bookHotel() = with(binding) {
        val hotelName = textViewHotelName.text.toString()
        val firstName = editTextFirstName.text.toString()
        val lastName = editTextLastName.text.toString()
        val email = editTextEmail.text.toString()
        val phoneNumber = editTextPhoneNumber.text.toString()
        val checkInDate = editTextCheckInDate.text.toString()
        val checkOutDate = editTextCheckOutDate.text.toString()
        val adultsNumber = editTextAdultsNumber.text.toString().toIntOrNull() ?: 0
        val childrenNumber = editTextChildrenNumber.text.toString().toIntOrNull() ?: 0
        val paymentType = getPaymentType()
        val cardNumber = editTextCardNumber.text.toString().toIntOrNull() ?: 0
        val expiryDate = editTextExpiryDate.text.toString()
        val cvv = editTextCVV.text.toString().toIntOrNull() ?: 0
        val userId = firebaseAuth.currentUser?.uid.toString()

        val bookingDetails = BookingDetails(
            hotelName = hotelName,
            userId = userId,
            firstName = firstName,
            lastName = lastName,
            email = email,
            phoneNum = phoneNumber,
            checkInDate = checkInDate,
            checkOutDate = checkOutDate,
            adultNumber = adultsNumber,
            childrenNum = childrenNumber,
            paymentType = paymentType,
            cardDetails = CardDetails(cardNumber, expiryDate, cvv)
        )

        viewModel.bookHotel(bookingDetails)

    }

    private fun areAllFieldsFilled(): Boolean = with(binding) {
        // Check if any of the required fields are empty
        return editTextFirstName.text.isNotEmpty() &&
                editTextLastName.text.isNotEmpty() &&
                editTextAdultsNumber.text.isNotEmpty() &&
                editTextChildrenNumber.text.isNotEmpty() &&
                editTextPhoneNumber.text.isNotEmpty() &&
                editTextEmail.text.isNotEmpty() &&
                editTextCheckInDate.text.isNotEmpty() &&
                editTextCheckOutDate.text.isNotEmpty() &&
                (!radioButtonCreditCard.isChecked ||
                        (editTextCardNumber.text.isNotEmpty() &&
                                editTextExpiryDate.text.isNotEmpty() &&
                                editTextCVV.text.isNotEmpty()))
    }

    private fun getPaymentType(): String {
        // Get the selected payment type from RadioGroup
        return if (binding.radioButtonCreditCard.isChecked) {
            "Credit Card"
        } else {
            "Cash"
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}