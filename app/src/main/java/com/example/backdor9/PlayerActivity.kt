package com.example.backdor9

import android.os.Bundle
import android.widget.Toast
import androidx.annotation.OptIn
import androidx.appcompat.app.AppCompatActivity
import androidx.media3.common.MediaItem
import androidx.media3.common.MimeTypes // Bu satır 'MimeTypes' hatasını çözer
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import com.example.backdor9.databinding.ActivityPlayerBinding

class PlayerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPlayerBinding
    private var player: ExoPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        try {
            binding = ActivityPlayerBinding.inflate(layoutInflater)
            setContentView(binding.root)

            val videoUrl = intent.getStringExtra("VIDEO_URL")
            if (videoUrl.isNullOrEmpty()) {
                Toast.makeText(this, "Yayın linki bulunamadı!", Toast.LENGTH_SHORT).show()
                finish()
                return
            }

            initializePlayer(videoUrl)
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Açılış hatası: ${e.message}", Toast.LENGTH_LONG).show()
            finish()
        }
    }

    @OptIn(UnstableApi::class)
    private fun initializePlayer(videoUrl: String) {
        player = ExoPlayer.Builder(this).build().also { exoPlayer ->
            binding.playerView.player = exoPlayer
            
            val mediaItem = MediaItem.Builder()
                .setUri(videoUrl)
                .setMimeType(MimeTypes.APPLICATION_M3U8) // Canlı yayın desteğini zorlar
                .build()

            exoPlayer.setMediaItem(mediaItem)
            exoPlayer.prepare()
            exoPlayer.playWhenReady = true
            
            exoPlayer.addListener(object : Player.Listener {
                override fun onPlayerError(error: PlaybackException) {
                    Toast.makeText(this@PlayerActivity, 
                        "Oynatma Hatası: ${error.message}", Toast.LENGTH_LONG).show()
                }
            })
        }
    }

    override fun onStop() {
        super.onStop()
        releasePlayer()
    }

    override fun onDestroy() {
        super.onDestroy()
        releasePlayer()
    }

    private fun releasePlayer() {
        player?.release()
        player = null
    }
} // Dosyanın sonundaki bu parantez eksikti, eklendi.
