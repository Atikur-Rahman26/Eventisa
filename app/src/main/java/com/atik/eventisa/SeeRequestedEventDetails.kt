package com.atik.eventisa

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_see_requested_event_details.*

class SeeRequestedEventDetails : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_see_requested_event_details)

        var hostmail=intent.getStringExtra("hostmail").toString()
        var seat=intent.getStringExtra("Seat").toString()
        var price=intent.getStringExtra("Price").toString()
        var eventId=intent.getStringExtra("eventId").toString()
        var eventTitle=intent.getStringExtra("eventTitle").toString()
        var eventDate=intent.getStringExtra("eventDate").toString()
        var eventLocation=intent.getStringExtra("eventLocation").toString()
        var eventDescription=intent.getStringExtra("eventDescription").toString()
        var eventimage=intent.getStringExtra("eventImage").toString()

        SeeRequestedDate.text=eventDate
        SeeRequestedSeat.text=seat
        SeeRequestedDescription.text=eventDescription
        SeeRequestedLocation.text=eventLocation
        SeeRequestedPrice.text=price
        requestedEventTitle.text=eventTitle
        SeeRequestedHostMail.text=hostmail

        val addEvent=AddEventData(eventimage,eventTitle,eventDate,eventLocation,
            eventDescription,eventId,price.toInt(),seat.toLong(),hostmail
        )

        ConfirmRequestButton.setOnClickListener{
            var dbref=FirebaseDatabase.getInstance().getReference("Events").
            child(eventId).setValue(addEvent).addOnSuccessListener {
                FirebaseDatabase.getInstance().getReference("PendingAddingEvents").child(eventId).removeValue()
                val intent=Intent(this,RequestedEventAdminActivity::class.java)
                startActivity(intent)
                finish()
            }.addOnFailureListener{
                Toast.makeText(this,"Failed to add!",Toast.LENGTH_SHORT).show()
            }
        }

    }
}