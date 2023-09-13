package com.koala.messagebottle


import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.koala.messagebottle.login.LoginViewScreen
import com.koala.messagebottle.main.getmessage.GetMessageView
import com.koala.messagebottle.main.home.HomeView
import com.koala.messagebottle.main.postmessage.ui.PostScreen
import com.koala.messagebottle.main.viewmessages.ViewMessagesScreen

@Composable
fun MessageInABottleApp() {
    val navController = rememberNavController()
    MessageInABottleAppNavHost(navController = navController)
}

@Composable
fun MessageInABottleAppNavHost(
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeView(
                onSignInHandler = { navController.navigate("login") },
                onGetMessageHandler = { navController.navigate("getMessage") },
                onPostMessageHandler = { navController.navigate("postMessage") },
                onViewMessageHandler = { navController.navigate("viewMessages") },
            )
        }
        composable("getMessage") { GetMessageView() }
        composable("postMessage") { PostScreen() }
        composable("viewMessages") { ViewMessagesScreen() }
        composable("login") { LoginViewScreen() }
    }
}
