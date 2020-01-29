package com.example.recyclerviewapp.ui.addnewsactivity

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.content.ContextCompat.startActivity
import com.example.recyclerviewapp.dto.DataDTO
import com.example.recyclerviewapp.ui.mainactivity.MainActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.util.*

class AddNewsActivityPresenter {

    private lateinit var mContext: Activity
    lateinit var database: DatabaseReference
    lateinit var mView: AddNewsActivityContruct.View
    internal var storage: FirebaseStorage? = null
    internal var storageReference: StorageReference? = null


    fun setView(view: AddNewsActivity, context: Activity) {
        this.mView = view
        this.mContext = context
        database = FirebaseDatabase.getInstance().reference.child("news")
    }

    fun created() {
        this.mView.bindViews()
        this.mView.initOnclick()
    }

    companion object {
        val IMAGE_PICK_CODE = 1000

    }

    fun pickImageFromGaleri() {

        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        //Benden bundle beklyor ama benim AddNewsActivity icinden alacagim bir deger olmadigi icin null veriyorum. startActivity'nin icini kontroi edip bunu bulabilirsiniz.
        startActivityForResult(mContext, Intent.createChooser(intent, "Choose The Photo"), IMAGE_PICK_CODE, null)
    }


    fun saveNews(filePath: Uri?, titleTextView: TextView, descriptionTextView: TextView) {

        storage = FirebaseStorage.getInstance()
        storageReference = storage!!.reference

        val newsId = database.push().key
        val title = titleTextView.text.toString().trim()
        val descrip = descriptionTextView.text.toString().trim()
        // val imageUrl adinda bir degisken eklememize gerek yok biz image secimi yaparken gelen image icin url aliyoruz bir daha ayni urli cekmemize gerek yok

        if (filePath != null && !title.equals("") && !descrip.equals("")) {

            val imageRef = storageReference!!.child("images/" + UUID.randomUUID().toString())
            imageRef.putFile(filePath)
                    .addOnSuccessListener {
                        successAddNews()

                        // uygulamadan tamamen çıkmayı sağlar:   finishAffinity(mContext)
                    }
                    .addOnFailureListener {
                        failLoadNews()
                    }
                    .addOnProgressListener {
                        //Yuklenme sirasinda kac byte olacagi ayarlaniyor
                        val progress = 100.0 * it.bytesTransferred / it.totalByteCount
                    }

            //news metod added for getting news items from model
            val news = DataDTO(title, filePath.toString(), descrip)
            //addOnCanceledListener listen my firebase when I added new Item turn to me
            newsId?.let {
                database.child(it).setValue(news).addOnCanceledListener {

                    val intent = Intent(mContext, MainActivity::class.java)
                    startActivity(mContext, intent, null)
                }
            }

        } else {
            doNotEmpty()
        }

    }

    fun doNotEmpty() {
        return Toast.makeText(mContext, "anyone colomn is not empty", Toast.LENGTH_LONG).show()

    }

    fun successAddNews() {
        Toast.makeText(mContext, "News added with success", Toast.LENGTH_LONG).show()

    }

    fun failLoadNews() {
        Toast.makeText(mContext, "Can not upload", Toast.LENGTH_LONG).show()
    }


}