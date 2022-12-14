package com.atik.eventisa.Adapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

import com.atik.eventisa.DataClasses.AddEventData
import com.atik.eventisa.DataClasses.Constants
import com.atik.eventisa.R
import com.atik.eventisa.Activity.SeeEvent
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth

class FavouriteItemViewAdaptar(private val eventList:ArrayList<AddEventData>,
                               private val context: Context
):
    RecyclerView.Adapter<FavouriteItemViewAdaptar.FavouriteEventViewHolder> () {
    lateinit var dbAuth: FirebaseAuth


    class FavouriteEventViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {

        val Date: TextView = itemview.findViewById(R.id.Event_Date)
        val Location: TextView = itemview.findViewById(R.id.Event_Location)
        val Title: TextView = itemview.findViewById(R.id.event_title1)
        val EventLogo: ImageView = itemview.findViewById(R.id.Event_logo)

    }

     lateinit var Description: String
     lateinit var EVENTTITLE: String
     lateinit var LOCATION: String
     lateinit var EVENTDATE: String
     lateinit var EVENTIMAGE: String

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteEventViewHolder {
        val itemView: View = LayoutInflater.from(parent.context).
        inflate(R.layout.favouriteitem,parent,false)
        return (FavouriteEventViewHolder(itemView))
    }

    override fun onBindViewHolder(holder: FavouriteEventViewHolder, position: Int) {
        val currentItem = eventList[position]

        dbAuth = FirebaseAuth.getInstance()
        var eventid: String = currentItem.eventId.toString()
        Constants.uId = dbAuth.uid.toString()

        println("Event id: ${eventid} \t\t\t uid : ${Constants.uId}")

        //getting which are favourite for a particular user


        holder.Date.text = currentItem.eventDate
        holder.Title.text = currentItem.eventTitle
        holder.Location.text = currentItem.eventLocation
        Glide.with(context).load(currentItem.imageUri).into(holder.EventLogo)
        holder.itemView.setOnClickListener {
            Description = currentItem.eventDescription.toString()
            EVENTTITLE = currentItem.eventTitle.toString()
            LOCATION = currentItem.eventLocation.toString()
            EVENTDATE = currentItem.eventDate.toString()
            EVENTIMAGE = currentItem.imageUri.toString()


            val intent = Intent(context, SeeEvent::class.java).also {
                it.putExtra("eventTitle", EVENTTITLE)
                it.putExtra("eventDate", EVENTDATE)
                it.putExtra("eventLocation", LOCATION)
                it.putExtra("eventDescription", Description)
                it.putExtra("eventImage", EVENTIMAGE)
                it.putExtra("eventId",currentItem.eventId)
                ContextCompat.startActivity(context, it, Bundle())
            }
        }
    }

    private fun refreshFavouriteFragment(context: Context) {
        context?.let {
            val fragmentManager=(context as? AppCompatActivity)?.supportFragmentManager
            fragmentManager?.let {
                val currentFragment=fragmentManager.findFragmentById(R.id.frame_layout)
                currentFragment?.let {
                    val fragmentTransaction=fragmentManager.beginTransaction()
                    fragmentTransaction.detach(it)
                    fragmentTransaction.attach(it)
                    fragmentTransaction.commit()
                }
            }
        }

    }

    override fun getItemCount(): Int {
        return eventList.size
    }


}