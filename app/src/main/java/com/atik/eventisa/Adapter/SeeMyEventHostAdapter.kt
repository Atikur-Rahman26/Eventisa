package com.atik.eventisa.Adapter

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.atik.eventisa.DataClasses.AddEventData
import com.atik.eventisa.DataClasses.Constants.Companion.EVENTBOOKED
import com.atik.eventisa.DataClasses.Constants.Companion.EVENTPRICE
import com.atik.eventisa.DataClasses.Constants.Companion.HostUid
import com.atik.eventisa.Activity.EventAdminDashboard
import com.atik.eventisa.R
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class SeeMyEventHostAdapter(private val SeeMyEventArray:ArrayList<AddEventData>,
                            private val context: Context):
    RecyclerView.Adapter<SeeMyEventHostAdapter.SeeMyEventViewHolder> () {

    class SeeMyEventViewHolder(itemview: View):RecyclerView.ViewHolder(itemview) {
        val Date: TextView = itemview.findViewById(R.id.Event_Date_Host)
        val Location: TextView = itemview.findViewById(R.id.Event_Location_Host)
        val Title: TextView = itemview.findViewById(R.id.event_title_Host)
        val EventLogo: ImageView = itemview.findViewById(R.id.Event_logo_Host)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeeMyEventViewHolder {
        val itemView: View = LayoutInflater.from(parent.context).
        inflate(R.layout.see_event_host_item,parent,false)
        return (SeeMyEventViewHolder(itemView))
    }


    private lateinit var Refund_Description: String
    private lateinit var Refund_EVENTTITLE: String
    private lateinit var Refund_LOCATION: String
    private lateinit var Refund_EVENTDATE: String
    private lateinit var Refund_EVENTIMAGE: String

    override fun onBindViewHolder(holder: SeeMyEventViewHolder, position: Int) {
        val currentItem=SeeMyEventArray[position]
        var dbAuth=FirebaseAuth.getInstance()
        var eventid:String=currentItem.eventId.toString()
        HostUid=dbAuth.uid.toString()

        holder.Date.text=currentItem.eventDate
        holder.Title.text=currentItem.eventTitle
        holder.Location.text = currentItem.eventLocation
        Glide.with(context).load(currentItem.imageUri).into(holder.EventLogo)

        holder.itemView.setOnClickListener{
            Refund_Description = currentItem.eventDescription.toString()
            Refund_EVENTTITLE = currentItem.eventTitle.toString()
            Refund_LOCATION = currentItem.eventLocation.toString()
            Refund_EVENTDATE = currentItem.eventDate.toString()
            Refund_EVENTIMAGE = currentItem.imageUri.toString()
            val dialog= Dialog(context)
            dialog.setContentView(R.layout.logging_dialog)
            if(dialog.window!=null){
                dialog?.window!!.setBackgroundDrawable(ColorDrawable(0))
                dialog.show()
            }

            var db=FirebaseDatabase.getInstance().getReference("Events").child(eventid)
            db.addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    EVENTPRICE=snapshot.child("eventPrice").value.toString().toInt()

                    var db2=FirebaseDatabase.getInstance().getReference("BookedList").child(eventid)
                    db2.addValueEventListener(object : ValueEventListener{
                        override fun onCancelled(error: DatabaseError) {
                            TODO("Not yet implemented")
                        }

                        override fun onDataChange(snapshot: DataSnapshot) {

                            EVENTBOOKED=snapshot.childrenCount.toString().toLong()

                            println("\t\t${EVENTBOOKED}")

                            val intent = Intent(context, EventAdminDashboard::class.java).also {
                                it.putExtra("eventTitle", Refund_EVENTTITLE)
                                it.putExtra("eventDate", Refund_EVENTDATE)
                                it.putExtra("eventLocation", Refund_LOCATION)
                                it.putExtra("eventDescription", Refund_Description)
                                it.putExtra("eventImage", Refund_EVENTIMAGE)
                                it.putExtra("eventId",currentItem.eventId)
                                dialog.dismiss()
                                ContextCompat.startActivity(context, it, Bundle())
                            }
                        }
                    })
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })

        }
    }

    override fun getItemCount(): Int {
        return SeeMyEventArray.size
    }

}