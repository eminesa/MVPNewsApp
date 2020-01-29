package com.example.recyclerviewapp.ui.mainactivity


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewapp.R
import com.example.recyclerviewapp.adapter.NewsListAdapter
import com.example.recyclerviewapp.dto.DataDTO
import com.example.recyclerviewapp.ui.newsdetailactivity.NewsDetailActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity(), MainActivityContruct.View {

    //eklemenin ilk adimi
    private lateinit var mPresenter: MainActivityPresenter
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mSearchView: SearchView
    private lateinit var adapter: NewsListAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Burada setViewi cagirmasak uygulama ayaga kalktiginda view'i bulamasa da crash olur.contect gondermemin amaci presenterda contect kullancak olmam
        mPresenter = MainActivityPresenter()
        mPresenter.setView(this, this)
        mPresenter.created()
        mPresenter.retrieveData()
    }

    override fun bindViews() {
        mRecyclerView = findViewById(R.id.recyclerView)
        mRecyclerView.apply {
            this.layoutManager = LinearLayoutManager(this@MainActivity)
        }
        this.mSearchView = findViewById(R.id.searchView)
    }

    override fun showNews(posts: ArrayList<DataDTO>) {

        if (!posts.isEmpty()) {

            adapter = NewsListAdapter(posts) { view, newsDTO ->
                onItemClicked(newsDTO)
            }
            mRecyclerView.adapter = adapter
            adapter.notifyDataSetChanged()

        }
    }

    //clickten sonra tasi
    // tiklanacak olan verileri burdan alir
    private fun onItemClicked(newsItem: DataDTO) {
        val intent = Intent(this, NewsDetailActivity::class.java)

        //Detail sayfasına gonderdigimiz verileri put extra kullanarak göndericez.
        intent.putExtra("newtitle", newsItem.newtitle)
        intent.putExtra("newdescrip", newsItem.newdescrip)
        intent.putExtra("newimageurl", newsItem.newimageurl)
        startActivity(intent)
    }


    override fun initOnclick() {

        fabButton.setOnClickListener {
            mPresenter.fabCliced()
        }

        mSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                adapter.getFilter().filter(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                return false
            }

        })
    }


}
