package com.atik.eventisa

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class SeeWhoBooked : AppCompatActivity() {

    private lateinit var getUser:ArrayList<UserInfo>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_see_who_booked)
        getUser= arrayListOf<UserInfo>()

        val eventId:String= intent.getStringExtra("eventId")!!
        getEventBookedUserList(eventId)
    }

    private fun getEventBookedUserList(eventId: String) {


        var UserDb: FirebaseDatabase
        UserDb= FirebaseDatabase.getInstance()

        var eventUserList: FirebaseDatabase
        eventUserList= FirebaseDatabase.getInstance()
        var getUserID=eventUserList.getReference("BookedList").child(eventId)
        var str:ArrayList<String>
        str= arrayListOf()
        getUserID.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (dataSnapshot in snapshot.children){
                    str.add(dataSnapshot.key.toString())
                }

                for (i in str.indices){
                    val userInfo=UserDb.getReference("UsersTable").orderByChild("uid").equalTo(str[i].toString())
                    var userName:ArrayList<String>
                    userName= arrayListOf()
                    userInfo.addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            for(datasnapshot in snapshot.children){
                                userName.add(datasnapshot.key.toString())
                            }

                            for(j in userName.indices){
                                var udb:FirebaseDatabase
                                udb=FirebaseDatabase.getInstance()
                                val storeUserData=udb.getReference("UsersTable").child(userName[i].toString())
                                storeUserData.addListenerForSingleValueEvent(object : ValueEventListener{
                                    override fun onDataChange(snapshot: DataSnapshot) {
                                        for (datasnapshot in snapshot.children){
                                            var str1=datasnapshot.getValue(UserInfo::class.java)
                                            userName.add(str1!!.toString())
                                        }
                                    }

                                    override fun onCancelled(error: DatabaseError) {
                                        TODO("Not yet implemented")
                                    }
                                })
                            }
                        }


                        override fun onCancelled(error: DatabaseError) {
                            TODO("Not yet implemented")
                        }
                    })
                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

    }
}