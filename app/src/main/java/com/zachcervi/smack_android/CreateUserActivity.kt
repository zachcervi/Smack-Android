package com.zachcervi.smack_android

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_create_user.*
import java.util.*

class CreateUserActivity : AppCompatActivity() {

    var userAvatar = "profileDefault"
    var avatarColor = "[0.5, 0.5, 0.5, 1]"
    val random = Random()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_user)
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
        val r = random.nextInt(255)
        val g = random.nextInt(255)
        val b = random.nextInt(255)

        createAvatarImageView.setBackgroundColor(Color.rgb(r,g,b))

        val savedR = r.toDouble() / 255
        val savedG = g.toDouble() /255
        val savedB = g.toDouble() / 255

        avatarColor = "[$savedR, $savedG, $savedB, 1]" //API format
    }

    fun generateColorClicked(view: View){

    }
}
