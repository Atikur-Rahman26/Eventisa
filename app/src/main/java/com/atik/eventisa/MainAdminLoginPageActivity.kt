package com.atik.eventisa

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main_admin_login_page.*

class MainAdminLoginPageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_admin_login_page)

        MainAdminLoginButton.setOnClickListener {
//            if(checking()){
//                var email=MainEmailtAdmin.text.toString()
//                var name=MainAdminName.text.toString()
//                var password=MainPassWordAdmin.text.toString()
//
//                if(email.equals("admin123@gmail.com")&&name.equals("admin1309")&&password.equals("Admin1309")){
//                    val intent=Intent(this,MainAdminHomeActivity::class.java)
//                    startActivity(intent)
//                }
//                else{
//                    Toast.makeText(this,"email or password or admin name is worng",Toast.LENGTH_SHORT).show()
//                }
//            }else{
//                Toast.makeText(this,"No field can be left empty",Toast.LENGTH_SHORT).show()
//            }
//        }
            val intent = Intent(this, MainAdminHomeActivity::class.java)
            startActivity(intent)
        }
    }

        private fun checking(): Boolean {
            if (MainAdminName.text.toString().isNotEmpty() && MainEmailtAdmin.text.toString()
                    .isNotEmpty()
                && MainPassWordAdmin.text.toString().isNotEmpty()
            ) {
                return true
            }

            return false
        }
    }

/**
 * email.equals("admin123@gmail.com")&&name.equals("admin1309")&&password.equals("Admin1309")
 */