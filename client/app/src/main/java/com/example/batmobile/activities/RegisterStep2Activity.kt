package com.example.batmobile.activities

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.Toast
import androidx.core.app.ActivityCompat
import android.widget.RadioGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.batmobile.R
import com.example.batmobile.models.User
import com.example.batmobile.network.ApiClient
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.ItemizedIconOverlay
import org.osmdroid.views.overlay.OverlayItem
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.FusedLocationProviderClient
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONException
import java.io.IOException
import java.nio.DoubleBuffer

class RegisterStep2Activity : AppCompatActivity() {

    private lateinit var constraintLayout: LinearLayout

    private lateinit var kupacRadioButton: RadioButton
    private lateinit var prodavacRadioButton: RadioButton
    private lateinit var dostavljacRadioButton: RadioButton

    private lateinit var kupacOptionsLayout: LinearLayout
    private lateinit var prodavacOptionsLayout: LinearLayout
    private lateinit var dostavljacOptionsLayout: LinearLayout

    private lateinit var kupacUserName: TextView
    private lateinit var kupacEmail: TextView
    private lateinit var dostavljacUserName: TextView
    private lateinit var dostavljacEmail: TextView
//    private lateinit var prodavacUserName: TextView
//    private lateinit var prodavacEmail: TextView

    private lateinit var user:User

    private lateinit var prodavac_pib: EditText
    private lateinit var prodavac_lokacija: EditText
    private lateinit var nastaviButton: Button

//    Validne vrednosti latitude:[-90, 90] &  longitude:[-180, 180]
    private var live_latitude:Double = -100.0
    private var live_longitude:Double = 200.0

    lateinit var mapView: MapView
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val REQUEST_CODE = 123

    lateinit var apiCall: ApiClient

