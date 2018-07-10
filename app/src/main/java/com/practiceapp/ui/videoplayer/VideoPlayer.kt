package com.practiceapp.ui.videoplayer

import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.LoopingMediaSource
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.ui.PlayerControlView
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.practiceapp.R
import kotlinx.android.synthetic.main.activity_video_player.*


class VideoPlayer : AppCompatActivity(), PlayerControlView.VisibilityListener {
    override fun onVisibilityChange(visibility: Int) {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_player)

        playerView.setControllerVisibilityListener(this)
        playerView.requestFocus()
        val bandwidthMeter = DefaultBandwidthMeter()
        val trackSelector = DefaultTrackSelector(AdaptiveTrackSelection.Factory(bandwidthMeter))
        val exoPlayer = ExoPlayerFactory.newSimpleInstance(this, trackSelector)

        val url = intent.getStringExtra("URL")
        url?.let {

            val dataSourceFactory = DefaultHttpDataSourceFactory("exoplayer_video")
            val extractorsFactory = DefaultExtractorsFactory()
            val mediaSource = ExtractorMediaSource(Uri.parse(url), dataSourceFactory, extractorsFactory, null, null)
            playerView.player = exoPlayer
            exoPlayer.prepare(LoopingMediaSource(mediaSource))
            exoPlayer.playWhenReady = true
        }
    }

}