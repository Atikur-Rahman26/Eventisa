package com.atik.eventisa.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.atik.eventisa.DataClasses.Constants.Companion.HostEmail
import com.atik.eventisa.DataClasses.Constants.Companion.HostName
import com.atik.eventisa.DataClasses.Constants.Companion.HostPhone
import com.atik.eventisa.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_host_home.*

class HostHomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_host_home)

        HostEmail=FirebaseAuth.getInstance().currentUser!!.email.toString()
        HostEmailTextView.setText(HostEmail)
        HostNameTextView.setText(HostName)
        HostPhoneTextView.setText(HostPhone)

        AddEventHost.setOnClickListener{
            val intent= Intent(this, AddEventActivity::class.java)
            startActivity(intent)
        }

        SeeMyEventHost.setOnClickListener{
            val intent=Intent(this, SeeMyEventHostActivity::class.java)
            startActivity(intent)
        }
    }
}