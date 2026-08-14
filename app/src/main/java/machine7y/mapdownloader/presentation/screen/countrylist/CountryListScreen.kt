package machine7y.mapdownloader.presentation.screen.countrylist

import androidx.compose.foundation.clickable
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import machine7y.mapdownloader.R
import machine7y.mapdownloader.presentation.component.StatusBarBackground
import machine7y.mapdownloader.presentation.theme.OrangeLight
import machine7y.mapdownloader.presentation.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CountryListScreen(
    onClicked: () -> Unit,
) {
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

    }
    StatusBarBackground()
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    CountryListScreen(
        onClicked = { }
    )
}
