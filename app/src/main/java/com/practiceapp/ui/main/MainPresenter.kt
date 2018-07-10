package com.practiceapp.ui.main

import com.practiceapp.data.source.ApiService
import com.practiceapp.utils.GIFY_API
import com.practiceapp.utils.isOnline
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit


class MainPresenter(val view: MainContract.View) : MainContract.Presenter {


    override fun doSearch(retrofit: Retrofit, query: String) {
        view.getContext()?.let {
            if (isOnline(it)) {
                val apiService = retrofit.create(ApiService::class.java)
                apiService.doSearch(GIFY_API, query)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({ data ->
                            view.showList(data.response()?.body()?.data ?: emptyList())
                        }, {
                            view.showMessage("Something went wrong. Please try again later..!")
                        })

            }
        }
    }
}