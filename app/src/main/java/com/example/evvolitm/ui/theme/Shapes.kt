package com.example.evvolitm.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp




// in Theme.kt in MaterialTheme it can be given as shapes = Shapes if so:
// BE CAREFUL- since Material composable use small, medium, or etc. for the container it provides,
// changing shapes might cause a behavior you don't expect.
// Ex. Card composable uses medium shape and thus changing the medium will change the default behavior.
val Shapes = Shapes(
    small = RoundedCornerShape(50.dp), // Can be used for image clipping - modifier.clip(MaterialTheme.shapes.small),
//    medium = RoundedCornerShape(bottomStart = 16.dp, topEnd = 16.dp),
)