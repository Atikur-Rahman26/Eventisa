package com.atik.eventisa

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_app_home.*

class AppHome : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app_home)

        MainAdmin.setOnClickListener{
            val intent=Intent(this,MainAdminLoginPageActivity::class.java)
            startActivity(intent)
        }

        EventUserButton.setOnClickListener{
            val intent=Intent(this,AdminLoginPageActivity::class.java)
            startActivity(intent)
        }

        NormalUserButton.setOnClickListener{
            var currentUserLoggedIn=FirebaseAuth.getInstance().currentUser
            var auth=FirebaseAuth.getInstance()

            if(currentUserLoggedIn!=null&&auth.currentUser!!.isEmailVerified){
                var ref=
                    FirebaseFirestore.getInstance().collection("USERS").whereEqualTo("Email",auth.currentUser!!.email.toString()).get().addOnSuccessListener {
                        it->
                    if(it.isEmpty){

                    }
                    else{
                        var UserName= auth?.currentUser?.email.toString()

                        var dbref= FirebaseDatabase.getInstance().getReference("UsersTable")
                        dbref.addListenerForSingleValueEvent(object : ValueEventListener {
                            override fun onDataChange(snapshot: DataSnapshot) {
                                for(datasnapshot in snapshot.children){
                                    var string:String=datasnapshot.child("email").value.toString()
                                    if(string.equals(UserName)){
                                        Constants.username =datasnapshot.child("userName").value.toString()
                                        break
                                    }
                                }

                                val dbref=
                                    FirebaseDatabase.getInstance().getReference("UsersTable").child(
                                        Constants.username
                                    )
                                dbref.addListenerForSingleValueEvent(object : ValueEventListener {
                                    override fun onDataChange(snapshot: DataSnapshot) {
                                        Constants.phone=snapshot.child("phoneNumber").value.toString()
                                        Constants.fname=snapshot.child("firstName").value.toString()
                                        Constants.lname=snapshot.child("lastName").value.toString()
                                        Constants.email=UserName
                                        val intent=Intent(this@AppHome,UserProfileActivity::class.java)
                                        intent.putExtra("email",UserName)
                                        startActivity(intent)
                                        finish()
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
                    }
                }

            }
            else {
                val intent = Intent(this, Login::class.java)
                startActivity(intent)
            }
        }
    }
}