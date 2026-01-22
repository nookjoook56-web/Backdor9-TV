package com.example.backdor9.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.backdor9.Channel
import com.example.backdor9.databinding.ItemChannelBinding

class ChannelAdapter(
    private val channelList: List<Channel>,
    private val onItemClick: (Channel) -> Unit
) : RecyclerView.Adapter<ChannelAdapter.ChannelViewHolder>() {

    class ChannelViewHolder(val binding: ItemChannelBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChannelViewHolder {
        val binding = ItemChannelBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ChannelViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChannelViewHolder, position: Int) {
        val channel = channelList[position]
        
        holder.binding.channelName.text = channel.name

        Glide.with(holder.itemView.context)
            .load(channel.logoUrl)
            .into(holder.binding.channelLogo)

        // "İzle" butonu tıklama olayı
        holder.binding.btnIzle.setOnClickListener {
            onItemClick(channel)
        }
        
        // Kartın geneline tıklama olayı
        holder.itemView.setOnClickListener {
            onItemClick(channel)
        }
    }

    override fun getItemCount(): Int = channelList.size
}
