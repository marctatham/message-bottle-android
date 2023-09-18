package com.koala.messagebottle


import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.koala.messagebottle.main.getmessage.GetMessageScreen
import com.koala.messagebottle.main.home.HomeScreen
import com.koala.messagebottle.main.login.LoginRequiredToPostScreen
import com.koala.messagebottle.main.postmessage.ui.PostScreen
import com.koala.messagebottle.main.viewmessages.ViewMessagesScreen

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
        composable(Screen.POST_MESSAGE) { PostScreen() }
        composable(Screen.VIEW_MESSAGES) { ViewMessagesScreen() }
    }
}
