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

    private val _title = MutableStateFlow("")
    val title = _title.asStateFlow()

    fun onTitleChanged(value: String) {
        _title.value = value
    }

    private val _description = MutableStateFlow("")
    val description = _description.asStateFlow()

    fun onDescriptionChanged(value: String) {
        _description.value = value
    }

    private val _month = MutableStateFlow(Month.JANUARY.value)
    val month = _month.asStateFlow()

    fun onMonthChanged(value: Int) {
        _month.value = value
    }

    private val _year = MutableStateFlow(Year.now().value)
    val year = _year.asStateFlow()

    fun onYearChanged(value: Int) {
        _year.value = value
    }

    val achievements: StateFlow<List<Achievement>> by lazy {
        repository.getAchievements().stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
    }

    fun onAddAchievement() {
        viewModelScope.launch {
            try {
                repository.insert(
                    title.value,
                    description.value.ifBlank { null },
                    Month.of(month.value),
                    Year.of(year.value)
                )
                resetUserInput()
                _onCloseBottomSheet.emit(Unit)
            } catch (e: Exception) {
                // nothing to do yet...
            }
        }
    }

    private fun resetUserInput() {
        // we don't want to reset selected month and year
        _title.value = ""
        _description.value = ""
    }

    fun onAchievementDeleteClicked(id: Int) {
        viewModelScope.launch {
            try {
                repository.delete(id)
            } catch (e: Exception) {
                // nothing to do yet...
            }
        }
    }
}