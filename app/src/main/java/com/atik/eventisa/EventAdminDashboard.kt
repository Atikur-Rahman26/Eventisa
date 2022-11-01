package com.atik.eventisa

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.atik.eventisa.Constants.Companion.EVENTBOOKED
import com.atik.eventisa.Constants.Companion.EVENTDATE
import com.atik.eventisa.Constants.Companion.EVENTPRICE
import com.atik.eventisa.Constants.Companion.EVENTTITLE
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_event_admin_dashboard.*

class EventAdminDashboard : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_admin_dashboard)
        TotalSell.setText(EVENTBOOKED.toString())
        AddEventPrice.setText(EVENTPRICE.toString())
        TotalSellTk.setText((EVENTBOOKED* EVENTPRICE).toString())
        eventDate.setText(EVENTDATE.toString())
        eventTitle.setText(EVENTTITLE.toString())
        var EventImage:String=intent.getStringExtra("eventImage").toString()
        Glide.with(this).load(EventImage).into(eventLogo)
    }
}