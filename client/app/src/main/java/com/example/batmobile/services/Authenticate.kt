package com.example.batmobile.services

import android.widget.EditText
import android.widget.Toast

class Authenticate {
    companion object{
        private fun regexEmailValidationPattern(email:String): Boolean {
            val regexString: String =
                "([a-zA-Z0-9]+(?:[._+-][a-zA-Z0-9]+)*)@([a-zA-Z0-9]+(?:[.-][a-zA-Z0-9]+)*[.][a-zA-Z]{2,})"
            val regex = Regex(regexString)
            if (email.matches(regex))
                return true
            return false
        }
        private fun validateEmail(email:EditText): Boolean {
            val email_text = email.text.toString()
            if(email_text.isEmpty()){
                email.error = "Email cannot be empty"
                return false
            }
            else if(!regexEmailValidationPattern(email_text)){
                email.error = "Please enter a valid email"
                return false
            }
            else {
                email.error = null;
                return true
            }
        }
        private fun validateBasicInput(password: EditText): Boolean {
            val password_text = password.text.toString()

            if(password_text.isEmpty()){
                password.error = "Password cannot be empty"
                return false
            }
            else {
                password.error = null;
                return true
            }
        }
        fun authenticateLogin(username: EditText, password: EditText):Boolean{
            if( !validateBasicInput(username) || !validateBasicInput(password)){
                return false
            }
            return true
        }
    }
}