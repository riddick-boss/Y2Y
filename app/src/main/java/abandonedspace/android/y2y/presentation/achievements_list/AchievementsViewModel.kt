package abandonedspace.android.y2y.presentation.achievements_list

import abandonedspace.android.y2y.domain.model.Achievement
import abandonedspace.android.y2y.domain.repository.achievements.AchievementsRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.Month
import java.time.Year
import javax.inject.Inject

@HiltViewModel
class AchievementsViewModel @Inject constructor(
    private val repository: AchievementsRepository
) : ViewModel() {

    val achievements: StateFlow<List<Achievement>> by lazy {
        repository.getAchievements().stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
    }

    fun onAddAchievement(title: String, description: String, month: Int, year: Int) {
        try {
            viewModelScope.launch {
                repository.insert(title, description.ifBlank { null }, Month.of(month), Year.of(year))
            }
        } catch (e: Exception) {
            //TODO
        }
    }

    fun onAchievementDeleteClicked(id: Int) {
        try {
            viewModelScope.launch {
                repository.delete(id)
            }
        } catch (e: Exception) {
            //TODO
        }
    }
}