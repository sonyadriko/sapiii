package com.example.sapiii.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.sapiii.constanst.Constant
import com.example.sapiii.domain.MonitoringKehamilan
import com.example.sapiii.domain.MonitoringPejantan
import com.google.firebase.database.*

class KehamilanRepository {
    private val databaseReference: DatabaseReference =
        FirebaseDatabase.getInstance().getReference(Constant.REFERENCE_KEHAMILAN)

    @Volatile
    private var INSTANCE: KehamilanRepository? = null

    fun getInstance(): KehamilanRepository {
        return INSTANCE ?: synchronized(this) {

            val instance = KehamilanRepository()
            INSTANCE = instance
            instance
        }
    }

    fun loadMonitoringKehamilan(): LiveData<List<MonitoringKehamilan>> {
        val data = MutableLiveData<List<MonitoringKehamilan>>()
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                try {
                    data.value = snapshot.children.map { dataSnapshot ->
                        dataSnapshot.getValue(MonitoringKehamilan::class.java)!!
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