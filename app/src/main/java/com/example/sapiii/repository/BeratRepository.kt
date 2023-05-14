package com.example.sapiii.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.sapiii.constanst.Constant
import com.google.firebase.database.*

class BeratRepository constructor (
    private val hewan: String
) {
    private val databaseReference: DatabaseReference =
        FirebaseDatabase.getInstance().getReference(hewan)

    companion object {
        @Volatile
        private var INSTANCE: BeratRepository? = null

        fun getInstance(hewan: String): BeratRepository {
            return INSTANCE ?: synchronized(this) {

                val instance = BeratRepository(hewan)
                INSTANCE = instance
                instance
            }
        }
    }

    fun getBobotHewan(
        onComplete: (data: Int) -> Unit,
        onError: (error: Exception) -> Unit
    ): LiveData<Int> {
        val data = MutableLiveData<Int>()
        val child: String = if (hewan.equals(Constant.REFERENCE_BERAT_KAMBING, true)) {
            Constant.REFERENCE_BERAT_KAMBING
        } else Constant.REFERENCE_BERAT_SAPI

        databaseReference.child(child)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    try {
                        onComplete(snapshot.value as Int)
                    } catch (e: Exception) {
                        onError(e)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    onError(error.toException())
                }
            })

        return data
    }
}