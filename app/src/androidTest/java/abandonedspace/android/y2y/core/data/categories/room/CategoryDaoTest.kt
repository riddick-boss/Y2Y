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
class CategoryDaoTest {

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
        val category1 = Category("dumbName1", 123456, id = 1)
        val category2 = Category("dumbName2", 654321, id = 2)

        dao.insert(category1)
        dao.insert(category2)

        val categories = dao.getCategories().first()

        Truth.assertThat(categories).contains(category1)
        Truth.assertThat(categories).contains(category2)
        Truth.assertThat(categories).hasSize(2)
    }

    @Test
    fun insertAutoGeneratesId() = runBlocking {
        val category = Category("dumbName", 123456)

        dao.insert(category)

        val categories = dao.getCategories().first()

        Truth.assertThat(categories.first().id).isNotNull()
    }

    @Test
    fun getByIdReturnsCorrectCategory() = runBlocking {
        val category1 = Category("dumbName1", 123456, id = 1)
        val category2 = Category("dumbName2", 654321, id = 2)
        val category3 = Category("dumbName3", 123321, id = 3)

        dao.run {
            insert(category1)
            insert(category2)
            insert(category3)
        }

        val categoryById = dao.getCategory(2)

        Truth.assertThat(categoryById).isEqualTo(category2)
        Truth.assertThat(categoryById.name).isEqualTo("dumbName2")
        Truth.assertThat(categoryById.colorInt).isEqualTo(654321)
        Truth.assertThat(categoryById.id).isEqualTo(2)
    }

    @Test
    fun getCategoriesReturnsSortedByName() = runBlocking {
        val name1 = "aaaaa"
        val name2 = "bbbb"
        val name3 = "cccccccc"
        val category1 = Category(name1, 123456)
        val category2 = Category(name2, 654321)
        val category3 = Category(name3, 123321)

        dao.run {
            insert(category2)
            insert(category3)
            insert(category1)
        }

        val categories = dao.getCategories().first()

        Truth.assertThat(categories[0].name).isEqualTo(name1)
        Truth.assertThat(categories[1].name).isEqualTo(name2)
        Truth.assertThat(categories[2].name).isEqualTo(name3)
    }
}