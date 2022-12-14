package com.atik.eventisa.Activity

import android.app.Dialog
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast

import com.atik.eventisa.DataClasses.Constants.Companion.uId
import com.atik.eventisa.DataClasses.UserInfo
import com.atik.eventisa.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_sign_up.*

class signUpActivity : AppCompatActivity() {

    private lateinit var databse:DatabaseReference
    private lateinit var auth: FirebaseAuth
    private lateinit var firebaseUser:FirebaseUser
    private lateinit var firestoreRef:FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        var country:String




        auth=FirebaseAuth.getInstance()

        firestoreRef=FirebaseFirestore.getInstance()
        registerBtn.setOnClickListener{
            /**
             * Dialog box is creating while checking the data exist or not
             *
             * And this is working to create an account
             */
            val dialog=Dialog(this)
            dialog.setContentView(R.layout.register_dialog)
            if(dialog.window!=null){
                dialog?.window!!.setBackgroundDrawable(ColorDrawable(0))
            }


            if(checking()){


                var emailTEXT:String=emailSignUp.text.toString()
                var passWORD:String=passwordTextSignUp.text.toString()
                var FNAME:String=fName.text.toString()
                var LNAME:String=lName.text.toString()
                var phoneNumbeR:String=phoneNumber.text.toString()
                var username:String=UserNameTextField.text.toString()

                if(passWORD.length>=6) {
                    if(phoneNumbeR.length==11){
                        if (Patterns.EMAIL_ADDRESS.matcher(emailTEXT).matches()) {

                            /**
                             *for storing data to firestore
                             * Dialog showing
                             */
                            dialog.show()


                            val Storeuser = firestoreRef.collection("USERS")
                            /*
                        *Query for getting the result if the email is existed or not
                         */
                            val queryForeEmail = Storeuser.whereEqualTo("Email", emailTEXT).get()
                                .addOnSuccessListener { it ->
                                    if (it.isEmpty) {
                                        /*
                                    *Query for getting the result if the username is existed or not
                                     */
                                        val queryForUserName =
                                            Storeuser.whereEqualTo("userName", username).get()
                                                .addOnSuccessListener { it ->
                                                    if (it.isEmpty) {
                                                        /**
                                                         * Creating Id with email and password
                                                         *
                                                         * firebase authentication
                                                         *
                                                         */
                                                        auth.createUserWithEmailAndPassword(
                                                            emailTEXT,
                                                            passWORD
                                                        ).addOnCompleteListener(this) { task ->
                                                            if (task.isSuccessful) {
                                                                val user = auth.currentUser
                                                                uId = auth.currentUser?.uid.toString()

                                                                auth.currentUser?.sendEmailVerification()!!
                                                                    .addOnSuccessListener {
                                                                        Toast.makeText(
                                                                            this,
                                                                            "Please verify your mail",
                                                                            Toast.LENGTH_SHORT
                                                                        ).show()
                                                                    }.addOnFailureListener {
                                                                        Toast.makeText(
                                                                            this,
                                                                            "Failed to sent verification mail",
                                                                            Toast.LENGTH_SHORT
                                                                        ).show()
                                                                    }


                                                                val USERS = hashMapOf(
                                                                    "uid" to uId,
                                                                    "userName" to username,
                                                                    "phoneNumber" to phoneNumbeR,
                                                                    "Email" to emailTEXT,
                                                                    "lastName" to LNAME,
                                                                    "firstName" to FNAME
                                                                )

                                                                databse = FirebaseDatabase.getInstance()
                                                                    .getReference("UsersTable")
                                                                username = username.toLowerCase()
                                                                val User = UserInfo(
                                                                    FNAME,
                                                                    LNAME,
                                                                    emailTEXT,
                                                                    phoneNumbeR,
                                                                    username,
                                                                    uId
                                                                )
                                                                databse.child(username).setValue(User)
                                                                    .addOnSuccessListener {
//                                                        user.document(username).set(USERS)
                                                                        Storeuser.document(username)
                                                                            .set(USERS)

                                                                        /**
                                                                         * Clearing field
                                                                         */
                                                                        clearEverything()
                                                                        dialog.dismiss()
                                                                        val intent = Intent(
                                                                            this,
                                                                            Login::class.java
                                                                        )
                                                                        startActivity(intent)
                                                                        finish()
                                                                        Toast.makeText(
                                                                            this,
                                                                            "Successfully registered",
                                                                            Toast.LENGTH_SHORT
                                                                        ).show()
                                                                    }
                                                            }
                                                        }
                                                        /**
                                                         *
                                                         * Store the user data in firebase database
                                                         * starting code  for  storing data
                                                         **/
                                                    } else {
                                                        dialog.dismiss()
                                                        Toast.makeText(
                                                            this,
                                                            "This username already registered",
                                                            Toast.LENGTH_SHORT
                                                        ).show()
                                                    }
                                                }
                                    } else {
                                        dialog.dismiss()
                                        Toast.makeText(
                                            this,
                                            "This eamil already registered",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                        }
                        else{
                            dialog.dismiss()
                            Toast.makeText(this, "Email type is invalid", Toast.LENGTH_SHORT).show()
                        }
                    }
                    else{
                        dialog.dismiss()
                        Toast.makeText(this,"Phone number should be 11 digit",Toast.LENGTH_SHORT).show()
                    }
                }
                else {

                    dialog.dismiss()
                    Toast.makeText(this,"Password length should be more than 5 characters",Toast.LENGTH_SHORT).show()
                }

            }
            else{
                dialog.dismiss()
                Toast.makeText(this,"No field can be left empty",Toast.LENGTH_SHORT).show()
            }
        }

        AdminLoginPageButton.setOnClickListener{
            val intent=Intent(this, Login::class.java)
            startActivity(intent)
            finish()
        }

    }

    private fun clearEverything() {
        fName.text.clear()
        lName.text.clear()
        UserNameTextField.text.clear()
        emailSignUp.text.clear()
        passwordTextSignUp.text.clear()
        phoneNumber.text.clear()
    }
/*

        /***
        **** This is for checking a uesr exist or not
        ***/

    private fun checkUniqueUser(username: String):Boolean {
        var userNAME:String=username.toLowerCase()
        var emailTEXT:String=emailSignUp.text.toString()
        val reference=FirebaseDatabase.getInstance().getReference("UsersTable")


        reference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (datasnapshot in snapshot.children){
                    var string:String=datasnapshot.child("email").value.toString()
                    var string1:String=datasnapshot.child("userName").value.toString()
                    if(string.equals(userNAME)||string1.equals(emailTEXT)){
                        flag=20
                    }
                    else{
                        flag=30
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                println("Cancelled from th f vsd vsd vns vs v sv s\n\n")
            }
        })

        println("Flag of existing " )


        return false

    }

    /****
    **** Ended of checking a particular data exist or not
    ****/
 */

    private fun checking(): Boolean {
        if(phoneNumber.text.trim{it<=' '}.toString().isEmpty()||fName.text.trim{it<=' '}.toString().isEmpty()
            ||lName.text.trim{it<=' '}.toString().isEmpty()|| passwordTextSignUp.text.trim{it<=' '}.toString().isEmpty()){
            return false
        }
        return true
    }

}