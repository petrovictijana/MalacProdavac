package com.example.batmobile.fragments

import android.content.pm.PackageManager
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.example.batmobile.R
import com.example.batmobile.viewModels.UserViewModel
import com.example.batmobile.models.User
import com.example.batmobile.network.ApiClient
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.ItemizedIconOverlay
import org.osmdroid.views.overlay.OverlayItem


class RegisterFragment_step2 : Fragment() {

    private lateinit var back: ImageView

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

    private lateinit var user: User

    private lateinit var automobil: FrameLayout
    private lateinit var motocikl: FrameLayout
    private lateinit var kombi: FrameLayout
    private lateinit var kamion: FrameLayout


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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_register_step2, container, false)

        apiCall = ApiClient(requireActivity())

        val userViewModel: UserViewModel by activityViewModels()

        mapView = view.findViewById(R.id.mapView)
        if ((live_latitude >= -90.0 && live_latitude <= 90.0) || (live_longitude >= -180.0 && live_longitude <= 180.0)){ setMap(live_latitude, live_longitude)}
        else setInitMap()

        user = userViewModel.user!!
        println(user)
        vehicle = user.vehicle

        back =                      view.findViewById<ImageView>(R.id.back)
        back.setOnClickListener{ Navigation.findNavController(view).navigate(R.id.navigate_step2ToStep1) }

        constraintLayout = view.findViewById<LinearLayout>(R.id.linearLayout2)
        kupacRadioButton = constraintLayout.findViewById<RadioButton>(R.id.Kupac)
        prodavacRadioButton = constraintLayout.findViewById<RadioButton>(R.id.Prodavac)
        dostavljacRadioButton = constraintLayout.findViewById<RadioButton>(R.id.Dostavljac)

        kupacOptionsLayout = view.findViewById<LinearLayout>(R.id.kupacOptionsLayout)
        prodavacOptionsLayout = view.findViewById<LinearLayout>(R.id.prodavacOptionsLayout)
        dostavljacOptionsLayout = view.findViewById<LinearLayout>(R.id.dostavljacOptionsLayout)

        kupacUserName = view.findViewById<TextView>(R.id.user_usernameKupac)
        kupacEmail = view.findViewById<TextView>(R.id.user_emailKupac)
        dostavljacUserName = view.findViewById<TextView>(R.id.user_usernameDostavljac)
        dostavljacEmail = view.findViewById<TextView>(R.id.user_emailDostavljac)

        if (user != null) {
            kupacUserName.setText(user.username)
            kupacEmail.setText(user.email)
            dostavljacUserName.setText(user.username)
            dostavljacEmail.setText(user.email)
        }

        automobil = view.findViewById<FrameLayout>(R.id.auto)
        motocikl = view.findViewById<FrameLayout>(R.id.motocikl)
        kombi = view.findViewById<FrameLayout>(R.id.kombi)
        kamion = view.findViewById<FrameLayout>(R.id.kamion)

        automobil.setOnClickListener{setVehicle(automobil)}
        motocikl.setOnClickListener{setVehicle(motocikl)}
        kombi.setOnClickListener{setVehicle(kombi)}
        kamion.setOnClickListener{setVehicle(kamion)}

        prodavac_pib = view.findViewById<EditText>(R.id.pib)
        prodavac_lokacija = view.findViewById<EditText>(R.id.lokacija)
        nastaviButton = view.findViewById<Button>(R.id.Nastavi)

        nastaviButton.setOnClickListener{
            user.pib = prodavac_pib.text.toString()
            user.latitude = live_latitude
            user.longitude = live_longitude
            val radioGroup = view.findViewById<RadioGroup>(R.id.radioGroup)
            val selectedRadioButton = view.findViewById<RadioButton>(radioGroup.checkedRadioButtonId)
            userViewModel.selectedOption = selectedRadioButton.text.toString()
            userViewModel.user = user
            println(user)
            Navigation.findNavController(view).navigate(R.id.navigate_step2ToStep3)
        }

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
            val drawable = ContextCompat.getDrawable(requireActivity(), R.drawable.full_fill_button)
            nastaviButton.background = drawable
            nastaviButton.isEnabled = true
        }

        prodavacRadioButton.setOnClickListener {
            kupacOptionsLayout.visibility = View.GONE
            dostavljacOptionsLayout.visibility=View.GONE
            prodavacOptionsLayout.visibility = View.VISIBLE

            fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
            if (ContextCompat.checkSelfPermission(requireActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                // Imate dozvolu za pristup lokaciji, možete zatražiti lokaciju
                getLocation()
            } else {
                // Ako nemate dozvolu, zatražite je od korisnika
                ActivityCompat.requestPermissions(requireActivity(), arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_CODE)
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

        return view
    }

    fun setInitMap(){
        Configuration.getInstance().userAgentValue = requireActivity().packageName
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
        overlayItem.setMarker(ContextCompat.getDrawable(requireContext(), R.drawable.location_pin))
        items.add(overlayItem)

        val overlay = ItemizedIconOverlay<OverlayItem>(items, null, requireContext())
        mapView.overlays.clear()
        mapView.overlays.add(overlay)
    }

    private fun getLocation() {
        if (ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                if (location != null) {
                    val latitude = location.latitude
                    val longitude = location.longitude
                    println("Lokacija " + latitude + ":" + longitude)
                    sendRequestForStringLocation(latitude, longitude)
                    setMap(latitude,longitude)
                    Toast.makeText(requireContext(),"Vase koordinate: latitude: " + latitude + " longitude: " + longitude, Toast.LENGTH_LONG).show()
                } else {
                    // Ako je lokacija null, znači da nije dostupna
                    Toast.makeText(requireContext(),"Uključite lokacije na vašem uređaju, unesite ručno", Toast.LENGTH_LONG).show()
                }
            }
                .addOnFailureListener { exception ->
                    // Neuspeh pri dobijanju lokacije
                    println("Lokacija nije dostupna")
                }
        } else {
            // Ako nemate dozvolu, zatražite je od korisnika
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_CODE)
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
                requireActivity().runOnUiThread{
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
        apiCall.getAddressFromCoordinates(requireContext(), latitude, longitude,
            { fullAddress ->
                requireActivity().runOnUiThread{
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
            val drawable = ContextCompat.getDrawable(requireContext(),
                R.drawable.full_fill_button_disabled
            )
            nastaviButton.background = drawable
            nastaviButton.isEnabled = false
        }
        else{
            val drawable = ContextCompat.getDrawable(requireContext(), R.drawable.full_fill_button)
            nastaviButton.background = drawable
            nastaviButton.isEnabled = true
        }
    }
    private fun validateNastaviButtonProdavac(){
        if(prodavac_pib.text.length == 0) {
            //tada nije dozvoljeno dalje
            val drawable = ContextCompat.getDrawable(requireContext(),
                R.drawable.full_fill_button_disabled
            )
            nastaviButton.background = drawable
            nastaviButton.isEnabled = false
        }
        else if (!(live_latitude >= -90.0 && live_latitude <= 90.0) || !(live_longitude >= -180.0 && live_longitude <= 180.0)){
            //ni onda nije dozvoljeno
            val drawable = ContextCompat.getDrawable(requireContext(),
                R.drawable.full_fill_button_disabled
            )
            nastaviButton.background = drawable
            nastaviButton.isEnabled = false
        }
        else{
            // sada je samo dozvoljeno
            val drawable = ContextCompat.getDrawable(requireContext(), R.drawable.full_fill_button)
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