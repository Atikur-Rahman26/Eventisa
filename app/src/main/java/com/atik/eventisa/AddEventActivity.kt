package com.atik.eventisa

import android.content.Intent
import android.net.Uri
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
import kotlinx.android.synthetic.main.activity_login.*
import java.net.URI

class AddEventActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddEventBinding
    private lateinit var database: DatabaseReference
    private lateinit var ImageUri:Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_event)

        uploadImage.setOnClickListener{
            selectImage()
        }

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

    private fun selectImage() {
        val intent=Intent(Intent.ACTION_PICK)
        intent.type="images/"
        intent.action=Intent.ACTION_GET_CONTENT

         startActivityForResult(intent,100)
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode==100&&resultCode== RESULT_OK){
            ImageUri= data?.data!!
            EventLogo.setImageURI(ImageUri)
        }
    }
}