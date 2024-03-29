package com.example.batmobile.fragments

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
import android.widget.ScrollView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.example.batmobile.DTOFromServer.Product
import com.example.batmobile.DTOFromServer.ProductComment
import com.example.batmobile.DTOFromServer.ProductViewResponse
import com.example.batmobile.DTOFromServer.Seller
import com.example.batmobile.R
import com.example.batmobile.network.ApiClient
import com.example.batmobile.network.Config
import com.example.batmobile.services.Comments
import com.example.batmobile.services.Map
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.osmdroid.views.MapView

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

    private lateinit var more_comments      : TextView

    private lateinit var product_view_response:  ProductViewResponse
    private lateinit var seller_location    : String

    private lateinit var list_of_comments   : List<ProductComment>

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

        more_comments       = view.findViewById<TextView>(R.id.more_comments)

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
        person_location .setOnClickListener{ showOverlayDialogForLocation() }
        more_comments   .setOnClickListener{ showOverlayDialogForMoreComments()  }

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

        if(product_comments.size == 0){
            no_comment.visibility       = View.VISIBLE
            more_comments.visibility    = View.GONE
        }
        else
            Comments.renderComments(product_comments, row,"HORIZONTAL",requireActivity(), requireContext())
    }

    private fun showOverlayDialogForLocation() {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.overlay_household_location)

        val width = (resources.displayMetrics.widthPixels * 1.0).toInt()
        dialog.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)

        val overlay_closeButton: ImageView      = dialog.findViewById<ImageView>(R.id.overlay_household_location_close)
        val overlay_person_household            = dialog.findViewById<TextView>(R.id.person_household)
        val overlay_person_username             = dialog.findViewById<TextView>(R.id.person_username)
        val overlay_person_location             = dialog.findViewById<TextView>(R.id.person_location)
        val overlay_map                         = dialog.findViewById<MapView>(R.id.mapView)
        overlay_closeButton.setOnClickListener { dialog.dismiss() }

        overlay_person_household.text = "Domaćinstvo "  +   product_view_response.sellerDTO.surname
        overlay_person_username.text  = "@ "            +   product_view_response.sellerDTO.username
        overlay_person_location.text  = seller_location
        Map.setMap(overlay_map, product_view_response.sellerDTO.latitude, product_view_response.sellerDTO.longitude, requireActivity(), requireContext())
        dialog.show()
    }

    private fun setCommentsFilterInOverlayDialogForMoreComments(button_list: List<TextView>, clicked: TextView){
        for(element in button_list){
            element.setBackgroundResource(R.drawable.empty_button)
            element.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
        }
        clicked.setBackgroundResource(R.drawable.full_fill_button)
        clicked.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))

        val resourceName = resources.getResourceEntryName(clicked.id)
        println(resourceName)
        when(resourceName){
            "overlay_comments_button_all" -> {
                row_in_all_comments.removeAllViews()
                Comments.renderComments(list_of_comments, row_in_all_comments,"VERTICAL" ,requireActivity(), requireContext())
            }
            "overlay_comments_button_5"   -> {
                row_in_all_comments.removeAllViews()
                val filteredComments = list_of_comments.filter { it.grade == 5 }
                Comments.renderComments(filteredComments, row_in_all_comments,"VERTICAL" ,requireActivity(), requireContext())
            }
            "overlay_comments_button_4"   -> {
                row_in_all_comments.removeAllViews()
                val filteredComments = list_of_comments.filter { it.grade == 4 }
                Comments.renderComments(filteredComments, row_in_all_comments,"VERTICAL" ,requireActivity(), requireContext())
            }
            "overlay_comments_button_3"   -> {
                row_in_all_comments.removeAllViews()
                val filteredComments = list_of_comments.filter { it.grade == 3 }
                Comments.renderComments(filteredComments, row_in_all_comments,"VERTICAL" ,requireActivity(), requireContext())
            }
            "overlay_comments_button_2"   -> {
                row_in_all_comments.removeAllViews()
                val filteredComments = list_of_comments.filter { it.grade == 2 }
                Comments.renderComments(filteredComments, row_in_all_comments,"VERTICAL" ,requireActivity(), requireContext())
            }
            "overlay_comments_button_1"   -> {
                row_in_all_comments.removeAllViews()
                val filteredComments = list_of_comments.filter { it.grade == 1 }
                Comments.renderComments(filteredComments, row_in_all_comments,"VERTICAL" ,requireActivity(), requireContext())
            }

        }

    }

    private lateinit var row_in_all_comments: LinearLayout
    private fun showOverlayDialogForMoreComments() {

        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.overlay_product_all_comments)

        val width = (resources.displayMetrics.widthPixels * 1.0).toInt()
        val height = (resources.displayMetrics.heightPixels * 0.8).toInt()
        dialog.window?.setLayout(width, height)

        val overlay_closeButton         : ImageView      = dialog.findViewById<ImageView>(R.id.overlay_comments_close)
        val overlay_comments_button_all : TextView       = dialog.findViewById<TextView>(R.id.overlay_comments_button_all)
        val overlay_comments_button_5   : TextView       = dialog.findViewById<TextView>(R.id.overlay_comments_button_5)
        val overlay_comments_button_4   : TextView       = dialog.findViewById<TextView>(R.id.overlay_comments_button_4)
        val overlay_comments_button_3   : TextView       = dialog.findViewById<TextView>(R.id.overlay_comments_button_3)
        val overlay_comments_button_2   : TextView       = dialog.findViewById<TextView>(R.id.overlay_comments_button_2)
        val overlay_comments_button_1   : TextView       = dialog.findViewById<TextView>(R.id.overlay_comments_button_1)

        val overlay_comment_list    = dialog.findViewById<ScrollView>(R.id.overlay_comment_list)
        var list_of_buttons_filter  = mutableListOf<TextView>(overlay_comments_button_all, overlay_comments_button_5, overlay_comments_button_4, overlay_comments_button_3, overlay_comments_button_2, overlay_comments_button_1 )

        for(element in list_of_buttons_filter){
            element.setOnClickListener{ setCommentsFilterInOverlayDialogForMoreComments(list_of_buttons_filter, element) }
        }
        overlay_closeButton.setOnClickListener { dialog.dismiss() }



        row_in_all_comments = LinearLayout(requireContext())
        row_in_all_comments.orientation = LinearLayout.VERTICAL
        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        row_in_all_comments.layoutParams = layoutParams
        overlay_comment_list.addView(row_in_all_comments)


        var url:String = Config.ip_address+":"+ Config.port + "/getComments/"+product_id
        apiClient.sendGetRequestEmpty(url,
            {response->
                val gson = Gson()
                val listType = object : TypeToken<List<ProductComment>>() {}.type
                list_of_comments = gson.fromJson(response, listType)

                Comments.renderComments(list_of_comments, row_in_all_comments,"VERTICAL" ,requireActivity(), requireContext())
            },
            {error->
                println(error)
            })

        dialog.show()
    }


}