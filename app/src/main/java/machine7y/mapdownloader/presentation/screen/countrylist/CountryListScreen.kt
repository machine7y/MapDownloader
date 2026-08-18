package machine7y.mapdownloader.presentation.screen.countrylist

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import machine7y.mapdownloader.R
import machine7y.mapdownloader.presentation.component.MemoryIndicator
import machine7y.mapdownloader.presentation.component.StatusBarBackground
import machine7y.mapdownloader.presentation.entity.MemoryUi
import machine7y.mapdownloader.presentation.entity.RegionUiItem
import machine7y.mapdownloader.presentation.entity.RegionUiItem.ContinentUiItem
import machine7y.mapdownloader.presentation.entity.RegionUiItem.CountryUiItem
import machine7y.mapdownloader.presentation.modifier.bottomShadow
import machine7y.mapdownloader.presentation.modifier.topShadow
import machine7y.mapdownloader.presentation.screen.countrylist.CountryListEvent.OnCountryClicked
import machine7y.mapdownloader.presentation.theme.Black
import machine7y.mapdownloader.presentation.theme.Gray
import machine7y.mapdownloader.presentation.theme.Gray3
import machine7y.mapdownloader.presentation.theme.OrangeLight
import machine7y.mapdownloader.presentation.theme.White

@Composable
fun CountryListScreen() {
    val viewModel = hiltViewModel<CountryListViewModel>()
    val state by viewModel.stateFlow.collectAsState()
    val context = LocalContext.current
    val noNestedRegionsToastText = stringResource(R.string.countryList_toastNoNestedRegions)

    LaunchedEffect(Unit) {
        viewModel.onEvent(CountryListEvent.OnScreenOpened)
    }
    LaunchedEffect(Unit) {
        viewModel.labelFlow.collect { label ->
            when (label) {
                CountryListLabel.ShowNoNestedRegionsMessage ->
                    Toast.makeText(context, noNestedRegionsToastText, Toast.LENGTH_SHORT).show()
            }
        }
    }

    CountryListContent(
        state = state,
        onCountryClicked = { localRegionId, hasChildren ->
            viewModel.onEvent(OnCountryClicked(localRegionId, hasChildren))
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CountryListContent(
    state: CountryListState,
    onCountryClicked: (localRegionId: Int, hasChildren: Boolean) -> Unit,
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
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Gray),
        ) {
            MemoryIndicator(
                title = stringResource(R.string.countryList_deviceMemoryTitle),
                freeSpaceLabel = stringResource(R.string.countryList_deviceMemoryFreeLabel, state.memory.freeGb),
                usedFraction = state.memory.usedFraction,
                modifier = Modifier
                    .shadow(elevation = 4.dp),
            )
            Spacer(
                modifier = Modifier
                    .height(24.dp),
            )
            RegionList(state.regionList, onCountryClicked)
        }
    }
    StatusBarBackground()
}

@Composable
private fun RegionList(
    itemList: List<RegionUiItem>,
    onCountryClicked: (localRegionId: Int, hasChildren: Boolean) -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .topShadow(2.dp)
            .bottomShadow(4.dp)
    ) {
        itemsIndexed(
            items = itemList,
            key = { _, item -> item.localRegionId },
            contentType = { _, item -> item::class },
        ) { index, item ->
            when (item) {
                is ContinentUiItem -> ContinentItem(item)
                is CountryUiItem -> CountryItem(
                    item = item,
                    isLast = index == itemList.lastIndex,
                    onCountryClicked = onCountryClicked,
                )
            }
        }
    }
}

@Composable
private fun ContinentItem(item: ContinentUiItem) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(White)
            .height(54.dp),
    ) {
        Text(
            text = item.name,
            fontSize = 16.sp,
            color = Black,
            letterSpacing = 0.03.em,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 16.dp),
        )
    }
}

@Composable
private fun CountryItem(
    item: CountryUiItem,
    isLast: Boolean,
    onCountryClicked: (localRegionId: Int, hasChildren: Boolean) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(White)
            .clickable { onCountryClicked(item.localRegionId, item.hasChildren) },
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
                Text(
                    text = item.name,
                    fontSize = 16.sp,
                    letterSpacing = 0.02.em,
                    color = Black,
                    modifier = Modifier
                        .weight(1f),
                )
                when {
                    item.isMap -> Image(
                        painter = painterResource(R.drawable.img_action_import),
                        contentDescription = stringResource(R.string.countryList_descriptionMap),
                        modifier = Modifier
                            .padding(16.dp),
                    )
                    item.hasChildren -> Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = null,
                        tint = Gray3,
                        modifier = Modifier
                            .padding(16.dp),
                    )
                }
            }
            if (!isLast) {
                HorizontalDivider(
                    thickness = 1.dp,
                    color = Gray,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    CountryListContent(
        state = CountryListState(
            memory = MemoryUi(
                freeGb = 12.5f,
                usedFraction = 0.6f,
            ),
            regionList = listOf(
                ContinentUiItem(localRegionId = 1, name = "Europe"),
                CountryUiItem(
                    localRegionId = 2, name = "Ukraine",
                    downloadName = "ukraine",
                    isMap = false,
                    hasChildren = true,
                ),
                CountryUiItem(
                    localRegionId = 3,
                    name = "Poland",
                    downloadName = "poland",
                    isMap = true,
                    hasChildren = false,
                ),
                ContinentUiItem(
                    localRegionId = 4,
                    name = "Asia",
                ),
                CountryUiItem(
                    localRegionId = 5,
                    name = "Japan",
                    downloadName = "japan",
                    isMap = false,
                    hasChildren = false,
                ),
            ),
        ),
        onCountryClicked = { _, _ -> },
    )
}
