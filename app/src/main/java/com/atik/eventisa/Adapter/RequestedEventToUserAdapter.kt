package com.atik.eventisa.Adapter

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.atik.eventisa.DataClasses.UserInfo
import com.atik.eventisa.Activity.MakingDecisionForRefund
import com.atik.eventisa.R
import com.atik.eventisa.Activity.RequestedEventToUserInfo

class RequestedEventToUserAdapter(
    private val RefundList: ArrayList<UserInfo>,
    private val context: RequestedEventToUserInfo,
    private var eventID: String,
    private var eventTitle:String
):
    RecyclerView.Adapter<RequestedEventToUserAdapter.RequestedUserViewHolder> (){
    class RequestedUserViewHolder(itemview:View):RecyclerView.ViewHolder(itemview) {

        val userEmail:TextView=itemview.findViewById(R.id.Refund_Event_User_Mail)
        val usernam:TextView=itemview.findViewById(R.id.Refund_Event_to_UserName)
    }

    override fun onBindViewHolder(holder: RequestedUserViewHolder, position: Int) {
        val currentItem=RefundList[position]

        holder.userEmail.text=currentItem.Email.toString()
        holder.usernam.text=currentItem.firstName.toString()+" "+currentItem.lastName.toString()

        holder.itemView.setOnClickListener{
            val intent=Intent(context, MakingDecisionForRefund::class.java).also{
                it.putExtra("userEmail",currentItem.Email.toString())
                it.putExtra("userName",currentItem.firstName.toString()+currentItem.lastName.toString())

                it.putExtra("uid",currentItem.uid.toString())
                it.putExtra("eventId",eventID)
                it.putExtra("eventTitle",eventTitle)
                ContextCompat.startActivity(context,it, Bundle())
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RequestedUserViewHolder {
        val itemView:View=LayoutInflater.from(parent.context).inflate(R.layout.requested_refund_list,parent,false)
        return (RequestedUserViewHolder(itemView))
    }


    override fun getItemCount(): Int {
        return RefundList.size
    }
}