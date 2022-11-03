package com.atik.eventisa.Activity

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.atik.eventisa.DataClasses.Constants.Companion.email
import com.atik.eventisa.DataClasses.Constants.Companion.fname
import com.atik.eventisa.DataClasses.Constants.Companion.lname
import com.atik.eventisa.DataClasses.Constants.Companion.phone
import com.atik.eventisa.DataClasses.Constants.Companion.username
import com.atik.eventisa.R
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.item_email.*
import kotlinx.android.synthetic.main.item_image.*
import kotlinx.android.synthetic.main.item_info.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Profile.newInstance] factory method to
 * create an instance of this fragment.
 */
class Profile : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

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
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Profile.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Profile().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        userUserNameProfileText.setText(username.toString())
        userPhoneNumberProfileText.setText((phone.toString()))
        userOriginalNameProfileText.setText(fname.toString()+" "+ lname.toString())
        userEmailProfileText.setText(email.toString())

        SignOutProfile.setOnClickListener{
            activity.let{
                val auth = Firebase.auth
                Firebase.auth.signOut()

                val intent=Intent(it, AppHome::class.java)
                startActivity(intent)
            }
        }

    }
}