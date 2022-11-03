package com.atik.eventisa.Adapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.atik.eventisa.DataClasses.AddEventData
import com.atik.eventisa.R
import com.atik.eventisa.Activity.SeeRequestedEventDetails
import com.bumptech.glide.Glide

class SeeRequestedEventAdapter(private val seeRequestedEventList: ArrayList<AddEventData>,
                               private val context: Context) :
    RecyclerView.Adapter<SeeRequestedEventAdapter.SeeRequestedViewHolder>() {

    class SeeRequestedViewHolder(itemview:View):RecyclerView.ViewHolder(itemview){
        val date:TextView=itemview.findViewById(R.id.Requested_Event_Date_Host)
        val location:TextView= itemview.findViewById(R.id.Requested_Event_Location_Host)
        val title:TextView=itemview.findViewById(R.id.Requested_event_title_Host)
        val Logo:ImageView=itemview.findViewById(R.id.Requested_Event_logo_Host)
    }

    private lateinit var Description:String
    private lateinit var EVENTTITLE:String
    private lateinit var LOCATION:String
    private lateinit var EVENTDATE:String
    private lateinit var EVENTIMAGE:String


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeeRequestedViewHolder {
        val itemview:View=LayoutInflater.from(parent.context).
                inflate(R.layout.see_requested_event_item,parent,false)

        return (SeeRequestedViewHolder(itemview))
    }

    override fun getItemCount(): Int {
        return seeRequestedEventList.size
    }

    override fun onBindViewHolder(holder: SeeRequestedViewHolder, position: Int) {
        val currentITem=seeRequestedEventList[position]

        holder.date.text=currentITem.eventDate
        holder.title.text=currentITem.eventTitle
        holder.location.text=currentITem.eventLocation
        Glide.with(context).load(currentITem.imageUri).into(holder.Logo)

        holder.itemView.setOnClickListener{
            Description=currentITem.eventDescription.toString()
            EVENTTITLE=currentITem.eventTitle.toString()
            LOCATION=currentITem.eventLocation.toString()
            EVENTDATE=currentITem.eventDate.toString()
            EVENTIMAGE=currentITem.imageUri.toString()
            var eventid=currentITem.eventId.toString()
            var Hostmail=currentITem.hostEmail
            var seat=currentITem.eventSeat.toString()
            var price=currentITem.eventPrice.toString()

            val intent=Intent(context, SeeRequestedEventDetails::class.java).also {
                it.putExtra("eventTitle",EVENTTITLE)
                it.putExtra("eventDate",EVENTDATE)
                it.putExtra("eventLocation",LOCATION)
                it.putExtra("eventDescription",Description)
                it.putExtra("eventImage",EVENTIMAGE)
                it.putExtra("eventId",eventid)
                it.putExtra("Seat",seat)
                it.putExtra("Price", price)
                it.putExtra("hostmail",Hostmail)
                ContextCompat.startActivity(context, it, Bundle())
            }
        }
    }

}
