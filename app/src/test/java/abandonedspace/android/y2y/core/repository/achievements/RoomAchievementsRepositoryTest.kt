package abandonedspace.android.y2y.core.repository.achievements

import abandonedspace.android.y2y.core.data.achievements.room.Achievement
import abandonedspace.android.y2y.core.data.achievements.room.AchievementDao
import com.google.common.truth.Truth
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.*
import java.time.Month
import java.time.Year

class RoomAchievementsRepositoryTest {
    private lateinit var repository: RoomAchievementsRepository
    private val mapper = mock<AchievementMapper>()
    private val dao = mock<AchievementDao>()

    @Before
    fun setup() {
        repository = RoomAchievementsRepository(mapper, dao)
    }

    @Test
    fun `insert throws exception when title is empty`() = runBlocking {
        var exception: Exception? = null
        try {
            repository.insert("", "desc", Month.JANUARY, Year.of(2022))
        } catch (e: Exception) {
            exception = e
        }
        Truth.assertThat(exception).isNotNull()
    }

    @Test
    fun `insert throws exception when title is blank`() = runBlocking {
        var exception: Exception? = null
        try {
            repository.insert("      ", "desc", Month.JANUARY, Year.of(2022))
        } catch (e: Exception) {
            exception = e
        }
        Truth.assertThat(exception).isNotNull()
    }

    @Test
    fun `insert invokes dao insert operation`() = runBlocking {
        doReturn(Unit).`when`(dao).insert(any())
        repository.insert("title", "desc", Month.JANUARY, Year.of(2022))
        verify(dao).insert(any())
    }

    @Test
    fun `delete invokes dao deleteById operation`() = runBlocking {
        doReturn(Unit).`when`(dao).deleteById(any())
        repository.delete(1)
        verify(dao).deleteById(any())
    }

    @Test
    fun `getAchievements returns mapped list`() = runBlocking {
        doReturn(flowOf(listOf(mock(), mock<Achievement>()))).`when`(dao).getAchievements()
        val list = repository.getAchievements().first()
        Truth.assertThat(list).hasSize(2)
    }
}