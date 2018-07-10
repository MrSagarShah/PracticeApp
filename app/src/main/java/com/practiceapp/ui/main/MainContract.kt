package com.practiceapp.ui.main

import android.content.Context
import com.practiceapp.data.Videos
import retrofit2.Retrofit

interface MainContract {

    interface Presenter {
        fun doSearch(retrofit: Retrofit, query: String)
    }

    interface View {
        fun showMessage(msg: String)
        fun getContext(): Context?
        fun showList(list: List<Videos>)
    }
}

