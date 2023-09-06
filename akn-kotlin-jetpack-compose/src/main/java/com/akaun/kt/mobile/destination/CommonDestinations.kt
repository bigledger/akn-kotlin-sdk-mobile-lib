package com.akaun.kt.mobile.destination

// AUTHORIZATION GRAPH
object AuthGraph: Destination{
    override val route = "Auth graph"
    override val title = "Auth graph"
}

object Login: Destination{
    override val route = "login"
    override val title = "Login Page"
}

object ForgotPassword: Destination{
    override val route = "Forgot Password"
    override val title = "Forgot Password"
}

object Register: Destination{
    override val route = "Register"
    override val title = "Register"
}

object ResendVerification : Destination {
    override val route = "ResendVerification"
    override val title = "Resend Verification"
}


// SPLASH GRAPH

object SplashGraph: Destination{
    override val route = "Splash graph"
    override val title = "Splash graph"
}
object Splash: Destination{
    override val route = "splash"
    override val title = "Splash Page"
}

// MAIN APP GRAPH
object MainAppGraph : Destination {
    override val route = "MainAppGraph"
    override val title = "Main App Graph"
}