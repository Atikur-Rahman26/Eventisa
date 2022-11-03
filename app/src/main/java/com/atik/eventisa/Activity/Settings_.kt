package com.atik.eventisa.Activity

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.atik.eventisa.Adapter.RefundListViewAdapter
import com.atik.eventisa.DataClasses.AddEventData
import com.atik.eventisa.DataClasses.Constants.Companion.uId
import com.atik.eventisa.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Settings.newInstance] factory method to
 * create an instance of this fragment.
 */
class Settings_ : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var dbref: DatabaseReference
    private lateinit var Userdbref: DatabaseReference
    private lateinit var RefundRecyclerView: RecyclerView
    private lateinit var RefundList: ArrayList<AddEventData>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Settings.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Settings_().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        RefundRecyclerView=view.findViewById(R.id.RefundRecyclerviewXML)
        RefundRecyclerView.layoutManager= LinearLayoutManager(context)


        RefundList= arrayListOf<AddEventData>()
        getEventItemData()
    }

    private fun getEventItemData() {
        var str= arrayListOf<String>()
        var dbAuth: FirebaseAuth = FirebaseAuth.getInstance()
        uId =dbAuth.uid.toString()
        dbref= FirebaseDatabase.getInstance().getReference("RefundList")
        Userdbref= FirebaseDatabase.getInstance().getReference("Events")
        dbref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for(datasnapshot in snapshot.children) {
                    for(dat in datasnapshot.children){
                        str.add(dat.key.toString())
                        if(dat.key.equals(uId)){
                            str.add(datasnapshot.key.toString())
                        }
                    }
                }
                passingDatatoRecyclerView(Userdbref,str)

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    private fun passingDatatoRecyclerView(eventdbref: DatabaseReference, eventIdFromFavourite: ArrayList<String>) {
        eventdbref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                RefundList= arrayListOf<AddEventData>()
                for (datasnapShot in snapshot.children) {
                    for (i in eventIdFromFavourite.indices) {
                        if (datasnapShot.key.equals(eventIdFromFavourite[i])) {
                            var eventlst=datasnapShot.getValue(AddEventData::class.java)
                            RefundList.add(eventlst!!)
                        }
                    }
                }

                try {
                    RefundRecyclerView.adapter =
                        RefundListViewAdapter(RefundList, requireContext())
                }catch (e:Exception){
                    println(e.message)
                    Log.i(ContentValues.TAG, "onDataChange: ${e.message} ")
                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}