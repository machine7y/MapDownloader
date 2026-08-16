package machine7y.mapdownloader.presentation.modifier

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp

fun Modifier.topShadow(
    height: Dp,
    color: Color = Color.Black.copy(alpha = 0.1f)
) = this.drawBehind {
    val h = height.toPx()
    drawRect(
        brush = Brush.verticalGradient(
            colors = listOf(Color.Transparent, color),
            startY = -h,
            endY = 0f
        ),
        topLeft = Offset(0f, -h),
        size = Size(size.width, h)
    )
}

fun Modifier.bottomShadow(
    height: Dp,
    color: Color = Color.Black.copy(alpha = 0.1f)
) = this.drawBehind {
    val h = height.toPx()
    drawRect(
        brush = Brush.verticalGradient(
            colors = listOf(color, Color.Transparent),
            startY = size.height,
            endY = size.height + h
        ),
        topLeft = Offset(0f, size.height),
        size = Size(size.width, h)
    )
}
