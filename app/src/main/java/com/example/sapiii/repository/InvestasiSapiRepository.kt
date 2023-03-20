package com.example.sapiii.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.sapiii.constanst.Constant
import com.example.sapiii.domain.Kambing
import com.example.sapiii.domain.Sapi
import com.google.firebase.database.*

class InvestasiSapiRepository {
    private val databaseReference: DatabaseReference =
        FirebaseDatabase.getInstance().getReference(Constant.REFERENCE_SAPI)

    @Volatile
    private var INSTANCE: InvestasiSapiRepository? = null

    fun getInstance(): InvestasiSapiRepository {
        return INSTANCE ?: synchronized(this) {

            val instance = InvestasiSapiRepository()
            INSTANCE = instance
            instance
        }
    }

}