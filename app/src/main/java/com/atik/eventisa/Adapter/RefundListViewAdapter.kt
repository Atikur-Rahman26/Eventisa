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
import com.atik.eventisa.DataClasses.Constants.Companion.uId
import com.atik.eventisa.R
import com.atik.eventisa.Activity.RefundStatusActivity
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth

class RefundListViewAdapter(private val RefundList:ArrayList<AddEventData>, private val context: Context):
    RecyclerView.Adapter<RefundListViewAdapter.RefundListViewHolder> () {
    private lateinit var dbAuth: FirebaseAuth
    class RefundListViewHolder(itemview:View):RecyclerView.ViewHolder(itemview) {
        val Date: TextView = itemview.findViewById(R.id.Refund_Event_Date)
        val Location: TextView = itemview.findViewById(R.id.Refund_Event_Location)
        val Title: TextView = itemview.findViewById(R.id.Refund_event_title)
        val EventLogo: ImageView = itemview.findViewById(R.id.Refund_Event_logo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RefundListViewHolder {
        val itemView: View = LayoutInflater.from(parent.context).
        inflate(R.layout.refund_list,parent,false)
        return (RefundListViewHolder(itemView))
    }

    private lateinit var Refund_Description: String
    private lateinit var Refund_EVENTTITLE: String
    private lateinit var Refund_LOCATION: String
    private lateinit var Refund_EVENTDATE: String
    private lateinit var Refund_EVENTIMAGE: String

    override fun onBindViewHolder(holder: RefundListViewHolder, position: Int) {
        val currentItem = RefundList[position]

        dbAuth = FirebaseAuth.getInstance()
        var eventid: String = currentItem.eventId.toString()
        uId = dbAuth.uid.toString()

        println("Event id: ${eventid} \t\t\t uid : ${uId}")

        //getting which are favourite for a particular user


        holder.Date.text = currentItem.eventDate
        holder.Title.text = currentItem.eventTitle
        holder.Location.text = currentItem.eventLocation
        Glide.with(context).load(currentItem.imageUri).into(holder.EventLogo)
        holder.itemView.setOnClickListener {
            Refund_Description = currentItem.eventDescription.toString()
            Refund_EVENTTITLE = currentItem.eventTitle.toString()
            Refund_LOCATION = currentItem.eventLocation.toString()
            Refund_EVENTDATE = currentItem.eventDate.toString()
            Refund_EVENTIMAGE = currentItem.imageUri.toString()


            val intent = Intent(context, RefundStatusActivity::class.java).also {
                it.putExtra("eventTitle", Refund_EVENTTITLE)
                it.putExtra("eventDate", Refund_EVENTDATE)
                it.putExtra("eventLocation", Refund_LOCATION)
                it.putExtra("eventDescription", Refund_Description)
                it.putExtra("eventImage", Refund_EVENTIMAGE)
                it.putExtra("eventId",currentItem.eventId)
                ContextCompat.startActivity(context, it, Bundle())
            }

        }
    }

    override fun getItemCount(): Int {
        return RefundList.size
    }
}