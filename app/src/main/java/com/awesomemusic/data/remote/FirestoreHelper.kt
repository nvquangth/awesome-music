package com.awesomemusic.data.remote

import com.google.firebase.firestore.CollectionReference

interface FirestoreHelper {
    fun getPlaylist(): CollectionReference

    fun getPlaying(): CollectionReference
}