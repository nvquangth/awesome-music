package com.awesomemusic.ui.screen.search

import androidx.recyclerview.widget.DiffUtil
import com.awesomemusic.R
import com.awesomemusic.data.model.Video
import com.awesomemusic.databinding.ItemVideoSearchBinding
import com.awesomemusic.ui.base.BaseRecyclerAdapter

class SearchVideoAdapter(private val listener: (Video, Int) -> Unit) :
    BaseRecyclerAdapter<Video, ItemVideoSearchBinding>(object : DiffUtil.ItemCallback<Video>() {
        override fun areContentsTheSame(oldItem: Video, newItem: Video): Boolean =
            oldItem.videoId == newItem.videoId

        override fun areItemsTheSame(oldItem: Video, newItem: Video): Boolean = oldItem == newItem
    }) {
    override fun getLayoutRes(viewType: Int): Int = R.layout.item_video_search

    override fun bindFirstTime(binding: ItemVideoSearchBinding) {
        super.bindFirstTime(binding)
    }

    override fun bindView(binding: ItemVideoSearchBinding, item: Video, position: Int) {
        super.bindView(binding, item, position)
        binding.apply {
            root.setOnClickListener {
                item?.apply {
                    listener.invoke(this, position)
                }
            }
        }
    }

    fun removeVideo(video: Video) {
        val newData = data?.filter {
            video.videoId != it.videoId
        }
        submitList(newData)
    }
}