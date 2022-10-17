package com.atik.eventisa

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_admin_login_page.*
import kotlinx.android.synthetic.main.activity_admin_login_page.EmailtAdmin
import kotlinx.android.synthetic.main.activity_sign_up.*

class AdminLoginPageActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_login_page)

        AdminLoginButton.setOnClickListener{
            if(checking()){
                var adminUserName:String=AdminName.text.toString()
                var adminEmailId:String=EmailtAdmin.text.toString()
                var adminPassword:String=PassWordAdmin.text.toString()
                if(AdminIsValid(adminUserName,adminEmailId,adminPassword)){
                    val intent=Intent(this,AddEventActivity::class.java)
                    startActivity(intent)
                }
                else{
                    Toast.makeText(this,"Invalid Admin Name or email or password",Toast.LENGTH_SHORT).show()
                }
            }
            else{
                Toast.makeText(this,"No field can be left empty",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun checking(): Boolean {

        if(AdminName.text.toString().isNotEmpty()&&EmailtAdmin.text.toString().isNotEmpty()
            &&PassWordAdmin.text.toString().isNotEmpty()){
            return true
        }

        return false
    }

    var a:Int=0
    var b:Int=0
    var c:Int=0

    private fun AdminIsValid(adminUserName: String, adminEmailId: String, adminPassword: String): Boolean {
        var AName:String=adminUserName
        var AEmail:String=adminEmailId
        var APass:String=adminPassword

        val reference=FirebaseDatabase.getInstance().getReference("AdminTable")
        val checkAdminName=reference.orderByChild("admin").equalTo(AName)
        val checkAdminEmail=reference.orderByChild("email").equalTo(AEmail)
        val checkAdminPassword=reference.orderByChild("password").equalTo(APass)

        checkAdminName.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    a=1;
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        checkAdminEmail.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    b=1
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        checkAdminPassword.addListenerForSingleValueEvent(object : ValueEventListener{

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    c=1
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        if(a==1&&b==1&&c==1){
            return true
        }
        return false

    }
}