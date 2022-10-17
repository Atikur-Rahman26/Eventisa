package com.atik.eventisa

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class Login : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        val userId:String
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth=FirebaseAuth.getInstance()


        //For sign in Button
        LoginButton.setOnClickListener{

            if(checking()){
                var UserName=EmailtAdmin.text.trim{it<=' '}.toString()//removing extra space
                var PassWord=PassWordAdmin.text.trim{it<=' '}.toString() //removing extra space

                auth.signInWithEmailAndPassword(UserName,PassWord)
                    .addOnCompleteListener(this){
                        task->
                        if(task.isSuccessful){
                            val intent=Intent(this,EventsActivity::class.java)
                            startActivity(intent)
                        }
                        else{
                            Toast.makeText(this,"Wrong email or password",Toast.LENGTH_SHORT).show()
                            EmailtAdmin.text.clear()
                            PassWordAdmin.text.clear()
                        }
                    }
            }
            else{
                Toast.makeText(this,"Email or Password field can't be left empty",Toast.LENGTH_SHORT).show()
            }
        }

        CreateAccount.setOnClickListener{
            val intent=Intent(this,signUpActivity::class.java)
            startActivity(intent)
        }
    }



    private fun checking():Boolean{

        if(PassWordAdmin.text.toString().trim{it<=' '}.isNotEmpty()&&EmailtAdmin.text.toString().trim{it<=' '}.isNotEmpty()){
            return true
        }
        return false
    }
}