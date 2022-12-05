package abandonedspace.android.y2y.presentation.add_achievement

import abandonedspace.android.y2y.domain.helper.ITimeConverter
import abandonedspace.android.y2y.domain.model.Category
import abandonedspace.android.y2y.domain.repository.achievements.AchievementsRepository
import abandonedspace.android.y2y.domain.repository.categories.CategoriesRepository
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddAchievementViewModel @Inject constructor(
    private val achievementsRepository: AchievementsRepository,
    private val categoriesRepository: CategoriesRepository,
    private val timeConverter: ITimeConverter
) : ViewModel() {

    private val _onNavigateToAchievementsList = MutableSharedFlow<Unit>()
    val onNavigateToAchievementsList: SharedFlow<Unit> = _onNavigateToAchievementsList.asSharedFlow()

    private val _name = MutableStateFlow("")
    val name: StateFlow<String> = _name.asStateFlow()

    fun onNameChanged(value: String) {
        _name.value = value
    }

    private val _description = MutableStateFlow("")
    val description: StateFlow<String> = _description.asStateFlow()

    fun onDescriptionChanged(value: String) {
        _description.value = value
    }

    val categories: StateFlow<List<Category>> by lazy {
        categoriesRepository.getCategories().stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
    }

    private val _selectedCategory = MutableStateFlow<Category?>(null)
    val selectedCategory: StateFlow<Category?> = _selectedCategory.asStateFlow()

    fun onCategoryChanged(category: Category) {
        viewModelScope.launch {
            _selectedCategory.value = category
        }
    }

    private val _prideLevel = MutableStateFlow(0)
    val prideLevel: StateFlow<Int> = _prideLevel.asStateFlow()

    fun onPrideLevelDecreased() {
        _prideLevel.value = _prideLevel.value.dec()
    }

    fun onPrideLevelIncreased() {
        _prideLevel.value = _prideLevel.value.inc()
    }

    private val _day = MutableStateFlow<Int?>(1)
    val day: StateFlow<Int?> = _day.asStateFlow()

    fun onDayChanged(value: Int?) {
        _day.value = value
    }

    private val _month = MutableStateFlow<Int?>(1)
    val month: StateFlow<Int?> = _month.asStateFlow()

    fun onMonthChanged(value: Int?) {
        _month.value = value
    }

    private val _year = MutableStateFlow<Int?>(1)
    val year: StateFlow<Int?> = _year.asStateFlow()

    fun onYearChanged(value: Int?) {
        _year.value = value
    }

    private val _progressBarVisible = mutableStateOf(false)
    val progressBarVisible: State<Boolean> = _progressBarVisible

    private fun showProgressBar() {
        _progressBarVisible.value = true
    }

    private fun hideProgressBar() {
        _progressBarVisible.value = false
    }

    fun onAddClicked() {
        viewModelScope.launch {
            showProgressBar()
            try {
                achievementsRepository.insert(
                    name = name.value,
                    description = description.value,
                    categoryId = selectedCategory.value?.id,
                    prideLevel = prideLevel.value,
                    timeStamp = timeConverter.dayMonthYearToZonedDateTime(
                        day = day.value!!,
                        month = month.value!!,
                        year = year.value!!
                    )
                )
                _onNavigateToAchievementsList.emit(Unit)
            } catch (e: Exception) {
                //TODO
            }
            hideProgressBar()
        }
    }
}