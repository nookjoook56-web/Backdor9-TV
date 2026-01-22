package com.example.backdor9

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.backdor9.adapter.ChannelAdapter
import com.example.backdor9.databinding.ActivityMainBinding
import com.example.backdor9.util.M3UParser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Yan yana 2'li görünüm
        binding.recyclerView.layoutManager = GridLayoutManager(this, 2)

        val m3uUrl = "https://raw.githubusercontent.com/nookjoook56-web/Update-mp3u/refs/heads/main/playlist.m3u"
        loadChannels(m3uUrl)
    }

    private fun loadChannels(url: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val m3uText = URL(url).readText()
                val channels = M3UParser.parse(m3uText)

                withContext(Dispatchers.Main) {
                    binding.recyclerView.adapter = ChannelAdapter(channels) { selectedChannel ->
                        // TIKLAMA BURADA GERÇEKLEŞİYOR
                        val intent = Intent(this@MainActivity, PlayerActivity::class.java)
                        intent.putExtra("VIDEO_URL", selectedChannel.streamUrl)
                        startActivity(intent)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
