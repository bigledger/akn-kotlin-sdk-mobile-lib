package com.akaun.kt.mobile.component.appbar

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.akaun.kt.mobile.R
import com.akaun.kt.mobile.component.BackArrow
import com.akaun.kt.mobile.core.sharedpreference.CommonPrefHelper
import com.akaun.kt.mobile.core.sharedpreference.CommonSharedPreferenceConstants
import com.akaun.kt.mobile.destination.Loading

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BackTopBar(
    title: String,
    actions: @Composable() (RowScope.() -> Unit) = { }) {
    TopAppBar(
        title = {
            Text(text = title)
        },
        navigationIcon = {
            BackArrow()
        },
        actions = actions
        )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TitleTopBar(
    title: String,
    openDrawer: () -> Unit,
    refreshNewTenant: () -> Unit
    ) {
    TopAppBar(
        title = {
            Text(text = title)
        },
        navigationIcon = {
            IconButton(onClick = { openDrawer() }) { // Call the provided lambda
                Icon(Icons.Default.Menu, contentDescription = null)
            }

        },
        actions ={
            TenantMenu(refreshNewTenant)
        })
}




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopAppBar(navController: NavHostController,
                  openDrawer : () -> Unit) {

    TopAppBar(
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.akaunsvg),
                    contentDescription = "akaunLogo",
                    modifier = Modifier
                        .size(120.dp)
                        .padding(end = 8.dp)
                )
            }
        },
        navigationIcon = {
            IconButton(onClick = { openDrawer() }) { // Call the provided lambda
                Icon(Icons.Default.Menu, contentDescription = "Open Drawer")
            }

        },
        actions ={
            TenantListMenu(navController)
        }
    )
}


@Composable
fun TenantListMenu(navController: NavHostController) {
    val expanded = remember { mutableStateOf(false) }

    val sharedPreferences = CommonPrefHelper.getPrefs(CommonPrefHelper.LOGIN_PREF_NAME)
    val serializedTenantCodes = sharedPreferences.getString(CommonSharedPreferenceConstants.TENANT_CODE_LIST, null)
    val serializedTenantGuids = sharedPreferences.getString(CommonSharedPreferenceConstants.TENANT_GUID_LIST, null)

    IconButton(
        onClick = { expanded.value = !expanded.value }
    ) {
        Icon(
            imageVector = Icons.Default.MoreVert,
            contentDescription = "More options"
        )
    }

    DropdownMenu(
        expanded = expanded.value,
        onDismissRequest = { expanded.value = false }
    ) {
        if(serializedTenantCodes == null){
            DropdownMenuItem(text = { Text("No Tenants Found") } , onClick = {} , enabled = false )
        }else {
            val tenantCodeList: List<String> = serializedTenantCodes.split(",")
            tenantCodeList.forEachIndexed { index,  tenantCode ->
                DropdownMenuItem(
                    text = { Text(text = tenantCode) },
                    onClick = {
                        sharedPreferences.edit().putString(CommonSharedPreferenceConstants.TENANT_CODE_SELECTED,tenantCode).apply()

                        // Set the tenantGuid to new tenantGuid that corresponds to the new tenantCode selected
                        val tenantGuidList: List<String> = serializedTenantGuids?.split(",") ?: listOf()
                        val correspondingTenantGuid = tenantGuidList[index]
                        sharedPreferences.edit().putString(CommonSharedPreferenceConstants.TENANT_GUID_SELECTED,correspondingTenantGuid).apply()
                        expanded.value = false
                        navController.navigate(Loading.route)
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopBar(title: String,
               openDrawer : () -> Unit,
               refreshNewTenant: () -> Unit) {
    TopAppBar(
        title = {
            Text(text = title)
        },
        navigationIcon = {
            IconButton(onClick = { openDrawer() }) { // Call the provided lambda
                Icon(Icons.Default.Menu, contentDescription = "Open Drawer")
            }

        },
        actions ={
            TenantMenu(refreshNewTenant)
        }
    )
}

// Newer
@Composable
fun TenantMenu(refreshNewTenant: () -> Unit) {
    val expanded = remember { mutableStateOf(false) }

    val sharedPreferences = CommonPrefHelper.getPrefs(CommonPrefHelper.LOGIN_PREF_NAME)
    val serializedTenantCodes = sharedPreferences.getString(CommonSharedPreferenceConstants.TENANT_CODE_LIST, null)
    val serializedTenantGuids = sharedPreferences.getString(CommonSharedPreferenceConstants.TENANT_GUID_LIST, null)

    IconButton(
        onClick = { expanded.value = !expanded.value }
    ) {
        Icon(
            imageVector = Icons.Default.MoreVert,
            contentDescription = "More options"
        )
    }

    DropdownMenu(
        expanded = expanded.value,
        onDismissRequest = { expanded.value = false }
    ) {
        if(serializedTenantCodes == null){
            DropdownMenuItem(text = { Text("No Tenants Found") } , onClick = {} , enabled = false )
        }else {
            val tenantCodeList: List<String> = serializedTenantCodes.split(",")
            tenantCodeList.forEachIndexed { index,  tenantCode ->
                DropdownMenuItem(
                    text = { Text(text = tenantCode) },
                    onClick = {
                        sharedPreferences.edit().putString(CommonSharedPreferenceConstants.TENANT_CODE_SELECTED,tenantCode).apply()

                        // Set the tenantGuid to new tenantGuid that corresponds to the new tenantCode selected
                        val tenantGuidList: List<String> = serializedTenantGuids?.split(",") ?: listOf()
                        val correspondingTenantGuid = tenantGuidList[index]
                        sharedPreferences.edit().putString(CommonSharedPreferenceConstants.TENANT_GUID_SELECTED,correspondingTenantGuid).apply()
                        expanded.value = false
                        refreshNewTenant()
                    }
                )
            }
        }
    }
}


//@Composable
//fun TenantListMenu(navController: NavHostController,
//                   getTntListSharedPref: () -> String?,
//                   onClick: () -> Unit) {
//    val expanded = remember { mutableStateOf(false) }
//
//    val serializedTenantCodes = getTntListSharedPref()
//
//    IconButton(
//        onClick = { expanded.value = !expanded.value }
//    ) {
//        Icon(
//            imageVector = Icons.Default.MoreVert,
//            contentDescription = "More options"
//        )
//    }
//
//    DropdownMenu(
//        expanded = expanded.value,
//        onDismissRequest = { expanded.value = false }
//    ) {
//        if (serializedTenantCodes == null){
//            DropdownMenuItem(text = { Text("No Tenants Found") } , onClick = {} , enabled = false )
//        }else {
//            val tenantCodeList: List<String> = serializedTenantCodes.split(",")
//            tenantCodeList.forEach { tenantCode ->
//                DropdownMenuItem(
//                    text = { Text(text = tenantCode) },
//                    onClick = {
//                        sharedPreferences.edit().putString("tenantCodeSelected",tenantCode).apply()
//
//                        // Set the tenantGuid to new tenantGuid that corresponds to the new tenantCode selected
//                        val tenantGuidList: List<String> = serializedTenantGuids?.split(",") ?: listOf()
//                        val correspondingTenantGuid = tenantGuidList[index]
//                        sharedPreferences.edit().putString("tenantGuidSelected",correspondingTenantGuid).apply()
//                        navController.navigate(Loading.route)
//                    }
//                )
//            }
//        }
//    }
//}

