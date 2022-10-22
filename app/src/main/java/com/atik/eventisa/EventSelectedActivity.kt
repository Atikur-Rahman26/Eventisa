package com.atik.eventisa

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_event_selected.*

class EventSelectedActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_selected)
        val EventTitle:String=intent.getStringExtra("eventTitle").toString()
        val EventDate:String=intent.getStringExtra("eventDate").toString()
        val EventLocation:String=intent.getStringExtra("eventLocation").toString()
        val EventDescription:String=intent.getStringExtra("eventDescription").toString()
        val EventImage:String=intent.getStringExtra("eventImage").toString()

        Glide.with(this).load(EventImage).into(event_image)
        event_title.setText(EventTitle)
        event_desc.setText(EventDescription)
        Event_Date.setText(EventDate)
        Event_Location.setText(EventLocation)


    }
}