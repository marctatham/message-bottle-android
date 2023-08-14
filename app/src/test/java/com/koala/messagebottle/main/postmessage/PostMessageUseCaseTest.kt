package com.koala.messagebottle.main.postmessage

import com.koala.messagebottle.common.authentication.domain.IAuthenticationRepository
import com.koala.messagebottle.common.authentication.domain.ProviderType
import com.koala.messagebottle.common.authentication.domain.UserEntity
import com.koala.messagebottle.common.messages.domain.IMessageRepository
import com.koala.messagebottle.main.postmessage.domain.PostMessageResult
import com.koala.messagebottle.main.postmessage.domain.PostMessageUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test

/**
 * Unit tests for [PostMessageUseCase].
 */
class PostMessageUseCaseTest {

    @Test
    fun `when user has not authenticated, they should not be able to post`() = runTest {
        // given
        val messageRepo = mockk<IMessageRepository>()
        val authRepo = mockk<IAuthenticationRepository>()
        val useCase = PostMessageUseCase(authRepo, messageRepo)
        every { authRepo.user.value } returns UserEntity.UnauthenticatedUser

        // when
        val result: PostMessageResult = useCase.postMessage("This should not be allowed")

        // then
        Assert.assertEquals(PostMessageResult.Unauthenticated, result)
    }


    @Test
    fun `when user has authenticated, they are able to post anything they want`() = runTest {
        // given
        val messageRepo = mockk<IMessageRepository>()
        val authRepo = mockk<IAuthenticationRepository>()
        val useCase = PostMessageUseCase(authRepo, messageRepo)
        val authenticatedUser =
            UserEntity.AuthenticatedUser(ProviderType.Google, "myFakeToken", "myFakeUserId")
        every { authRepo.user.value } returns authenticatedUser
        coEvery { messageRepo.postMessage(any()) } returns Unit

        // when
        val result: PostMessageResult =
            useCase.postMessage("I should be allowed to post my message")

        // then
        coVerify(exactly = 1) { messageRepo.postMessage(any()) }
        Assert.assertTrue(result is PostMessageResult.Success)
    }
}