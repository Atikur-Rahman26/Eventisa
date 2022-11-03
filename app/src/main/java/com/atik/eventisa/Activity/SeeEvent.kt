package com.atik.eventisa.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.atik.eventisa.DataClasses.Constants.Companion.uId
import com.atik.eventisa.R
import com.bumptech.glide.Glide
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_see_event.*
import kotlinx.android.synthetic.main.activity_see_event.Event_Date
import kotlinx.android.synthetic.main.activity_see_event.Event_Location
import kotlinx.android.synthetic.main.activity_see_event.event_desc
import kotlinx.android.synthetic.main.activity_see_event.event_image
import kotlinx.android.synthetic.main.activity_see_event.event_title

class SeeEvent : AppCompatActivity() {

    private var RefundFlag:Boolean=false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_see_event)
        val eventId:String=intent.getStringExtra("eventId").toString()

        val EventTitle:String=intent.getStringExtra("eventTitle").toString()
        val EventDate:String=intent.getStringExtra("eventDate").toString()
        val EventLocation:String=intent.getStringExtra("eventLocation").toString()
        val EventDescription:String=intent.getStringExtra("eventDescription").toString()
        val EventImage:String=intent.getStringExtra("eventImage").toString()
        val eventID:String=intent.getStringExtra("eventId").toString()

        Glide.with(this).load(EventImage).into(event_image)
        event_title.setText(EventTitle)
        event_desc.setText(EventDescription)
        Event_Date.setText(EventDate)
        Event_Location.setText(EventLocation)

        getFavouriteButtonStatus(eventId)

    }

    fun getFavouriteButtonStatus( eventId: String) {
        lateinit var favouriteReference: DatabaseReference
        favouriteReference= FirebaseDatabase.getInstance().getReference("RefundList")
        favouriteReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.child(eventId).hasChild(uId)){

                    RequestingRefund.setText("Refund Processing...")
                    RequestingRefund.resources.getColor(R.color.lightBlue)
                    RefundFlag=true

                }
                else{
                    RefundFlag= false
                    RequestingRefund.setOnClickListener{
                        if(RefundFlag==false){
                            var dbref: DatabaseReference =FirebaseDatabase.getInstance().getReference("RefundList")
                            dbref.child(eventId).child(uId).setValue(true)


                            var ref:DatabaseReference=FirebaseDatabase.getInstance().getReference("BookedList")
                            ref.addListenerForSingleValueEvent(object : ValueEventListener{
                                override fun onDataChange(snapshot: DataSnapshot) {
                                    if(snapshot.child(eventId).hasChild(uId)){
                                        ref.child(eventId).removeValue()
                                        val intent= Intent(this@SeeEvent, UserProfileActivity::class.java)
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
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}