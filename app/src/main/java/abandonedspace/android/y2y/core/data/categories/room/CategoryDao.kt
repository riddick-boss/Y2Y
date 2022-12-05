package abandonedspace.android.y2y.core.data.categories.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {

    @Insert
    suspend fun insert(roomCategory: RoomCategory)

    @Query("SELECT * FROM categories_table ORDER BY name")
    fun getCategories(): Flow<List<RoomCategory>>

    @Query("SELECT * FROM categories_table WHERE id LIKE :id")
    fun getCategory(id: Int): RoomCategory
}