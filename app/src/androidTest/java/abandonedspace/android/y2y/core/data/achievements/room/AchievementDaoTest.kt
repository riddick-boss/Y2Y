package abandonedspace.android.y2y.core.data.achievements.room

import abandonedspace.android.y2y.core.data.room.AchievementsDatabase
import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@SmallTest
@RunWith(AndroidJUnit4::class)
class AchievementDaoTest {

    private lateinit var dao: AchievementDao
    private lateinit var db: AchievementsDatabase

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AchievementsDatabase::class.java).build()
        dao = db.achievementDao()
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun testInsertAndRead() = runBlocking {
        val achievement = Achievement("title", "description", 1, 2022)
        dao.insert(achievement)
        val list = dao.getAchievements().first()
        Truth.assertThat(list).hasSize(1)
        val first = list.first()
        Truth.assertThat(first.title).isEqualTo(achievement.title)
        Truth.assertThat(first.description).isEqualTo(achievement.description)
        Truth.assertThat(first.month).isEqualTo(achievement.month)
        Truth.assertThat(first.year).isEqualTo(achievement.year)
        Truth.assertThat(first.id).isNotEqualTo(achievement.id)
    }

    @Test
    fun testDeletion() = runBlocking {
        val achievement = Achievement("title", "description", 1, 2022)
        dao.insert(achievement)
        val list = dao.getAchievements().first()
        Truth.assertThat(list).hasSize(1)
        val id = list.first().id!!
        dao.deleteById(id)
        val listAfterDeletion = dao.getAchievements().first()
        Truth.assertThat(listAfterDeletion).hasSize(0)
    }

    @Test
    fun testSortOrder() = runBlocking {
        val first = Achievement("first", "firstDesc", 1, 2020)
        val second = Achievement("second", "secondDesc", 5, 2021)
        val third = Achievement("third", "thirdDesc", 7, 2021)
        val fourth = Achievement("fourth", "fourthDesc", 8, 2022)
        dao.run {
            insert(second)
            insert(fourth)
            insert(first)
            insert(third)
        }
        val list = dao.getAchievements().first()
        Truth.assertThat(list[0].title).isEqualTo(first.title)
        Truth.assertThat(list[1].title).isEqualTo(second.title)
        Truth.assertThat(list[2].title).isEqualTo(third.title)
        Truth.assertThat(list[3].title).isEqualTo(fourth.title)
    }
}