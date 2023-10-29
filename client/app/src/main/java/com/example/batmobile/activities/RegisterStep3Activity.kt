package com.example.batmobile.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.example.batmobile.R
import com.example.batmobile.models.User

class RegisterStep3Activity : AppCompatActivity() {

    private lateinit var name: TextView;
    private lateinit var lastname: TextView;
    private lateinit var username: TextView;
    private lateinit var email: TextView;
    private lateinit var password: TextView;

    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_step3)

        name =      findViewById<TextView>(R.id.user_nameKupac)
        lastname =  findViewById<TextView>(R.id.user_lastnameKupac)
        username =  findViewById<TextView>(R.id.user_usernameKupac)
        email =     findViewById<TextView>(R.id.user_emailKupac)
        password =  findViewById<TextView>(R.id.user_passwordKupac)

        user = intent.getParcelableExtra<User>("user")!!

        if(user!=null){
            name.setText(user.name)
            lastname.setText(user.lastname)
            username.setText(user.username)
            email.setText(user.email)
            password.setText(user.password)
        }

        val selectedOption = intent.getStringExtra("selectedOption")

        when (selectedOption) {
            "Kupac" -> {
                // Prikazi layout za kupca
                val kupacLayout = findViewById<LinearLayout>(R.id.kupacLayout)
                kupacLayout.visibility = View.VISIBLE
            }
            "Dostavljač" -> {
                // Prikazi layout za dostavljača
                val dostavljacLayout = findViewById<LinearLayout>(R.id.dostavljacLayout)
                dostavljacLayout.visibility = View.VISIBLE
            }
            "Mali prodavac" -> {
                // Prikazi layout za malog prodavca
                val prodavacLayout = findViewById<LinearLayout>(R.id.prodavacLayout)
                prodavacLayout.visibility = View.VISIBLE
            }
        }


    }

    fun goToRegisterstep2Activity(view: View){
        val intent: Intent = Intent(this, RegisterStep2Activity::class.java)
        startActivity(intent)
        finish()
    }
}