package com.example.hotelsapp.di

import android.content.Context
import com.example.hotelsapp.data.api.HotelsListApi
import com.example.hotelsapp.data.api.LocationGeoIdApi
import com.example.hotelsapp.data.repository.FavoriteHotelsRepositoryImpl
import com.example.hotelsapp.data.repository.RegionIdRepositoryImpl
import com.example.hotelsapp.data.repository.HotelsListRepositoryImpl
import com.example.hotelsapp.domain.repository.FavoriteHotelsRepository
import com.example.hotelsapp.domain.repository.RegionIdRepository
import com.example.hotelsapp.domain.repository.HotelsListRepository
import com.example.hotelsapp.helper.AuthManager
import com.example.hotelsapp.helper.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideGeoIdApi(): LocationGeoIdApi =
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(LocationGeoIdApi::class.java)


    @Provides
    @Singleton
    fun provideHotelsListApi(): HotelsListApi =
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(HotelsListApi::class.java)

    @Provides
    @Singleton
    fun provideHotelsListRepository(api: HotelsListApi): HotelsListRepository =
        HotelsListRepositoryImpl(api)

    @Provides
    @Singleton
    fun provideGeoIdRepository(api: LocationGeoIdApi): RegionIdRepository =
        RegionIdRepositoryImpl(api)

    @Provides
    @Singleton
    fun provideAuthManager(@ApplicationContext context: Context): AuthManager =
        AuthManager(context)

    @Provides
    @Singleton
    fun provideFavoriteHotelsRepository(
        firestore: FirebaseFirestore,
        firebaseAuth: FirebaseAuth
    ): FavoriteHotelsRepository =
        FavoriteHotelsRepositoryImpl(firestore, firebaseAuth)

}