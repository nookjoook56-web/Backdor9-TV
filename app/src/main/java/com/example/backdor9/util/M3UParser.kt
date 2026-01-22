package com.example.backdor9.util

import com.example.backdor9.Channel

object M3UParser {
    fun parse(m3uText: String): List<Channel> {
        val channels = mutableListOf<Channel>()
        val lines = m3uText.lines()
        var currentName = ""
        var currentLogo = ""

        for (line in lines) {
            when {
                line.startsWith("#EXTINF:") -> {
                    // Kanal adını al
                    currentName = line.substringAfterLast(",").trim()
                    
                    // Logo URL'sini tırnaklar arasından çek
                    currentLogo = if (line.contains("tvg-logo=\"")) {
                        line.substringAfter("tvg-logo=\"").substringBefore("\"")
                    } else {
                        ""
                    }
                }
                line.startsWith("http") -> {
                    val streamUrl = line.trim()
                    if (currentName.isNotEmpty()) {
                        // BURASI KRİTİK: Yeni değişken isimlerini kullanıyoruz
                        channels.add(Channel(
                            name = currentName, 
                            logoUrl = currentLogo, 
                            streamUrl = streamUrl
                        ))
                    }
                }
            }
        }
        return channels
    }
}
