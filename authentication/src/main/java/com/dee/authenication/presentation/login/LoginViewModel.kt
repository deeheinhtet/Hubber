package com.dee.authenication.presentation.login

import android.net.Uri
import androidx.core.net.toUri
import androidx.lifecycle.viewModelScope
import com.dee.authenication.domain.model.AccessTokenRequest
import com.dee.authenication.domain.model.AuthState
import com.dee.authenication.domain.usecase.GetAccessTokenUseCase
import com.dee.authenication.utils.OAuthCallbackManager
import com.dee.common.AppSharedPreference
import com.dee.core_network.base.NetworkException
import com.dee.core_ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Named


@HiltViewModel
class LoginViewModel @Inject constructor(
    @Named("DEFAULT_CLIENT_TOKEN") private val defaultClientToken: String,
    @Named("GITHUB_CLIENT_ID") private val clientId: String,
    @Named("GITHUB_CLIENT_SECRET") private val clientSecret: String,
    @Named("GITHUB_REDIRECT_URL") private val redirectUrl: String,
    private val getAccessTokenUseCase: GetAccessTokenUseCase,
    private val appSharedPreference: AppSharedPreference,
    private val oAuthCallbackManager: OAuthCallbackManager,
) : BaseViewModel() {
    private val _uiState = MutableStateFlow(LoginUiState())

    fun onInitCheckOAuthUri() {
        oAuthCallbackManager.oAuthUri?.let {
            handleOAuthCallback(it)
            oAuthCallbackManager.clearOAuthUri()
        }
    }

    fun onGenerateAuthorizationUrl(scope: String = "user repo") {
        val url = "https://github.com/login/oauth/authorize?" +
                "client_id=$clientId&" +
                "redirect_uri=$redirectUrl&" +
                "scope=$scope&" +
                "state=${generateState()}"
        Timber.d("AUTH_URL $url")
        emitEvent(LoginEvents.OnOAuthStart(url.toUri()))
    }

    private fun generateState(): String {
        return java.util.UUID.randomUUID().toString()
    }

    private fun handleOAuthCallback(uri: Uri) {
        val code = uri.getQueryParameter("code")
        Timber.d("AUTH_CODE $code")
        viewModelScope.launch {
            showLoading()
            val result = getAccessTokenUseCase.invoke(
                AccessTokenRequest(
                    clientId = clientId,
                    clientSecret = clientSecret,
                    code = code.orEmpty()
                )
            )
            when {
                result.isSuccess -> {
                    _uiState.value = _uiState.value.copy(
                        authState = AuthState.AUTHORIZED_USER,
                    )
                    hideLoading()
                    emitEvent(LoginEvents.OnOAuthLoginSuccess)
                }

                result.isFailure -> {
                    hideLoading()
                    val exception = result.exceptionOrNull() as? NetworkException
                    emitEvent(LoginEvents.OnOAuthLoginFailed(exception?.message.orEmpty()))
                }
            }
        }
    }


    fun onStartExploreWithDefaultToken() {
        appSharedPreference.token = defaultClientToken
        emitEvent(LoginEvents.OnOAuthLoginSuccess)
    }
}