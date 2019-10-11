package com.awesomemusic.ui.screen.playlist

import androidx.recyclerview.widget.DiffUtil
import com.awesomemusic.R
import com.awesomemusic.data.model.Video
import com.awesomemusic.databinding.ItemVideoBinding
import com.awesomemusic.ui.base.BaseRecyclerAdapter

class VideoAdapter(private val listener: (Video) -> Unit) :
    BaseRecyclerAdapter<Video, ItemVideoBinding>(object : DiffUtil.ItemCallback<Video>() {
        override fun areContentsTheSame(oldItem: Video, newItem: Video): Boolean =
            oldItem.videoId == newItem.videoId

        override fun areItemsTheSame(oldItem: Video, newItem: Video): Boolean = oldItem == newItem
    }) {
    override fun getLayoutRes(viewType: Int): Int = R.layout.item_video

    override fun bindFirstTime(binding: ItemVideoBinding) {
        super.bindFirstTime(binding)
        binding.apply {
            root.setOnClickListener {
                item?.apply {
                    listener.invoke(this)
                }
            }
        }
    }
}