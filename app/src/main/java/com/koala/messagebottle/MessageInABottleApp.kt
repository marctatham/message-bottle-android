package com.koala.messagebottle


import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.koala.messagebottle.feature.getmessage.GetMessageScreen
import com.koala.messagebottle.feature.home.HomeScreen
import com.koala.messagebottle.feature.login.LoginRequiredToPostScreen
import com.koala.messagebottle.feature.postmessage.ui.PostMessageScreen
import com.koala.messagebottle.feature.viewmessages.ViewMessagesScreen

@Composable
fun MessageInABottleApp() {
    val navController = rememberNavController()
    val viewModel: AppNavigationStateViewModel = hiltViewModel()
    val backHandler: () -> Unit = { navController.popBackStack() }
    val homeHandler: () -> Unit = {
        navController.navigate(Screen.HOME) {
            popUpTo(Screen.HOME) {
                inclusive = true // Pop up to the "home" destination, including "home" itself
            }
        }
    }

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
        composable(Screen.GET_MESSAGES) { GetMessageScreen(homeHandler) }
        composable(Screen.POST_MESSAGE_EDUCATIONAL) {
            LoginRequiredToPostScreen(
                onProceedHandler = {
                    navController.popBackStack(Screen.POST_MESSAGE_EDUCATIONAL, inclusive = true)
                    navController.navigate(Screen.POST_MESSAGE)
                },
                onBackHandler = backHandler,
                onCancelHandler = homeHandler
            )
        }
        composable(Screen.POST_MESSAGE) {
            PostMessageScreen(
                onBackHandler = backHandler,
                onHomeHandler = homeHandler,
            )
        }
        composable(Screen.VIEW_MESSAGES) { ViewMessagesScreen() }
    }
}
