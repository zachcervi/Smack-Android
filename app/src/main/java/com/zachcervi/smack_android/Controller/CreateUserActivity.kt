package com.zachcervi.smack_android.Controller

import Services.AuthService
import Services.UserDataService
import Utilities.BROADCAST_USER_DATA_CHANGE
import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.LocalBroadcastManager
import android.view.View
import android.widget.Toast
import com.zachcervi.smack_android.R
import kotlinx.android.synthetic.main.activity_create_user.*
import java.util.*

class CreateUserActivity : AppCompatActivity() {

    var userAvatar = "profileDefault"
    var avatarColor = "[0.5, 0.5, 0.5, 1]"
    val random = Random()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_user)
        createSpinner.visibility = View.INVISIBLE
    }

    fun generateUserAvatar(view: View){

        val color = random.nextInt(2)
        val avatar = random.nextInt(28)

        if(color == 0)
        {
            userAvatar = "light$avatar"
        }
        else
        {
            userAvatar = "dark$avatar"
        }
        val resourceId = resources.getIdentifier(userAvatar, "drawable", packageName)
        createAvatarImageView.setImageResource(resourceId)
    }

    fun createUserClicked(view: View){
        enableSpinner(true)
       val userName = createUserNameTxt.text.toString()
        val email = createEmailTxt.text.toString()
        val password = createPasswordTxt.text.toString()

        if(userName.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()){
            AuthService.registerUser( email, password){
                    registerSuccess ->
                if(registerSuccess){
                    AuthService.loginUser(email, password){
                            loginSuccess ->
                        if(loginSuccess){
                            AuthService.createUser( userName, email, userAvatar, avatarColor) {createSuccess ->
                                if(createSuccess){
                                    val userDataChange = Intent(BROADCAST_USER_DATA_CHANGE)
                                    LocalBroadcastManager.getInstance(this).sendBroadcast(userDataChange)
                                    enableSpinner(false)
                                    finish()
                                }else {
                                    println("Create account unsuccessful")
                                    errorToast()
                                }
                            }
                        }else {
                            println("Login Unsuccessful")
                            errorToast()
                        }
                    }
                } else {
                    errorToast()
                }
            }
        }
        else {
            Toast.makeText(this, "Make sure user name, email and password are not empty.", Toast.LENGTH_LONG).show()
            enableSpinner(false)
        }


        
    }
    private fun errorToast(){
        Toast.makeText(this, "Something went wrong try again.", Toast.LENGTH_LONG).show()
        enableSpinner(false)
    }

    fun generateColorClicked(view: View){
        val r = random.nextInt(255)
        val g = random.nextInt(255)
        val b = random.nextInt(255)

        createAvatarImageView.setBackgroundColor(Color.rgb(r,g,b))

        val savedR = r.toDouble() / 255
        val savedG = g.toDouble() /255
        val savedB = g.toDouble() / 255

        avatarColor = "[$savedR, $savedG, $savedB, 1]" //API format

    }

    fun enableSpinner(enable: Boolean){
        if(enable){
            createSpinner.visibility = View.VISIBLE
        }else {
            createSpinner.visibility = View.INVISIBLE
        }

        createCreateUserBtn.isEnabled = !enable
        createAvatarImageView.isEnabled = !enable
        backgroundColorBtn.isEnabled = !enable
    }
}
