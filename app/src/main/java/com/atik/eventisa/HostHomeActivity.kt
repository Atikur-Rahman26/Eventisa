package com.atik.eventisa

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.atik.eventisa.Constants.Companion.HostEmail
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_host_home.*

class HostHomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_host_home)

        HostEmail=FirebaseAuth.getInstance().currentUser!!.email.toString()

        AddEventHost.setOnClickListener{
            val intent= Intent(this,AddEventActivity::class.java)
            startActivity(intent)
        }

        UpdateEventHost.setOnClickListener{

        }
        SeeMyEventHost.setOnClickListener{
            val intent=Intent(this,SeeMyEventHostActivity::class.java)
            startActivity(intent)
        }
    }
}