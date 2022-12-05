package abandonedspace.android.y2y.core.repository.categories

import abandonedspace.android.y2y.core.data.categories.room.RoomCategory
import abandonedspace.android.y2y.domain.model.Category
import androidx.compose.ui.graphics.Color

fun RoomCategory.toCategory() =
    Category(
        id = id!!,
        name = name,
        color = colorInt?.let { Color(it) }
    )