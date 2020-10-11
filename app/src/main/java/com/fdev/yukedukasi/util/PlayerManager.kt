package com.fdev.yukedukasi.util

import android.content.Context
import android.net.Uri
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer

class OneTimePlayerManager(
        context: Context
) {

    private var _exoPlayer: SimpleExoPlayer? = null


    private val exoPlayer
        get() = _exoPlayer!!


    private var currentPlay = ""

    init {
        _exoPlayer = SimpleExoPlayer.Builder(context).build();
    }

    fun prepareMusic(url : String){
        if(!currentPlay.equals(url)){
            exoPlayer.clearMediaItems()
            currentPlay = url
            val mediaItem = MediaItem.fromUri(Uri.parse(url))
            exoPlayer.addMediaItem(mediaItem)
            exoPlayer.prepare()
        }
    }

    fun playMusic() {
        exoPlayer.seekTo(0);
        exoPlayer.play()
    }


    fun pausePlayer(){
        exoPlayer.pause()
    }

    fun releasePlayer() {
        exoPlayer.stop();
        exoPlayer.release();
    }

}