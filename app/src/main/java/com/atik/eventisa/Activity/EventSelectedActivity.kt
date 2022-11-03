package com.atik.eventisa.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.atik.eventisa.DataClasses.Constants.Companion.uId
import com.atik.eventisa.R
import com.bumptech.glide.Glide
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_event_selected.*

class EventSelectedActivity : AppCompatActivity() {
    private var BookedAddedTest:Boolean=false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_selected)
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

        getFavouriteButtonStatus(uId,eventID)



    }

    fun getFavouriteButtonStatus(email: String, eventId: String) {
        lateinit var favouriteReference: DatabaseReference
        favouriteReference=FirebaseDatabase.getInstance().getReference("BookedList")
        favouriteReference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.child(eventId).hasChild(uId)){
                    var favouriteCount: Long = snapshot.child(eventId).childrenCount
                    var count:Long=3

                        BookingEvent.setText("You booked!")
                        BookingEvent.resources.getColor(R.color.lightBlue)
                        BookedAddedTest=true

                }
                else{
                    var BookedCount: Long =snapshot.child(eventId).childrenCount
                    var count:Long=3

                    println("Booked count: ${BookedCount}")
                    if(BookedCount>=count){

                        BookingEvent.setText("Seat Filled up!")
                        BookingEvent.resources.getColor(R.color.red)
                        BookedAddedTest=true

                    }
                    else {
                        BookedAddedTest = false
                        BookingEvent.setOnClickListener{
                            if(BookedAddedTest==false){
                                var dbref:DatabaseReference=FirebaseDatabase.getInstance().getReference("BookedList")
                                dbref.child(eventId).child(uId).setValue(true)
                                val intent=Intent(this@EventSelectedActivity, UserProfileActivity::class.java)
                                startActivity(intent)
                                finish()
                            }
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