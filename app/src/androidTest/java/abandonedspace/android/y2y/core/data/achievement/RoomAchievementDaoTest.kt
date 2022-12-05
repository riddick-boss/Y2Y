package abandonedspace.android.y2y.core.data.achievement

import abandonedspace.android.y2y.core.data.room.AchievementsDatabase
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class RoomAchievementDaoTest {

    private lateinit var db: AchievementsDatabase
    private lateinit var dao: AchievementDao

    @Before
    fun setUp() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AchievementsDatabase::class.java
        ).allowMainThreadQueries().build()

        dao = db.achievementDao()
    }

    @After
    fun tearDown() {
        db.close()
    }

    //TODO
}