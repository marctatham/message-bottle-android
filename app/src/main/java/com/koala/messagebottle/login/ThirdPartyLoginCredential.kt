package com.koala.messagebottle.login

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ThirdPartyLoginCredential(
    val code: String
) : Parcelable