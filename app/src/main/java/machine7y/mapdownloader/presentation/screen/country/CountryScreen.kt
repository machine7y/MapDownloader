package machine7y.mapdownloader.presentation.screen.country

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import machine7y.mapdownloader.R
import machine7y.mapdownloader.presentation.component.StatusBarBackground
import machine7y.mapdownloader.presentation.theme.OrangeLight
import machine7y.mapdownloader.presentation.theme.White

@Composable
fun CountryScreen(
    localRegionId: Int,
    onBackClicked: () -> Unit,
) {
    val viewModel = hiltViewModel<CountryViewModel, CountryViewModelFactory>(
        key = localRegionId.toString(),
        creationCallback = { factory -> factory.create(CountryInternalState(localRegionId)) },
    )
    val state by viewModel.stateFlow.collectAsState()

    CountryContent(
        state = state,
        onBackClicked = onBackClicked,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CountryContent(state: CountryState, onBackClicked: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = state.name,
                        color = White,
                        fontSize = 20.sp,
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBackClicked,
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.countryScreen_descriptionBack),
                            tint = White,
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = OrangeLight,
                    titleContentColor = White,
                )
            )
        }
    ) { innerPadding ->

    }
    StatusBarBackground()
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    CountryContent(
        state = CountryState(name = "Germany"),
        onBackClicked = { },
    )
}
