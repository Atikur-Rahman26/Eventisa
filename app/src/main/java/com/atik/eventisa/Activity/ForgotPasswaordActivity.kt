package com.atik.eventisa.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.atik.eventisa.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_forgot_passwaord.*

class ForgotPasswaordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_passwaord)

        submitRestoreButton.setOnClickListener{
            if(EmaiSubmitTextView.text.toString().isEmpty()){

            }
            else{
                FirebaseAuth.getInstance().sendPasswordResetEmail(EmaiSubmitTextView.text.toString()).addOnCompleteListener{
                    it->
                    if(it.isSuccessful){
                        Toast.makeText(this,"Please check your mail",Toast.LENGTH_SHORT).show()
                        val intent= Intent(this, Login::class.java)
                        EmaiSubmitTextView.text.clear()
                        startActivity(intent)
                        finish()
                    }
                    else{
                        Toast.makeText(this,"Failed to send reset link in your mail address",Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

    }
}