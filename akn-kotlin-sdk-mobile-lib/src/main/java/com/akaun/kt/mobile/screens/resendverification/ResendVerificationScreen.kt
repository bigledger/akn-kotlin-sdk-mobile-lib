
package com.akaun.kt.mobile.screens.resendverification
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.akaun.kt.mobile.R
import com.akaun.kt.mobile.component.button.CommonButtonComponent
import com.akaun.kt.mobile.component.inputfield.LoginTextFieldComponent
import com.akaun.kt.mobile.component.multimedia.LogoComponent
import com.akaun.kt.mobile.component.text.BasicTextComponent
import com.akaun.kt.mobile.component.text.TitleTextComponent
import com.akaun.kt.mobile.destination.Login


@Composable
fun ResendVerificationScreen(navController: NavHostController) {

    val emailState = rememberSaveable { mutableStateOf("") }
    val mobileCodeState = rememberSaveable { mutableStateOf("") }
    val mobileNumberState = rememberSaveable { mutableStateOf("") }

    val emailChosenState = rememberSaveable {
        mutableStateOf(true)
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LogoComponent(width = 200.dp, height = 50.dp)

                Spacer(Modifier.height(10.dp))
                
                TitleTextComponent(value = "Request for New Verification")

                Spacer(Modifier.height(10.dp))


                Row(){
                    RadioButton(
                        selected = emailChosenState.value,
                        onClick = { emailChosenState.value = true },
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )

                    Text(text = "Email" , modifier = Modifier.align(Alignment.CenterVertically))

                    RadioButton(
                        selected = !emailChosenState.value,
                        onClick = { emailChosenState.value = false },
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )

                    Text(text = "Mobile Number" , modifier = Modifier.align(Alignment.CenterVertically))


                }

                if(emailChosenState.value){
                    LoginTextFieldComponent(valueState = emailState,
                        label = stringResource(R.string.email)
                    )
                } else {

                    Row(){
                        // TODO : Use string resource for label
                        LoginTextFieldComponent(valueState = mobileCodeState,
                            label = "Code",
                            modifier = Modifier.width(90.dp)

                        )
                        
                        Spacer(modifier = Modifier.width(10.dp))

                        // TODO : Use string resource for label
                        LoginTextFieldComponent(valueState = mobileNumberState,
                            label = "Mobile no" ,
                        )
                    }


                }

                Spacer(Modifier.height(10.dp))

                CommonButtonComponent(text = "Submit",modifier = Modifier.width(280.dp))

                Spacer(Modifier.height(10.dp))

                Row(modifier = Modifier.padding(15.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically) {
                    BasicTextComponent(value = stringResource(id = R.string.go_to_login))
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(text = stringResource(id = R.string.sign_in),
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.clickable {
                            navController.navigate(Login.route)
                        })
                }


            }
        }
    }
}
