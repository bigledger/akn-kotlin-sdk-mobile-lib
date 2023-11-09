package com.akaun.kt.mobile.screens.login

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.activity.result.contract.ActivityResultContract
import androidx.annotation.CallSuper
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException

class GoogleSignContract(
    private val client: GoogleSignInClient
): ActivityResultContract<Void?, String?>() {
    @CallSuper
    override fun createIntent(context: Context, input: Void?): Intent {
        return client.signInIntent
    }

    override fun getSynchronousResult(
        context: Context,
        input: Void?
    ): SynchronousResult<String?>? = null

    override fun parseResult(resultCode: Int, intent: Intent?): String? {
        Log.d("CONTRACT RESULT", "resultCode: $resultCode")
        intent.takeIf { resultCode == Activity.RESULT_OK }?.apply {
            val task = GoogleSignIn.getSignedInAccountFromIntent(this)
            try {
                val account = task.getResult(ApiException::class.java)
                Log.d("GOOGLE SUCCESS", "Authorization with google account.id: ${account.id}\"")
                Log.d("GOOGLE SUCCESS", "Authorization with google account.idToken: ${account.idToken}\"")
                Log.d("GOOGLE SUCCESS", "Authorization with google account.serverAuthCode: ${account.serverAuthCode}\"")
                return account.idToken
            } catch (e: ApiException) {
                Log.d("GOOGLE FAIL", "Google sign in failed")
            }
        }
        return null
    }

}