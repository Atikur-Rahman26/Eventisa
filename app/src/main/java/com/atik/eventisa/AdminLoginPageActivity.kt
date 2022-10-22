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
                /**
                 *
                 * Passing data to the fun AdminIsValid
                 * And
                 * This is for checking that the Admin is valid or not
                 *
                 */
                AdminIsValid(adminUserName,adminEmailId,adminPassword)
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


    private fun AdminIsValid(adminUserName: String, adminEmailId: String, adminPassword: String) {
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


                                            val intent=Intent(this@AdminLoginPageActivity,AddEventActivity::class.java)
                                            startActivity(intent)
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
                    Toast.makeText(this@AdminLoginPageActivity,"Admin name or email or password wrong",Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

    }
}