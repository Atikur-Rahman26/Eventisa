package com.atik.eventisa.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.atik.eventisa.Adapter.SeeRequestedEventAdapter
import com.atik.eventisa.DataClasses.AddEventData
import com.atik.eventisa.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class RequestedEventAdminActivity : AppCompatActivity() {

    private lateinit var SeeRequestedEventRecyclerView:RecyclerView
    private lateinit var SeeRequestedEventList:ArrayList<AddEventData>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_requested_event_admin)

        SeeRequestedEventRecyclerView=findViewById(R.id.SeeEventRequestedRecyclerview)
        SeeRequestedEventRecyclerView.layoutManager= LinearLayoutManager(this)
        SeeRequestedEventRecyclerView.setHasFixedSize(true)


        SeeRequestedEventList= arrayListOf<AddEventData>()

        getSeeEventRequested()
    }

    private fun getSeeEventRequested() {
        var dbref= FirebaseDatabase.getInstance().getReference("PendingAddingEvents")
        dbref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(datasnapshopt in snapshot.children){

                            var list=datasnapshopt.getValue(AddEventData::class.java)
                            SeeRequestedEventList.add(list!!)
                            break;
                    }

                    if(SeeRequestedEventList.isEmpty()){

                    }
                    else{
                        SeeRequestedEventRecyclerView.adapter=
                            SeeRequestedEventAdapter(SeeRequestedEventList,this@RequestedEventAdminActivity)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

    }
}