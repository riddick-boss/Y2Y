package abandonedspace.android.y2y.presentation.achievements_list

import abandonedspace.android.y2y.MainDispatcherRule
import abandonedspace.android.y2y.domain.repository.achievements.AchievementsRepository
import app.cash.turbine.test
import com.google.common.truth.Truth
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

@RunWith(JUnit4::class)
class AchievementsViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: AchievementsViewModel
    private val repository = mock<AchievementsRepository>()

    @Before
    fun setup() {
        viewModel = AchievementsViewModel(repository)
    }

    @Test
    fun `repository insert invokes on achievement addition`() = runBlocking {
        doReturn(Unit).`when`(repository).insert(any(), any(), any(), any())
        viewModel.onTitleChanged("title")
        viewModel.onDescriptionChanged("description")
        viewModel.onAddAchievement()
        verify(repository).insert(any(), any(), any(), any())
    }

    @Test
    fun `user input reset after achievement addition`() = runBlocking {
        doReturn(Unit).`when`(repository).insert(any(), any(), any(), any())
        viewModel.onTitleChanged("title")
        viewModel.onDescriptionChanged("description")
        viewModel.onAddAchievement()
        Truth.assertThat(viewModel.title.value).isEmpty()
        Truth.assertThat(viewModel.description.value).isEmpty()
    }

    @Test
    fun `onCloseBottomSheet emits after achievement addition`() = runBlocking {
        doReturn(Unit).`when`(repository).insert(any(), any(), any(), any())
        viewModel.onCloseBottomSheet.test {
            viewModel.onTitleChanged("title")
            viewModel.onDescriptionChanged("description")
            viewModel.onAddAchievement()
            Truth.assertThat(awaitItem()).isNotNull()
        }
    }
}