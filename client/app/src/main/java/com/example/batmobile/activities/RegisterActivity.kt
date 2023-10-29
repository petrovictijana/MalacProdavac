package com.example.batmobile.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.batmobile.R
import com.example.batmobile.services.Authenticate
import com.example.batmobile.models.User

class RegisterActivity : AppCompatActivity() {

    lateinit var name: EditText
    lateinit var lastname: EditText
    lateinit var username: EditText
    lateinit var email: EditText
    lateinit var password: EditText
    lateinit var password_confirm: EditText

    lateinit var error_name: TextView
    lateinit var error_lastname: TextView
    lateinit var error_username: TextView
    lateinit var error_email: TextView
    lateinit var error_password: TextView
    lateinit var error_password_confirm: TextView

    lateinit var btn_nastavi: Button

    var flagName: Boolean = false
    var flagLastname: Boolean = false
    var flagUsername: Boolean = false
    var flagEmail: Boolean = false
    var flagPassword: Boolean = false
    var flagPassword_confirm: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        name =                      findViewById<EditText>(R.id.name)
        lastname =                  findViewById<EditText>(R.id.lastname)
        username =                  findViewById<EditText>(R.id.username)
        email =                     findViewById<EditText>(R.id.email)
        password =                  findViewById<EditText>(R.id.password)
        password_confirm =          findViewById<EditText>(R.id.password_confirm)

        error_name =                findViewById<TextView>(R.id.error_name)
        error_lastname =            findViewById<TextView>(R.id.error_lastname)
        error_username =            findViewById<TextView>(R.id.error_username)
        error_email =               findViewById<TextView>(R.id.error_email)
        error_password =            findViewById<TextView>(R.id.error_password)
        error_password_confirm =    findViewById<TextView>(R.id.error_password_confirm)

        btn_nastavi =               findViewById<Button>(R.id.nastavi)

        name.setOnFocusChangeListener   { _, hasFocus -> if (!hasFocus)                                         { validateIsHaveInput(name,"name", error_name) } }
        name.setOnEditorActionListener  { _, actionId, _ -> if (actionId == EditorInfo.IME_ACTION_DONE)         { validateIsHaveInput(name,"name", error_name) }; false }

        lastname.setOnFocusChangeListener   { _, hasFocus -> if (!hasFocus)                                     { validateIsHaveInput(lastname,"lastname", error_lastname) } }
        lastname.setOnEditorActionListener  { _, actionId, _ -> if (actionId == EditorInfo.IME_ACTION_DONE)     { validateIsHaveInput(lastname,"lastname", error_lastname) }; false }

        username.setOnFocusChangeListener   { _, hasFocus -> if (!hasFocus)                                     { validateIsHaveInput(username,"username", error_username) } }
        username.setOnEditorActionListener  { _, actionId, _ -> if (actionId == EditorInfo.IME_ACTION_DONE)     { validateIsHaveInput(username,"username", error_username) }; false }

        email.setOnFocusChangeListener  { _, hasFocus -> if (!hasFocus)                                   { validateEmail(email, error_email) } }
        email.setOnEditorActionListener { _, actionId, _ -> if (actionId == EditorInfo.IME_ACTION_DONE)  { validateEmail(email, error_email) }; false }

        password.setOnFocusChangeListener   { _, hasFocus -> if (!hasFocus)                                   { validatePassword(password, error_password) } }
        password.setOnEditorActionListener  { _, actionId, _ -> if (actionId == EditorInfo.IME_ACTION_DONE)  { validateEmail(password, error_password) }; false }

        password_confirm.setOnFocusChangeListener   { _, hasFocus -> if (!hasFocus)                                   { validateConfirmPassword(password, password_confirm, error_password_confirm) } }
        password_confirm.setOnEditorActionListener  { _, actionId, _ -> if (actionId == EditorInfo.IME_ACTION_DONE)  { validateConfirmPassword(password, password_confirm, error_password_confirm) }; false }

    }
    fun goToTheMainActivity(view: View){
        val intent: Intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun goToStep_2(view: View){
        if(view.isEnabled == true){
            val intent: Intent = Intent(this, RegisterStep2Activity::class.java)
            val user = User(name.text.toString(),
                            lastname.text.toString(),
                            username.text.toString(),
                            email.text.toString(),
                            password.text.toString())
            intent.putExtra("user", user)
            startActivity(intent)
            finish()
        }
    }

    fun validateIsHaveInput(text: EditText, type:String, error_text: TextView){
        //Proverava da li ima neki input, 2 ili vise karaktera
        if(text.text.toString().length >= 2) {
            when(type){
                "name" ->       { flagName = true ;         error_text.visibility = View.INVISIBLE }
                "lastname" ->   { flagLastname = true ;     error_text.visibility = View.INVISIBLE }
                "username" ->   { flagUsername = true ;     error_text.visibility = View.INVISIBLE }
            }
        }
        else{
            when(type){
                "name" ->       { flagName = false ;        error_text.visibility = View.VISIBLE  }
                "lastname" ->   { flagLastname = false ;    error_text.visibility = View.VISIBLE  }
                "username" ->   { flagUsername = false ;    error_text.visibility = View.VISIBLE  }
            }
        }
        validateAllInputs()
    }

    fun validateEmail(text: EditText, error_text: TextView){
        val response = Authenticate.validateEmail(text.text.toString())
        when(response){
            "*Morate uneti email adresu"->          { flagEmail = false; error_text.setText(response); error_text.visibility = View.VISIBLE}
            "*Uneta email adresa nije ispravna"->   { flagEmail = false; error_text.setText(response); error_text.visibility = View.VISIBLE}
            "true"->                                { flagEmail = true; error_text.setText(response); error_text.visibility = View.INVISIBLE}
        }
        validateAllInputs()
    }
    fun validatePassword(text: EditText, error_text: TextView){
        val response = Authenticate.validatePassword(text.text.toString())
        when(response){
            "*Morate uneti neku šifru"->                            { flagPassword = false; error_text.setText(response); error_text.visibility = View.VISIBLE}
            "*Šifra mora sadržati velika, mala slova i brojeve"->   { flagPassword = false; error_text.setText(response); error_text.visibility = View.VISIBLE}
            "true"->                                                { flagPassword = true; error_text.setText(response); error_text.visibility = View.INVISIBLE}
        }
        validateAllInputs()
    }

    fun validateConfirmPassword(password: EditText, password_configm: EditText,error_text: TextView){
        if(password.text.toString().length > 0){
            if(!password_configm.text.toString().equals(password.text.toString())){ error_text.visibility = View.VISIBLE ; flagPassword_confirm = false }
            else{ error_text.visibility = View.INVISIBLE ; flagPassword_confirm = true }
        }
        else{ error_text.visibility = View.INVISIBLE ; flagPassword_confirm = false }
        validateAllInputs()
    }

    fun validateAllInputs(){
        if(!flagName || !flagLastname || !flagUsername || !flagEmail || !flagPassword || !flagPassword_confirm){
            btn_nastavi.isEnabled = false
            btn_nastavi.setBackgroundResource(R.drawable.full_fill_button_disabled)
        }
        else{
            btn_nastavi.isEnabled = true
            btn_nastavi.setBackgroundResource(R.drawable.full_fill_button)
        }
    }
    fun showPassword(view: View){
        if(view.id.equals(R.id.eye_password))
            Authenticate.showPassword(view, password)
        else
            Authenticate.showPassword(view, password_confirm)
    }

}