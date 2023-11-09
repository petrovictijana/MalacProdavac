package com.example.batmobile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.HorizontalScrollView
import android.widget.LinearLayout

class ProductViewFragment : Fragment() {
    private lateinit var view: View
    private lateinit var horizontal_comments: HorizontalScrollView

    fun getAllStuff(){
        horizontal_comments = view.findViewById<HorizontalScrollView>(R.id.horizontal_layout_comments)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        view = inflater.inflate(R.layout.fragment_product_view, container, false)

        getAllStuff()

        renderComments()    //kasnije pozovi u funkciji koja getuje komentare

        return view
    }

    fun renderComments(){
        var row: LinearLayout
        row = LinearLayout(requireContext())
        row.orientation = LinearLayout.HORIZONTAL
        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        row.layoutParams = layoutParams
        horizontal_comments.addView(row)

        val itemView1 = layoutInflater.inflate(R.layout.component_comment, null)
        val itemView2 = layoutInflater.inflate(R.layout.component_comment, null)
        val itemView3 = layoutInflater.inflate(R.layout.component_comment, null)
        val itemView4 = layoutInflater.inflate(R.layout.component_comment, null)

        val marginInDp = 4
        val marginInPx = (marginInDp * resources.displayMetrics.density).toInt()

        val itemLayoutParams = LinearLayout.LayoutParams(
            0,
            LinearLayout.LayoutParams.MATCH_PARENT,
            1f
        )
        itemLayoutParams.setMargins(marginInPx, (marginInDp+14), marginInPx, 0)

        itemView1.layoutParams = itemLayoutParams
        itemView2.layoutParams = itemLayoutParams
        itemView3.layoutParams = itemLayoutParams
        itemView4.layoutParams = itemLayoutParams

        row.addView(itemView1); row.addView(itemView2); row.addView(itemView3); row.addView(itemView4)

    }

}