package com.atik.eventisa.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.atik.eventisa.Adapter.RequestedRefundAdapterEvent
import com.atik.eventisa.DataClasses.AddEventData
import com.atik.eventisa.R
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_requested_refund_admin.*

class RequestedRefundAdminActivity : AppCompatActivity() {

    private lateinit var SeeRequestedRefundRecyclerView: RecyclerView
    private lateinit var SeeRequestedRefundList:ArrayList<AddEventData>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_requested_refund_admin)

        SeeRequestedRefundRecyclerView=findViewById(R.id.SeeRefundRequestedRecyclerview)
        SeeRequestedRefundRecyclerView.layoutManager= LinearLayoutManager(this)
        SeeRequestedRefundRecyclerView.setHasFixedSize(true)


        SeeRequestedRefundList= arrayListOf<AddEventData>()

        getEventData()
    }

    private fun getEventData() {
        NothingToShow.visibility=View.VISIBLE
        var str:ArrayList<String>
        str= arrayListOf<String>()
        var ref=FirebaseDatabase.getInstance().getReference("RefundList")
        ref.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for(ds in snapshot.children){
                    println(ds.key)
                    str.add(ds.key.toString())
                }

                for(i in str.indices){
                    FirebaseDatabase.getInstance().getReference("Events").child(str[i]).
                    addValueEventListener(object : ValueEventListener{
                        override fun onDataChange(snapshot: DataSnapshot) {
                            val ls=snapshot.getValue(AddEventData::class.java)
                            SeeRequestedRefundList.add(ls!!)

                            if(i==str.size-1){
                                if(SeeRequestedRefundList.isEmpty()){
                                    println("this is printing!!!1")

                                }
                                else{
                                    NothingToShow.visibility=View.INVISIBLE
                                    SeeRequestedRefundRecyclerView.adapter=
                                        RequestedRefundAdapterEvent(SeeRequestedRefundList,this@RequestedRefundAdminActivity)
                                }
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {
                            TODO("Not yet implemented")
                        }
                    })
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