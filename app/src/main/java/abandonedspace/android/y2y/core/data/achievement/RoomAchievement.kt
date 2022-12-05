package abandonedspace.android.y2y.core.data.achievement

import abandonedspace.android.y2y.core.data.categories.room.RoomCategory
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "achievements_table",
    indices = [Index("id")],
    foreignKeys = [ForeignKey(
        entity = RoomCategory::class,
        parentColumns = ["id"],
        childColumns = ["category_id"],
        onDelete = ForeignKey.SET_NULL,
        onUpdate = ForeignKey.CASCADE
    )]
)
data class RoomAchievement(

    @ColumnInfo(name = "timestamp_millis")
    val timestampMillis: Long,

    @ColumnInfo(name = "category_id")
    val categoryId: Int?,

    @ColumnInfo(name = "pride_level")
    val prideLevel: Int,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "description")
    val description: String?,

    @PrimaryKey(autoGenerate = true)
    val id: Int? = null
)
