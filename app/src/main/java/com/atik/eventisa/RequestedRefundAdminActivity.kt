package com.atik.eventisa

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class RequestedRefundAdminActivity : AppCompatActivity() {

    private lateinit var SeeRequestedRefundRecyclerView: RecyclerView
    private lateinit var SeeRequestedRefundList:ArrayList<AddEventData>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_requested_refund_admin)



        getEventData()
    }

    private fun getEventData() {
        FirebaseDatabase.getInstance().getReference("RefundList")
            .addValueEventListener(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (datasnapshot in snapshot.children){

                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
    }

    private fun passingDatatoRecyclerView(userdbref: DatabaseReference, str: ArrayList<String>) {

    }


}