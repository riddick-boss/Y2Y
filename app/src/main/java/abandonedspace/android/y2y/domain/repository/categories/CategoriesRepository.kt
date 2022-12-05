package abandonedspace.android.y2y.domain.repository.categories

import abandonedspace.android.y2y.domain.model.Category
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.flow.Flow

interface CategoriesRepository {

    fun getCategories(): Flow<List<Category>>

    suspend fun getCategoryById(id: Int): Category

    suspend fun insert(name: String, color: Color?)
}