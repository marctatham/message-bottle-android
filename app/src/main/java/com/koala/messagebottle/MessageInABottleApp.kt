package com.koala.messagebottle


import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.koala.messagebottle.app.getmessage.GetMessageScreen
import com.koala.messagebottle.app.home.HomeScreen
import com.koala.messagebottle.app.login.LoginRequiredToPostScreen
import com.koala.messagebottle.app.postmessage.ui.PostMessageScreen
import com.koala.messagebottle.app.viewmessages.ViewMessagesScreen

@Composable
fun MessageInABottleApp() {
    val navController = rememberNavController()
    val viewModel: AppNavigationStateViewModel = hiltViewModel()
    val backHandler: () -> Unit = { navController.popBackStack() }
    NavHost(navController = navController, startDestination = Screen.HOME) {
        composable(Screen.HOME) {
            HomeScreen(
                onGetMessageHandler = { navController.navigate(Screen.GET_MESSAGES) },
                onPostMessageHandler = {
                    val destination: String = viewModel.onNavigateToPostMessage()
                    navController.navigate(destination)
                },
            )
        }
        composable(Screen.GET_MESSAGES) { GetMessageScreen(backHandler) }
        composable(Screen.POST_MESSAGE_EDUCATIONAL) {
            LoginRequiredToPostScreen(
                onProceedHandler = { navController.navigate(Screen.POST_MESSAGE) },
                onBackHandler = backHandler,
                onCancelHandler = backHandler
            )
        }
        composable(Screen.POST_MESSAGE) {
            PostMessageScreen(
                onBackHandler = backHandler,
                onCompletionHandler = {
                    navController.navigate(Screen.HOME) {
                        popUpTo("home") {
                            // Pop up to the "home" destination, including "home" itself
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable(Screen.VIEW_MESSAGES) { ViewMessagesScreen() }
    }
}
