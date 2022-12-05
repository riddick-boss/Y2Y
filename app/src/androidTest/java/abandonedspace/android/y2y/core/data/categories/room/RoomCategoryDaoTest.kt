package abandonedspace.android.y2y.core.data.categories.room

import abandonedspace.android.y2y.core.data.room.AchievementsDatabase
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

@RunWith(AndroidJUnit4::class)
@SmallTest
class RoomCategoryDaoTest {

    private lateinit var db: AchievementsDatabase
    private lateinit var dao: CategoryDao

    @Before
    fun setUp() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AchievementsDatabase::class.java
        ).allowMainThreadQueries().build()

        dao = db.categoryDao()
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun insertCategoryAddsNewRecord() = runBlocking {
        val roomCategory1 = RoomCategory("dumbName1", 123456, id = 1)
        val roomCategory2 = RoomCategory("dumbName2", 654321, id = 2)

        dao.insert(roomCategory1)
        dao.insert(roomCategory2)

        val categories = dao.getCategories().first()

        Truth.assertThat(categories).contains(roomCategory1)
        Truth.assertThat(categories).contains(roomCategory2)
        Truth.assertThat(categories).hasSize(2)
    }

    @Test
    fun insertAutoGeneratesId() = runBlocking {
        val roomCategory = RoomCategory("dumbName", 123456)

        dao.insert(roomCategory)

        val categories = dao.getCategories().first()

        Truth.assertThat(categories.first().id).isNotNull()
    }

    @Test
    fun getByIdReturnsCorrectCategory() = runBlocking {
        val roomCategory1 = RoomCategory("dumbName1", 123456, id = 1)
        val roomCategory2 = RoomCategory("dumbName2", 654321, id = 2)
        val roomCategory3 = RoomCategory("dumbName3", 123321, id = 3)

        dao.run {
            insert(roomCategory1)
            insert(roomCategory2)
            insert(roomCategory3)
        }

        val categoryById = dao.getCategory(2)

        Truth.assertThat(categoryById).isEqualTo(roomCategory2)
        Truth.assertThat(categoryById.name).isEqualTo("dumbName2")
        Truth.assertThat(categoryById.colorInt).isEqualTo(654321)
        Truth.assertThat(categoryById.id).isEqualTo(2)
    }

    @Test
    fun getCategoriesReturnsSortedByName() = runBlocking {
        val name1 = "aaaaa"
        val name2 = "bbbb"
        val name3 = "cccccccc"
        val roomCategory1 = RoomCategory(name1, 123456)
        val roomCategory2 = RoomCategory(name2, 654321)
        val roomCategory3 = RoomCategory(name3, 123321)

        dao.run {
            insert(roomCategory2)
            insert(roomCategory3)
            insert(roomCategory1)
        }

        val categories = dao.getCategories().first()

        Truth.assertThat(categories[0].name).isEqualTo(name1)
        Truth.assertThat(categories[1].name).isEqualTo(name2)
        Truth.assertThat(categories[2].name).isEqualTo(name3)
    }
}