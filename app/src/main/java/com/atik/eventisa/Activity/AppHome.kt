package com.atik.eventisa.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.atik.eventisa.R
import kotlinx.android.synthetic.main.activity_app_home.*

class AppHome : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app_home)

        MainAdmin.setOnClickListener{
            val intent=Intent(this, MainAdminLoginPageActivity::class.java)
            startActivity(intent)
        }

        EventUserButton.setOnClickListener{
            val intent=Intent(this, AdminLoginPageActivity::class.java)
            startActivity(intent)
        }

        NormalUserButton.setOnClickListener{
                val intent = Intent(this, Login::class.java)
                startActivity(intent)
        }
    }
}