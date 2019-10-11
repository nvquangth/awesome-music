package com.awesomemusic.data.repository

import com.awesomemusic.data.remote.FirestoreHelper
import com.google.firebase.firestore.CollectionReference

class CloudRepository(private val firestoreHelper: FirestoreHelper) {

    fun getPlaylist(): CollectionReference = firestoreHelper.getPlaylist()

    fun getPlaying(): CollectionReference = firestoreHelper.getPlaying()
}