package com.example.sapiii.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.sapiii.constanst.Constant
import com.example.sapiii.domain.MonitoringPejantan
import com.example.sapiii.domain.MutasiHewan
import com.google.firebase.database.*

class PejantanRepository {
    private val databaseReference: DatabaseReference =
        FirebaseDatabase.getInstance().getReference(Constant.REFERENCE_PEJANTAN)

    @Volatile
    private var INSTANCE: PejantanRepository? = null

    fun getInstance(): PejantanRepository {
        return INSTANCE ?: synchronized(this) {

            val instance = PejantanRepository()
            INSTANCE = instance
            instance
        }
    }

    fun loadMonitoringPejantan(): LiveData<List<MonitoringPejantan>> {
        val data = MutableLiveData<List<MonitoringPejantan>>()
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                try {
                    data.value = snapshot.children.map { dataSnapshot ->
                        dataSnapshot.getValue(MonitoringPejantan::class.java)!!
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