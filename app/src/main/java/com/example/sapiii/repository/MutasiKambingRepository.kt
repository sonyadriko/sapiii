package com.example.sapiii.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.sapiii.constanst.Constant
import com.example.sapiii.domain.MutasiKambing
import com.example.sapiii.domain.MutasiSapi
import com.google.firebase.database.*

class MutasiKambingRepository {
    private val databaseReference: DatabaseReference =
        FirebaseDatabase.getInstance().getReference(Constant.REFERENCE_MUTASI_SAPI)

    @Volatile
    private var INSTANCE: MutasiKambingRepository? = null

    fun getInstance(): MutasiKambingRepository {
        return INSTANCE ?: synchronized(this) {

            val instance = MutasiKambingRepository()
            INSTANCE = instance
            instance
        }
    }

    fun loadMutasiKambing(): LiveData<List<MutasiKambing>> {
        val data = MutableLiveData<List<MutasiKambing>>()
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                try {
                    data.value = snapshot.children.map { dataSnapshot ->
                        dataSnapshot.getValue(MutasiKambing::class.java)!!
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

}