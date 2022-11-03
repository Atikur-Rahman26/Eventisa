package com.atik.eventisa.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.atik.eventisa.Adapter.SeeMyEventHostAdapter
import com.atik.eventisa.DataClasses.AddEventData
import com.atik.eventisa.DataClasses.Constants.Companion.HostEmail
import com.atik.eventisa.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class SeeMyEventHostActivity : AppCompatActivity() {

    private lateinit var SeeEventRecyclerView:RecyclerView
    private lateinit var SeeEventArray:ArrayList<AddEventData>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_see_my_event_host)

        SeeEventRecyclerView=findViewById(R.id.SeeEventHostRecyclerview)
        SeeEventRecyclerView.layoutManager= LinearLayoutManager(this)
        SeeEventRecyclerView.setHasFixedSize(true)


        SeeEventArray= arrayListOf<AddEventData>()

        getSeeEventHost()
    }

    private fun getSeeEventHost() {
        var getEventName:ArrayList<String>
        getEventName= arrayListOf<String>()
        var dbref=FirebaseDatabase.getInstance().getReference("Events")
        dbref.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(datasnapshot in snapshot.children){
                        if(datasnapshot.child("hostEmail").value.toString().equals(HostEmail)){
                            getEventName.add(datasnapshot.key.toString())
                        }
                    }

                    for(datasnapshopt in snapshot.children){
                        for(i in getEventName.indices){
                            if(datasnapshopt.key.toString().equals(getEventName[i])){
                                var list=datasnapshopt.getValue(AddEventData::class.java)
                                SeeEventArray.add(list!!)
                                break;
                            }
                        }
                    }

                    if(SeeEventArray.isEmpty()){

                    }
                    else{
                        SeeEventRecyclerView.adapter= SeeMyEventHostAdapter(SeeEventArray,this@SeeMyEventHostActivity)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

}