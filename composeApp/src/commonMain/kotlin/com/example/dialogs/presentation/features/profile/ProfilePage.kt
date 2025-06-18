package com.example.dialogs.presentation.features.profile

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.dialogs.presentation.widgets.scaffold.AppPage
import dialogsexample.composeapp.generated.resources.Res
import dialogsexample.composeapp.generated.resources.profile_page_title
import org.jetbrains.compose.resources.stringResource

@Composable
fun ProfilePage(
    component: ProfileComponent,
    modifier: Modifier = Modifier,
) {
    ProfilePage(modifier = modifier)
}

@Composable
fun ProfilePage(
    modifier: Modifier = Modifier,
) {
    AppPage(
        title = stringResource(Res.string.profile_page_title)
    ) {

    }
}