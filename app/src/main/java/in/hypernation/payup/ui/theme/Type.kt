package `in`.hypernation.payup.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import `in`.hypernation.payup.R

// Set of Material typography styles to start with

val DMSansFamily = FontFamily(
        Font(R.font.dmsans_regular, weight = FontWeight.Normal),
        Font(R.font.dmsans_medium, weight = FontWeight.Medium),
        Font(R.font.dmsans_semibold, weight = FontWeight.SemiBold),
        Font(R.font.dmsans_bold, weight = FontWeight.Bold)
)

val Typography = Typography(
        bodyLarge = TextStyle(
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                lineHeight = 24.sp,
                letterSpacing = 0.5.sp
        ),
        titleMedium = TextStyle(
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.SemiBold,
                fontSize = 24.sp,
                color = Color.Black
        ),

        displayLarge = TextStyle(
                fontFamily = DMSansFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 48.sp
        ),

        headlineSmall = TextStyle(
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.SemiBold,
                fontSize = 28.sp,
                color = Color.Black
        ),


        /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)

