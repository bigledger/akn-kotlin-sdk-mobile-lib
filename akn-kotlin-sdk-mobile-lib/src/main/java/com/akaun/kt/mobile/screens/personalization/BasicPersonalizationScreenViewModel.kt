package com.akaun.kt.mobile.screens.personalization

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akaun.kt.mobile.core.sharedpreference.CommonPrefHelper
import com.akaun.kt.mobile.di.UserProfileModule
import com.akaun.kt.mobile.repository.UserProfileRepository
import com.akaun.kt.mobile.utils.responseBodyToUri
import com.akaun.kt.sdk.utils.wrapper.DataOrException
import kotlinx.coroutines.launch
import okhttp3.ResponseBody


class BasicPersonalizationScreenViewModel : ViewModel() {

    private val userProfileRepo = UserProfileRepository(UserProfileModule.provideUserProfileApi())

    val userProfilePhoto : MutableState<DataOrException<ResponseBody, Boolean, Exception>> =
        mutableStateOf(DataOrException(null, true, null))

    val userProfilePhotoUri = mutableStateOf<Uri?>(null)


    fun getProfilePhoto(context : Context){
        viewModelScope.launch {
            val commonSharedPref = CommonPrefHelper.getPrefs(CommonPrefHelper.COMMON_PREF_NAME)
            val extGuid = commonSharedPref.getString("photoExtGuid",null)

            if (extGuid != null) {
                userProfilePhoto.value = userProfilePhoto.value.copy(loading = true)
                val results = userProfileRepo.getUserProfilePhoto(extGuid = extGuid)

                userProfilePhoto.value = userProfilePhoto.value.copy(data = results.data)
                userProfilePhoto.value = userProfilePhoto.value.copy(e = results.e)

                userProfilePhotoUri.value = results.data?.let { responseBodyToUri(context, it, extGuid) }

                userProfilePhoto.value = userProfilePhoto.value.copy(loading = false)


            }



        }

    }






}