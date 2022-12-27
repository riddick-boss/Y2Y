package abandonedspace.android.y2y.presentation.achievements_list

import abandonedspace.android.y2y.domain.model.Achievement
import abandonedspace.android.y2y.domain.repository.achievements.AchievementsRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class AchievementsViewModel @Inject constructor(
    private val repository: AchievementsRepository
) : ViewModel() {

    val achievements: StateFlow<List<Achievement>> by lazy {
        repository.getAchievements().stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
    }

    fun onAddAchievementClicked() {
        //TODO
    }

    fun onAchievementDeleteClicked(id: Int) {

    }
}