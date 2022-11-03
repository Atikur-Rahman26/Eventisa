package com.atik.eventisa.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.atik.eventisa.R
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_making_decision_for_refund.*

class MakingDecisionForRefund : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_making_decision_for_refund)

        val eventTitle=intent.getStringExtra("eventTitle").toString()
        val userNam=intent.getStringExtra("userName").toString()
        val uid=intent.getStringExtra("uid").toString()
        val eventid=intent.getStringExtra("eventId").toString()
        val userEmail=intent.getStringExtra("userEmail").toString()

        RefundUserMail.text=userEmail
        RefundUserNAme.text=userNam
        EventTitleRefund.text=eventTitle


        ConfrimRefundButton.setOnClickListener{
            FirebaseDatabase.getInstance().getReference("RefundList").child(eventid).child(uid).removeValue()
            val intent=Intent(this, MainAdminHomeActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}