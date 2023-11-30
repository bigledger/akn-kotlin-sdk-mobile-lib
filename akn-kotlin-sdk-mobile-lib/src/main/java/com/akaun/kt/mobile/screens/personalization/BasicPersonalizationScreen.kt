package com.akaun.kt.mobile.screens.personalization

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.akaun.kt.mobile.component.button.CommonButtonComponent
import com.akaun.kt.mobile.core.sharedpreference.CommonPrefHelper

@Composable
fun BasicPersonalizationScreenViewModel(
    basicPersonalizationScreenViewModel: BasicPersonalizationScreenViewModel = viewModel(),
    onSignOut : ()->Unit
) {

    val context = LocalContext.current
    LaunchedEffect(Unit){
        Log.d("uri", "ProfileScreen: ${basicPersonalizationScreenViewModel.userProfilePhotoUri.value}")
        if(basicPersonalizationScreenViewModel.userProfilePhotoUri.value == null){
            Log.d("fetch photo", "ProfileScreen: ")
            basicPersonalizationScreenViewModel.getProfilePhoto(context = context)
        }


    }

    val commonSharedPref = CommonPrefHelper.getPrefs(CommonPrefHelper.COMMON_PREF_NAME)


    Column(verticalArrangement = Arrangement.Center) {

        Spacer(modifier = Modifier.height(30.dp))

        Row(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.3f), horizontalArrangement = Arrangement.Center) {

            if(basicPersonalizationScreenViewModel.userProfilePhotoUri.value == null){
                Icon(imageVector = Icons.Filled.AccountCircle, contentDescription = "Profile Icon", modifier = Modifier.fillMaxSize() , tint = Color.Gray )
            }else {
                ProfilePhoto(basicPersonalizationScreenViewModel.userProfilePhotoUri.value!!)
            }

        }

        Spacer(modifier = Modifier.height(20.dp))

        Row(modifier = Modifier.padding(start = 20.dp)) {
            Text(text = "Username : ")

            val username = commonSharedPref.getString("username","NA")
            if(username != null){
                Text(text = username,fontWeight = FontWeight.Light)
            }else {
                Text(text = "NA",fontWeight = FontWeight.Light)
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row(modifier = Modifier.padding(start = 20.dp)) {
            Text(text = "E-mail : ")
            val email = commonSharedPref.getString("email","No registered e-mail")
            if(email != null){
                Text(text = email,fontWeight = FontWeight.Light)
            }else {
                Text(text = "NA",fontWeight = FontWeight.Light)
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row(modifier = Modifier.padding(start = 20.dp)) {
            Text(text = "Phone no. : ")
            val phone = commonSharedPref.getString("phone","No registered phone number")
            if(phone != null){
                Text(text = phone,fontWeight = FontWeight.Light)
            }else {
                Text(text = "NA",fontWeight = FontWeight.Light)
            }
        }

        Spacer(modifier = Modifier.height(50.dp))

        var showDialog by remember { mutableStateOf(false) }

        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text(text = "Sign Out") },
                text = { Text(text = "Are you sure you want to sign out?") },
                confirmButton = {
                    Button(
                        onClick = {
                            // Perform sign out actions here
                            showDialog = false
                            onSignOut()
                        }
                    ) {
                        Text(text = "Yes")
                    }
                },
                dismissButton = {
                    Button(
                        onClick = { showDialog = false },
                        colors = ButtonDefaults.buttonColors(
                            Color.LightGray
                        )
                    ) {
                        Text(text = "No", color = Color.Black)
                    }
                }
            )
        }
        CommonButtonComponent(text = "Sign Out" , modifier = Modifier
            .fillMaxWidth(0.6f)
            .align(Alignment.CenterHorizontally)){
            showDialog = true
        }


    }


}



@Composable
fun ProfilePhoto(uri: Uri) {
    val painter = rememberAsyncImagePainter(
        ImageRequest.Builder(LocalContext.current).data(data = uri).build()
    )

    Image(
        painter = painter,
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(200.dp)// Adjust the size as needed
            .clip(CircleShape) // Clip the image to a circular shape
    )

}