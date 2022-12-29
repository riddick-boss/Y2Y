package abandonedspace.android.y2y.core.repository.achievements

import abandonedspace.android.y2y.core.data.achievements.room.Achievement
import com.google.common.truth.Truth
import org.junit.Before
import org.junit.Test

class AchievementMapperTest {

    private lateinit var mapper: AchievementMapper

    @Before
    fun setup() {
        mapper = AchievementMapper()
    }

    @Test
    fun `month and year maps correctly from int and shows display value`() {
        val roomAchievement = Achievement("title", "desc", 6, 2022)
        val achievement = mapper.toDomainAchievement(roomAchievement)
        Truth.assertThat(achievement.date.displayValue).isNotEmpty()
    }
}