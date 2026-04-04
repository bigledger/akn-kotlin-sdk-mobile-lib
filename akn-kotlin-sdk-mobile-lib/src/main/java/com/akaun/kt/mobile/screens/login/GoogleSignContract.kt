package com.akaun.kt.mobile.screens.login

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.activity.result.contract.ActivityResultContract
import androidx.annotation.CallSuper
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException

class GoogleSignContract(
    private val client: GoogleSignInClient
): ActivityResultContract<Void?, GoogleLoginResult>() {
    @CallSuper
    override fun createIntent(context: Context, input: Void?): Intent {
        return client.signInIntent
    }

    override fun getSynchronousResult(
        context: Context,
        input: Void?
    ): SynchronousResult<GoogleLoginResult>? = null

    override fun parseResult(resultCode: Int, intent: Intent?): GoogleLoginResult {
        Log.d("CONTRACT RESULT", "resultCode: $resultCode")
        return when (resultCode) {
            Activity.RESULT_OK -> if (intent != null) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(intent)
                try {
                    val account = task.getResult(ApiException::class.java)
                    Log.d("GOOGLE SUCCESS", "Authorization with google account.id: ${account.id}\"")
                    Log.d("GOOGLE SUCCESS", "Authorization with google account.idToken: ${account.idToken}\"")
                    Log.d("GOOGLE SUCCESS", "Authorization with google account.serverAuthCode: ${account.serverAuthCode}\"")
                    GoogleLoginResult.Success(account.idToken)
                } catch (e: ApiException) {
                    Log.d("GOOGLE FAIL", "Google sign in failed with status code: ${e.statusCode}, message: ${e.message}")
                    GoogleLoginResult.Error("Google Sign In Failed (code: ${e.statusCode})")
                }
            } else {
                GoogleLoginResult.Error("Google Sign In Empty Intent")
            }
            Activity.RESULT_CANCELED -> {
                // Try to extract more info from the intent even on cancel
                if (intent != null) {
                    try {
                        val task = GoogleSignIn.getSignedInAccountFromIntent(intent)
                        task.getResult(ApiException::class.java)
                    } catch (e: ApiException) {
                        Log.d("GOOGLE CANCELED", "Canceled with ApiException status code: ${e.statusCode}, message: ${e.message}")
                        return GoogleLoginResult.Error("Google Sign In failed (code: ${e.statusCode})")
                    }
                }
                Log.d("GOOGLE CANCELED", "User canceled with no additional info")
                GoogleLoginResult.UserCanceled
            }
            else -> GoogleLoginResult.Error("General Error (resultCode: $resultCode)")
        }
    }

}