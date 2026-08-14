package machine7y.mapdownloader.presentation.screen.countrylist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import machine7y.mapdownloader.R
import machine7y.mapdownloader.presentation.component.MemoryIndicator
import machine7y.mapdownloader.presentation.component.StatusBarBackground
import machine7y.mapdownloader.presentation.theme.OrangeLight
import machine7y.mapdownloader.presentation.theme.White

@Composable
fun CountryListScreen(
    onClicked: () -> Unit,
) {
    val viewModel = hiltViewModel<CountryListViewModel>()
    val state by viewModel.stateFlow.collectAsState()

    CountryListContent(
        state = state,
        onClicked = onClicked,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CountryListContent(state: CountryListState, onClicked: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.countryList_title),
                        color = White,
                        fontSize = 20.sp,
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = OrangeLight,
                    titleContentColor = White,
                ),
            )
        },
        modifier = Modifier
            .clickable(
                onClick = onClicked,
            )
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            MemoryIndicator(
                title = stringResource(R.string.countryList_deviceMemoryTitle),
                freeSpaceLabel = stringResource(R.string.countryList_deviceMemoryFreeLabel, state.memory.freeGb),
                usedFraction = state.memory.usedFraction,
            )
        }
    }
    StatusBarBackground()
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    CountryListScreen(
        onClicked = { },
    )
}
