package com.atik.eventisa

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class EventItemViewAdapter(private val eventList:ArrayList<AddEventData>,
                           private val context:Context):
    RecyclerView.Adapter<EventItemViewAdapter.EventViewHolder> (){
    class EventViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview){
        val Date: TextView =itemview.findViewById(R.id.Event_Date)
        val Location: TextView =itemview.findViewById(R.id.Event_Location)
        val Title: TextView =itemview.findViewById(R.id.event_title)
        val EventLogo: ImageView=itemview.findViewById(R.id.Event_logo)

    }




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val itemView: View = LayoutInflater.from(parent.context).
        inflate(R.layout.eventitem,parent,false)

        return (EventViewHolder(itemView))
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val currentItem=eventList[position]

        println(eventList)
        holder.Date.text=currentItem.eventDate
        holder.Title.text=currentItem.eventTitle
        holder.Location.text=currentItem.eventLocation
        Glide.with(context).load(currentItem.imageUri).into(holder.EventLogo)
        holder.itemView.setOnClickListener{
            val Description:String=currentItem.eventDescription.toString()
            val EVENTTITLE:String=currentItem.eventTitle.toString()
            val LOCATION:String=currentItem.eventLocation.toString()
            val EVENTDATE:String=currentItem.eventDate.toString()

            val intent=Intent(this,)
        }
    }

    override fun getItemCount(): Int {
        return eventList.size
    }
}