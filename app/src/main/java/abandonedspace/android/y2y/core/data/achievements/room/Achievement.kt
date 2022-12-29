package abandonedspace.android.y2y.core.data.achievements.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "achievements_table",
    indices = [Index("id")]
)
data class Achievement(

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "description")
    val description: String?,

    @ColumnInfo(name = "month")
    val month: Int,

    @ColumnInfo(name = "year")
    val year: Int,

    @PrimaryKey(autoGenerate = true)
    val id: Int? = null
)
