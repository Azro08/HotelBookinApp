package com.example.hotelsapp.data.repository

import com.example.hotelsapp.data.dto.hotel.SingleHotelItem
import com.example.hotelsapp.domain.repository.FavoriteHotelsRepository
import com.example.hotelsapp.helper.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FavoriteHotelsRepositoryImpl
@Inject constructor(
    private val firebaseFireStore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth) : FavoriteHotelsRepository {
    override suspend fun saveHotelToFavorites(hotel: SingleHotelItem) {
        val userId = firebaseAuth.currentUser?.uid
        val favHotelId = userId + hotel.id
        firebaseFireStore.collection(Constants.FAVORITE_HOTELS).document(favHotelId).set(hotel)
    }

    override suspend fun getHotelFromFavorites(): List<SingleHotelItem> {
        val userId = firebaseAuth.currentUser?.uid
        val collection = firebaseFireStore.collection(Constants.FAVORITE_HOTELS)
        val querySnapshot: QuerySnapshot = collection
            .whereEqualTo("userId", userId)
            .get()
            .await()

        return querySnapshot.documents.map { document ->
            document.toObject(SingleHotelItem::class.java)
                ?: throw IllegalStateException("Error converting document to SingleHotelItem")
        }
    }

}