package com.example.batmobile.activities

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.batmobile.R
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.ItemizedIconOverlay
import org.osmdroid.views.overlay.OverlayItem
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.FusedLocationProviderClient

class RegisterStep2Activity : AppCompatActivity() {

    private lateinit var constraintLayout: LinearLayout
    private lateinit var kupacRadioButton: RadioButton
    private lateinit var prodavacRadioButton: RadioButton
    private lateinit var dostavljacRadioButton: RadioButton
    private lateinit var kupacOptionsLayout: LinearLayout
    private lateinit var prodavacOptionsLayout: LinearLayout
    private lateinit var dostavljacOptionsLayout: LinearLayout

    lateinit var mapView: MapView
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val REQUEST_CODE = 123

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_step2)

        constraintLayout = findViewById<LinearLayout>(R.id.linearLayout2)
        kupacRadioButton = constraintLayout.findViewById<RadioButton>(R.id.Kupac)
        prodavacRadioButton = constraintLayout.findViewById<RadioButton>(R.id.Prodavac)
        dostavljacRadioButton = constraintLayout.findViewById<RadioButton>(R.id.Dostavljac)

        kupacOptionsLayout = findViewById<LinearLayout>(R.id.kupacOptionsLayout)
        prodavacOptionsLayout = findViewById<LinearLayout>(R.id.prodavacOptionsLayout)
        dostavljacOptionsLayout = findViewById<LinearLayout>(R.id.dostavljacOptionsLayout)

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

        mapView = findViewById(R.id.mapView)
        setInitMap()

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // Imate dozvolu za pristup lokaciji, možete zatražiti lokaciju
            getLocation()
        } else {
            // Ako nemate dozvolu, zatražite je od korisnika
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_CODE)
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
        mapView.controller.setCenter(newPoint)
        mapView.controller.setZoom(13.0)
        // Dodavanje pina na tacnu lokaciju
        val items = ArrayList<OverlayItem>()
        val overlayItem = OverlayItem("Lokacija", "Lokacija domacinstva", newPoint)
        overlayItem.setMarker(ContextCompat.getDrawable(this, R.drawable.location_pin))
        items.add(overlayItem)

        val overlay = ItemizedIconOverlay<OverlayItem>(items, null, applicationContext)
        mapView.overlays.add(overlay)
    }

    private fun getLocation() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                    if (location != null) {
                        val latitude = location.latitude
                        val longitude = location.longitude
                        println("Lokacija " + latitude + ":" + longitude)
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

}