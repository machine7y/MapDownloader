package machine7y.mapdownloader.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import machine7y.mapdownloader.presentation.theme.Blue
import machine7y.mapdownloader.presentation.theme.Gray2

@Composable
fun ProgressBar(
    fraction: Float,
    modifier: Modifier = Modifier,
    color: Color = Blue,
    trackColor: Color = Gray2,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(trackColor),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(fraction.coerceIn(0f, 1f))
                .fillMaxHeight()
                .background(color),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ProgressBarPreview() {
    ProgressBar(
        fraction = 0.4f,
        modifier = Modifier.height(3.dp),
    )
}
