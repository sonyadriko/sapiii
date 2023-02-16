package com.example.sapiii.Repository

import androidx.lifecycle.MutableLiveData
import com.example.sapiii.domain.Sapi
import com.google.firebase.database.*

class SapiRepository {
    private val databaseReference : DatabaseReference = FirebaseDatabase.getInstance().getReference("Sapi")

    @Volatile private var INSTANCE : SapiRepository ?= null

    fun getInstance() : SapiRepository{
        return INSTANCE ?: synchronized(this){

            val instance = SapiRepository()
            INSTANCE = instance
            instance
        }


    }

    fun loadSapis(sapiList : MutableLiveData<List<Sapi>>){

        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                try {

                    val _sapiList : List<Sapi> = snapshot.children.map { dataSnapshot ->

                        dataSnapshot.getValue(Sapi::class.java)!!

                    }

                    sapiList.postValue(_sapiList)

                }catch (e : Exception){


                }


            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        })


    }

}