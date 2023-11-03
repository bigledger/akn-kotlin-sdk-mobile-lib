package com.akaun.kt.mobile.widget

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

sealed class TableCellContent {
    data class TextContent(val text: String) : TableCellContent()
    data class ButtonContent(val icon: ImageVector, val onClick: () -> Unit) : TableCellContent()
    data class StatusContent(val text : String, val boxColor : Color, val textColor : Color) : TableCellContent()
}

@Composable
fun Table(
    modifier : Modifier = Modifier,
    data: List<List<TableCellContent>>,
    columnHeaders: List<String>,
    columnSize: List<Float>,
    rowClickable: Boolean = true,
    textAlignment: List<TextAlign> = emptyList(),
    onClick: (Int) -> Unit  = {},

    ) {

    Column(modifier = modifier) {
        TableHeaderRow(
            columnHeaders = columnHeaders,
            columnSize =  columnSize,
            textAlignment = textAlignment)

        Divider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 1.dp, // Line thickness
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f) // Line color
        )
        LazyColumn() {
            itemsIndexed(data) { index, rowData ->
                TableRow(rowData, columnSize, index,rowClickable) {
                    onClick(it)
                }
            }
        }

    }

}

@Composable
fun TableHeaderRow(columnHeaders: List<String>, textAlignment: List<TextAlign> = emptyList(), columnSize: List<Float>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        columnHeaders.forEachIndexed { index,data ->
            Box(
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth()
                    .weight(columnSize[index])
            ) {
                Text(
                    text = data,
                    textAlign = if(textAlignment.isEmpty()){
                        TextAlign.Left
                    }else {
                        textAlignment[index]
                    },
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth()

                )
            }
        }
    }
    Divider(
        modifier = Modifier.fillMaxWidth(),
        thickness = 1.dp, // Line thickness
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f) // Line color
    )
}


@Composable
fun TableRow(
    rowData: List<TableCellContent>,
    columnSize: List<Float>,
    rowIndex: Int,
    rowClickable : Boolean,
    onRowClicked: (Int) -> Unit = {}
) {

    val commonModifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)
    Row(
        modifier = if (rowClickable) {
            commonModifier
                .clickable { onRowClicked(rowIndex) }
        } else {
            commonModifier
        },
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        rowData.forEachIndexed { index,data ->
            Box(
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth()
                    .weight(columnSize[index]),
            ) {
                when (data) {
                    is TableCellContent.TextContent -> {
                        Text(
                            text = data.text,
                            textAlign = TextAlign.Left
                        )
                    }

                    is TableCellContent.ButtonContent -> {
                        IconButton(
                            onClick = data.onClick,
                            modifier = Modifier
                                .padding(0.dp)
                                .fillMaxHeight()
                        ) {
                            Icon(
                                imageVector = data.icon,
                                contentDescription = null,
                            )
                        }
                    }

                    is TableCellContent.StatusContent -> {
                        Box(
                            modifier = Modifier
                                .background(
                                    color = data.boxColor,
                                    shape = RoundedCornerShape(50)
                                )
                                .padding(8.dp)
                                .fillMaxWidth(), // Adjust padding as needed
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = data.text , fontSize = 9.sp, color = data.textColor , fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            }
        }
    }
    Divider(
        modifier = Modifier.fillMaxWidth(),
        thickness = 1.dp, // Line thickness
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f) // Line color
    )
}