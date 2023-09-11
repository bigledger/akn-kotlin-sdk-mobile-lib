package com.akaun.kt.mobile.component.navigationdrawer


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.akaun.kt.mobile.component.appbar.MainTopAppBar
import com.akaun.kt.mobile.core.sharedpreference.CommonPrefHelper
import com.akaun.kt.mobile.core.sharedpreference.CommonSharedPreferenceConstants
import com.akaun.kt.mobile.destination.AuthGraph
import com.akaun.kt.mobile.destination.MainAppGraph
import com.akaun.kt.mobile.destination.Personalization
import kotlinx.coroutines.launch

class MenuItem(
    val name: String,
    val onClick: () -> Unit
)


@Composable
fun CustomModalNavigationDrawer(
    screenComposable: @Composable () -> Unit,
    menuItemsList: List<MenuItem>,
    navController: NavHostController,
    selectedItemIndexState: MutableState<Int>
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed);
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            NavigationDrawerSheet(
                menuItemList = menuItemsList,
                applicationName = "Stock Transfer Application",
                tenantName = CommonPrefHelper.getPrefs(CommonPrefHelper.LOGIN_PREF_NAME)
                    .getString(CommonSharedPreferenceConstants.TENANT_CODE_SELECTED, null)
                    ?: "No Tenant Selected",
                selectedItemIndexState = selectedItemIndexState,
                navController = navController
            ) {
                scope.launch { drawerState.close() }
            }

        }
    ) {
        Scaffold(
            topBar = {
                MainTopAppBar(navController) { scope.launch { drawerState.open() } }
            },
            content = {
                Box(Modifier.padding(it)) {
                    screenComposable()
                }
            }
        )
    }
}

@Composable
fun NavigationDrawerSheet(
    menuItemList: List<MenuItem>,
    applicationName: String,
    tenantName: String,
    selectedItemIndexState: MutableState<Int>,
    navController: NavHostController,
    closeNavigationDrawer: () -> Unit
) {
    ModalDrawerSheet() {

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

        // Menu Items
        menuItemList.forEachIndexed { index, menuItem ->
            NavigationDrawerItem(label = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = menuItem.name)
                }
            },
                selected = selectedItemIndexState.value == index,
                onClick = {
                    selectedItemIndexState.value = index
                    closeNavigationDrawer()
                    menuItem.onClick()

                })
        }

        // Fill remaining space
        Spacer(modifier = Modifier.weight(1f))

        // Common modifier for Settings,Personalization and Sign out
        val commonModifier = Modifier.padding(4.dp)
            .height(40.dp)
            .fillMaxWidth()


        // Common Menu Items
        // Additional rows at the very bottom of the drawer
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.Bottom

        ) {
            Row(modifier = commonModifier
                .clickable {
                    closeNavigationDrawer()
                }) {
                Icon(imageVector = Icons.Default.Settings, contentDescription = "Settings")
                Spacer(modifier = Modifier.width(5.dp))
                Text(text = "Settings")
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(modifier = commonModifier
                .clickable {
                    closeNavigationDrawer()
                    navController.navigate(Personalization.route)
                }
            ) {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "Personalization"
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(text = "Personalization")
            }

            Spacer(modifier = Modifier.height(10.dp))

            SignOutRow(modifier = commonModifier, navController = navController) {
                closeNavigationDrawer()
            }
        }
    }
}

@Composable
fun SignOutRow(
    modifier: Modifier = Modifier,
    navController: NavController,
    closeNavigationDrawer: () -> Unit
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
                        closeNavigationDrawer()
                        navController.navigate(AuthGraph.route) {
                            popUpTo(MainAppGraph.route)
                        }
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

    Row(
        modifier = modifier
            .clickable {
                // Display the confirmation dialog
                showDialog = true
            }
    ) {
        Icon(imageVector = Icons.Default.ExitToApp, contentDescription = "Sign out")
        Spacer(modifier = Modifier.width(5.dp))
        Text(text = "Sign Out")
    }
}