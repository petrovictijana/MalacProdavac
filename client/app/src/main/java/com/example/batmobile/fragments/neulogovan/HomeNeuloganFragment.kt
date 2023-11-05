package com.example.batmobile.fragments.neulogovan

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.HorizontalScrollView
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.batmobile.R
import androidx.core.content.ContextCompat
import com.example.batmobile.models.Category
import com.example.batmobile.models.Seller
import com.example.batmobile.models.TopProduct
import com.example.batmobile.network.ApiClient
import com.example.batmobile.network.Config
import com.google.gson.Gson

class HomeNeuloganFragment : Fragment() {

    private lateinit var view: View
    private lateinit var apiClient: ApiClient

    private lateinit var container_category_products:   LinearLayout
    private lateinit var horizontal_top_seller:         HorizontalScrollView
    private lateinit var horizontal_top_products:       HorizontalScrollView

    @SuppressLint("ResourceAsColor")
    fun setColorForRegistrujSe() {
        val text = "Mala proizvodnja velika zajednica. Postani i ti deo iste - Registruj se"
        val spannableString = SpannableString(text)
        val startIndex = text.indexOf("Registruj se")
        val endIndex = startIndex + "Registruj se".length

        val orangeColor = ContextCompat.getColor(requireContext(), R.color.orange)

        spannableString.setSpan(ForegroundColorSpan(orangeColor), startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        val textView = view.findViewById<TextView>(R.id.ponuda_za_registraciju)
        textView.text = spannableString
    }

    fun getAllStuff(){
        apiClient = ApiClient(requireContext())
        container_category_products =   view.findViewById<LinearLayout>(R.id.products_category)
        horizontal_top_seller       =   view.findViewById<HorizontalScrollView>(R.id.top_sellers)
        horizontal_top_products     =   view.findViewById<HorizontalScrollView>(R.id.top_products)
    }

    fun getCategoryProducts(){
        var url:String = Config.ip_address+":"+ Config.port + "/allCategories"
        println(url)
        apiClient.sendGetRequestEmpty(url,
            { response ->
                var gson = Gson()
                var categoryList = gson.fromJson(response, Array<Category>::class.java).toList()
                renderCategoryProducts(categoryList)
            },
            { error ->
                println(error)
            }
        )
    }

    fun getTopSellers(){
        var url:String = Config.ip_address+":"+ Config.port + "/top3/sellers"
        println(url)
        apiClient.sendGetRequestEmpty(url,
            { response ->
                var gson = Gson()
                var sellerList = gson.fromJson(response, Array<Seller>::class.java).toList()
                renderHorizontalTopSellers(sellerList)
            },
            { error ->
                println(error)
            }
        )
    }
    fun getTopProducts(){
        var url:String = Config.ip_address+":"+ Config.port + "/top3/products"
        println(url)
        apiClient.sendGetRequestEmpty(url,
            { response ->
                var gson = Gson()
                var productList = gson.fromJson(response, Array<TopProduct>::class.java).toList()
                renderHorizontalTopProducts(productList)
            },
            { error ->
                println(error)
            }
        )
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        view =  inflater.inflate(R.layout.fragment_home_neulogan, container, false)

        getAllStuff()
        setColorForRegistrujSe()

        getCategoryProducts()
        getTopSellers()
        getTopProducts()

        return view
    }

    fun renderCategoryProducts(categoryList: List<Category>){
        var row: LinearLayout
        row = LinearLayout(requireContext())
        row.orientation = LinearLayout.HORIZONTAL
        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        row.layoutParams = layoutParams
        container_category_products.addView(row)
        for ((index, category) in categoryList.withIndex()) {
            if(index % 4 == 0 && index > 0){
                row = LinearLayout(requireContext())
                row.orientation = LinearLayout.HORIZONTAL
                val layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                row.layoutParams = layoutParams
                container_category_products.addView(row)
            }


            val itemView = layoutInflater.inflate(R.layout.component_item_category, null)
                var categoryImage = itemView.findViewById<ImageView>(R.id.categoryImage)
                val categoryText = itemView.findViewById<TextView>(R.id.categoryText)
                when(category.categoryId){
                    1 -> { categoryImage.setImageResource(R.drawable.mlecni_proizvodi) }
                    2 -> { categoryImage.setImageResource(R.drawable.voce_i_povrce) }
                    3 -> { categoryImage.setImageResource(R.drawable.mesne_preradjevine) }
                    4 -> { categoryImage.setImageResource(R.drawable.meso) }
                    5 -> { categoryImage.setImageResource(R.drawable.zitarice) }
                    6 -> { categoryImage.setImageResource(R.drawable.napici) }
                    7 -> { categoryImage.setImageResource(R.drawable.biljna_ulja) }
                    8 -> { categoryImage.setImageResource(R.drawable.namazi) }
                }
                categoryText.text = category.name

            val marginInDp = 4
            val marginInPx = (marginInDp * resources.displayMetrics.density).toInt()

            val itemLayoutParams = LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.MATCH_PARENT,
                1f
            )
            itemLayoutParams.setMargins(marginInPx, (marginInDp+14), marginInPx, 0)

            itemView.layoutParams = itemLayoutParams
            row.addView(itemView)
        }
    }

