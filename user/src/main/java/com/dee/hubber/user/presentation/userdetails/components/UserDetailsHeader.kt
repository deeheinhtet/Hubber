package com.dee.hubber.user.presentation.userdetails.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.dee.core_ui.ext.formatWithSuffix
import com.dee.hubber.user.R
import com.dee.hubber.user.domain.model.GitHubUser

@Composable
fun UserDetailsHeader(
    user: GitHubUser,
    onViewGithubUser: () -> Unit = {}
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AsyncImage(
                model = user.avatarUrl,
                contentDescription = "${user.name}'s avatar",
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    user.name.orEmpty(),
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    user.login,
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = Color.Blue.copy(alpha = 0.5f),
                    ),
                    textDecoration = TextDecoration.Underline,
                    modifier = Modifier.clickable {
                        onViewGithubUser()
                    }
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        Icons.Default.Person,
                        contentDescription = "Follower",
                        tint = Color.Gray,
                        modifier = Modifier
                            .size(12.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    val followerAnnotatedString = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.onSurface,
                            )
                        ) {
                            append(user.followers.formatWithSuffix())
                        }
                        withStyle(
                            style = SpanStyle(
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.onSurface,
                            )
                        ) {
                            append(" ${stringResource(R.string.label_follower)}")
                        }
                    }
                    Text(followerAnnotatedString)
                    Text(
                        " • ", style = MaterialTheme.typography.labelSmall.copy(
                            color = MaterialTheme.colorScheme.onSurface,
                        )
                    )
                    val followingAnnotatedString = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.onSurface,
                            )
                        ) {
                            append(user.following.formatWithSuffix())
                        }
                        withStyle(
                            style = SpanStyle(
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                            )
                        ) {
                            append(" ${stringResource(R.string.label_following)}")
                        }
                    }
                    Text(followingAnnotatedString)
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewDetailsHeader() {
    UserDetailsHeader(
        user = GitHubUser(
            name = "Hein Htet",
            login = "Dee",
            id = 1L,
            avatarUrl = "",
            bio = "Bio",
            followers = 1000,
            following = 100,
            publicRepos = 20,
            htmlUrl = ""
        )
    )
}