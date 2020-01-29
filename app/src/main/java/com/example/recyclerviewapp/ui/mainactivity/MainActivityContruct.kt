package com.example.recyclerviewapp.ui.mainactivity

import com.example.recyclerviewapp.dto.DataDTO
import java.util.*

interface MainActivityContruct {
    //Main activity icerinde nesnesini olusturamadigim ama cagirmam gereken function/metodlari interface uzerinden birbirine baglayip aralarinda bir iliski kurmasini sagliyorum.
    //data sonra oveerride
    interface View {
        fun bindViews()
        fun initOnclick()
        fun showNews(posts: ArrayList<DataDTO>)

    }
}