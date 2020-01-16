package com.example.recyclerviewapp.ui.mainactivity

import com.example.recyclerviewapp.dto.DataDTO
import java.util.*

interface MainActivityContruct {
    interface View {
        fun bindViews()
        fun initOnclick()
        fun showNews(posts: ArrayList<DataDTO>)

    }
}