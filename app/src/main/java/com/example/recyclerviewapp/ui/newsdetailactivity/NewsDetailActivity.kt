package com.example.recyclerviewapp.ui.newsdetailactivity

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.recyclerviewapp.R

class NewsDetailActivity : AppCompatActivity() {

    private lateinit var newsTitleDetailTxt: TextView
    private lateinit var newsDescriptionDetailTxt: TextView
    private lateinit var newsUrlImage: ImageView


    //Hangi aktivity de oldugunuza dikkat edin buradan cektiginiz presenter ile baglanti kuracaginiz yer hangi activity de iseniz onun Presenter ini kullanicaz
    private lateinit var msPresenter: NewsDetailActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_detail)

        msPresenter = NewsDetailActivityPresenter()
        this.msPresenter.setView(this)

        bindViews()
        // Verileri geridi ama click ozelligi verilemedi


//        newsTitleDetailTxt.text = intent.extras.get("newtitle").toString()
//        newsDescriptionDetailTxt.text = intent.extras.get("newdescrip").toString()
//        //Veri tabanından gelen degerimi detailProjectImageUrl icerisine atiyorum.
//        val detailProjectImageUrl = intent.extras.get("newimageurl").toString()
//        Glide.with(this).load(detailProjectImageUrl).into(newsUrlImage)
    }

    fun bindViews() {
        newsTitleDetailTxt = findViewById(R.id.txtNewTitleDetail)
        newsDescriptionDetailTxt = findViewById(R.id.txtNewSummaryDetail)
        newsUrlImage = findViewById(R.id.imageNewDetail)

        msPresenter.sendInformation(intent, newsTitleDetailTxt, newsDescriptionDetailTxt, newsUrlImage)
    }
}
