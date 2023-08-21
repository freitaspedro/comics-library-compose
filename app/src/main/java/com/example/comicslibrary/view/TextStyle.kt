package com.example.comicslibrary.view

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

object TextStyle {
    val SmallMinus = TextStyle(fontSize = 8.sp)
    val Small = TextStyle(fontSize = 12.sp)
    val Medium = TextStyle(fontSize = 20.sp)
    val Large = TextStyle(fontSize = 30.sp)
    val BoldSmallMinus = TextStyle(fontSize = 8.sp, fontWeight = FontWeight.Bold)
    val BoldSmall = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.Bold)
    val BoldMedium = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold)
    val BoldLarge = TextStyle(fontSize = 30.sp, fontWeight = FontWeight.Bold)
    val ItalicSmallMinus = TextStyle(fontSize = 8.sp, fontStyle = FontStyle.Italic)
    val ItalicSmall = TextStyle(fontSize = 12.sp, fontStyle = FontStyle.Italic)
    val ItalicMedium = TextStyle(fontSize = 20.sp, fontStyle = FontStyle.Italic)
    val ItalicLarge = TextStyle(fontSize = 30.sp, fontStyle = FontStyle.Italic)
}