package com.example.batmobile.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.batmobile.R

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
    }
    fun goToTheMainActivity(view: View){
        val intent: Intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun goToStep_2(view: View){
        val intent: Intent = Intent(this, RegisterStep2Activity::class.java)
        startActivity(intent)
        finish()
    }
}