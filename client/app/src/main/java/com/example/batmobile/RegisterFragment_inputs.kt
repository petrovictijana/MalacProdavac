package com.example.batmobile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import com.example.batmobile.network.ApiClient
import com.example.batmobile.network.Config
import com.example.batmobile.services.Authenticate
import org.json.JSONObject
import androidx.fragment.app.activityViewModels
import com.example.batmobile.models.User

class RegisterFragment_inputs : Fragment() {

    lateinit var back: ImageView

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

    lateinit var apiClient: ApiClient

    lateinit var eye_password: ImageView
    lateinit var eye_password_confirm: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_register_inputs, container, false)

        apiClient = ApiClient(requireActivity())

        val userViewModel: UserViewModel by activityViewModels()

        back =                      view.findViewById<ImageView>(R.id.back)
        back.setOnClickListener{ }

        name =                      view.findViewById<EditText>(R.id.name)
        lastname =                  view.findViewById<EditText>(R.id.lastname)
        username =                  view.findViewById<EditText>(R.id.username)
        email =                     view.findViewById<EditText>(R.id.email)
        password =                  view.findViewById<EditText>(R.id.password)
        password_confirm =          view.findViewById<EditText>(R.id.password_confirm)

        error_name =                view.findViewById<TextView>(R.id.error_name)
        error_lastname =            view.findViewById<TextView>(R.id.error_lastname)
        error_username =            view.findViewById<TextView>(R.id.error_username)
        error_email =               view.findViewById<TextView>(R.id.error_email)
        error_password =            view.findViewById<TextView>(R.id.error_password)
        error_password_confirm =    view.findViewById<TextView>(R.id.error_password_confirm)

        eye_password =              view.findViewById<ImageView>(R.id.eye_password)
        eye_password_confirm =              view.findViewById<ImageView>(R.id.eye_password_confirm)

        btn_nastavi =               view.findViewById<Button>(R.id.nastavi)
        btn_nastavi.setOnClickListener{
            val user = User(name.text.toString(),
                lastname.text.toString(),
                username.text.toString(),
                email.text.toString(),
                password.text.toString())
            userViewModel.user = user
            Navigation.findNavController(view).navigate(R.id.navigate_step1ToStep2)
        }

        name.setOnFocusChangeListener   { _, hasFocus -> if (!hasFocus)                                         { validateIsHaveInput(name,"name", error_name) } }
        name.setOnEditorActionListener  { _, actionId, _ -> if (actionId == EditorInfo.IME_ACTION_DONE)         { validateIsHaveInput(name,"name", error_name) }; false }

        lastname.setOnFocusChangeListener   { _, hasFocus -> if (!hasFocus)                                     { validateIsHaveInput(lastname,"lastname", error_lastname) } }
        lastname.setOnEditorActionListener  { _, actionId, _ -> if (actionId == EditorInfo.IME_ACTION_DONE)     { validateIsHaveInput(lastname,"lastname", error_lastname) }; false }

        username.setOnFocusChangeListener   { _, hasFocus   ->      checkExisting("username",username.text.toString());    if (!hasFocus){ validateIsHaveInput(username,"username", error_username) } }
        username.setOnEditorActionListener  { _, actionId, _ ->     checkExisting("username",username.text.toString());   if (actionId == EditorInfo.IME_ACTION_DONE)     { validateIsHaveInput(username,"username", error_username) }; false }

        email.setOnFocusChangeListener  { _, hasFocus    ->     checkExisting("email",email.text.toString());        if (!hasFocus) { validateEmail(email, error_email)  } }
        email.setOnEditorActionListener { _, actionId, _ ->     checkExisting("email",email.text.toString()) ;       if (actionId == EditorInfo.IME_ACTION_DONE)  { validateEmail(email, error_email) }; false }

        password.setOnFocusChangeListener   { _, hasFocus ->    if (!hasFocus)                                      { validatePassword(password, error_password) } }
        password.setOnEditorActionListener  { _, actionId, _ -> if (actionId == EditorInfo.IME_ACTION_DONE)         { validateEmail(password, error_password) }; false }

        password_confirm.setOnFocusChangeListener   { _, hasFocus -> if (!hasFocus)                                   { validateConfirmPassword(password, password_confirm, error_password_confirm) } }
        password_confirm.setOnEditorActionListener  { _, actionId, _ -> if (actionId == EditorInfo.IME_ACTION_DONE)  { validateConfirmPassword(password, password_confirm, error_password_confirm) }; false }

        eye_password.setOnClickListener{showPasswordOg(eye_password)}
        eye_password_confirm.setOnClickListener{showPasswordOg(eye_password_confirm)}

        return view

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

    fun showPasswordOg(view: View){
        if(view.id.equals(R.id.eye_password))
            Authenticate.showPassword(view, password)
        else
            Authenticate.showPassword(view, password_confirm)
    }

    fun checkExisting(type:String, value:String){
        val jsonObject: JSONObject = JSONObject()

        jsonObject.put("username", "")
        jsonObject.put("email", "")

        if(type.equals("username"))
            jsonObject.put("username", value)
        if(type.equals("email"))
            jsonObject.put("email", value)

        var url:String = Config.ip_address+":"+ Config.port + "/registration/step1"
        println(url)
        println("SALJEM"+jsonObject)

        apiClient.sendPostRequestWithJSONObjectWithJsonResponse(
            url,
            jsonObject,
            { response ->
                println(response)
                when (type) {
                    "username"->{   flagUsername = true;
                        error_username.setText("");
                        error_username.visibility = View.INVISIBLE
                    }
                    "email" -> {
                        flagEmail = true;
                        error_email.setText("");
                        error_email.visibility = View.INVISIBLE
                    }
                }
            },
            { error ->
                when (type) {
                    "username"->{   flagUsername = false;
                        error_username.setText("*Korisničko ime se već koristi");
                        error_username.visibility = View.VISIBLE
                    }
                    "email" -> {
                        flagEmail = false;
                        error_email.setText("*Ovaj email se već koristi");
                        error_email.visibility = View.VISIBLE
                    }
                }
            }
        )
    }
}