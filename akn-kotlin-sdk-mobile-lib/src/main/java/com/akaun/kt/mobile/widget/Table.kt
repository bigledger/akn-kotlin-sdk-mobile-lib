package com.akaun.kt.mobile.widget

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
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

sealed class TableCellContent {
    data class TextContent(val text: String) : TableCellContent()
    data class ButtonContent(val icon: ImageVector, val onClick: () -> Unit) : TableCellContent()
}

@Composable
fun Table(
    modifier : Modifier = Modifier,
    data: List<List<TableCellContent>>,
    columnHeaders: List<String>,
    columnSize: List<Float>,
    rowClickable: Boolean = true,
    onClick: (Int) -> Unit  = {},

    ) {

    Column(modifier = modifier) {
        TableHeaderRow(columnHeaders, columnSize)

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
fun TableHeaderRow(rowData: List<String>, columnSize: List<Float>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        var columnSizeIndex = 0
        rowData.forEach { data ->
            Box(
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth()
                    .weight(columnSize[columnSizeIndex])
            ) {
                Text(
                    text = data,
                    textAlign = TextAlign.Start
                )
            }
            columnSizeIndex += 1
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
        var columnSizeIndex = 0
        rowData.forEach { data ->
            Box(
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth()
                    .weight(columnSize[columnSizeIndex])
            ) {
                when (data) {
                    is TableCellContent.TextContent -> {
                        Text(
                            text = data.text,
                            textAlign = TextAlign.Start
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
                }
            }
            columnSizeIndex += 1
        }
    }
    Divider(
        modifier = Modifier.fillMaxWidth(),
        thickness = 1.dp, // Line thickness
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f) // Line color
    )
}