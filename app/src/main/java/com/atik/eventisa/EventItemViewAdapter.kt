package com.atik.eventisa

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class EventItemViewAdapter(private val eventList:ArrayList<EventData>):
    RecyclerView.Adapter<EventItemViewAdapter.EventViewHolder> (){
    class EventViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview){
        val Date: TextView =itemview.findViewById(R.id.Event_Date)
        val Location: TextView =itemview.findViewById(R.id.Event_Location)
        val Title: TextView =itemview.findViewById(R.id.event_title)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val itemView: View = LayoutInflater.from(parent.context).
        inflate(R.layout.eventitem,parent,false)

        return (EventViewHolder(itemView))
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val currentItem=eventList[position]

        println(eventList)
        holder.Date.text=currentItem.Date
        holder.Title.text=currentItem.title
        holder.Location.text=currentItem.location
        holder.itemView.setOnClickListener{
            val Description:String=currentItem.description.toString()
            val EVENTTITLE:String=currentItem.title.toString()
            val LOCATION:String=currentItem.location.toString()
            val EVENTDATE:String=currentItem.Date.toString()

        }
    }

    override fun getItemCount(): Int {
        return eventList.size
    }
}