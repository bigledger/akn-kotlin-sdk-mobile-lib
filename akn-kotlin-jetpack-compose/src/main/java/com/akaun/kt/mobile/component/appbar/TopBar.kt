package com.akaun.kt.mobile.component.appbar

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BackTopBar(title: String, onClick: () -> Unit) {
    TopAppBar(
        title = {
            Text(text = title)
        },
        navigationIcon = {
            IconButton(onClick = { onClick() }) { // Call the provided lambda
                Icon(Icons.Default.ArrowBack, contentDescription = "Go back")
            }
        })
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopAppBar(navController : NavHostController,
                  openDrawer : () -> Unit,
                  tenantMenu: @Composable () -> Unit
                  ) {

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
            tenantMenu()
        }
    )
}

@Composable
fun TenantListMenu(navController: NavHostController,
                   getTntListSharedPref: () -> String?,
                   onClick: () -> Unit) {
    val expanded = remember { mutableStateOf(false) }

    val serializedTenantCodes = getTntListSharedPref()

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
        if (serializedTenantCodes == null){
            DropdownMenuItem(text = { Text("No Tenants Found") } , onClick = {} , enabled = false )
        }else {
            val tenantCodeList: List<String> = serializedTenantCodes.split(",")
            tenantCodeList.forEach { tenantCode ->
                DropdownMenuItem(
                    text = { Text(text = tenantCode) },
                    onClick = {
                        onClick()
                    }
                )
            }
        }
    }
}