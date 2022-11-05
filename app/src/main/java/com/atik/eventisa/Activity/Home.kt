package com.atik.eventisa.Activity

import android.opengl.Visibility
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.atik.eventisa.Adapter.EventItemViewAdapter
import com.atik.eventisa.DataClasses.AddEventData
import com.atik.eventisa.DataClasses.Constants.Companion.username
import com.atik.eventisa.R
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlin.collections.ArrayList

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Home.newInstance] factory method to
 * create an instance of this fragment.
 */
class Home : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var dbref: DatabaseReference
    private lateinit var Userdbref: DatabaseReference
    private lateinit var eventRecyclerView: RecyclerView
    private lateinit var EventArrayList: ArrayList<AddEventData>

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
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Home.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Home().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        eventRecyclerView=view.findViewById(R.id.Recyclerview)
        eventRecyclerView.layoutManager=LinearLayoutManager(context)
        eventRecyclerView.setHasFixedSize(true)

        EventArrayList= arrayListOf<AddEventData>()
        getEventItemData()
    }

    private fun getEventItemData() {
        dbref= FirebaseDatabase.getInstance().getReference("Events")
        println("\n\n\n username: ${username}\n\n\n")
        Userdbref=FirebaseDatabase.getInstance().getReference(username)

        dbref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(eventSnapShot in snapshot.children){
                        var eventLst=eventSnapShot.getValue(AddEventData::class.java)

                        println("eventList: ${eventLst}")
//                        val sdf=SimpleDateFormat("dd-MM-yyyy")
//                        var simpleDateFormat= SimpleDateFormat("dd-MM-yyyy")
//                        var calendar1: Calendar = Calendar.getInstance()
//                        var date=eventLst?.eventDate.toString()
//
//                        var eventDate:java.util.Date =simpleDateFormat.parse(date)
//                        var thisDate:java.util.Date =sdf.parse(sdf.format(calendar1.time).toString())
//                        if(eventDate.before(thisDate)){
                            EventArrayList.add(eventLst!!)
                            println(EventArrayList)
//                        }
                    }
                }
                if(EventArrayList.isEmpty()){
                    println(EventArrayList)
                }
                else {
                    try{
                        DataLoadingProgressBar.visibility=View.INVISIBLE
                        println(EventArrayList)
                        eventRecyclerView.adapter =
                            EventItemViewAdapter(EventArrayList, requireContext())
                    }
                    catch (e:Exception){
                        println(e.message)
                    }
                }

            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context,error.toString(), Toast.LENGTH_SHORT).show()
            }
        })

    }
}