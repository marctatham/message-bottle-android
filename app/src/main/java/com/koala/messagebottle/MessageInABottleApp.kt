package com.koala.messagebottle


import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.koala.messagebottle.login.LoginScreen
import com.koala.messagebottle.main.getmessage.GetMessageScreen
import com.koala.messagebottle.main.home.HomeScreen
import com.koala.messagebottle.main.postmessage.ui.LoginRequiredToPostScreen
import com.koala.messagebottle.main.postmessage.ui.PostScreen
import com.koala.messagebottle.main.viewmessages.ViewMessagesScreen

@Composable
fun MessageInABottleApp() {
    val navController = rememberNavController()
    val viewModel: AppNavigationStateViewModel = hiltViewModel()
    val appState: AppState by viewModel.state.collectAsStateWithLifecycle()
    MessageInABottleAppNavHost(navController = navController, appState)
}

@Composable
fun MessageInABottleAppNavHost(
    navController: NavHostController,
    appState: AppState,
) {
    val backHandler: () -> Unit = { navController.popBackStack() }
    NavHost(navController = navController, startDestination = Screen.HOME) {
        composable(Screen.HOME) {
            HomeScreen(
                onGetMessageHandler = { navController.navigate(Screen.GET_MESSAGES) },
                onPostMessageHandler = { navController.navigate(Flow.POST_MESSAGE_FLOW) },
            )
        }
        composable(Screen.GET_MESSAGES) { GetMessageScreen(backHandler) }
        postGraphFlow(navController, appState)
        composable(Screen.VIEW_MESSAGES) { ViewMessagesScreen() }
        composable(Screen.LOGIN) { LoginScreen() }
    }
}

fun NavGraphBuilder.postGraphFlow(
    navController: NavHostController, appState: AppState
) {

    navigation(
        // TODO: roll back this change when I no longer need a hook into the login screen
        //startDestination = if (appState.isAuthenticated) Screen.POST_MESSAGE else Screen.POST_MESSAGE_EDUCATIONAL,
        startDestination = Screen.POST_MESSAGE_EDUCATIONAL,
        route = Flow.POST_MESSAGE_FLOW,
    ) {
        composable(Screen.POST_MESSAGE_EDUCATIONAL) {
            val backHandler: () -> Unit = { navController.popBackStack() }
            LoginRequiredToPostScreen(
                onProceedHandler = { navController.navigate(Screen.LOGIN) },
                onBackHandler = backHandler,
                onCancelHandler = backHandler
            )
        }
        composable(Screen.POST_MESSAGE) { PostScreen() }
    }
}
