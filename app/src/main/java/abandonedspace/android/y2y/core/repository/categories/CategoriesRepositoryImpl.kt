package abandonedspace.android.y2y.core.repository.categories

import abandonedspace.android.y2y.core.data.categories.room.CategoryDao
import abandonedspace.android.y2y.domain.model.Category
import abandonedspace.android.y2y.domain.repository.categories.CategoriesRepository
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CategoriesRepositoryImpl @Inject constructor(
    private val dao: CategoryDao
) : CategoriesRepository {
    override fun getCategories(): Flow<List<Category>> = dao.getCategories().map {
        it.map { category -> category.toDomainCategory() }
    }

    override suspend fun getCategoryById(id: Int): Category = dao.getCategory(id = id).toDomainCategory()

    override suspend fun insertCategory(name: String, color: Color?) {
        if (name.isBlank()) throw IllegalArgumentException("Category name can not be null!")

        val category = abandonedspace.android.y2y.core.data.categories.room.Category(
            name = name,
            colorInt = color?.toArgb()
        )
        dao.insert(category)
    }
}