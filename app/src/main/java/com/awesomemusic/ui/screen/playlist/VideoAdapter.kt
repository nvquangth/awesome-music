package com.awesomemusic.ui.screen.playlist

import android.content.Context
import android.view.View
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import com.awesomemusic.R
import com.awesomemusic.data.model.Video
import com.awesomemusic.databinding.ItemVideoBinding
import com.awesomemusic.ui.base.BaseRecyclerAdapter
import kotlinx.android.synthetic.main.item_video.view.*

class VideoAdapter(
    private val context: Context,
    private val removeItemListener: (Video) -> Unit,
    private val listener: (Video) -> Unit
) :
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

    override fun bindView(binding: ItemVideoBinding, item: Video, position: Int) {
        super.bindView(binding, item, position)
        binding.apply {
            root.btn_more_video.setOnClickListener {
                showPopupMenu(it, item)
            }
        }
    }

    private fun showPopupMenu(view: View, video: Video) {
        PopupMenu(context, view).apply {
            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.action_remove -> {
                        removeItemListener.invoke(video)
                        true
                    }
                    else -> {
                        false
                    }
                }
            }
            inflate(R.menu.menu_item_video_playlist)
            show()
        }
    }
}