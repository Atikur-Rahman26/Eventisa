package com.atik.eventisa

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.get
import androidx.core.view.isNotEmpty
import com.atik.eventisa.Constants.Companion.fname
import com.atik.eventisa.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.database.core.Tag
import kotlinx.android.synthetic.main.activity_admin_login_page.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_sign_up.*
import java.util.regex.Pattern

class signUpActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding
    private lateinit var databse:DatabaseReference
    private lateinit var auth: FirebaseAuth
    private lateinit var firebaseUser:FirebaseUser
    private var flag:Int= 10
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        var country:String


        val languages=resources.getStringArray(R.array.Languages)
        val arrayAdapter=ArrayAdapter(this,R.layout.country_item,languages)
        arrayAdapter.setDropDownViewResource(R.layout.country_item)
        CountryDropDownMenu.adapter=arrayAdapter

        auth=FirebaseAuth.getInstance()
        var a=0

        registerBtn.setOnClickListener{

            country=CountryDropDownMenu.selectedItem.toString()



            if(checking()){
                var emailTEXT:String=emailSignUp.text.toString()
                var passWORD:String=passwordTextSignUp.text.toString()
                var FNAME:String=fName.text.toString()
                var LNAME:String=lName.text.toString()
                var phoneNumbeR:String=phoneNumber.text.toString()
                var username:String=UserNameTextField.text.toString()
                if(Patterns.EMAIL_ADDRESS.matcher(emailTEXT).matches()){

                    if(a==0){
                        checkUniqueUser(username)
                        a++
                    }
                    if(checkUniqueUser(username)){

                        /*
                        * Creating Id with email and password
                        *
                        * firebase authentication
                        * */
                        auth.createUserWithEmailAndPassword(emailTEXT,passWORD)

                        /*
                        *
                        * Store the user data in firebase database
                        *
                        *
                        * starting code  for  storing data  */
                        databse=FirebaseDatabase.getInstance().getReference("UsersTable")
                        username=username.toLowerCase()
                        val User=UserInfo(FNAME,LNAME,emailTEXT,country,phoneNumbeR,username)
                        databse.child(username).setValue(User).addOnSuccessListener {
                            fName.text.clear()
                            lName.text.clear()
                            UserNameTextField.text.clear()
                            emailSignUp.text.clear()
                            passwordTextSignUp.text.clear()
                            phoneNumber.text.clear()
                            Toast.makeText(this,"Successfully registered",Toast.LENGTH_SHORT).show()

                        }
                        /*
                        * Ended code for storing data
                        */
                        Toast.makeText(this,"Successfully created ",Toast.LENGTH_SHORT).show()
                        val intent=Intent(this,Login::class.java)
                        startActivity(intent)
                    }
                    else{
                        Toast.makeText(this,"This username already exist!!",Toast.LENGTH_SHORT).show()
                    }
                }
                else{
                    Toast.makeText(this,"Email type is invalid",Toast.LENGTH_SHORT).show()
                }

            }
            else{
                Toast.makeText(this,"No field can't be left empty",Toast.LENGTH_SHORT).show()
            }
        }

        AdminLoginPageButton.setOnClickListener{
            val intent=Intent(this,AdminLoginPageActivity::class.java)
            startActivity(intent)
        }

    }

    private fun checkUniqueUser(username: String):Boolean {
        var userNAME:String=username.toLowerCase()
        val reference=FirebaseDatabase.getInstance().getReference("UsersTable")
        val checkUser=reference.orderByChild("userName").equalTo(userNAME)


        checkUser.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    flag=20
                }
                else{
                    flag=30
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        println("Flag of existing "+flag )

        if(flag==30){
            return true
        }
        return false

    }

    private fun checking(): Boolean {
        if(phoneNumber.text.trim{it<=' '}.toString().isEmpty()||fName.text.trim{it<=' '}.toString().isEmpty()
            ||lName.text.trim{it<=' '}.toString().isEmpty()|| passwordTextSignUp.text.trim{it<=' '}.toString().isEmpty()){
            return false
        }
        return true
    }

}