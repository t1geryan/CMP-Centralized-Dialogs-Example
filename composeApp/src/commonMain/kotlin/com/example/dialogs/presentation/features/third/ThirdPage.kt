package com.example.dialogs.presentation.features.third

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.dialogs.presentation.features.dialogs.DialogsPresenterComponent
import com.example.dialogs.presentation.features.dialogs.DialogsPresenterPane
import com.example.dialogs.presentation.widgets.scaffold.AppPage
import com.example.dialogs.presentation.widgets.spacing.Expanded
import dialogsexample.composeapp.generated.resources.Res
import dialogsexample.composeapp.generated.resources.third_page_instruction
import dialogsexample.composeapp.generated.resources.third_page_title
import org.jetbrains.compose.resources.stringResource

@Composable
fun ThirdPage(
    component: ThirdComponent,
    modifier: Modifier = Modifier,
) {
    ThirdPage(
        dialogPresenterComponent = component.dialogsPresenter,
        onNavigateBack = component.onNavigateBack,
        modifier = modifier,
    )
}

@Composable
fun ThirdPage(
    dialogPresenterComponent: DialogsPresenterComponent,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    AppPage(
        title = stringResource(Res.string.third_page_title),
        onNavigateBack = onNavigateBack,
        modifier = modifier,
    ) { paddingsValue ->
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize().padding(paddingsValue),
        ) {
            Text(
                text = stringResource(Res.string.third_page_instruction),
            )
            Expanded()
            DialogsPresenterPane(
                component = dialogPresenterComponent,
            )
        }
    }
}