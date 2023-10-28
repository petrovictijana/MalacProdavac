package com.example.batmobile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.RadioButton

class RegisterStep2Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_step2)

        val constraintLayout = findViewById<LinearLayout>(R.id.linearLayout2)
        val kupacRadioButton = constraintLayout.findViewById<RadioButton>(R.id.Kupac)
        val prodavacRadioButton = constraintLayout.findViewById<RadioButton>(R.id.Prodavac)
        val dostavljacRadioButton = constraintLayout.findViewById<RadioButton>(R.id.Dostavljac)

        val kupacOptionsLayout = findViewById<LinearLayout>(R.id.kupacOptionsLayout)
        val prodavacOptionsLayout = findViewById<LinearLayout>(R.id.prodavacOptionsLayout)
        val dostavljacOptionsLayout = findViewById<LinearLayout>(R.id.dostavljacOptionsLayout)


        kupacRadioButton.setOnClickListener {
            kupacOptionsLayout.visibility = View.VISIBLE
            dostavljacOptionsLayout.visibility=View.GONE
            prodavacOptionsLayout.visibility = View.GONE
        }

        prodavacRadioButton.setOnClickListener {
            kupacOptionsLayout.visibility = View.GONE
            dostavljacOptionsLayout.visibility=View.GONE
            prodavacOptionsLayout.visibility = View.VISIBLE
        }

        dostavljacRadioButton.setOnClickListener{
            kupacOptionsLayout.visibility = View.GONE
            dostavljacOptionsLayout.visibility=View.VISIBLE
            prodavacOptionsLayout.visibility = View.GONE
        }
    }
    fun goToRegisterActivity(view:View){
        val intent: Intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
        finish()
    }
    fun goToRegisterStep3Activity(view:View){
        val intent: Intent = Intent(this, RegisterStep3Activity::class.java)
        startActivity(intent)
        finish()
    }
}