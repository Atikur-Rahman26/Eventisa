package com.atik.eventisa

import android.content.Intent
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import kotlinx.android.synthetic.main.activity_admin_hom.*
import kotlinx.android.synthetic.main.activity_admin_home.*
import kotlinx.android.synthetic.main.activity_admin_home.DashboardButton

class AdminHome : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_home)

        BookedEventButton.setOnClickListener{
            val intent=Intent(this,Booked_Event::class.java)
            startActivity(intent)
        }

        Update_Event.setOnClickListener{
            val intent=Intent(this,UpdateEventActivity::class.java)
            startActivity(intent)
        }
        DashboardButton.setOnClickListener{
            val intent=Intent(this,EventAdminDashboard::class.java)
            startActivity(intent)
        }
    }

}