package com.example.batmobile.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.batmobile.R
import com.example.batmobile.models.User
import com.example.batmobile.network.ApiClient
import com.example.batmobile.network.Config
import org.json.JSONArray
import org.json.JSONObject
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.ItemizedIconOverlay
import org.osmdroid.views.overlay.OverlayItem

class RegisterStep3Activity : AppCompatActivity() {

    private lateinit var selectedOption:String

    private lateinit var name: TextView;
    private lateinit var lastname: TextView;
    private lateinit var username: TextView;
    private lateinit var email: TextView;
    private lateinit var password: TextView;

    private lateinit var vehicle_auto: FrameLayout
    private lateinit var vehicle_motocikl: FrameLayout
    private lateinit var vehicle_kombi: FrameLayout
    private lateinit var vehicle_kamion: FrameLayout

    private lateinit var mapView: MapView

    private lateinit var user: User

    private lateinit var apiCall: ApiClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_step3)

        user = intent.getParcelableExtra<User>("user")!!

        selectedOption = intent.getStringExtra("selectedOption").toString()

        apiCall = ApiClient(this)

        when (selectedOption) {
            "Kupac" -> {
                // Prikazi layout za kupca
                val kupacLayout = findViewById<LinearLayout>(R.id.kupacLayout)
                kupacLayout.visibility = View.VISIBLE
                name =      findViewById<TextView>(R.id.user_nameKupac)
                lastname =  findViewById<TextView>(R.id.user_lastnameKupac)
                username =  findViewById<TextView>(R.id.user_usernameKupac)
                email =     findViewById<TextView>(R.id.user_emailKupac)
                password =  findViewById<TextView>(R.id.user_passwordKupac)
                setPreview()
            }
            "Dostavljač" -> {
                // Prikazi layout za dostavljača
                val dostavljacLayout = findViewById<LinearLayout>(R.id.dostavljacLayout)
                dostavljacLayout.visibility = View.VISIBLE
                name =      findViewById<TextView>(R.id.user_nameDostavljac)
                lastname =  findViewById<TextView>(R.id.user_lastnameDostavljac)
                username =  findViewById<TextView>(R.id.user_usernameDostavljac)
                email =     findViewById<TextView>(R.id.user_emailDostavljac)
                password =  findViewById<TextView>(R.id.user_passwordDostavljac)
                vehicle_auto = findViewById<FrameLayout>(R.id.auto)
                vehicle_motocikl = findViewById<FrameLayout>(R.id.motocikl)
                vehicle_kombi = findViewById<FrameLayout>(R.id.kombi)
                vehicle_kamion = findViewById<FrameLayout>(R.id.kamion)
                setPreview()
                setVehicle()
            }
            "Mali prodavac" -> {
                // Prikazi layout za malog prodavca
                val prodavacLayout = findViewById<LinearLayout>(R.id.prodavacLayout)
                prodavacLayout.visibility = View.VISIBLE
                name =      findViewById<TextView>(R.id.user_nameProdavac)
                lastname =  findViewById<TextView>(R.id.user_lastnameProdavac)
                username =  findViewById<TextView>(R.id.user_usernameProdavac)
                email =     findViewById<TextView>(R.id.user_emailProdavac)
                password =  findViewById<TextView>(R.id.user_passwordProdavac)
                mapView =   findViewById<MapView>(R.id.mapView)
                setPreview()
                setMap(user.latitude as Double, user.longitude as Double)
            }
        }
    }

    fun setPreview(){
        name.text = user.name
        lastname.text = user.lastname
        username.text = user.username
        email.text = user.email
        password.text = user.password
    }

    fun setVehicle(){
        val onlyVehicle:MutableMap<String, Boolean> = user.vehicle
        println()
        println(onlyVehicle)
        if(onlyVehicle["auto"] == true)
            vehicle_auto.visibility = View.VISIBLE
        if(onlyVehicle["motocikl"] == true)
            vehicle_motocikl.visibility = View.VISIBLE
        if(onlyVehicle["kombi"] == true)
            vehicle_kombi.visibility = View.VISIBLE
        if(onlyVehicle["kamion"] == true)
            vehicle_kamion.visibility = View.VISIBLE
    }

    fun setMap(latitude:Double, longitude:Double){
        val newPoint = GeoPoint(latitude, longitude)
        mapView.controller.setCenter(newPoint)
        mapView.controller.setZoom(13.0)
        // Dodavanje pina na tacnu lokaciju
        val items = ArrayList<OverlayItem>()
        val overlayItem = OverlayItem("Lokacija", "Lokacija domacinstva", newPoint)
        overlayItem.setMarker(ContextCompat.getDrawable(this, R.drawable.location_pin))
        items.add(overlayItem)

        val overlay = ItemizedIconOverlay<OverlayItem>(items, null, applicationContext)
        mapView.overlays.clear()
        mapView.overlays.add(overlay)
    }

    fun goToRegisterstep2Activity(view: View){
        val intent: Intent = Intent(this, RegisterStep2Activity::class.java)
        startActivity(intent)
        finish()
    }

    fun getUserVehicle():JSONArray{
        val licenceCategories = JSONArray()
        if(user.vehicle["auto"]==true)
            licenceCategories.put(1)
        if(user.vehicle["motocikl"]==true)
            licenceCategories.put(2)
        if(user.vehicle["kombi"]==true)
            licenceCategories.put(3)
        if(user.vehicle["kamion"]==true)
            licenceCategories.put(4)
        return licenceCategories
    }

    fun sendRequestForRegistration(view: View){
        val jsonObject: JSONObject = JSONObject()
        jsonObject.put("name",user.name)
        jsonObject.put("surname",user.lastname)
        jsonObject.put("username",user.username)
        jsonObject.put("password",user.password)
        jsonObject.put("email",user.email)
        jsonObject.put("picture","slika.png")
        when(selectedOption){
            "Kupac" -> {
                jsonObject.put("roleId",1)
            }
            "Dostavljač" -> {
                jsonObject.put("roleId",2)
                jsonObject.put("licenceCategories", getUserVehicle())
            }
            "Mali prodavac" -> {
                jsonObject.put("roleId",3)
                jsonObject.put("pib", user.pib)
                jsonObject.put("location", user.latitude.toString() + " " + user.longitude.toString())
            }
        }

        println(jsonObject)
        var url:String = Config.ip_address+":"+ Config.port + "/registration"
        println(url)
        apiCall.sendPostRequestWithJSONObjectWithStringResponse(
            url,
            jsonObject,
            { response -> println(response)
                Toast.makeText(this, response, Toast.LENGTH_LONG).show()
            },
            { error ->
                Toast.makeText(this, "korisnik je vec registrovan", Toast.LENGTH_LONG).show()
                error.printStackTrace()
                println(error.message)
            }
        )


    }

}