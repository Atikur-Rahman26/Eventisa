package com.atik.eventisa

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class RequestedEventToUserInfo : AppCompatActivity() {

    private lateinit var RefundEventToUserRecyclerView:RecyclerView
    private lateinit var RefundEventToUserList:ArrayList<UserInfo>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_requested_event_to_user_info)

        RefundEventToUserRecyclerView=findViewById(R.id.SeeRefundEventToUserRecyclerView)
        RefundEventToUserRecyclerView.layoutManager=LinearLayoutManager(this)
        RefundEventToUserRecyclerView.setHasFixedSize(true)

        RefundEventToUserList= arrayListOf<UserInfo>()

        getUserInfo()
    }

    private fun getUserInfo() {
        var str:ArrayList<String>
        str= arrayListOf<String>()

        FirebaseDatabase.getInstance().getReference("RefundList").child(intent.getStringExtra("Id").toString())
            .addValueEventListener(object : ValueEventListener{
                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    for (datasnapshot in snapshot.children){
                        str.add(datasnapshot.key.toString())

                    }

                    for(i in str.indices){
                        FirebaseDatabase.getInstance().getReference("UsersTable").orderByChild("uid").equalTo(str[i])
                            .addValueEventListener(object :ValueEventListener{
                                override fun onDataChange(snapshot: DataSnapshot) {
                                    for (ds in snapshot.children){
                                        val add=ds.getValue(UserInfo::class.java)
                                        RefundEventToUserList.add(add!!)

                                        if(RefundEventToUserList.isEmpty()){

                                        }else{
                                            RefundEventToUserRecyclerView.adapter=RequestedEventToUserAdapter(RefundEventToUserList,this@RequestedEventToUserInfo,intent.getStringExtra("Id").toString(),intent.getStringExtra("Title").toString())
                                        }
                                    }
                                }

                                override fun onCancelled(error: DatabaseError) {
                                    TODO("Not yet implemented")
                                }
                            })
                    }
                }
            })

    }
}