package com.example.sapiii.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.sapiii.constanst.Constant
import com.example.sapiii.domain.MutasiHewan
import com.google.firebase.database.*

class MutasiKambingRepository {
    private val databaseReference: DatabaseReference =
        FirebaseDatabase.getInstance().getReference(Constant.REFERENCE_MUTASI_KAMBING)

    @Volatile
    private var INSTANCE: MutasiKambingRepository? = null

    fun getInstance(): MutasiKambingRepository {
        return INSTANCE ?: synchronized(this) {

            val instance = MutasiKambingRepository()
            INSTANCE = instance
            instance
        }
    }

    fun loadMutasiKambing(): LiveData<List<MutasiHewan>> {
        val data = MutableLiveData<List<MutasiHewan>>()
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                try {
                    data.value = snapshot.children.map { dataSnapshot ->
                        dataSnapshot.getValue(MutasiHewan::class.java)!!
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