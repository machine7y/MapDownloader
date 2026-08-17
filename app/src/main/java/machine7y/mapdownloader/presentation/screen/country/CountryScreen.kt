package machine7y.mapdownloader.presentation.screen.country

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import machine7y.mapdownloader.R
import machine7y.mapdownloader.domain.entity.DownloadState
import machine7y.mapdownloader.presentation.component.ProgressBar
import machine7y.mapdownloader.presentation.component.StatusBarBackground
import machine7y.mapdownloader.presentation.entity.RegionUiItem.CountryUiItem
import machine7y.mapdownloader.presentation.modifier.bottomShadow
import machine7y.mapdownloader.presentation.modifier.topShadow
import machine7y.mapdownloader.presentation.screen.country.CountryEvent.OnBackClicked
import machine7y.mapdownloader.presentation.screen.country.CountryEvent.OnItemClicked
import machine7y.mapdownloader.presentation.theme.Black
import machine7y.mapdownloader.presentation.theme.Gray2
import machine7y.mapdownloader.presentation.theme.Gray4
import machine7y.mapdownloader.presentation.theme.OrangeLight
import machine7y.mapdownloader.presentation.theme.Red
import machine7y.mapdownloader.presentation.theme.White

@Composable
fun CountryScreen(
    localRegionId: Int,
) {
    val viewModel = hiltViewModel<CountryViewModel, CountryViewModelFactory>(
        key = localRegionId.toString(),
        creationCallback = { factory -> factory.create(CountryInternalState(localRegionId)) },
    )
    val state by viewModel.stateFlow.collectAsState()
    val context = LocalContext.current
    val noNestedRegionsToastText = stringResource(R.string.countryList_toastNoNestedRegions)

    LaunchedEffect(Unit) {
        viewModel.labelFlow.collect { label ->
            when (label) {
                CountryLabel.ShowNoNestedRegionsMessage ->
                    Toast.makeText(context, noNestedRegionsToastText, Toast.LENGTH_SHORT).show()
            }
        }
    }

    CountryContent(
        state = state,
        onBackClicked = { viewModel.onEvent(OnBackClicked) },
        onItemClicked = { item ->
            viewModel.onEvent(
                OnItemClicked(
                    localRegionId = item.localRegionId,
                    name = item.name,
                    downloadName = item.downloadName,
                    isMap = item.isMap,
                    hasChildren = item.hasChildren,
                )
            )
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CountryContent(
    state: CountryState,
    onBackClicked: () -> Unit,
    onItemClicked: (item: CountryUiItem) -> Unit,
) {
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
        RegionList(
            state = state,
            onItemClicked = onItemClicked,
            modifier = Modifier
                .padding(innerPadding)
                .background(Gray2),
        )
    }
    StatusBarBackground()
}

@Composable
private fun RegionList(
    state: CountryState,
    onItemClicked: (item: CountryUiItem) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .topShadow(2.dp)
            .bottomShadow(4.dp),
    ) {
        itemsIndexed(
            items = state.regionList,
            key = { _, item -> item.name },
        ) { index, item ->
            RegionItem(
                item = item,
                downloadState = state.downloadStates[item.downloadName],
                isDownloaded = item.isMap && state.isDownloadCompleted(item.downloadName),
                isLast = index == state.regionList.lastIndex,
                onItemClicked = onItemClicked,
            )
        }
    }
}

@Composable
private fun RegionItem(
    item: CountryUiItem,
    downloadState: DownloadState?,
    isDownloaded: Boolean,
    isLast: Boolean,
    onItemClicked: (item: CountryUiItem) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(White)
            .clickable { onItemClicked(item) },
    ) {
        Image(
            painter = painterResource(R.drawable.img_map),
            contentDescription = stringResource(R.string.countryList_descriptionMap),
            modifier = Modifier
                .padding(16.dp),
        )
        Column(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .weight(1f)
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f),
                ) {
                    Text(
                        text = item.name,
                        fontSize = 16.sp,
                        letterSpacing = 0.02.em,
                        color = Black,
                    )
                    val fraction = (downloadState as? DownloadState.InProgress)?.fraction
                    if (fraction != null) {
                        ProgressBar(
                            fraction = fraction,
                            modifier = Modifier
                                .padding(top = 6.dp)
                                .height(3.dp),
                        )
                    }
                    if (downloadState == DownloadState.Failed) {
                        Text(
                            text = stringResource(R.string.countryScreen_downloadFailed),
                            fontSize = 13.sp,
                            color = Red,
                        )
                    }
                }
                when {
                    isDownloaded -> Image(
                        painter = painterResource(R.drawable.img_action_remove_dark),
                        contentDescription = stringResource(R.string.countryList_descriptionRemove),
                        modifier = Modifier
                            .padding(16.dp),
                    )
                    item.isMap -> Image(
                        painter = painterResource(R.drawable.img_action_import),
                        contentDescription = stringResource(R.string.countryList_descriptionMap),
                        modifier = Modifier
                            .padding(16.dp),
                    )
                    item.hasChildren -> Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = null,
                        tint = Gray4,
                        modifier = Modifier
                            .padding(16.dp),
                    )
                }
            }
            if (!isLast) {
                HorizontalDivider(
                    thickness = 1.dp,
                    color = Gray2,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    CountryContent(
        state = CountryState(
            name = "Germany",
            regionList = listOf(
                CountryUiItem(
                    localRegionId = 0,
                    name = "Bavaria",
                    downloadName = "bavaria_germany",
                    isMap = false,
                    hasChildren = true,
                ),
                CountryUiItem(
                    localRegionId = 1,
                    name = "Berlin",
                    downloadName = "berlin_germany",
                    isMap = true,
                    hasChildren = false,
                ),
                CountryUiItem(
                    localRegionId = 2,
                    name = "Hamburg",
                    downloadName = "hamburg_germany",
                    isMap = false,
                    hasChildren = false,
                ),
            ),
        ),
        onBackClicked = { },
        onItemClicked = { },
    )
}
