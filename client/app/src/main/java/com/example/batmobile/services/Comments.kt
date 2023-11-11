package com.example.batmobile.services

import android.app.Activity
import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.batmobile.DTOFromServer.ProductComment
import com.example.batmobile.R

class Comments {

    companion object{
        fun renderComments(product_comments: List<ProductComment>, row:LinearLayout ,activity: Activity, context: Context){
            val marginInDp = 4
            val marginInPx = (marginInDp * activity.resources.displayMetrics.density).toInt()
            val itemLayoutParams = LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.MATCH_PARENT,
                1f
            )
            itemLayoutParams.setMargins(marginInPx, (marginInDp+14), marginInPx, 0)

            val widthInDp = 15
            val heightInDp = 15

            val density = activity.resources.displayMetrics.density

            val widthInPx = (widthInDp * density).toInt()
            val heightInPx = (heightInDp * density).toInt()

            var commentParams = LinearLayout.LayoutParams(widthInPx, heightInPx)
            commentParams.rightMargin = marginInPx

            for((index, comment) in product_comments.withIndex()){
                val itemView = activity.layoutInflater.inflate(R.layout.component_comment, null)
                val comment_image           = itemView.findViewById<ImageView>(R.id.comment_image)
                val comment_username        = itemView.findViewById<TextView>(R.id.comment_username)
                val comment_comment         = itemView.findViewById<TextView>(R.id.comment_comment)
                val comment_horizontal_star = itemView.findViewById<LinearLayout>(R.id.horizontal_layout_star)
                comment_username.text = comment.username
                comment_comment.text = comment.text
                for(i in 0 until comment.grade){
                    val star = ImageView(context)
                    star.layoutParams = commentParams
                    star.setImageResource(R.drawable.star_filled)
                    comment_horizontal_star.addView(star)
                }
                for(i in 0 until (5-comment.grade)){
                    val star = ImageView(context)
                    star.layoutParams = commentParams
                    star.setImageResource(R.drawable.star)
                    comment_horizontal_star.addView(star)
                }
                itemView.layoutParams = itemLayoutParams
                row.addView(itemView);
            }
        }

    }

}