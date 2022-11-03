package com.atik.eventisa.Activity

import android.app.Dialog
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.atik.eventisa.DataClasses.Constants.Companion.HostEmail
import com.atik.eventisa.DataClasses.Constants.Companion.HostFirstName
import com.atik.eventisa.DataClasses.Constants.Companion.HostLastName
import com.atik.eventisa.DataClasses.Constants.Companion.HostName
import com.atik.eventisa.DataClasses.Constants.Companion.HostPhone
import com.atik.eventisa.DataClasses.Constants.Companion.HostUserName
import com.atik.eventisa.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_admin_login_page.*

class AdminLoginPageActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_login_page)
        var auth=FirebaseAuth.getInstance()


        AdminLoginButton.setOnClickListener{
            if(checking()){

                var adminUserName:String=AdminName.text.toString()
                var adminEmailId:String=EmailtAdmin.text.toString()
                var adminPassword:String=PassWordAdmin.text.toString()
                /**
                 *
                 * Passing data to the fun AdminIsValid
                 * And
                 * This is for checking that the Admin is valid or not
                 *
                 */
                AdminIsValidOrNot(adminUserName,adminEmailId,adminPassword)
            }
            else{
                Toast.makeText(this,"No field can be left empty", Toast.LENGTH_SHORT).show()
            }
        }

        HostSignUpButton.setOnClickListener{
            val intent=Intent(this@AdminLoginPageActivity, HostSignUpActivity::class.java)
            startActivity(intent)
        }
    }

    private fun AdminIsValidOrNot(adminUserName: String, adminEmailId: String, adminPassword: String) {

        val dialog= Dialog(this@AdminLoginPageActivity)
        dialog.setContentView(R.layout.logging_dialog)
        dialog.show()


        var fstore=FirebaseDatabase.getInstance().getReference("HostsTable")
        fstore.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                var flag=0
                if(snapshot.exists()){
                    for(datasnapshot in snapshot.children){
                        if(datasnapshot.key.toString().equals(adminUserName)){
                            HostEmail=adminEmailId
                            HostFirstName=datasnapshot.child("hostFname").value.toString()
                            HostLastName=datasnapshot.child("hostLname").value.toString()
                            HostPhone=datasnapshot.child("hostPhone").value.toString()
                            HostName= HostFirstName+" "+ HostLastName
                            HostUserName=adminUserName
                            println("Host name: ${HostName}\t")
                            flag=1
                        }
                    }
                    if(flag==1){
                        var auth=FirebaseAuth.getInstance()
                        auth.signInWithEmailAndPassword(HostEmail,adminPassword).addOnSuccessListener {
                            val intent=Intent(this@AdminLoginPageActivity, HostHomeActivity::class.java)
                            startActivity(intent)
                            finish()
                        }.addOnFailureListener{
                            dialog.dismiss()
                            Toast.makeText(this@AdminLoginPageActivity,"Email or Password is wrong",Toast.LENGTH_SHORT).show()
                        }
                    }
                    else{
                        Toast.makeText(this@AdminLoginPageActivity,"sorry!!such type of admin name found",Toast.LENGTH_SHORT)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

    }

    private fun checking(): Boolean {



        if(AdminName.text.toString().isNotEmpty()&&EmailtAdmin.text.toString().isNotEmpty()
            &&PassWordAdmin.text.toString().isNotEmpty()){
            return true
        }

        return false
    }


    /**
     *

    private fun AdminIsValid(adminUserName: String, adminEmailId: String, adminPassword: String) {
        val dialog= Dialog(this)
        dialog.setContentView(R.layout.logging_dialog)
        if(dialog.window!=null){
            dialog?.window!!.setBackgroundDrawable(ColorDrawable(0))
        }
        dialog.show()
        var AName:String=adminUserName
        var AEmail:String=adminEmailId
        var APass:String=adminPassword

        /**
         * this is for AdminTable reference
         */

        val reference=FirebaseDatabase.getInstance().getReference("AdminTable")

        /**
         * This is for Admin name checking query
         */
        val checkAdminName=reference.orderByChild("admin").equalTo(AName)
        /**
         * This is for Admin Email checking query
         */


        val checkAdminEmail=reference.orderByChild("email").equalTo(AEmail)
        /**
         * This is for Admin Password checking query
         */

        val checkAdminPassword=reference.orderByChild("password").equalTo(APass)
        /**
         * Checking Admin Name in the firebase database
         */

        checkAdminName.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){

                    /**
                     * Checking Admin Email in the firebase database
                     */
                    checkAdminEmail.addListenerForSingleValueEvent(object : ValueEventListener{
                        override fun onDataChange(snapshot: DataSnapshot) {
                            if(snapshot.exists()){

                                /**
                                 * Checking Admin Password
                                 */


                                checkAdminPassword.addListenerForSingleValueEvent(object : ValueEventListener{

                                    override fun onDataChange(snapshot: DataSnapshot) {
                                        if(snapshot.exists()){
                                            /**
                                             * Successfully found the data in Admin Table
                                             */

                                            /**
                                             * clearing data in editText
                                             */
                                            AdminName.text.clear()
                                            EmailtAdmin.text.clear()
                                            PassWordAdmin.text.clear()


                                            /**
                                             * This is for Admin name checking query
                                             */
                                            val checkAdminName=reference.orderByChild("admin").equalTo(AName)
                                            checkAdminName.addListenerForSingleValueEvent(object : ValueEventListener{
                                                override fun onDataChange(snapshot: DataSnapshot) {
                                                    for(datasnapshot in snapshot.children){
                                                        var evId=(datasnapshot.child("eventId").value.toString())
                                                        val ref=FirebaseDatabase.getInstance().getReference("Events")
                                                        val checkAndGetData=ref.child(evId)
                                                        checkAndGetData.addListenerForSingleValueEvent(object : ValueEventListener{
                                                            override fun onDataChange(snapshot: DataSnapshot) {
                                                                for(datasnapshot in snapshot.children){
                                                                    if(datasnapshot.key.equals("eventId")){
                                                                        EVENTID=datasnapshot.value.toString()
                                                                    }
                                                                    else if(datasnapshot.key.equals("eventPrice")){
                                                                        EVENTPRICE=(datasnapshot.value.toString().toInt())
                                                                    }
                                                                    else if(datasnapshot.key.equals("eventTitle")){
                                                                        EVENTTITLE=datasnapshot.value.toString()
                                                                    }
                                                                    else if(datasnapshot.key.equals("eventDate")){
                                                                        EVENTDATE=datasnapshot.value.toString()
                                                                    }
                                                                    else if(datasnapshot.key.equals("imageUri")){
                                                                        EVENTIMAGE=datasnapshot.value.toString()
                                                                    }
                                                                    else if(datasnapshot.key.equals("eventLocation")){
                                                                        EVENTLOCATION=datasnapshot.value.toString()
                                                                    }
                                                                    else if(datasnapshot.key.equals("eventDescription")){
                                                                        EVENTDESCRPTION=datasnapshot.value.toString()
                                                                    }
                                                                    else if(datasnapshot.key.equals("eventSeat")){
                                                                        EVENTSEAT=datasnapshot.value.toString().toLong()
                                                                    }
                                                                }
                                                                lateinit var favouriteReference: DatabaseReference
                                                                favouriteReference=FirebaseDatabase.getInstance().getReference("addedFavourite")
                                                                favouriteReference.addValueEventListener(object : ValueEventListener{
                                                                    override fun onDataChange(snapshot: DataSnapshot) {
                                                                            var favouriteCount: Long =snapshot.child(
                                                                                EVENTID).childrenCount
                                                                            EVENTBOOKED=favouriteCount
                                                                        dialog.dismiss()
                                                                        val intent=Intent(this@AdminLoginPageActivity,AdminHome::class.java)
                                                                        startActivity(intent)
                                                                    }

                                                                    override fun onCancelled(error: DatabaseError) {
                                                                        TODO("Not yet implemented")
                                                                    }
                                                                })
                                                            }

                                                            override fun onCancelled(error: DatabaseError) {
                                                                TODO("Not yet implemented")
                                                            }
                                                        })

                                                    }
                                                }

                                                override fun onCancelled(error: DatabaseError) {
                                                    TODO("Not yet implemented")
                                                }
                                            })
                                        }
                                        else{
                                            return
                                        }
                                    }

                                    override fun onCancelled(error: DatabaseError) {
                                        TODO("Not yet implemented")
                                    }
                                })

                                /**
                                 * Checked the admin password
                                 */
                            }
                            else{
                                return
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {
                            TODO("Not yet implemented")
                        }
                    })

                    /**
                     * Checked everything in the Admin table
                     */

                }
                else{
                    dialog.dismiss()
                    Toast.makeText(this@AdminLoginPageActivity,"Admin name or email or password wrong",Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

    }
    */
}