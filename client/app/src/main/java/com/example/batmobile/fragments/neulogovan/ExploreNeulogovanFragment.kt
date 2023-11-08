package com.example.batmobile.fragments.neulogovan

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.batmobile.R
import com.example.batmobile.models.TopProduct
import com.example.batmobile.network.ApiClient
import com.example.batmobile.network.Config
import com.google.gson.Gson

class ExploreNeulogovanFragment : Fragment() {

    private lateinit var view: View
    private lateinit var apiClient: ApiClient
    private lateinit var products_explore : LinearLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        view = inflater.inflate(R.layout.fragment_explore_neulogovan, container, false)
        apiClient = ApiClient(requireContext())
        products_explore = view.findViewById<LinearLayout>(R.id.products_explore)

        getRandomProducts()

        return view
    }

    fun getRandomProducts(){
        var url:String = Config.ip_address+":"+ Config.port + "/random48/products"
        println(url)
        apiClient.sendGetRequestEmpty(url,
            { response ->
                var gson = Gson()
                var productList = gson.fromJson(response, Array<TopProduct>::class.java).toList()
                renderHorizontalRandomProducts(productList)
            },
            { error ->
                println(error)
            }
        )
    }

    private fun renderHorizontalRandomProducts(productList: List<TopProduct>) {

    }
}