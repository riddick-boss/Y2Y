package abandonedspace.android.y2y.core.data.categories.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "categories_table",
    indices = [Index("id")]
)
data class RoomCategory(

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "color_int")
    val colorInt: Int?,

    @PrimaryKey(autoGenerate = true)
    val id: Int? = null
)
