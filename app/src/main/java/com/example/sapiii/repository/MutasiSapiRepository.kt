package com.example.sapiii.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.sapiii.constanst.Constant
import com.example.sapiii.domain.Artikel
import com.example.sapiii.domain.Kambing
import com.example.sapiii.domain.MutasiSapi
import com.example.sapiii.util.toKambingDomain
import com.google.firebase.database.*

class MutasiSapiRepository {
    private val databaseReference: DatabaseReference =
        FirebaseDatabase.getInstance().getReference(Constant.REFERENCE_MUTASI_SAPI)


        @Volatile
        private var INSTANCE: MutasiSapiRepository? = null

        fun getInstance(): MutasiSapiRepository {
            return INSTANCE ?: synchronized(this) {

                val instance = MutasiSapiRepository()
                INSTANCE = instance
                instance
            }
        }




    fun loadMutasiSapi(): LiveData<List<MutasiSapi>> {
        val data = MutableLiveData<List<MutasiSapi>>()
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                try {
                    data.value = snapshot.children.map { dataSnapshot ->
                        dataSnapshot.getValue(MutasiSapi::class.java)!!
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

    fun addMutasiSapi(mutasiSapi: MutasiSapi, onComplete: (isSuccess: Boolean) -> Unit) {
        databaseReference.child(mutasiSapi.nama).setValue(mutasiSapi)
            .addOnCompleteListener {
                onComplete(it.isSuccessful)
            }
    }

    fun updateMutsiSapi(mutasiSapi: MutasiSapi, onComplete: (isSuccess: Boolean) -> Unit) {
        databaseReference.child(mutasiSapi.nama)
            .updateChildren(mutasiSapi.toMap())
            .addOnCompleteListener {
                onComplete(it.isSuccessful)
            }
    }

    fun removeMutasiSpi(namaMutasiSapi: String, onComplete: (isSuccess: Boolean) -> Unit) {
        databaseReference.child(namaMutasiSapi).removeValue { error, _ ->
            onComplete(error == null)
        }
    }

}