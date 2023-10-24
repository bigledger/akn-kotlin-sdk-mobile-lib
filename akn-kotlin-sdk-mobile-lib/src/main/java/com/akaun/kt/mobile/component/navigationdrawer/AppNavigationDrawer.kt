package com.akaun.kt.mobile.component.navigationdrawer

import android.graphics.drawable.Icon
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.akaun.kt.mobile.component.Spacer12
import com.akaun.kt.mobile.component.Spacer8h
import com.akaun.kt.mobile.component.Spacer8v
import com.akaun.kt.mobile.core.sharedpreference.CommonPrefHelper
import com.akaun.kt.mobile.core.sharedpreference.CommonSharedPreferenceConstants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/*
Navigation Drawer Version 2
 */

class AppMenuItem(
    val name: String,
    val icon: ImageVector,
    val onClick: () -> Unit,
    val route: String = ""
)


@Composable
fun AppNavigationDrawer(
    applicationName: String,
    tenantName: String =  CommonPrefHelper.getPrefs(CommonPrefHelper.LOGIN_PREF_NAME)
        .getString(CommonSharedPreferenceConstants.TENANT_CODE_SELECTED, null)
        ?: "No Tenant Selected",
    scope: CoroutineScope,
    drawerState: DrawerState,
    currentRoute: String,
    menuItemList: List<AppMenuItem>,
    onSettings: () -> Unit,
    onPersonalization: () -> Unit,
    onSignOut: () -> Unit,
    content: @Composable () -> Unit
) {
    ModalNavigationDrawer(
        gesturesEnabled = drawerState.isOpen,
        drawerState = drawerState,
        drawerContent = {
            AppDrawer(
                applicationName,
                currentRoute,
                tenantName,
                menuItemList,
                onSettings,
                onPersonalization,
                onSignOut,
                closeDrawer = {
                    scope.launch { drawerState.close() }
                }
            )
        }) {
        content()
    }
}

@Composable
fun AppDrawer(
    applicationName: String,
    currentRoute: String,
    tenantName: String,
    menuItemList: List<AppMenuItem>,
    onSettings: () -> Unit,
    onPersonalization: () -> Unit,
    onSignOut: () -> Unit,
    closeDrawer: () -> Unit
) {
    var selectedItemIndex by remember {
       mutableIntStateOf(0)
    }

    ModalDrawerSheet(modifier = Modifier.requiredWidth(320.dp)) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.secondaryContainer)
                .padding(bottom = 12.dp)
        ) {
            // App Title Display
            Row(
                modifier = Modifier
                    .padding(10.dp)
            ) {
                Text(text = applicationName, fontWeight = FontWeight.Bold, fontSize = 30.sp)
            }

            // Tenant Name
            Row(
                modifier = Modifier
                    .padding(10.dp)
            ) {
                Text(text = tenantName, fontWeight = FontWeight.Medium, fontSize = 20.sp)
            }
        }

        Spacer8v()

        // Menu Items
        menuItemList.forEachIndexed { index, menuItem ->
            NavigationDrawerItem(
                modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
                label = { Text(text = menuItem.name) },
                icon = { Icon(menuItem.icon, null) },
                selected = currentRoute == menuItem.route,
                onClick = {
                    selectedItemIndex = index
                    closeDrawer()
                    menuItem.onClick()
                })
        }

        // Fill remaining space
        Spacer(modifier = Modifier.weight(1f))

        Divider()

        NavigationDrawerItem(
            label = { Text(text = "Settings") },
            selected = false,
            onClick = { onSettings() },
            icon = { Icon(Icons.Default.Settings, null) },
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding))

        NavigationDrawerItem(
            label = { Text(text = "Personalization") },
            selected = false,
            onClick = { onPersonalization() },
            icon = { Icon(Icons.Default.AccountCircle, null) },
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding))

        SignOutDrawerItem(
            signOut = onSignOut,
            closeDrawer = closeDrawer)
    }
}

@Composable
fun SignOutDrawerItem(
    signOut: () -> Unit,
    closeDrawer: () -> Unit
) {
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
                        closeDrawer()
                        signOut()
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

    NavigationDrawerItem(
        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
        label = { Text(text = "Sign Out") },
        icon = { Icon(Icons.Default.ExitToApp, null) },
        selected = false,
        onClick = {
        // Display the confirmation dialog
        showDialog = true
    })
}

@Preview
@Composable
fun PreviewDrawer() {
    val menuItemList = listOf(
        AppMenuItem(
            "My Sessions",
            Icons.Default.List,
            {},
            ""
        ),
        AppMenuItem(
            "All Sessions",
            Icons.Default.List,
            {},
            ""
        ),
        AppMenuItem(
            "My Device",
            Icons.Default.List,
            {},
            ""
        )
    )
    AppDrawer(
        applicationName = "Stock take",
        tenantName = "staging tenant",
        currentRoute = "",
        menuItemList = menuItemList,
        onSettings = { /*TODO*/ },
        onPersonalization = { /*TODO*/ },
        onSignOut = { /*TODO*/ }) {
    }
}
