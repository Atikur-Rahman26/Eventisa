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
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_add_event.*
import kotlinx.android.synthetic.main.activity_login.*
import java.net.URI

class AddEventActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddEventBinding
    private lateinit var database: DatabaseReference
    private lateinit var ImageUri:Uri
    private lateinit var storage: FirebaseStorage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_event)
        storage=FirebaseStorage.getInstance()

        uploadImage.setOnClickListener{
            selectImage()
        }

        AddEventButton.setOnClickListener {

            if(checking()) {
                if(ImageUri==null){
                    Toast.makeText(this,"Please select an image ",Toast.LENGTH_SHORT).show()
                }
                else{
                    DowloadUrlAndUpload()
                }
            }
        }

    }

    private fun DowloadUrlAndUpload() {


        val EventTitle = eventTitle.text.toString()


        val reference=storage.reference.child("EventLogoImg").child(EventTitle)
        reference.putFile(ImageUri).addOnCompleteListener{
            if(it.isSuccessful){
                reference.downloadUrl.addOnSuccessListener { task->
                    uploadEventInfo(task.toString())
                }
            }
        }


    }

    private fun uploadEventInfo(imageUrl: String) {

        val EventTitle = eventTitle.text.toString()
        val EventLocation = eventLocation.text.toString()
        val EventDate = eventDate.text.toString()
        val EventDescription = eventDescription.text.toString()

        database= FirebaseDatabase.getInstance().getReference("Events")

        val addEvent=AddEventData(imageUrl.toString(),EventTitle,EventDate,EventLocation,EventDescription)
        database.child(EventTitle).setValue(addEvent).addOnSuccessListener {
            binding.eventTitle.text.clear()
            binding.eventDate.text.clear()
            binding.eventLocation.text.clear()
            binding.eventDescription.text.clear()

            Toast.makeText(this, "Successfully added", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
        }

    }

    private fun selectImage() {
        val intent=Intent(Intent.ACTION_PICK)
        intent.type="image/*"
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
            ImageUri=data?.data!!
            EventLogo.setImageURI(data?.data)
        }
    }
}