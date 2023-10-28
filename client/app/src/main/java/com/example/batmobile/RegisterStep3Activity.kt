package com.example.batmobile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class RegisterStep3Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_step3)
    }
    fun goToRegisterstep2Activity(view: View){
        val intent: Intent = Intent(this, RegisterStep2Activity::class.java)
        startActivity(intent)
        finish()
    }
}