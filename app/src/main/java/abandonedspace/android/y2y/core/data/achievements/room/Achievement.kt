package abandonedspace.android.y2y.core.data.achievements.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Achievement(

    @PrimaryKey(autoGenerate = true)
    val id: Int? = null
)
