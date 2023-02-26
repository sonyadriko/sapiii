package com.example.sapiii.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.sapiii.constanst.Constant
import com.example.sapiii.domain.Kambing
import com.example.sapiii.domain.Sapi
import com.google.firebase.database.*

class KambingRepository {
    private val databaseReference: DatabaseReference =
        FirebaseDatabase.getInstance().getReference(Constant.REFERENCE_KAMBING)

    @Volatile
    private var INSTANCE: KambingRepository? = null

    fun getInstance(): KambingRepository {
        return INSTANCE ?: synchronized(this) {

            val instance = KambingRepository()
            INSTANCE = instance
            instance
        }
    }

    fun loadKambing(): LiveData<List<Kambing>> {
        val data = MutableLiveData<List<Kambing>>()
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                try {
                    data.value = snapshot.children.map { dataSnapshot ->
                        dataSnapshot.getValue(Kambing::class.java)!!
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

    fun addKambing(kambing: Kambing, onComplete: (isSuccess: Boolean) -> Unit) {
        databaseReference.child(kambing.tag).setValue(kambing)
            .addOnCompleteListener {
                onComplete(it.isSuccessful)
            }
    }

    fun updateKambing(kambing: Kambing, onComplete: (isSuccess: Boolean) -> Unit) {
        databaseReference.child(kambing.tag)
            .updateChildren(kambing.toMap())
            .addOnCompleteListener {
                onComplete(it.isSuccessful)
            }
    }


}