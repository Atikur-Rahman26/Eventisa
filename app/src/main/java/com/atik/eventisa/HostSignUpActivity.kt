package com.atik.eventisa

import android.app.Dialog
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.atik.eventisa.Constants.Companion.HostUid
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_host_sign_up.*


class HostSignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_host_sign_up)

        registerBtn.setOnClickListener{
            if(checking()){
                getEveryDataAndStore()
            }
        }

        AdminLoginPageButton.setOnClickListener{
            val intent=Intent(this,AdminLoginPageActivity::class.java)
            startActivity(intent)
        }
    }

    private fun getEveryDataAndStore() {

        val dialog= Dialog(this)
        dialog.setContentView(R.layout.register_dialog)
        if(dialog.window!=null){
            dialog?.window!!.setBackgroundDrawable(ColorDrawable(0))
        }
        dialog.show()

        var firestoreRef=FirebaseFirestore.getInstance()
        val Storeuser=firestoreRef.collection("HOSTS")
        var auth=FirebaseAuth.getInstance()
        /*
        *Query for getting the result if the email is existed or not
         */
        if(checking()){
            var emailTEXT:String=emailSignUp.text.toString()
            var passWORD:String=passwordTextSignUp.text.toString()
            var FNAME:String=fName.text.toString()
            var LNAME:String=lName.text.toString()
            var phoneNumbeR:String=phoneNumber.text.toString()
            var username:String=UserNameTextField.text.toString()
            if(Patterns.EMAIL_ADDRESS.matcher(emailTEXT).matches()){

                /**
                 *for storing data to firestore
                 * Dialog showing
                 */
                dialog.show()


                val Storeuser=firestoreRef.collection("HOSTS")
                /*
                *Query for getting the result if the email is existed or not
                 */
                val queryForeEmail=Storeuser.whereEqualTo("Email",emailTEXT).get()
                    .addOnSuccessListener {
                            it1->
                        if(it1.isEmpty){
                            /*
                            *Query for getting the result if the username is existed or not
                             */
                            val queryForUserName=Storeuser.whereEqualTo("userName",username).get()
                                .addOnSuccessListener {
                                        it2->
                                    if(it2.isEmpty){

                                        val userStore=firestoreRef.collection("USERS")
                                        userStore.whereEqualTo("Email",emailTEXT).get().addOnSuccessListener {
                                            it3->
                                            if(it3.isEmpty){

                                                userStore.whereEqualTo("userName",username).get().addOnSuccessListener {
                                                    it4->
                                                    if(it4.isEmpty){
                                                        auth.createUserWithEmailAndPassword(emailTEXT,passWORD).addOnCompleteListener(this){task->
                                                            if(task.isSuccessful){
                                                                val user=auth.currentUser
                                                                HostUid =auth.currentUser?.uid.toString()

                                                                auth.currentUser?.sendEmailVerification()!!.addOnSuccessListener {
                                                                    Toast.makeText(this,"Please verify your mail",Toast.LENGTH_SHORT).show()
                                                                }.addOnFailureListener{
                                                                    Toast.makeText(this,"Failed to sent verification mail",Toast.LENGTH_SHORT).show()
                                                                }


                                                                val USERS=hashMapOf(
                                                                    "uid" to HostUid,
                                                                    "userName" to username,
                                                                    "phoneNumber" to phoneNumbeR,
                                                                    "Email" to emailTEXT,
                                                                    "lastName" to LNAME,
                                                                    "firstName" to FNAME
                                                                )

                                                                var databse=FirebaseDatabase.getInstance().getReference("HostsTable")
                                                                username=username.toLowerCase()
                                                                val User=AddHostInfro(FNAME,LNAME,emailTEXT,phoneNumbeR,username,
                                                                    HostUid)
                                                                databse.child(username).setValue(User).addOnSuccessListener {
//                                                        user.document(username).set(USERS)
                                                                    Storeuser.document(username).set(USERS)

                                                                    /**
                                                                     * Clearing field
                                                                     */
                                                                    clearEverything()
                                                                    dialog.dismiss()
                                                                    val intent=Intent(this,AdminLoginPageActivity::class.java)
                                                                    startActivity(intent)
                                                                    finish()
                                                                }
                                                            }
                                                        }
                                                    }
                                                    else{
                                                        dialog.dismiss()
                                                        Toast.makeText(this@HostSignUpActivity,"This email is already registered as an user",Toast.LENGTH_SHORT).show()
                                                    }
                                                }

                                            }
                                            else{
                                                dialog.dismiss()
                                                Toast.makeText(this@HostSignUpActivity,"This email is already exist as an user",Toast.LENGTH_SHORT).show()
                                            }
                                        }

                                    }
                                    else{
                                        dialog.dismiss()
                                        Toast.makeText(this,"This username already registered",Toast.LENGTH_SHORT).show()
                                    }
                                }
                        }
                        else{
                            dialog.dismiss()
                            Toast.makeText(this,"This eamil already registered",Toast.LENGTH_SHORT).show()
                        }
                    }
            }
            else{
                dialog.dismiss()
                Toast.makeText(this,"Email type is invalid",Toast.LENGTH_SHORT).show()
            }

        }
        else{
            dialog.dismiss()
            Toast.makeText(this,"No field can be left empty",Toast.LENGTH_SHORT).show()
        }

}


    private fun checking(): Boolean {
        if(phoneNumber.text.trim{it<=' '}.toString().isEmpty()||fName.text.trim{it<=' '}.toString().isEmpty()
            ||lName.text.trim{it<=' '}.toString().isEmpty()|| passwordTextSignUp.text.trim{it<=' '}.toString().isEmpty()){
            return false
        }
        return true
    }

    private fun clearEverything() {
        fName.text.clear()
        lName.text.clear()
        UserNameTextField.text.clear()
        emailSignUp.text.clear()
        passwordTextSignUp.text.clear()
        phoneNumber.text.clear()
    }
}