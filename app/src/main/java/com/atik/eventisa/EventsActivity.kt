package com.atik.eventisa

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class EventsActivity : AppCompatActivity() {
    private lateinit var dbref: DatabaseReference
    private lateinit var eventRecyclerView:RecyclerView
    private lateinit var EventArrayList: ArrayList<AddEventData>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_events)

        eventRecyclerView=findViewById(R.id.Recyclerview)
        eventRecyclerView.layoutManager=LinearLayoutManager(this)
        eventRecyclerView.setHasFixedSize(true)

        EventArrayList= arrayListOf<AddEventData>()
        getEventItemData()
    }

    private fun getEventItemData() {
        dbref=FirebaseDatabase.getInstance().getReference("Events")
        dbref.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(eventSnapShot in snapshot.children){
                        var eventLst=eventSnapShot.getValue(AddEventData::class.java)
                        EventArrayList.add(eventLst!!)
                    }
                }

                eventRecyclerView.adapter=EventItemViewAdapter(EventArrayList,this@EventsActivity)

            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@EventsActivity,error.toString(),Toast.LENGTH_SHORT).show()
            }
        })
    }
}