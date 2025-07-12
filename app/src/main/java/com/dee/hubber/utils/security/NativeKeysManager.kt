package com.dee.hubber.utils.security

object NativeKeysManager {

    init {
        System.loadLibrary("native-keys")
    }

    private external fun loadClientToken(): String
    private external fun loadClientId(): String
    private external fun loadClientSecret(): String

    fun getClientToken() = loadClientToken()
    fun getClientId() = loadClientId()
    fun getClientSecret() = loadClientSecret()

}