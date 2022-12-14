package com.atik.eventisa.Adapter

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.atik.eventisa.Activity.RequestedEventAdminActivity
import com.atik.eventisa.DataClasses.AddEventData
import com.atik.eventisa.R
import com.atik.eventisa.Activity.RequestedEventToUserInfo
import com.atik.eventisa.Activity.RequestedRefundAdminActivity

class RequestedRefundAdapterEvent(private val RefundEvent:ArrayList<AddEventData>,
                                  private val context: RequestedRefundAdminActivity
):RecyclerView.Adapter<RequestedRefundAdapterEvent.RequestedRefundEventViewHolder> (){
    class RequestedRefundEventViewHolder(itemview: View):RecyclerView.ViewHolder(itemview)  {

        val EventTitle:TextView=itemview.findViewById(R.id.Refund_Event_User_Mail)
        val EventDate:TextView=itemview.findViewById(R.id.Refund_Event_to_UserName)
        val EventHost:TextView=itemview.findViewById(R.id.Refund_Event_Host)

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RequestedRefundEventViewHolder {
        val itemView:View=LayoutInflater.from(parent.context).
                inflate(R.layout.requested_refund_list,parent,false)
        return (RequestedRefundEventViewHolder(itemView))
    }

    override fun onBindViewHolder(holder: RequestedRefundEventViewHolder, position: Int) {
        val currentItem = RefundEvent[position]

        holder.EventHost.text=currentItem.hostEmail
        holder.EventDate.text=currentItem.eventDate
        holder.EventTitle.text=currentItem.eventTitle

        holder.itemView.setOnClickListener{
            val intent=Intent(context, RequestedEventToUserInfo::class.java).also {
                it.putExtra("Title",currentItem.eventTitle.toString())
                it.putExtra("Date",currentItem.eventDate.toString())
                it.putExtra("Location",currentItem.eventLocation.toString())
                it.putExtra("Description",currentItem.eventDescription.toString())
                it.putExtra("Id",currentItem.eventId.toString())
                it.putExtra("HostEmail",currentItem.hostEmail.toString())

                ContextCompat.startActivity(context, it, Bundle())
            }

            (context as RequestedRefundAdminActivity).finish()

        }
    }

    override fun getItemCount(): Int {
        return  RefundEvent.size
    }
}