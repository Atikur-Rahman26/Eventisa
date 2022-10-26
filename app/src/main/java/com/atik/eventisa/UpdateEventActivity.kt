package com.atik.eventisa

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.atik.eventisa.Constants.Companion.EVENTDATE
import com.atik.eventisa.Constants.Companion.EVENTDESCRPTION
import com.atik.eventisa.Constants.Companion.EVENTID
import com.atik.eventisa.Constants.Companion.EVENTIMAGE
import com.atik.eventisa.Constants.Companion.EVENTLOCATION
import com.atik.eventisa.Constants.Companion.EVENTPRICE
import com.atik.eventisa.Constants.Companion.EVENTSEAT
import com.atik.eventisa.Constants.Companion.EVENTTITLE
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_add_event.*
import kotlinx.android.synthetic.main.activity_update_event.*
import kotlinx.android.synthetic.main.activity_update_event.Seat
import java.text.SimpleDateFormat
import java.util.*

class UpdateEventActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_event)

        UpdateeventTitle.setText(EVENTTITLE)
        UpdateEventDate.setText(EVENTDATE)
        UpdateEventPrice.setText(EVENTPRICE.toString())
        UpdateEventDescription.setText(EVENTDESCRPTION)
        UpdateEventLocation.setText(EVENTLOCATION)
        Seat.setText(EVENTSEAT.toString())

        var calendar:Calendar

        calendar=Calendar.getInstance()
        val datep= DatePickerDialog.OnDateSetListener{ view, year, month, dayOfMonth ->
            calendar.set(Calendar.YEAR,year)
            calendar.set(Calendar.MONTH,month)
            calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth)

            updateLabel(calendar)
        }

        UpdatePickingDate.setOnClickListener {
            DatePickerDialog(
                this,
                datep,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
        UpdateButton.setOnClickListener{
            if(checking()){
                EVENTDATE=UpdateEventDate.text.toString()
                EVENTLOCATION=UpdateEventLocation.text.toString()
                EVENTDESCRPTION=UpdateEventDescription.text.toString()
                EVENTPRICE=UpdateEventPrice.text.toString().toInt()
                EVENTSEAT=Seat.text.toString().toLong()

                val dialog= Dialog(this)
                dialog.setContentView(R.layout.logging_dialog)
                if(dialog.window!=null){
                    dialog?.window!!.setBackgroundDrawable(ColorDrawable(0))
                }
                dialog.show()
                var database= FirebaseDatabase.getInstance().getReference("Events")

                val addEvent=AddEventData(EVENTIMAGE,
                    EVENTTITLE,
                    EVENTDATE, EVENTLOCATION, EVENTDESCRPTION, EVENTID, EVENTPRICE,EVENTSEAT)

                database.child(EVENTID).setValue(addEvent).addOnSuccessListener {
                    UpdateEventDate.setText("")
                    UpdateEventDescription.text.clear()
                    UpdateEventPrice.text.clear()
                    Seat.text.clear()
                    Reason.text.clear()

                    dialog.dismiss()
                    Toast.makeText(this, "Successfully Updated", Toast.LENGTH_SHORT).show()
                    val intent=Intent(this,AdminHome::class.java)
                    startActivity(intent)
                    finish()
                }.addOnFailureListener {
                    dialog.dismiss()
                    Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
                }
            }
            else{
                Toast.makeText(this,"No field can be left empty",Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun updateLabel(calendar: Calendar) {
        val format="dd-MM-yyyy"
        val sdf= SimpleDateFormat(format)
        var simpleDateFormat= SimpleDateFormat("dd-MM-yyyy")
        var calendar1: Calendar = Calendar.getInstance()
        var date=simpleDateFormat.format(calendar1.time)

        var eventDate: java.util.Date =simpleDateFormat.parse(date)
        var thisDate: java.util.Date =sdf.parse(sdf.format(calendar.time).toString())
        if(eventDate.before(thisDate)){
            UpdateEventDate.setText(sdf.format(calendar.time))
        }
        else{
            Toast.makeText(this,"Wrong Date selected!!", Toast.LENGTH_SHORT).show()
        }

    }

    private fun checking(): Boolean {

        if(UpdateeventTitle.text.toString().isNotEmpty()&&
            UpdateEventDate.text.toString().isNotEmpty()&&
            UpdateEventDescription.text.toString().isNotEmpty()&&
            UpdateEventLocation.text.toString().isNotEmpty()&&
            UpdateEventPrice.text.toString().isNotEmpty()&&
            Seat.text.toString().isNotEmpty()&&
            Reason.text.toString().isNotEmpty()){
            return true
        }
        return false

    }
}