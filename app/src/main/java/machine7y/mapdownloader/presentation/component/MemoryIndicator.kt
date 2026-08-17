package machine7y.mapdownloader.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import machine7y.mapdownloader.presentation.theme.Black
import machine7y.mapdownloader.presentation.theme.Gray
import machine7y.mapdownloader.presentation.theme.OrangeLight
import machine7y.mapdownloader.presentation.theme.White

@Composable
fun MemoryIndicator(
    title: String,
    freeSpaceLabel: String,
    usedFraction: Float,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(White),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 15.dp,
                    end = 16.dp,
                    top = 12.dp,
                    bottom = 9.dp,
                ),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = title,
                fontSize = 14.sp,
                color = Black,
            )
            Text(
                text = freeSpaceLabel,
                fontSize = 14.sp,
                color = Black,
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 12.dp,
                )
                .height(16.dp)
                .background(Gray),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(usedFraction.coerceIn(0f, 1f))
                    .fillMaxHeight()
                    .background(OrangeLight),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DeviceMemoryIndicatorPreview() {
    MemoryIndicator(
        title = "Device memory",
        freeSpaceLabel = "Free 3.61 Gb",
        usedFraction = 0.33f,
    )
}
