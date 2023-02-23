package com.example.sapiii.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.sapiii.constanst.Constant.REFERENCE_ARTIKEL
import com.example.sapiii.domain.Artikel
import com.google.firebase.database.*

class ArtikelRepository {
    private val databaseReference: DatabaseReference =
        FirebaseDatabase.getInstance().getReference(REFERENCE_ARTIKEL)

    companion object {
        @Volatile
        private var INSTANCE: ArtikelRepository? = null

        fun getInstance(): ArtikelRepository {
            return INSTANCE ?: synchronized(this) {

                val instance = ArtikelRepository()
                INSTANCE = instance
                instance
            }
        }
    }

    fun loadArtikel(): LiveData<List<Artikel>> {
        val data = MutableLiveData<List<Artikel>>()
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                try {
                    data.value = snapshot.children.map { dataSnapshot ->
                        dataSnapshot.getValue(Artikel::class.java)!!
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