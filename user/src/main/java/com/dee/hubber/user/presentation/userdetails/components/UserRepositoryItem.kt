package com.dee.hubber.user.presentation.userdetails.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dee.core_ui.ext.formatWithSuffix
import com.dee.hubber.user.R
import com.dee.hubber.user.domain.model.GithubUserRepository


@Composable
fun UserRepositoryItem(
    repo: GithubUserRepository,
    onItemClicked: () -> Unit = {},
) {
    ListItem(
        modifier = Modifier.clickable {
            onItemClicked()
        },
        colors = ListItemDefaults.colors(
            containerColor = MaterialTheme.colorScheme.background
        ), headlineContent = {
            Column {
                Text(
                    repo.repoName,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                )
                if (repo.repoDescription.isNotEmpty()) {
                    Text(
                        repo.repoDescription,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (repo.language.isNotEmpty()) {
                        Text(
                            "(${repo.language})", style = TextStyle(
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.onSurface,
                            )
                        )
                        Spacer(modifier = Modifier.width(24.dp))
                    }
                    Image(
                        painter = painterResource(R.drawable.ic_star),
                        modifier = Modifier.size(12.dp),
                        contentDescription = "Star Icon"
                    )
                    val starAnnotatedString = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.onSurface,
                            )
                        ) {
                            append(repo.star.formatWithSuffix())
                        }
                        withStyle(
                            style = SpanStyle(
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                            )
                        ) {
                            append(" ${stringResource(R.string.label_star)}")
                        }
                    }
                    Text(starAnnotatedString)
                    Spacer(modifier = Modifier.width(16.dp))
                    Image(
                        painter = painterResource(R.drawable.ic_fork),
                        modifier = Modifier.size(12.dp),
                        contentDescription = "Star Icon"
                    )
                    val forkAnnotatedString = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.onSurface,
                            )
                        ) {
                            append(repo.forkCount.formatWithSuffix())
                        }
                        withStyle(
                            style = SpanStyle(
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                            )
                        ) {
                            append(" ${stringResource(R.string.label_fork)}")
                        }
                    }
                    Text(forkAnnotatedString)

                    Spacer(modifier = Modifier.width(16.dp))

                    Image(
                        painter = painterResource(R.drawable.ic_eye),
                        modifier = Modifier.size(12.dp),
                        contentDescription = "Star Icon"
                    )
                    val watchAnnotatedString = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.onSurface,
                            )
                        ) {
                            append(repo.watcherCount.formatWithSuffix())
                        }
                        withStyle(
                            style = SpanStyle(
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                            )
                        ) {
                            append(" ${stringResource(R.string.label_watch)}")
                        }
                    }
                    Text(watchAnnotatedString)
                }
            }
        })
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewUserRepositoryItem() {
    UserRepositoryItem(
        GithubUserRepository(
            repoName = "Hello World",
            repoDescription = "This is repos description",
            star = 10000,
            forkCount = 100,
            repoUrl = "url",
            id = 1L,
            language = "Kotlin",
            watcherCount = 1
        )
    )
}