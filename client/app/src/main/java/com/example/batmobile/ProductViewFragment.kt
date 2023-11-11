package com.example.batmobile

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.HorizontalScrollView
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.example.batmobile.DTOFromServer.Product
import com.example.batmobile.DTOFromServer.ProductComment
import com.example.batmobile.DTOFromServer.ProductViewResponse
import com.example.batmobile.DTOFromServer.Seller
import com.example.batmobile.network.ApiClient
import com.example.batmobile.network.Config
import com.google.gson.Gson
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.ItemizedIconOverlay
import org.osmdroid.views.overlay.OverlayItem

class ProductViewFragment : Fragment() {
    private lateinit var view               : View
    private lateinit var horizontal_comments: HorizontalScrollView
    private lateinit var no_comment         : TextView
    private lateinit var back_button        : ImageView
    private lateinit var apiClient          : ApiClient
    private          var product_id         : Int = -1

    private lateinit var header_title       : TextView
    private lateinit var header_stars       : TextView
    private lateinit var product_description: TextView
    private lateinit var product_unit       : TextView
    private lateinit var product_price      : TextView

    private lateinit var person_image       : ImageView
    private lateinit var person_household   : TextView
    private lateinit var person_username    : TextView
    private lateinit var person_location    : TextView

    private lateinit var product_view_response:  ProductViewResponse
    private lateinit var seller_location    : String

    fun getAllStuff(){
        apiClient = ApiClient(requireContext())
        horizontal_comments = view.findViewById<HorizontalScrollView>(R.id.horizontal_layout_comments)
        no_comment          = view.findViewById<TextView>(R.id.no_comment)
        back_button         = view.findViewById<ImageView>(R.id.back)

        header_title        = view.findViewById<TextView>(R.id.header_title)
        header_stars        = view.findViewById<TextView>(R.id.header_stars)
        product_description = view.findViewById<TextView>(R.id.product_description)
        product_unit        = view.findViewById<TextView>(R.id.product_unit)
        product_price       = view.findViewById<TextView>(R.id.product_price)

        person_image        = view.findViewById<ImageView>(R.id.person_image)
        person_household    = view.findViewById<TextView>(R.id.person_household)
        person_username     = view.findViewById<TextView>(R.id.person_username)
        person_location     = view.findViewById<TextView>(R.id.person_location)

    }

    fun getInformationOfProduct(product_id: Int){
        var url:String = Config.ip_address+":"+ Config.port + "/getProduct/"+product_id
        println(url)
        apiClient.sendGetRequestEmpty(url,
            {response->
                val gson = Gson()
                val sellersResponse = gson.fromJson(response, ProductViewResponse::class.java)
                product_view_response = sellersResponse
                renderProduct(sellersResponse.productDTO)
                renderHousehold(sellersResponse.sellerDTO)
                renderComments(sellersResponse.productCommentList)
            },
            {error->
                println(error)
            })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        view = inflater.inflate(R.layout.fragment_product_view, container, false)

        val args: ProductViewFragmentArgs = ProductViewFragmentArgs.fromBundle(requireArguments())
        product_id = args.productId

        getAllStuff()
        back_button     .setOnClickListener{ findNavController().navigateUp() }
        person_location .setOnClickListener{ showOverlayDialog() }

        getInformationOfProduct(product_id)

        return view
    }

    fun renderProduct(product_information:Product){
        header_title            .setText(product_information.productName)
        header_stars            .setText("3HC")
        product_description     .setText(product_information.description)
        product_unit            .setText("Cena po "+product_information.measurement)
        product_price           .setText(product_information.price.toString() +" rsd.")
    }
    fun renderHousehold(seller_information: Seller){
//        person_image
        person_household.setText("Domaćinstvo "+seller_information.surname)
        person_username.setText("@ "+seller_information.username)
        apiClient.getAddressFromCoordinates(requireContext(),seller_information.latitude, seller_information.longitude,
            {response-> seller_location = response ; person_location.text = response }, {  })
    }

    fun renderComments(product_comments: List<ProductComment>){
        var row: LinearLayout
        row = LinearLayout(requireContext())
        row.orientation = LinearLayout.HORIZONTAL
        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        row.layoutParams = layoutParams
        horizontal_comments.addView(row)

        val marginInDp = 4
        val marginInPx = (marginInDp * resources.displayMetrics.density).toInt()
        val itemLayoutParams = LinearLayout.LayoutParams(
            0,
            LinearLayout.LayoutParams.MATCH_PARENT,
            1f
        )
        itemLayoutParams.setMargins(marginInPx, (marginInDp+14), marginInPx, 0)

        val widthInDp = 15
        val heightInDp = 15

        val density = resources.displayMetrics.density

        val widthInPx = (widthInDp * density).toInt()
        val heightInPx = (heightInDp * density).toInt()

        var commentParams = LinearLayout.LayoutParams(widthInPx, heightInPx)
        commentParams.rightMargin = marginInPx

        if(product_comments.size == 0){
            no_comment.visibility = View.VISIBLE
        }
        else{
            for((index, comment) in product_comments.withIndex()){
                val itemView = layoutInflater.inflate(R.layout.component_comment, null)
                val comment_image           = itemView.findViewById<ImageView>(R.id.comment_image)
                val comment_username        = itemView.findViewById<TextView>(R.id.comment_username)
                val comment_comment         = itemView.findViewById<TextView>(R.id.comment_comment)
                val comment_horizontal_star = itemView.findViewById<LinearLayout>(R.id.horizontal_layout_star)
                comment_username.text = comment.username
                comment_comment.text = comment.text
                for(i in 0 until comment.grade){
                    val star = ImageView(requireContext())
                    star.layoutParams = commentParams
                    star.setImageResource(R.drawable.star_filled)
                    comment_horizontal_star.addView(star)
                }
                for(i in 0 until (5-comment.grade)){
                    val star = ImageView(requireContext())
                    star.layoutParams = commentParams
                    star.setImageResource(R.drawable.star)
                    comment_horizontal_star.addView(star)
                }
                itemView.layoutParams = itemLayoutParams
                row.addView(itemView);
            }
        }

    }

    private fun showOverlayDialog() {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.overlay_household_location)

        val overlay_closeButton: ImageView      = dialog.findViewById<ImageView>(R.id.overlay_household_location_close)
        val overlay_person_household            = dialog.findViewById<TextView>(R.id.person_household)
        val overlay_person_username             = dialog.findViewById<TextView>(R.id.person_username)
        val overlay_person_location             = dialog.findViewById<TextView>(R.id.person_location)
        val overlay_map                         = dialog.findViewById<MapView>(R.id.mapView)
        overlay_closeButton.setOnClickListener { dialog.dismiss() }

        overlay_person_household.text = "Domaćinstvo "  +   product_view_response.sellerDTO.surname
        overlay_person_username.text  = "@ "            +   product_view_response.sellerDTO.username
        overlay_person_location.text  = seller_location
        setMap(overlay_map, product_view_response.sellerDTO.latitude, product_view_response.sellerDTO.longitude)
        // Prikazivanje dialoga
        dialog.show()
    }

    fun setMap(mapView:MapView, latitude:Double, longitude:Double){
        Configuration.getInstance().userAgentValue = requireActivity().packageName
        // Postavite parametre mape
        mapView.setTileSource(TileSourceFactory.MAPNIK)
        mapView.setBuiltInZoomControls(true)

        val newPoint = GeoPoint(latitude, longitude)
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

}