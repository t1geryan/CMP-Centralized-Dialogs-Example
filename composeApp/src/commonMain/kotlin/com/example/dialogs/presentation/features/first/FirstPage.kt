package com.example.dialogs.presentation.features.first

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.dialogs.presentation.widgets.spacing.Spacer
import dialogsexample.composeapp.generated.resources.Res
import dialogsexample.composeapp.generated.resources.first_page_next_button_title
import dialogsexample.composeapp.generated.resources.first_page_title
import org.jetbrains.compose.resources.stringResource

@Composable
fun FirstPage(
    component: FirstComponent,
    modifier: Modifier = Modifier,
) {
    FirstPage(
        onNextClicked = component::onNextClicked,
        modifier = modifier,
    )
}

@Composable
fun FirstPage(
    onNextClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
    ) {
        Text(
            text = stringResource(Res.string.first_page_title),
        )
        Spacer(12.dp)
        Button(onClick = onNextClicked) {
            Text(
                text = stringResource(Res.string.first_page_next_button_title)
            )
        }
    }
}