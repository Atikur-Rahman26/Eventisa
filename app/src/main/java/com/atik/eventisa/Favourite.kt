package com.atik.eventisa

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.atik.eventisa.Constants.Companion.uId
import com.atik.eventisa.Constants.Companion.username
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_favourite.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Favourite.newInstance] factory method to
 * create an instance of this fragment.
 */
class Favourite : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var dbref: DatabaseReference
    private lateinit var Userdbref: DatabaseReference
    private lateinit var favouriteRecyclerView: RecyclerView
    private lateinit var EventFavouriteList: ArrayList<AddEventData>

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

        return inflater.inflate(R.layout.fragment_favourite, container, false)
        showTextEmplty.visibility=View.INVISIBLE
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Favourite.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Favourite().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        favouriteRecyclerView=view.findViewById(R.id.favouriteRecyclerview)
        favouriteRecyclerView.layoutManager= LinearLayoutManager(context)
        favouriteRecyclerView.setHasFixedSize(true)

        EventFavouriteList= arrayListOf<AddEventData>()
        getEventItemData()
    }

    private fun getEventItemData() {
        var str= arrayListOf<String>()
        var dbAuth:FirebaseAuth= FirebaseAuth.getInstance()
        uId =dbAuth.uid.toString()
        dbref= FirebaseDatabase.getInstance().getReference("addedFavourite")
        Userdbref=FirebaseDatabase.getInstance().getReference("Events")
            dbref.addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (datasnapshot in snapshot.children) {

                        var str1: String = datasnapshot.value.toString()
                        var str2: String = ""
                        for (j in str1.indices) {
                            if (str1[j] == '=') {
                                break;
                            } else if (str1[j] != '{') {
                                str2 += str1[j]
                            }
                        }

                        if (str2.equals(uId)) {
                            str.add(datasnapshot.key.toString())
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
                EventFavouriteList= arrayListOf<AddEventData>()
                for (datasnapShot in snapshot.children) {
                    for (i in eventIdFromFavourite.indices) {
                        if (datasnapShot.key.equals(eventIdFromFavourite[i])) {
                            var eventlst=datasnapShot.getValue(AddEventData::class.java)
                            EventFavouriteList.add(eventlst!!)
                        }
                    }
                }
                if(EventFavouriteList.isEmpty()){
                    showTextEmplty.visibility=View.VISIBLE
                }
                if(requireContext()!=null) {
                    favouriteRecyclerView.adapter =
                        FavouriteItemViewAdaptar(EventFavouriteList, requireContext())
                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}