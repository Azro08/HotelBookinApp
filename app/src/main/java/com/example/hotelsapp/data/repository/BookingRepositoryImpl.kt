package com.example.hotelsapp.data.repository

import com.example.hotelsapp.data.dto.hotel_booking.BookingDetails
import com.example.hotelsapp.domain.repository.BookingRepository
import com.example.hotelsapp.helper.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class BookingRepositoryImpl
@Inject constructor(
    private val firebaseFireStore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth
) : BookingRepository {
    override suspend fun bookHotel(bookingDetails: BookingDetails) {
        firebaseFireStore.collection(Constants.BOOKING).document().set(bookingDetails)
    }

    override suspend fun getBookingHistory(): List<BookingDetails> {
        val userId = firebaseAuth.currentUser?.uid
        val collection = firebaseFireStore.collection(Constants.BOOKING)
        val querySnapshot: QuerySnapshot = collection
            .whereEqualTo("userId", userId)
            .get()
            .await()

        return querySnapshot.documents.map { document ->
            document.toObject(BookingDetails::class.java)
                ?: throw IllegalStateException("Error converting document")
        }
    }

    override suspend fun removeBookedHotel(hotelName: String) {
        val userId = firebaseAuth.currentUser?.uid
        val collection = firebaseFireStore.collection(Constants.BOOKING)
        val querySnapshot: QuerySnapshot = collection
            .whereEqualTo("userId", userId)
            .whereEqualTo("hotelName", hotelName)
            .get()
            .await()

        querySnapshot.documents.forEach { document ->
            collection.document(document.id).delete()
        }
    }

}