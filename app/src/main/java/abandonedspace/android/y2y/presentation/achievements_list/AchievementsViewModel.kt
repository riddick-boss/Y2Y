package abandonedspace.android.y2y.presentation.achievements_list

import abandonedspace.android.y2y.domain.model.Achievement
import abandonedspace.android.y2y.domain.repository.achievements.AchievementsRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.Month
import java.time.Year
import javax.inject.Inject

@HiltViewModel
class AchievementsViewModel @Inject constructor(
    private val repository: AchievementsRepository
) : ViewModel() {

    private val _onCloseBottomSheet = MutableSharedFlow<Unit>()
    val onCloseBottomSheet: SharedFlow<Unit> = _onCloseBottomSheet.asSharedFlow()

    val achievements: StateFlow<List<Achievement>> by lazy {
        repository.getAchievements().stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
    }

    fun onAddAchievement(title: String, description: String, month: Int, year: Int) {
        viewModelScope.launch {
            try {
                repository.insert(
                    title,
                    description.ifBlank { null },
                    Month.of(month),
                    Year.of(year)
                )
                _onCloseBottomSheet.emit(Unit)
            } catch (e: Exception) {
                //TODO
            }
        }
    }

    fun onAchievementDeleteClicked(id: Int) {
        viewModelScope.launch {
            try {
                repository.delete(id)
            } catch (e: Exception) {
                //TODO
            }
        }
    }
}