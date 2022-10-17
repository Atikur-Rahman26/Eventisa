package com.atik.eventisa

import android.os.Bundle
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.atik.eventisa.databinding.ActivityAddEventBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_add_event.*

class AddEventActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddEventBinding
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_event)

        AddEventButton.setOnClickListener {

            if(checking()) {
                val EventTitle = eventTitle.text.toString()
                val EventLocation = eventLocation.text.toString()
                val EventDate = eventDate.text.toString()
                val EventDescription = eventDescription.text.toString()

                database= FirebaseDatabase.getInstance().getReference("Events")
                var addevents=EventData(EventDate,EventTitle,EventLocation,EventDescription)
                database.child(EventTitle).setValue(addevents).addOnSuccessListener {
                    binding.eventTitle.text.clear()
                    binding.eventDate.text.clear()
                    binding.eventLocation.text.clear()
                    binding.eventDescription.text.clear()

                    Toast.makeText(this, "Successfully added", Toast.LENGTH_SHORT).show()
                }.addOnFailureListener {
                    Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    private fun checking(): Boolean {

        if(eventTitle.text.toString().isNotEmpty()&&
                eventDate.text.toString().isNotEmpty()&&
                eventDescription.text.toString().isNotEmpty()&&
                eventLocation.text.toString().isNotEmpty()){
            return true
        }
        return false

    }

}