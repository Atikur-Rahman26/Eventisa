package com.atik.eventisa

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.atik.eventisa.Constants.Companion.EVENTID
import com.google.firebase.database.*

class Booked_Event : AppCompatActivity() {



    private lateinit var BookedListRecyclerView:RecyclerView
    private lateinit var BookedList:ArrayList<UserInfo>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booked_event)

        BookedListRecyclerView=findViewById(R.id.BookedRecyclerview)
        BookedListRecyclerView.layoutManager=LinearLayoutManager(this)
        BookedListRecyclerView.setHasFixedSize(true)

        BookedList= arrayListOf<UserInfo>()

        getBookedListData()
    }

    private fun getBookedListData() {
        var str = arrayListOf<String>()
        var username = arrayListOf<String>()
        var Userdbref = FirebaseDatabase.getInstance().getReference("UsersTable")
        var dbref = FirebaseDatabase.getInstance().getReference("BookedList")

        dbref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (datasnapshot in snapshot.children) {
                    if (datasnapshot.key.toString().equals(EVENTID)) {
                        for (dat in datasnapshot.children) {
                            str.add(dat.key.toString())
                        }
                    }
                }
                println(str)
                val gettingUserInf = Userdbref.orderByChild("uid")
                gettingUserInf.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        for (dat in snapshot.children) {
                            for(ds in dat.children){
                                for (i in str.indices) {
                                    if (ds.value?.equals(str[i]) == true) {
                                        username.add(dat.key.toString())
                                        println("username:: ${dat.key}")
                                    }

                                }
                            }
                        }

                        val storeList=Userdbref
                        storeList.addListenerForSingleValueEvent(object : ValueEventListener{
                            override fun onDataChange(snapshot: DataSnapshot) {
                                for(datasnapshot in snapshot.children){
                                    for(j in username.indices){
                                        if(datasnapshot.key.equals(username[j])){
                                            var eventLst=datasnapshot.getValue(UserInfo::class.java)
                                            BookedList.add(eventLst!!)
                                        }
                                    }
                                }
                                if(BookedList.isEmpty()){

                                }
                                else {
                                    try{
                                        println(BookedList.toString())
                                        BookedListRecyclerView.adapter =
                                            BookedListViewAdapter(BookedList)
                                    }
                                    catch (e:Exception){
                                        println(e.message)
                                    }
                                }

                            }
                            override fun onCancelled(error: DatabaseError) {
                                TODO("Not yet implemented")
                            }
                        })

//                        println(username)
                    }

                    override fun onCancelled(error: DatabaseError) {

                    }
                })
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }


}