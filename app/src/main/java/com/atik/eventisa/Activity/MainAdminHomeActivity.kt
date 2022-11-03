package com.atik.eventisa.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.atik.eventisa.R
import kotlinx.android.synthetic.main.activity_main_admin_home.*

class MainAdminHomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_admin_home)

        RequestedRefundButton.setOnClickListener{
            val intent=Intent(this, RequestedRefundAdminActivity::class.java)
            startActivity(intent)
        }
        EventRequestedAdmin.setOnClickListener{
            val intent=Intent(this, RequestedEventAdminActivity::class.java)
            startActivity(intent)
        }
    }
}