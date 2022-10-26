package com.atik.eventisa

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.atik.eventisa.databinding.ActivityAddEventBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_add_event.*
import java.text.SimpleDateFormat
import java.util.*

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
        var calendar:Calendar

        calendar=Calendar.getInstance()
        val datep= DatePickerDialog.OnDateSetListener{ view, year, month, dayOfMonth ->
            calendar.set(Calendar.YEAR,year)
            calendar.set(Calendar.MONTH,month)
            calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth)

            updateLabel(calendar)
        }

        pickingDate.setOnClickListener {
            DatePickerDialog(
                this,
                datep,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        UpdateEventButton.setOnClickListener {

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


        val EventTitle = AddeventTitle.text.toString()


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
        val dialog=Dialog(this)
        dialog.setContentView(R.layout.logging_dialog)
        if(dialog.window!=null){
            dialog?.window!!.setBackgroundDrawable(ColorDrawable(0))
        }
        dialog.show()

        val EventTitle = AddeventTitle.text.toString()
        val EventLocation = AddeventLocation.text.toString()
        val EventDate = AddeventDate.text.toString()
        val EventDescription = AddeventDescription.text.toString()
        val EventPrice=AddEventPrice.text.toString()
        val EVENTSEAT=Seat.text.toString()


        var simpleDateFormat= SimpleDateFormat("ddMMyyyyHHmmss")
        var calendar: Calendar = Calendar.getInstance()
        var date=simpleDateFormat.format(calendar.time)
        val eventid=EventTitle+date.toString()


        database= FirebaseDatabase.getInstance().getReference("Events")

        val addEvent=AddEventData(imageUrl.toString(),EventTitle,EventDate,EventLocation,EventDescription,eventid,EventPrice.toInt(),EVENTSEAT.toLong())

        database.child(eventid).setValue(addEvent).addOnSuccessListener {
            AddeventTitle.text.clear()
            AddeventDate.setText("")
            AddeventLocation.text.clear()
            AddeventDescription.text.clear()
            EventLogo.setImageURI(null)
            AddEventPrice.text.clear()
            Seat.text.clear()
            dialog.dismiss()

            Toast.makeText(this, "Successfully added", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            dialog.dismiss()
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

        if(AddeventTitle.text.toString().isNotEmpty()&&
            AddeventDate.text.toString().isNotEmpty()&&
            AddeventDescription.text.toString().isNotEmpty()&&
            AddeventLocation.text.toString().isNotEmpty()&&
                AddEventPrice.text.toString().isNotEmpty()){
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

    private fun updateLabel(calendar: Calendar) {
        val format="dd-MM-yyyy"
        val sdf=SimpleDateFormat(format)
        var simpleDateFormat= SimpleDateFormat("dd-MM-yyyy")
        var calendar1: Calendar = Calendar.getInstance()
        var date=simpleDateFormat.format(calendar1.time)

        var eventDate: java.util.Date =simpleDateFormat.parse(date)
        var thisDate: java.util.Date =sdf.parse(sdf.format(calendar.time).toString())
        if(eventDate.before(thisDate)){
            AddeventDate.setText(sdf.format(calendar.time))
        }
        else{
            Toast.makeText(this,"Wrong Date selected!!",Toast.LENGTH_SHORT).show()
        }

    }
}