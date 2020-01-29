package com.example.recyclerviewapp.ui.addnewsactivity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.recyclerviewapp.R
import kotlinx.android.synthetic.main.activity_add_news.*


class AddNewsActivity : AppCompatActivity(), AddNewsActivityContruct.View {


    private var filePath: Uri? = null


    private lateinit var mPresenter: AddNewsActivityPresenter
    private lateinit var titleTextView: TextView
    private lateinit var descriptionTextView: TextView
    private lateinit var newsImageView: ImageView
    private lateinit var imageChooseButton: Button
    private lateinit var addNewsButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.recyclerviewapp.R.layout.activity_add_news)

        //check runtime permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {

                val permissions = arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                // show pop up to request runtime permission
                requestPermissions(permissions, PERMISSION_CODE)
            }
        }

        mPresenter = AddNewsActivityPresenter()

        mPresenter.setView(this, this)
        mPresenter.created()

    }

    override fun bindViews() {

        titleTextView = findViewById(R.id.addNewsTitleEditText)
        descriptionTextView = findViewById(R.id.addNewsDescripEditText)
        newsImageView = findViewById(R.id.addNewsImageView)
        imageChooseButton = findViewById(R.id.chooseImageButton)
        addNewsButton = findViewById((R.id.addNewsFirebaseButton))
    }


    override fun initOnclick() {
        imageChooseButton.setOnClickListener {
            mPresenter.pickImageFromGaleri()
        }
        addNewsButton.setOnClickListener {
            mPresenter.saveNews(filePath, titleTextView, descriptionTextView)
            onBackPressed()
        }
    }


    //File path degiskenini parametre olarak gonder ve icerisin doldurarak geri getir


    companion object {
        private val IMAGE_PICK_CODE = 1000
        private val PERMISSION_CODE = 1001
    }

    //Fotograflara erisim izni istemek icin kullanilan override metodu
    // model ile ilgisi olmayan foncsiton veya metodlarin activity icinde birakiyorum
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {

        when (requestCode) {
            PERMISSION_CODE -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(this, "Permisssion is success", Toast.LENGTH_LONG).show()

                    mPresenter.pickImageFromGaleri()
                } else {
                    Toast.makeText(this, "Permisssion DENIED", Toast.LENGTH_LONG).show()

                }
            }
        }
    }

    @SuppressLint("MissingSuperCall")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            filePath = data?.data
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, filePath)
                addNewsImageView.setImageBitmap(bitmap)

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    }

}
