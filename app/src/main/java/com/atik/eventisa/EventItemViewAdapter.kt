package com.atik.eventisa

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.atik.eventisa.Constants.Companion.email
import com.atik.eventisa.Constants.Companion.uId
import com.atik.eventisa.Constants.Companion.username
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class EventItemViewAdapter(private val eventList:ArrayList<AddEventData>,
                           private val context: Context
):
    RecyclerView.Adapter<EventItemViewAdapter.EventViewHolder> () {
    lateinit var dbAuth:FirebaseAuth
    var favouriteAddedTest:Boolean=false



    class EventViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview){
        fun getFavouriteButtonStatus(email: String, eventId: String) {
            lateinit var favouriteReference: DatabaseReference
            favouriteReference=FirebaseDatabase.getInstance().getReference("addedFavourite")
            favouriteReference.addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if(snapshot.child(eventId).hasChild(uId)){
                        var favouriteCount: Long =snapshot.child(eventId).childrenCount
                        LoveTxtView.setText(favouriteCount.toString()+" loves")

                        favouritebutton.setBackgroundResource(R.drawable.favourites_red)
                    }
                    else{
                        var favouriteCount: Long =snapshot.child(eventId).childrenCount

                        LoveTxtView.setText(favouriteCount.toString()+" loves")
                        favouritebutton.setBackgroundResource(R.drawable.favourites_shadow)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
        }

        val Date: TextView =itemview.findViewById(R.id.Event_Date)
        val Location: TextView =itemview.findViewById(R.id.Event_Location)
        val Title: TextView =itemview.findViewById(R.id.event_title)
        val EventLogo: ImageView=itemview.findViewById(R.id.Event_logo)
        val favouritebutton:ImageView=itemview.findViewById(R.id.FavouriteButton)
        val LoveTxtView:TextView=itemview.findViewById(R.id.loveText)

    }

    private lateinit var Description:String
    private lateinit var EVENTTITLE:String
    private lateinit var LOCATION:String
    private lateinit var EVENTDATE:String
     private lateinit var EVENTIMAGE:String

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val itemView: View = LayoutInflater.from(parent.context).
        inflate(R.layout.eventitem,parent,false)

        return (EventViewHolder(itemView))
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val currentItem=eventList[position]

        dbAuth=FirebaseAuth.getInstance()
        var eventid:String=currentItem.eventId.toString()
        uId=dbAuth.uid.toString()


        //getting which are favourite for a particular user
        holder.getFavouriteButtonStatus(uId,eventid)

        holder.Date.text=currentItem.eventDate
        holder.Title.text=currentItem.eventTitle
        holder.Location.text=currentItem.eventLocation
        Glide.with(context).load(currentItem.imageUri).into(holder.EventLogo)
        holder.itemView.setOnClickListener{
            Description=currentItem.eventDescription.toString()
            EVENTTITLE=currentItem.eventTitle.toString()
            LOCATION=currentItem.eventLocation.toString()
            EVENTDATE=currentItem.eventDate.toString()
            EVENTIMAGE=currentItem.imageUri.toString()


            val intent= Intent(context,EventSelectedActivity::class.java).also {
                it.putExtra("eventTitle",EVENTTITLE)
                it.putExtra("eventDate",EVENTDATE)
                it.putExtra("eventLocation",LOCATION)
                it.putExtra("eventDescription",Description)
                it.putExtra("eventImage",EVENTIMAGE)
                it.putExtra("eventId",eventid)
                it.putExtra("uId", uId)
                startActivity(context,it, Bundle())
            }

        }
        holder.favouritebutton.setOnClickListener{
            favouriteAddedTest=true
            var dbref:DatabaseReference=FirebaseDatabase.getInstance().getReference("addedFavourite")
            dbref.addValueEventListener(object: ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if(favouriteAddedTest==true){
                        if(snapshot.child(eventid).hasChild(uId)){
                            dbref.child(eventid).removeValue()
                            favouriteAddedTest=false
                            holder.favouritebutton.setBackgroundResource(R.drawable.favourites_shadow)
                        }
                        else{
                            dbref.child(eventid).child(uId).setValue(true)
                            favouriteAddedTest=false
                            holder.favouritebutton.setBackgroundResource(R.drawable.favourites_red)
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
        }



    }

    override fun getItemCount(): Int {
        return eventList.size
    }
}

