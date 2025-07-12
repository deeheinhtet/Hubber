package com.dee.hubber.user.presentation.userlist.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SearchInputItem(
    onTextChanged: (value: String) -> Unit = {},
) {
    var value by remember { mutableStateOf("") }
    BasicTextField(
        modifier = Modifier.fillMaxWidth(),
        value = value,
        onValueChange = {
            value = it
            onTextChanged(it)
        },
        singleLine = true,
        decorationBox = @Composable { innerTextField ->
            Box(
                modifier = Modifier
                    .height(50.dp)
                    .fillMaxWidth()
                    .background(
                        shape = RoundedCornerShape(8),
                        color = Color.Gray.copy(alpha = 0.2f),
                    )
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize(1f)
                        .padding(
                            vertical = 8.dp,
                            horizontal = 16.dp,
                        ), contentAlignment = Alignment.CenterStart
                ) {
                    if (value.isEmpty()) {
                        Text(
                            text = "Enter username",
                            style = androidx.compose.ui.text.TextStyle(
                                fontSize = 16.sp,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                            )
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(modifier = Modifier.weight(1f)) {
                            innerTextField()
                        }
                        AnimatedVisibility(visible = value.isNotEmpty()) {
                            Icon(
                                Icons.Default.Clear,
                                contentDescription = "Clear Text",
                                modifier = Modifier.clickable {
                                    value = ""
                                    onTextChanged(value)
                                },
                            )
                        }
                    }
                }
            }
        }
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewSearchInputItem() {
    SearchInputItem()
}