package com.atik.eventisa.Activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.atik.eventisa.R
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_user_profile.*

class UserProfileActivity : AppCompatActivity() {
    private lateinit var db:FirebaseFirestore
    var i:Int=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

//        val email=getString("email")
        val sharedPreferences=this?.getPreferences(Context.MODE_PRIVATE)?:return
        val isLogin=sharedPreferences.getString("Email","1")


        if(isLogin=="1"){
            var email=intent.getStringExtra("email")
            if(email!=null){
                setEmail(email)
                with(sharedPreferences.edit())
                {
                    putString("Email",email)
                    apply()
                }
            }
            else{
                val intent=Intent(this, Login::class.java)
                startActivity(intent)
                finish()
            }
        }
        else{
            setEmail(isLogin)
        }
        if(i==0){
            i=1
            replaceFragment(Home())
        }

        bottomNavigationView.setOnItemSelectedListener{
            when(it.itemId){
                R.id.Home -> replaceFragment(Home())
                R.id.Profile -> replaceFragment(Profile())

                R.id.Favourite -> replaceFragment(Favourite())
                else ->{

                }
            }
            true
        }
    }

    private fun setEmail(login: String?) {
        db= FirebaseFirestore.getInstance()
        if (login != null) {
            val ref=db.collection("USERS").orderBy("Email").equals(login)

        }
    }

    private fun replaceFragment(fragment: Fragment){
        val fragmentManager=supportFragmentManager
        val fragmentTransaction=fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout,fragment)
        fragmentTransaction.commit()
    }
}