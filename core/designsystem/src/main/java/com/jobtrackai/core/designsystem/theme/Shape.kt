package com.jobtrackai.core.designsystem.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

/**
 * Corner-radius scale for the app's cards, dialogs, bottom sheets, and
 * buttons (Section 28). Slightly more rounded than stock Material3
 * defaults at the larger sizes — cards in particular — to read as
 * "premium/friendly" rather than "enterprise-dense," matching Section 28's
 * "minimal, clean, professional, premium" brief without going fully
 * playful (which would undercut "recruiter-friendly").
 */
val JobTrackShapes = Shapes(
    extraSmall = RoundedCornerShape(4.dp),
    small = RoundedCornerShape(8.dp),
    medium = RoundedCornerShape(12.dp),
    large = RoundedCornerShape(20.dp),
    extraLarge = RoundedCornerShape(28.dp),
)
