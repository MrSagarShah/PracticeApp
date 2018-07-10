package com.practiceapp.data


data class VideoList(val data: List<Videos>)
data class Videos(val id: String,
                  val title: String,
                  val images: VideoWrapper)


data class VideoWrapper(val original: VideoData?)
data class VideoData(val url: String,
                     val mp4: String)