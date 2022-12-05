package abandonedspace.android.y2y.core.repository.categories

import abandonedspace.android.y2y.core.data.categories.room.CategoryDao
import abandonedspace.android.y2y.domain.model.Category
import abandonedspace.android.y2y.domain.repository.categories.CategoriesRepository
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RoomCategoriesRepository @Inject constructor(
    private val dao: CategoryDao
) : CategoriesRepository {

    override fun getCategories(): Flow<List<Category>> = dao.getCategories().map {
        it.map { category -> category.toCategory() }
    }

    override suspend fun getCategoryById(id: Int): Category = dao.getCategory(id = id).toCategory()

    override suspend fun insert(name: String, color: Color?) {
        if (name.isBlank()) throw IllegalArgumentException("RoomCategory name can not be null!")

        val roomCategory = abandonedspace.android.y2y.core.data.categories.room.RoomCategory(
            name = name,
            colorInt = color?.toArgb()
        )
        dao.insert(roomCategory)
    }
}