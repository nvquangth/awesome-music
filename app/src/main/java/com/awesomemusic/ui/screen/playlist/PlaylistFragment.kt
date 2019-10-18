package com.awesomemusic.ui.screen.playlist

import android.app.Activity
import android.app.Fragment
import android.app.FragmentTransaction
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.awesomemusic.R
import com.awesomemusic.data.model.Video
import com.awesomemusic.data.remote.FirestoreHelperImpl
import com.awesomemusic.data.repository.CloudRepository
import com.awesomemusic.ui.base.ItemVideoClickListenter
import com.awesomemusic.ui.screen.player.PlayerFragment
import kotlinx.android.synthetic.main.fragment_playlist.*

class PlaylistFragment : Fragment(), PlaylistContract.View {

    companion object {
        const val TAG = "PlaylistFragment"

        fun newInstance() = PlaylistFragment()
    }

    private lateinit var adapter: VideoAdapter
    private lateinit var itemClickListener: ItemVideoClickListenter
    lateinit var presenter: PlaylistPresenter

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
        val view = inflater?.inflate(R.layout.fragment_playlist, container, false)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        presenter = PlaylistPresenter(
            view = this,
            cloudRepository = CloudRepository(firestoreHelper = FirestoreHelperImpl())
        )

        adapter = VideoAdapter(::onItemVideoClick)

        recyclerPlaylist.adapter = adapter

        presenter.apply {
            getPlaylist()
            listeningPlaylist()
        }

    }

    override fun showPlaylist(playlist: List<Video>) {
        adapter.submitList(playlist)
    }

    override fun showLoadingIndicator() {
    }

    override fun hideLoadingIndicator() {
    }

    override fun showError(throwable: Throwable) {
    }

    private fun onItemVideoClick(video: Video) {
        itemClickListener.onItemClick(video)
    }

    private fun addFragment(
        fragment: Fragment, TAG: String?, addToBackStack: Boolean = false
    ) {
        activity.fragmentManager.beginTransaction().add(R.id.container, fragment, TAG).apply {
            commitTransaction(this, addToBackStack)
        }
    }

    private fun replaceFragment(
        fragment: Fragment, TAG: String?, addToBackStack: Boolean = false
    ) {
        activity.fragmentManager.beginTransaction().replace(R.id.container, fragment, TAG).apply {
            commitTransaction(this, addToBackStack)
        }
    }

    private fun findFragment(TAG: String): Fragment? {
        return activity.fragmentManager.findFragmentByTag(TAG)
    }

    private fun commitTransaction(
        transaction: FragmentTransaction, addToBackStack: Boolean = false
    ) {
        if (addToBackStack) transaction.addToBackStack(null)
        transaction.commit()
    }

}