package com.akaun.kt.mobile.destination

interface Destinations {
    val route: String
    val title : String
}

// Authorization Graph
object AuthGraph: Destinations{
    override val route = "Auth graph"
    override val title = "Auth graph"
}

object Login: Destinations{
    override val route = "login"
    override val title = "Login Page"
}

object ForgotPassword: Destinations{
    override val route = "Forgot Password"
    override val title = "Forgot Password"
}

object Register: Destinations{
    override val route = "Register"
    override val title = "Register"
}

object ResendVerification : Destinations {
    override val route = "ResendVerification"
    override val title = "Resend Verification"
}


// Splash Graph
object Splash: Destinations{
    override val route = "splash"
    override val title = "Splash Page"
}

object SplashGraph: Destinations{
    override val route = "Splash graph"
    override val title = "Splash graph"
}

// Main App Graph
object MainAppGraph : Destinations {
    override val route = "MainAppGraph"
    override val title = "Main App Graph"
}