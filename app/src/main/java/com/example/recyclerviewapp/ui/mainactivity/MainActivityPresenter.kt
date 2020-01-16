package com.example.recyclerviewapp.ui.mainactivity

import android.app.Activity
import android.content.Intent
import com.example.recyclerviewapp.dto.DataDTO
import com.example.recyclerviewapp.ui.addnewsactivity.AddNewsActivity
import com.google.firebase.database.*
import java.util.*

class MainActivityPresenter {


    //  MainActivityContruct.Presenter'dan miras aldigim icicn benden override etmem gereken metod oldugunu soyluyor override etmezsem kizaracak
    //view olustumam gerekiyor.
    private lateinit var mView: MainActivityContruct.View
    private lateinit var mContext: Activity
    lateinit var database: DatabaseReference

    val posts = ArrayList<DataDTO>()

    fun setView(view: MainActivityContruct.View, context: Activity) {
        //Burdaki mView ve mContect'in MainActivity icerisinden getirip  UI'ini kontrol etmek istedigim icin kullanıyorum
        this.mView = view
        this.mContext = context
        database = FirebaseDatabase.getInstance().reference.child("news")
    }

    fun created() {
        //findviewById yi burda create'e verdik cunku biz artık bu islemi UI kisminda yapmak istiyoruz.
        this.mView.bindViews() // Burada ayaga kalkacak olan activity xml kismina baglamak istiyorum
        this.mView.initOnclick() // bir onclick eventi oldugunda bilgi vermek icin kullanilacak
    }

    fun fabCliced() {
        val intent = Intent(mContext, AddNewsActivity::class.java)
        mContext.startActivity(intent)
    }

    //Burda listesyi dolduruyoruz
    fun retrieveData() {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                dataSnapshot.children.forEach {
                    val post = it.getValue(DataDTO::class.java)
                    posts.add(post!!)
                }
                //recycler icerisine listeyi eklemek istiyoruz eger gidip showNews'i created fonksiiyonu icinde cagirirsak bos olan listeyi dönecegi icin icerisini dolduramyacak
                //Bunun olmaması icin listeyi doldurduktan sonra showNews metodunu cagirip listeyi icerisine atiyoruz
                mView.showNews(posts)
            }

            override fun onCancelled(error: DatabaseError) {
                //print error.message
            }
        })

    }


    //search'ın sonucunu verir burda alması gereken bir sey olmadigi icin dönusumune gerek yok
}