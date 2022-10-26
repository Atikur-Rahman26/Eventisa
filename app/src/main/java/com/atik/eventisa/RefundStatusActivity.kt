package com.atik.eventisa

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.atik.eventisa.Constants.Companion.uId
import com.bumptech.glide.Glide
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_refund_status.*
import kotlinx.android.synthetic.main.activity_see_event.*

class RefundStatusActivity : AppCompatActivity() {
    private var RefundFlag:Boolean=false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_refund_status)

        val EventTitle:String=intent.getStringExtra("eventTitle").toString()
        val EventDate:String=intent.getStringExtra("eventDate").toString()
        val EventLocation:String=intent.getStringExtra("eventLocation").toString()
        val EventDescription:String=intent.getStringExtra("eventDescription").toString()
        val EventImage:String=intent.getStringExtra("eventImage").toString()
        val eventID:String=intent.getStringExtra("eventId").toString()

        Glide.with(this).load(EventImage).into(Refund_Status_event_image)
        Refund_Status_event_title.setText(EventTitle)
        Refund_Status_event_desc.setText(EventDescription)
        Refund_Status_Event_Date.setText(EventDate)
        Refund_Status_Event_Location.setText(EventLocation)

        var eventId:String=intent.getStringExtra("eventId")!!
        getFavouriteButtonStatus(eventId)
    }

    private fun getFavouriteButtonStatus( eventId: String) {
        lateinit var favouriteReference: DatabaseReference
        favouriteReference= FirebaseDatabase.getInstance().getReference("RefundList")
        favouriteReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.child(eventId).hasChild(uId)){
                    RefundStatus.setText("Refund Processing...")
                    RefundStatus.resources.getColor(R.color.lightBlue)
                    RefundFlag=true


                    /**
                     * if user wants to cancel refund request
                     *
                     */
                    CancelRefundRequest.setOnClickListener{
                        var ref=FirebaseDatabase.getInstance().getReference("RefundList")
                        ref.addListenerForSingleValueEvent(object :ValueEventListener{
                            override fun onDataChange(snapshot: DataSnapshot) {
                                if(snapshot.child(eventId).hasChild(uId)) {

                                    /**
                                     * removing user id from refund list
                                     */
                                    ref.child(eventId).removeValue()

                                    /**
                                     * adding user id to the BookedList
                                     */

                                    var addref=FirebaseDatabase.getInstance().getReference("BookedList")
                                    addref.child(eventId).child(uId).setValue(true)

                                    /**
                                     * work has been finished
                                     */

                                    val intent=Intent(this@RefundStatusActivity,UserProfileActivity::class.java)
                                    startActivity(intent)
                                    finish()
                                }
                            }

                            override fun onCancelled(error: DatabaseError) {
                                TODO("Not yet implemented")
                            }
                        })

                    }

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}