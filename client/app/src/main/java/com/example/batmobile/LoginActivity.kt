package com.example.batmobile

import android.content.Intent
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.core.content.ContextCompat

class LoginActivity : AppCompatActivity() {
    var input_password:EditText? = null;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val button_switch_Prijavi_se = findViewById<Button>(R.id.switch_Prijavi_se)
        val button_switch_Registruj_se = findViewById<Button>(R.id.switch_Registruj_se)
        setRadiusOnButton(button_switch_Prijavi_se, 12f, 0f , 12f , 0f)
        setRadiusOnButton(button_switch_Registruj_se, 0f, 12f , 0f , 12f)
        this.input_password = findViewById<EditText>(R.id.password)
    }
    fun goToTheMainActivity(view:View){
        val intent: Intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
    fun goToRegisterActivity(view:View){
        val intent: Intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
        finish()
    }
    fun setRadiusOnButton(button: Button, topLeftRadius:Float, topRightRadius:Float, bottomLeftRadius:Float, bottomRightRadius:Float ){
        val originalDrawable = button.background.constantState?.newDrawable()   // kopiramo originalni drawable
        val shapeDrawable = originalDrawable?.mutate() as? GradientDrawable     // ovde obezbedimo da to bude mutable i kasnije se te izmene nece primeniti na ostale buttone koji dele isti background
        shapeDrawable?.cornerRadii = floatArrayOf(
            topLeftRadius, topLeftRadius,
            topRightRadius, topRightRadius,
            bottomRightRadius, bottomRightRadius,
            bottomLeftRadius, bottomLeftRadius
        )
        button.background = shapeDrawable
    }
    fun onEyeIconClick(view: View){
        this!!.input_password?.let { showPassword(view, it) }
    }

//  Ovo bi trebao neki servis da bude
    fun showPassword(view: View, input: EditText){
        val selectionStart = this.input_password!!.selectionStart
        val selectionEnd = this.input_password!!.selectionEnd

        val imageView = view as ImageView

        if (input.inputType == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD){
            input.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            imageView.setImageResource(R.drawable.icons8_not_visible_96___)
        }
        else{
            input.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            imageView.setImageResource(R.drawable.icons8_visible_96___)
        }

        input.setSelection(selectionStart, selectionEnd)
    }
}