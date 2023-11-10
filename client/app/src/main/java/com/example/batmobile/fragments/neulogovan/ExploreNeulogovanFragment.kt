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
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView


class ExploreNeulogovanFragment : Fragment() {

    private lateinit var view: View
    private lateinit var apiClient: ApiClient
    private lateinit var recyclerViewExplore : RecyclerView
    private lateinit var layoutManager : GridLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        view = inflater.inflate(R.layout.fragment_explore_neulogovan, container, false)
        apiClient = ApiClient(requireContext())

        recyclerViewExplore = view.findViewById(R.id.recyclerViewExplore)

        layoutManager = GridLayoutManager(requireContext(), 3)
        recyclerViewExplore.layoutManager = layoutManager

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
                renderRandomProducts(productList)
            },
            { error ->
                println(error)
            }
        )
    }

    private fun renderRandomProducts(productList: List<TopProduct>) {
        var row: LinearLayout
        row = LinearLayout(requireContext())
        row.orientation = LinearLayout.HORIZONTAL
        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        row.layoutParams = layoutParams
        recyclerViewExplore.addView(row)

        for ((index, product) in productList.withIndex()) {
            if (index % 3 == 0 && index > 0) {
                row = LinearLayout(requireContext())
                row.orientation = LinearLayout.HORIZONTAL
                val layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                row.layoutParams = layoutParams
                recyclerViewExplore.addView(row)
            }

            val itemView = layoutInflater.inflate(R.layout.component_explore_product, null)

            val marginInDp = 4
            val marginInPx = (marginInDp * resources.displayMetrics.density).toInt()

            val itemLayoutParams = LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1f
            )
            itemLayoutParams.setMargins(marginInPx, marginInPx, marginInPx, 0)

            itemView.layoutParams = itemLayoutParams
            row.addView(itemView)
        }
    }
}