    fun renderHorizontalTopSellers(sellersList: List<Seller>){
        var row: LinearLayout
        row = LinearLayout(requireContext())
        row.orientation = LinearLayout.HORIZONTAL
        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        row.layoutParams = layoutParams

        val marginInDp = 4
        val marginInPx = (marginInDp * resources.displayMetrics.density).toInt()
        val itemLayoutParams = LinearLayout.LayoutParams(
            0,
            LinearLayout.LayoutParams.MATCH_PARENT,
            1f
        )

        for((index, seller) in sellersList.withIndex()){
            val itemView = layoutInflater.inflate(R.layout.component_top_seller, null)
                var sellerImage                 = itemView.findViewById<ImageView>(R.id.imageViewSeller)
                var sellerUsername              = itemView.findViewById<TextView>(R.id.seller_username)
                var sellerAddress               = itemView.findViewById<TextView>(R.id.seller_address)
                var sellerAvailableProducts     = itemView.findViewById<TextView>(R.id.available_products)
                var sellerFollowers             = itemView.findViewById<TextView>(R.id.followers)

                if(index % 2 == 0){
                    sellerImage.setImageResource(R.drawable.person_usnplash)
                }
                sellerUsername.text = seller.username
                apiClient.getAddressFromCoordinates(requireContext(),seller.latitude, seller.longitude,
                    {response-> sellerAddress.text = response }, {  })
                sellerAvailableProducts.text = seller.numberOfOrders.toString()
                sellerFollowers.text = "100"

            itemLayoutParams.setMargins(marginInPx, (marginInDp), marginInPx, 0)
            itemView.layoutParams = itemLayoutParams
            row.addView(itemView)
        }
        horizontal_top_seller.addView(row)
    }

    fun renderHorizontalTopProducts(productList: List<TopProduct>){
        var row: LinearLayout
        row = LinearLayout(requireContext())
        row.orientation = LinearLayout.HORIZONTAL
        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        row.layoutParams = layoutParams

        val marginInDp = 4
        val marginInPx = (marginInDp * resources.displayMetrics.density).toInt()
        val itemLayoutParams = LinearLayout.LayoutParams(
            0,
            LinearLayout.LayoutParams.MATCH_PARENT,
            1f
        )

        for((index, product) in productList.withIndex()){
            val itemView = layoutInflater.inflate(R.layout.component_top_product, null)
                val product_name    = itemView.findViewById<TextView>(R.id.product_name)
                val product_star    = itemView.findViewById<TextView>(R.id.product_star)
                val product_seller  = itemView.findViewById<TextView>(R.id.product_seller)
                val product_location = itemView.findViewById<TextView>(R.id.product_loaction)
                product_name.text   = product.productName
                product_star.text   = product.soldItems.toString()        //ZAMENITI
                product_seller.text = product.sellerUsername
                apiClient.getAddressFromCoordinates(requireContext(),product.latitude, product.longitude,
                {response-> product_location.text = response }, {  })

            itemLayoutParams.setMargins(marginInPx, (marginInDp), marginInPx, 0)
            itemView.layoutParams = itemLayoutParams
            row.addView(itemView)
        }
        horizontal_top_products.addView(row)
    }

}