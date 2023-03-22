package com.example.sapiii.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.sapiii.constanst.Constant
import com.example.sapiii.domain.MutasiHewan
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




    fun loadMutasiSapi(): LiveData<List<MutasiHewan>> {
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

    fun addMutasiSapi(mutasiHewan: MutasiHewan, onComplete: (isSuccess: Boolean) -> Unit) {
        databaseReference.child(mutasiHewan.nama).setValue(mutasiHewan)
            .addOnCompleteListener {
                onComplete(it.isSuccessful)
            }
    }

    fun updateMutsiSapi(mutasiHewan: MutasiHewan, onComplete: (isSuccess: Boolean) -> Unit) {
        databaseReference.child(mutasiHewan.nama)
            .updateChildren(mutasiHewan.toMap())
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