    private lateinit var vehicle: MutableMap<String, Boolean>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_step2)

        apiCall = ApiClient(this)

        mapView = findViewById(R.id.mapView)
        if ((live_latitude >= -90.0 && live_latitude <= 90.0) || (live_longitude >= -180.0 && live_longitude <= 180.0)){ setMap(live_latitude, live_longitude)}
        else setInitMap()

        user = intent.getParcelableExtra<User>("user")!!

        vehicle = user.vehicle

        constraintLayout = findViewById<LinearLayout>(R.id.linearLayout2)
        kupacRadioButton = constraintLayout.findViewById<RadioButton>(R.id.Kupac)
        prodavacRadioButton = constraintLayout.findViewById<RadioButton>(R.id.Prodavac)
        dostavljacRadioButton = constraintLayout.findViewById<RadioButton>(R.id.Dostavljac)

        kupacOptionsLayout = findViewById<LinearLayout>(R.id.kupacOptionsLayout)
        prodavacOptionsLayout = findViewById<LinearLayout>(R.id.prodavacOptionsLayout)
        dostavljacOptionsLayout = findViewById<LinearLayout>(R.id.dostavljacOptionsLayout)

        kupacUserName = findViewById<TextView>(R.id.user_usernameKupac)
        kupacEmail = findViewById<TextView>(R.id.user_emailKupac)
        dostavljacUserName = findViewById<TextView>(R.id.user_usernameDostavljac)
        dostavljacEmail = findViewById<TextView>(R.id.user_emailDostavljac)

        if (user != null) {
            kupacUserName.setText(user.username)
            kupacEmail.setText(user.email)
            dostavljacUserName.setText(user.username)
            dostavljacEmail.setText(user.email)
        }

        prodavac_pib = findViewById<EditText>(R.id.pib)
        prodavac_lokacija = findViewById<EditText>(R.id.lokacija)
        nastaviButton = findViewById<Button>(R.id.Nastavi)

        prodavac_lokacija.setOnEditorActionListener{ _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                // Pozvati željenu metodu ili izvršiti akciju kada se pritisne "Done" na tastaturi
                sendRequestForCoordinates()
            }
            false
        }

        kupacRadioButton.setOnClickListener {
            kupacOptionsLayout.visibility = View.VISIBLE
            dostavljacOptionsLayout.visibility=View.GONE
            prodavacOptionsLayout.visibility = View.GONE
            val drawable = ContextCompat.getDrawable(this, R.drawable.full_fill_button)
            nastaviButton.background = drawable
            nastaviButton.isEnabled = true
        }

        prodavacRadioButton.setOnClickListener {
            kupacOptionsLayout.visibility = View.GONE
            dostavljacOptionsLayout.visibility=View.GONE
            prodavacOptionsLayout.visibility = View.VISIBLE

            fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                // Imate dozvolu za pristup lokaciji, možete zatražiti lokaciju
                getLocation()
            } else {
                // Ako nemate dozvolu, zatražite je od korisnika
                ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_CODE)
            }
            if ((live_latitude >= -90.0 && live_latitude <= 90.0) || (live_longitude >= -180.0 && live_longitude <= 180.0)){ setMap(live_latitude, live_longitude)}
            else setInitMap()
            validateNastaviButtonProdavac()
        }
        prodavac_pib.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Implementacija pre promene teksta
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Implementacija tokom promene teksta
            }

            override fun afterTextChanged(s: Editable?) {
                validateNastaviButtonProdavac() }
        })
        dostavljacRadioButton.setOnClickListener{
            kupacOptionsLayout.visibility = View.GONE
            dostavljacOptionsLayout.visibility=View.VISIBLE
            prodavacOptionsLayout.visibility = View.GONE
            validateNastaviButtonDostavljac()
        }

    }
    fun goToRegisterActivity(view:View){
        val intent: Intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
        finish()
    }
    fun goToRegisterStep3Activity(view: View) {
        if(view.isEnabled) {
            val radioGroup = findViewById<RadioGroup>(R.id.radioGroup)
            val selectedRadioButton = findViewById<RadioButton>(radioGroup.checkedRadioButtonId)

            val intent = Intent(this, RegisterStep3Activity::class.java)
            user.vehicle = vehicle
            user.pib = prodavac_pib.text.toString()
            user.latitude = live_latitude
            user.longitude = live_longitude
            print(user)
            intent.removeExtra("user")
            intent.putExtra("user", user)
            intent.putExtra("selectedOption", selectedRadioButton.text.toString())
            startActivity(intent)
        }
}


    fun setInitMap(){
        Configuration.getInstance().userAgentValue = packageName
        // Postavite parametre mape
        mapView.setTileSource(TileSourceFactory.MAPNIK)
        mapView.setBuiltInZoomControls(true)
        mapView.controller.setZoom(9.0)

        // Postavite početnu tačku mape (npr. Beograd)
        val startPoint = GeoPoint(44.7866, 20.4489)
        mapView.controller.setCenter(startPoint)

    }

    fun setMap(latitude:Double, longitude:Double){
        val newPoint = GeoPoint(latitude, longitude)
        live_latitude = latitude
        live_longitude = longitude
        validateNastaviButtonProdavac()
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

    private fun getLocation() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                    if (location != null) {
                        val latitude = location.latitude
                        val longitude = location.longitude
                        println("Lokacija " + latitude + ":" + longitude)
                        sendRequestForStringLocation(latitude, longitude)
                        setMap(latitude,longitude)
                        Toast.makeText(this,"Vase koordinate: latitude: " + latitude + " longitude: " + longitude, Toast.LENGTH_LONG).show()
                    } else {
                        // Ako je lokacija null, znači da nije dostupna
                        Toast.makeText(this,"Uključite lokacije na vašem uređaju, unesite ručno", Toast.LENGTH_LONG).show()
                    }
                }
                .addOnFailureListener { exception ->
                    // Neuspeh pri dobijanju lokacije
                    println("Lokacija nije dostupna")
                }
        } else {
            // Ako nemate dozvolu, zatražite je od korisnika
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_CODE)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Korisnik je odobrio pristup lokaciji
                getLocation()
            } else {
                // Korisnik je odbio pristup lokaciji
            }
        }
    }
    fun sendRequestForCoordinates(){
        val address = prodavac_lokacija.text.toString()
        apiCall.getCoordinatesForAddress(address,
            { latitude, longitude ->
                runOnUiThread{
                    // Dobijene su koordinate (latitude i longitude)
                    setMap(latitude,longitude)
                    println("Latitude: $latitude, Longitude: $longitude")
                }
            },
            {
                // Greška prilikom dobijanja koordinata
                println("Greška prilikom dobijanja koordinata.")
            })
    }

    fun sendRequestForStringLocation(latitude: Double, longitude: Double){
        apiCall.getAddressFromCoordinates(applicationContext, latitude, longitude,
            { fullAddress ->
                runOnUiThread{
                    // Dobijena je adresa u obliku "Grad, Ulica"
                    prodavac_lokacija.setText(fullAddress)
                    println("Adresa: $fullAddress")
                }
            },
            {
                // Greška prilikom dobijanja adrese
                println("Greška prilikom dobijanja adrese.")
            })
    }

    private fun validateNastaviButtonDostavljac(){
        if(vehicle["auto"]==false && vehicle["motocikl"]==false && vehicle["kombi"]==false && vehicle["kamion"]==false){
            val drawable = ContextCompat.getDrawable(this, R.drawable.full_fill_button_disabled)
            nastaviButton.background = drawable
            nastaviButton.isEnabled = false
        }
        else{
            val drawable = ContextCompat.getDrawable(this, R.drawable.full_fill_button)
            nastaviButton.background = drawable
            nastaviButton.isEnabled = true
        }
    }
    private fun validateNastaviButtonProdavac(){
        if(prodavac_pib.text.length == 0) {
            //tada nije dozvoljeno dalje
            val drawable = ContextCompat.getDrawable(this, R.drawable.full_fill_button_disabled)
            nastaviButton.background = drawable
            nastaviButton.isEnabled = false
        }
        else if (!(live_latitude >= -90.0 && live_latitude <= 90.0) || !(live_longitude >= -180.0 && live_longitude <= 180.0)){
            //ni onda nije dozvoljeno
            val drawable = ContextCompat.getDrawable(this, R.drawable.full_fill_button_disabled)
            nastaviButton.background = drawable
            nastaviButton.isEnabled = false
        }
        else{
            // sada je samo dozvoljeno
            val drawable = ContextCompat.getDrawable(this, R.drawable.full_fill_button)
            nastaviButton.background = drawable
            nastaviButton.isEnabled = true
        }
    }

    private fun setVehicleInformation(type:String, view:View){
        if(vehicle[type]==true)     {vehicle[type]=false; view.setBackgroundResource(R.drawable.border_background)}
        else if(vehicle[type]==false)    {vehicle[type]=true; view.setBackgroundResource(R.drawable.border_background_orange)}
        validateNastaviButtonDostavljac()
    }

    fun setVehicle(view:View){
        when(view.id){
            R.id.auto ->        {setVehicleInformation("auto", view)}
            R.id.motocikl ->    {setVehicleInformation("motocikl", view)}
            R.id.kombi ->       {setVehicleInformation("kombi", view)}
            R.id.kamion ->      {setVehicleInformation("kamion", view)}
        }
    }

}