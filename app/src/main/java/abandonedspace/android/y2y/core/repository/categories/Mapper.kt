package abandonedspace.android.y2y.core.repository.categories

import abandonedspace.android.y2y.core.data.categories.room.Category
import androidx.compose.ui.graphics.Color

fun Category.toDomainCategory() =
    abandonedspace.android.y2y.domain.model.Category(
        id = id,
        name = name,
        color = colorInt?.let { Color(it) }
    )