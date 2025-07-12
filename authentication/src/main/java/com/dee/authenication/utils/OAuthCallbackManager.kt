package com.dee.authenication.utils

import android.net.Uri
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OAuthCallbackManager @Inject constructor() {

    private var _oAuthUri: Uri? = null
    val oAuthUri: Uri?
        get() = _oAuthUri

    fun setOAuthUri(uri: Uri) {
        _oAuthUri = uri
    }


    fun clearOAuthUri() {
        _oAuthUri = null
    }
}
