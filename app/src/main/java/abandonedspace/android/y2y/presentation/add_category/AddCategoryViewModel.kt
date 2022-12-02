package abandonedspace.android.y2y.presentation.add_category

import abandonedspace.android.y2y.domain.repository.categories.CategoriesRepository
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddCategoryViewModel @Inject constructor(
    private val categoriesRepository: CategoriesRepository
) : ViewModel() {

    private val _categoryName = MutableStateFlow("")
    val categoryName: StateFlow<String> = _categoryName.asStateFlow()

    fun onNameChanged(value: String?) {
        _categoryName.value = value.orEmpty()
    }

    private val _categoryColor = MutableStateFlow<Color?>(null)
    val categoryColor: StateFlow<Color?> = _categoryColor.asStateFlow()

    fun onColorChanged(value: Color?) {
        _categoryColor.value = value
    }

    private val _selectedColorOption = MutableStateFlow(ColorSelectionOption.NONE)
    val selectedColorOption: StateFlow<ColorSelectionOption> = _selectedColorOption.asStateFlow()

    fun onColorSelectedOptionChanged(value: ColorSelectionOption) {
        _selectedColorOption.value = value
        _categoryColor.value = null
    }

    private val _progressBarVisible = mutableStateOf(false)
    val progressBarVisible: State<Boolean> = _progressBarVisible

    private fun showProgressBar() {
        _progressBarVisible.value = true
    }

    private fun hideProgressBar() {
        _progressBarVisible.value = false
    }

    fun onDoneClicked() {
        if (categoryName.value.isBlank()) return

        viewModelScope.launch {
            showProgressBar()
            try {
                delay(5000)
                categoriesRepository.insertCategory(
                    name = categoryName.value,
                    color = categoryColor.value
                )
            } catch (e: Exception) {
                // TODO: show toast
            }
            hideProgressBar()
        }
    }

    //TODO: test case: when onDone clicked with blank name, should show error on category name textField
}