package com.atik.eventisa.Activity

import android.app.Dialog
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.atik.eventisa.DataClasses.Constants.Companion.email
import com.atik.eventisa.DataClasses.Constants.Companion.fname
import com.atik.eventisa.DataClasses.Constants.Companion.lname
import com.atik.eventisa.DataClasses.Constants.Companion.phone
import com.atik.eventisa.DataClasses.Constants.Companion.username
import com.atik.eventisa.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_login.*

class Login : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var dbref:DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        val userId:String
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth=FirebaseAuth.getInstance()

        val dialog= Dialog(this)
        dialog.setContentView(R.layout.logging_dialog)
        if(dialog.window!=null){
            dialog?.window!!.setBackgroundDrawable(ColorDrawable(0))
        }
        val currentUserLoggedIn=auth.currentUser
        var UserName:String=""
//        if(currentUserLoggedIn!=null&&auth.currentUser!!.isEmailVerified){
//           var ref=FirebaseFirestore.getInstance().collection("USERS").whereEqualTo("Email",auth.currentUser!!.email.toString()).get().addOnSuccessListener {
//               it->
//               if(it.isEmpty){
//
//               }
//               else{
//                   var UserName= auth?.currentUser?.email.toString()
//
//                   dbref=FirebaseDatabase.getInstance().getReference("UsersTable")
//                   dbref.addListenerForSingleValueEvent(object : ValueEventListener {
//                       override fun onDataChange(snapshot: DataSnapshot) {
//                           for(datasnapshot in snapshot.children){
//                               var string:String=datasnapshot.child("email").value.toString()
//                               if(string.equals(UserName)){
//                                   username=datasnapshot.child("userName").value.toString()
//                                   break
//                               }
//                           }
//
//                           val dbref=FirebaseDatabase.getInstance().getReference("UsersTable").child(username)
//                           dbref.addListenerForSingleValueEvent(object :ValueEventListener{
//                               override fun onDataChange(snapshot: DataSnapshot) {
//                                   Constants.phone=snapshot.child("phoneNumber").value.toString()
//                                   Constants.fname=snapshot.child("firstName").value.toString()
//                                   Constants.lname=snapshot.child("lastName").value.toString()
//                                   Constants.email=UserName
//                                   val intent=Intent(this@Login,UserProfileActivity::class.java)
//                                   intent.putExtra("email",UserName)
//                                   startActivity(intent)
//                                   finish()
//                               }
//
//                               override fun onCancelled(error: DatabaseError) {
//                                   TODO("Not yet implemented")
//                               }
//                           })
//                       }
//
//                       override fun onCancelled(error: DatabaseError) {
//                           TODO("Not yet implemented")
//                       }
//                   })
//               }
//           }
//
//        }

        //For sign in Button
        LoginButton.setOnClickListener{

            if(checking()){
                dialog.show()
                var UserName=EmailtAdmin.text.trim{it<=' '}.toString()//removing extra space
                var PassWord=PassWordAdmin.text.trim{it<=' '}.toString() //removing extra space

                auth.signInWithEmailAndPassword(UserName,PassWord)
                    .addOnCompleteListener(this){
                            task->
                        if(task.isSuccessful){

                            if(auth.currentUser!!.isEmailVerified){
                                dbref=FirebaseDatabase.getInstance().getReference("UsersTable")
                                dbref.addListenerForSingleValueEvent(object : ValueEventListener {
                                    override fun onDataChange(snapshot: DataSnapshot) {
                                        for(datasnapshot in snapshot.children){
                                            var string:String=datasnapshot.child("email").value.toString()
                                            if(string.equals(UserName)){
                                                username=datasnapshot.child("userName").value.toString()
                                                break
                                            }
                                        }

                                        val dbref=FirebaseDatabase.getInstance().getReference("UsersTable").child(username)
                                        dbref.addListenerForSingleValueEvent(object :ValueEventListener{
                                            override fun onDataChange(snapshot: DataSnapshot) {
                                                phone=snapshot.child("phoneNumber").value.toString()
                                                fname=snapshot.child("firstName").value.toString()
                                                lname=snapshot.child("lastName").value.toString()
                                                email=UserName
                                            }

                                            override fun onCancelled(error: DatabaseError) {
                                                TODO("Not yet implemented")
                                            }
                                        })
                                    }

                                    override fun onCancelled(error: DatabaseError) {
                                        TODO("Not yet implemented")
                                    }
                                })
                                EmailtAdmin.text.clear()
                                PassWordAdmin.text.clear()
                                dialog.dismiss()
                                val intent=Intent(this, UserProfileActivity::class.java)
                                intent.putExtra("email",UserName)
                                startActivity(intent)
                                finish()
                            }
                            else{
                                dialog.dismiss()
                                Toast.makeText(this,"Please verify your mail first",Toast.LENGTH_SHORT).show()
                            }
                        }
                        else{
                            dialog.dismiss()
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

        SignupActivitybutton.setOnClickListener{
            val intent=Intent(this, signUpActivity::class.java)
            startActivity(intent)
            finish()
        }

        ForgotPasswordUserButton.setOnClickListener {
            val intent=Intent(this, ForgotPasswaordActivity::class.java)
            startActivity(intent)
            finish()
        }
    }



    private fun checking():Boolean{

        if(PassWordAdmin.text.toString().trim{it<=' '}.isNotEmpty()&&EmailtAdmin.text.toString().trim{it<=' '}.isNotEmpty()){
            return true
        }
        return false
    }
}