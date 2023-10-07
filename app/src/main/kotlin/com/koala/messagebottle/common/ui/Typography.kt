/*
 * Copyright 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.koala.messagebottle.common.ui

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.unit.sp
import com.koala.messagebottle.R


val provider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)

val RobotoSlabFont = GoogleFont(name = "RobotoSlab")

val RobotoSlabFontFamily = FontFamily(
    Font(googleFont = RobotoSlabFont, fontProvider = provider),
    Font(resId = R.font.robotoslab_regular),

    Font(googleFont = RobotoSlabFont, fontProvider = provider, weight = FontWeight.Black),
    Font(resId = R.font.robotoslab_black, weight = FontWeight.Black),

    Font(googleFont = RobotoSlabFont, fontProvider = provider, weight = FontWeight.Bold),
    Font(resId = R.font.robotoslab_bold, weight = FontWeight.Bold),

    Font(googleFont = RobotoSlabFont, fontProvider = provider, weight = FontWeight.ExtraBold),
    Font(resId = R.font.robotoslab_extrabold, weight = FontWeight.ExtraBold),

    Font(googleFont = RobotoSlabFont, fontProvider = provider, weight = FontWeight.ExtraLight),
    Font(resId = R.font.robotoslab_extralight, weight = FontWeight.ExtraLight),

    Font(googleFont = RobotoSlabFont, fontProvider = provider, weight = FontWeight.Light),
    Font(resId = R.font.robotoslab_light, weight = FontWeight.Light),

    Font(googleFont = RobotoSlabFont, fontProvider = provider, weight = FontWeight.Medium),
    Font(resId = R.font.robotoslab_medium, weight = FontWeight.Medium),

    Font(googleFont = RobotoSlabFont, fontProvider = provider, weight = FontWeight.Normal),
    Font(resId = R.font.robotoslab_regular, weight = FontWeight.Normal),

    Font(googleFont = RobotoSlabFont, fontProvider = provider, weight = FontWeight.SemiBold),
    Font(resId = R.font.robotoslab_semibold, weight = FontWeight.SemiBold),

    Font(googleFont = RobotoSlabFont, fontProvider = provider, weight = FontWeight.Thin),
    Font(resId = R.font.robotoslab_thin, weight = FontWeight.Thin),
)

val bottlingTypography = Typography(
    displayLarge = TextStyle(
        fontFamily = RobotoSlabFontFamily,
        fontWeight = FontWeight.Black,
        fontSize = 57.sp,
        lineHeight = 64.sp,
        letterSpacing = 0.sp
    ),
    displayMedium = TextStyle(
        fontFamily = RobotoSlabFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 45.sp,
        lineHeight = 52.sp,
        letterSpacing = 0.sp
    ),
    displaySmall = TextStyle(
        fontFamily = RobotoSlabFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 36.sp,
        lineHeight = 44.sp,
        letterSpacing = 0.sp
    ),
    headlineLarge = TextStyle(
        fontFamily = RobotoSlabFontFamily,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 32.sp,
        lineHeight = 40.sp,
        letterSpacing = 0.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = RobotoSlabFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp,
        lineHeight = 36.sp,
        letterSpacing = 0.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = RobotoSlabFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.sp
    ),
    titleLarge = TextStyle(
        fontFamily = RobotoSlabFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    titleMedium = TextStyle(
        fontFamily = RobotoSlabFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.15.sp
    ),
    titleSmall = TextStyle(
        fontFamily = RobotoSlabFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = RobotoSlabFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.15.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = RobotoSlabFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.25.sp
    ),
    bodySmall = TextStyle(
        fontFamily = RobotoSlabFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.4.sp
    ),
    labelLarge = TextStyle(
        fontFamily = RobotoSlabFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp
    ),
    labelMedium = TextStyle(
        fontFamily = RobotoSlabFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    ),
    labelSmall = TextStyle(
        fontFamily = RobotoSlabFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
)
