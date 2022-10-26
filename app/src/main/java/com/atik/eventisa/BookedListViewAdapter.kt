package com.atik.eventisa

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
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class BookedListViewAdapter(private val eventList:ArrayList<UserInfo>):
    RecyclerView.Adapter<BookedListViewAdapter.BookedListViewHolder> () {


    class BookedListViewHolder(itemview: View) :RecyclerView.ViewHolder(itemview){
        val name: TextView = itemview.findViewById(R.id.ProfileNameBookedList)
        val email: TextView = itemview.findViewById(R.id.ProfileEmail)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookedListViewHolder {
        val itemView: View = LayoutInflater.from(parent.context).
        inflate(R.layout.booked_list,parent,false)
        return (BookedListViewHolder(itemView))
    }

    override fun onBindViewHolder(holder: BookedListViewHolder, position: Int) {
        val currentItem = eventList[position]
        /**
         *

//        dbAuth = FirebaseAuth.getInstance()
//        var eventid: String = currentItem.eventId.toString()
//        Constants.uId = dbAuth.uid.toString()
//
//        println("Event id: ${eventid} \t\t\t uid : ${Constants.uId}")

        //getting which are favourite for a particular user
        */

        holder.name.text = currentItem.userName
        holder.email.text = currentItem.Email

//        Glide.with(context).load(currentItem.imageUri).into(holder.EventLogo)
    }


    override fun getItemCount(): Int {
        return eventList.size
    }

}