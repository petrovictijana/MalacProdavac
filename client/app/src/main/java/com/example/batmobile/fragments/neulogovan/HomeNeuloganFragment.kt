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
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.batmobile.R
import androidx.core.content.ContextCompat
import com.example.batmobile.models.Category
import com.example.batmobile.network.ApiClient
import com.example.batmobile.network.Config
import com.google.gson.Gson

class HomeNeuloganFragment : Fragment() {

    private lateinit var view: View
    private lateinit var apiClient: ApiClient

    private lateinit var container: LinearLayout

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
        container = view.findViewById<LinearLayout>(R.id.products_category)
    }

    fun getCategoryProducts(){
        var url:String = Config.ip_address+":"+ Config.port + "/allCategorys"
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        view =  inflater.inflate(R.layout.fragment_home_neulogan, container, false)

        getAllStuff()
        setColorForRegistrujSe()

        getCategoryProducts()

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
        container.addView(row)
        for ((index, category) in categoryList.withIndex()) {
            if(index % 4 == 0 && index > 0){
                row = LinearLayout(requireContext())
                row.orientation = LinearLayout.HORIZONTAL
                val layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                row.layoutParams = layoutParams
                container.addView(row)
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

}