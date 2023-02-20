package com.example.sapiii.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.sapiii.constanst.Constant.REFERENCE_SAPI
import com.example.sapiii.domain.Sapi
import com.google.firebase.database.*
import com.google.gson.Gson

class SapiRepository {
    private val databaseReference: DatabaseReference =
        FirebaseDatabase.getInstance().getReference(REFERENCE_SAPI)

    @Volatile
    private var INSTANCE: SapiRepository? = null

    fun getInstance(): SapiRepository {
        return INSTANCE ?: synchronized(this) {

            val instance = SapiRepository()
            INSTANCE = instance
            instance
        }
    }

    fun loadSapi(): LiveData<List<Sapi>> {
        val data = MutableLiveData<List<Sapi>>()
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                try {
                    data.value = snapshot.children.map { dataSnapshot ->
                        dataSnapshot.getValue(Sapi::class.java)!!
                    }
                } catch (e: Exception) {
                    data.value = emptyList()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                data.value = emptyList()
            }
        })

        return data
    }

    fun addSapi(sapi: Sapi, onComplete: (isSuccess: Boolean) -> Unit) {
        databaseReference.child(sapi.tag).setValue(sapi)
            .addOnCompleteListener {
                onComplete(it.isSuccessful)
            }
    }

    fun updateSapi(sapi: Sapi, onComplete: (isSuccess: Boolean) -> Unit) {
        databaseReference.child(sapi.tag)
            .updateChildren(sapi.toMap())
            .addOnCompleteListener {
                onComplete(it.isSuccessful)
            }
    }
}