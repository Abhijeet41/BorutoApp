package com.abhi41.borutoapp.presentation.screen.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.abhi41.borutoapp.ui.theme.SMALL_PADDING
import com.abhi41.borutoapp.ui.theme.titleColor

@Composable
fun OrderedList(
    title: String,
    items: List<String>,
    textColor: Color
) {
    Column {
        Text(
            modifier = Modifier.padding(bottom = SMALL_PADDING),
            text = title,
            color = textColor,
            fontSize = MaterialTheme.typography.subtitle1.fontSize,
            fontWeight = FontWeight.Bold
        )
        items.forEachIndexed { index, item ->
            if (index < 5) {
                Text(
                    modifier = Modifier.alpha(ContentAlpha.medium),
                    text = "${index + 1}. $item", //our index start from 0 thats why se add plus 1
                    color = textColor,
                    fontSize = MaterialTheme.typography.body1.fontSize,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OrderedListPreview() {
    OrderedList(
        title = "Family",
        items = listOf("Minator", "Kushina"),
        textColor = MaterialTheme.colors.titleColor
    )
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun OrderedListDarkPreview() {
    OrderedList(
        title = "Family",
        items = listOf("Minator", "Kushina"),
        textColor = MaterialTheme.colors.titleColor
    )
}