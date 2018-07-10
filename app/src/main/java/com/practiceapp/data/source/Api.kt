package com.practiceapp.data.source

import com.practiceapp.data.VideoList
import com.practiceapp.data.Videos
import io.reactivex.Observable
import retrofit2.adapter.rxjava2.Result
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("gifs/search")
    fun doSearch(@Query("api_key") apiKey: String,
                 @Query("q") query: String): Observable<Result<VideoList>>

}