package com.awesomemusic.data.remote

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

class FirestoreHelperImpl : FirestoreHelper {
    companion object {
        const val PLAYLIST_COLLECTION_REF = "playlist"
        const val PLAYING_COLLECTION_REF = "playing"
    }

    private val db = FirebaseFirestore.getInstance()

    override fun getPlaylist(): CollectionReference = db.collection(PLAYLIST_COLLECTION_REF)

    override fun getPlaying(): CollectionReference = db.collection(PLAYING_COLLECTION_REF)
}