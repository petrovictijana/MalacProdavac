package com.example.batmobile.services

import android.widget.EditText

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
        fun validateEmail(email: String): String {
            val email_text = email
            if(email_text.isEmpty()){
                return "*Morate uneti email adresu"
            }
            else if(!regexEmailValidationPattern(email_text)){
                return "*Uneta email adresa nije ispravna"
            }
            else {
                return "true"
            }
        }

        private fun regexPasswordValidationPattern(password:String): Boolean {
            val regexString: String = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+\$"
            val regex = Regex(regexString)
            return regex.matches(password)
        }

        fun validatePassword(password: String): String {
            val password_text = password
            if(password_text.isEmpty()){
                return "*Morate uneti neku šifru"
            }
            else if(!regexPasswordValidationPattern(password_text)){
                return "*Šifra mora sadržati velika, mala slova i brojeve"
            }
            else {
                return "true"
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