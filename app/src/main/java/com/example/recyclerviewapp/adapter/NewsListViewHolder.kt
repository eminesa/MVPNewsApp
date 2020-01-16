package com.example.recyclerviewapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recyclerviewapp.R
import com.example.recyclerviewapp.dto.DataDTO

class NewsListViewHolder(viewGroup: ViewGroup) : RecyclerView.ViewHolder(LayoutInflater.from(viewGroup.context)
        .inflate(R.layout.adapter_item_news_list, viewGroup, false)) {

    private val txtTitle by lazy { itemView.findViewById<TextView>(R.id.txtNewTitle) }
    private val txtSummary by lazy { itemView.findViewById<TextView>(R.id.txtNewSummary) }
    private val image by lazy { itemView.findViewById<ImageView>(R.id.imageNew) }
    private val txtData by lazy { itemView.findViewById<TextView>(R.id.txtReleaseDate) }

    //OnClickItem icin interface yerine Kotlinde Higher order metodu olusturuyoruz ayni anlami ifade eder

    fun bindTo(dataDTO: DataDTO, onItemClick: (view: View, newsDTO: DataDTO) -> Unit) {

        //databseden gelene veriyi kendi uygulamammızin içerisine burda yediriyoruz
        txtTitle.text = dataDTO.newtitle
        txtSummary.text = dataDTO.newdescrip
        txtData.text = dataDTO.releasedate

        //fotografı glide kütüphanesi yardimiyla cekip kendi imageimizin icerisine ata
        Glide.with(itemView.context).load(dataDTO.newimageurl).into(image)

        itemView.setOnClickListener {
            onItemClick(it, dataDTO)
        }


    }


}
