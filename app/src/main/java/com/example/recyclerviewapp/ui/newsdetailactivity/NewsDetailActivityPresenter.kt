package com.example.recyclerviewapp.ui.newsdetailactivity

import android.app.Activity
import android.content.Intent
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class NewsDetailActivityPresenter {

    private lateinit var mContext: Activity
    lateinit var database: DatabaseReference

    fun setView(context: Activity) {

        this.mContext = context
        database = FirebaseDatabase.getInstance().reference.child("news")

    }

    fun sendInformation(intent: Intent, newsTitleDetailTxt: TextView, newsDescriptionDetailTxt: TextView, newsUrlImage: ImageView) {

        newsTitleDetailTxt.text = intent.extras.get("newtitle").toString()
        newsDescriptionDetailTxt.text = intent.extras.get("newdescrip").toString()
        //Veri tabanından gelen degerimi detailProjectImageUrl icerisine atiyorum.
        val detailProjectImageUrl = intent.extras.get("newimageurl").toString()
        Glide.with(mContext).load(detailProjectImageUrl).into(newsUrlImage)

    }

}