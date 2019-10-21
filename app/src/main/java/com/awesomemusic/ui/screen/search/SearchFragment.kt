package com.awesomemusic.ui.screen.search

import android.app.Activity
import android.app.Fragment
import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import com.awesomemusic.R
import com.awesomemusic.data.model.Video
import com.awesomemusic.data.remote.Network
import com.awesomemusic.data.repository.VideoRepository
import com.awesomemusic.ui.base.ItemVideoClickListenter
import com.awesomemusic.ui.screen.playlist.VideoAdapter
import kotlinx.android.synthetic.main.fragment_loadmore_refresh.*
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment: Fragment(), SearchContract.View {

    companion object {
        const val TAG = "SearchFragment"
        fun newInstance() = SearchFragment()
    }

    private lateinit var adapter: VideoAdapter
    private lateinit var itemClickListener: ItemVideoClickListenter
    private var presenter: SearchPresenter? = null

    override fun onAttach(activity: Activity?) {
        super.onAttach(activity)
        if (activity is ItemVideoClickListenter) {
            itemClickListener = activity
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater?,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater?.inflate(R.layout.fragment_search, container, false)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        presenter = SearchPresenter(
            view = this,
            repository = VideoRepository(
                api = Network().createMovieApi()
            )
        )

        adapter = VideoAdapter(::onItemVideoClick)
        recycler_video?.adapter = adapter

        text_search?.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val q = v.text.toString()
                if (!TextUtils.isEmpty(q)) {
                    presenter?.searchVideo(q)
                }
            }
            true
        }

        btnClearQuery?.setOnClickListener {
            presenter?.clearQuery()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        hideKeyBoard()
    }

    override fun showVideos(videos: List<Video>) {
        hideKeyBoard()
        adapter.submitList(videos)
    }

    override fun showQuery(q: String) {
        text_search?.setText(q)
    }

    override fun showLoadingIndicator() {
        loading_indicator?.visibility = View.VISIBLE
    }

    override fun hideLoadingIndicator() {
        loading_indicator?.visibility = View.GONE
    }

    override fun showError(throwable: Throwable) {

    }

    private fun onItemVideoClick(video: Video) {
        itemClickListener.onItemClick(video)
    }

    private fun hideKeyBoard() {
        val imm =
            activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view!!.windowToken, 0)
    }

    private fun showKeyBoard() {
        val imm =
            activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
    }
}