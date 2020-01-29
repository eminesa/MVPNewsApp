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
        msPresenter.setView(this)

        bindViews()
    }

    fun bindViews() {
        newsTitleDetailTxt = findViewById(R.id.txtNewTitleDetail)
        newsDescriptionDetailTxt = findViewById(R.id.txtNewSummaryDetail)
        newsUrlImage = findViewById(R.id.imageNewDetail)

        msPresenter.sendInformation(intent, newsTitleDetailTxt, newsDescriptionDetailTxt, newsUrlImage)
    }
}
