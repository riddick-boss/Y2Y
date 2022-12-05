package abandonedspace.android.y2y.presentation.add_achievement

import abandonedspace.android.y2y.R
import abandonedspace.android.y2y.domain.model.Category
import abandonedspace.android.y2y.domain.presentation.components.ProgressIndicatorDialog
import abandonedspace.android.y2y.domain.presentation.navigation.SCREENS
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
fun AddAchievementScreen(
    navController: NavController,
    viewModel: AddAchievementViewModel = hiltViewModel()
) {

    val name by viewModel.name.collectAsState()
    val description by viewModel.description.collectAsState()
    val categories by viewModel.categories.collectAsState()
    val selectedCategory by viewModel.selectedCategory.collectAsState()
    val prideLevel by viewModel.prideLevel.collectAsState()
    val day by viewModel.day.collectAsState()
    val month by viewModel.month.collectAsState()
    val year by viewModel.year.collectAsState()

    val navigateToAddCategory: () -> Unit = {
        navController.navigate(SCREENS.ADD_CATEGORY.route.value)
    }

    LaunchedEffect(Unit) {
        viewModel.onNavigateToAchievementsList.onEach {
            //TODO
//            navController.popBackStack(SCREENS.ACHIEVEMENTS.route.value, true)
        }.launchIn(this)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = name,
            onValueChange = viewModel::onNameChanged,
            label = {
                Text(text = "Achievement")
            },
            modifier = Modifier.fillMaxWidth(),
            maxLines = 1,
            singleLine = true
        )

        OutlinedTextField(
            value = description,
            onValueChange = viewModel::onDescriptionChanged,
            label = {
                Text(text = "Description...")
            },
            modifier = Modifier
                .fillMaxWidth()
                .weight(1.0f, true)
        )

        CategoryPicker(
            selectedCategory = selectedCategory,
            categories = categories,
            onCategoryChanged = viewModel::onCategoryChanged,
            onCreateNewClicked = navigateToAddCategory
        )

        PrideLevelPicker(
            medalsNumber = prideLevel,
            onDecreaseClicked = viewModel::onPrideLevelDecreased,
            onIncreaseClicked = viewModel::onPrideLevelIncreased
        )

        DatePicker(
            day = day,
            month = month,
            year = year,
            onDayChanged = viewModel::onDayChanged,
            onMonthChanged = viewModel::onMonthChanged,
            onYearChanged = viewModel::onYearChanged

        )

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = viewModel::onAddClicked
        ) {
            Text(text = "Add")
        }

        if (viewModel.progressBarVisible.value) {
            ProgressIndicatorDialog()
        }
    }
}

@Composable
fun CategoryPicker(
    selectedCategory: Category?,
    categories: List<Category>,
    onCategoryChanged: (Category) -> Unit,
    onCreateNewClicked: () -> Unit
) {
    var menuExpanded by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "Category")

        Column {
            Box(
                modifier = Modifier
                    .background(color = Color.White)
                    .clickable { menuExpanded = !menuExpanded },
                contentAlignment = Alignment.Center
            ) {
                Text(text = selectedCategory?.name ?: "Select Category")
            }

            DropdownMenu(expanded = menuExpanded, onDismissRequest = { menuExpanded = false }) {
                categories.forEach {
                    DropdownMenuItem(
                        modifier = Modifier.background(color = it.color ?: Color.Transparent),
                        onClick = {
                            onCategoryChanged(it)
                            menuExpanded = false
                        }
                    ) {
                        Text(text = it.name)
                    }
                }
            }
        }
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End
    ) {
        TextButton(onClick = onCreateNewClicked) {
            Text(text = "Create new")
        }
    }
}

@Composable
private fun DatePicker(
    day: Int?,
    month: Int?,
    year: Int?,
    onDayChanged: (Int?) -> Unit,
    onMonthChanged: (Int?) -> Unit,
    onYearChanged: (Int?) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Date"
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            DateValuePicker(labelText = "Day", value = day, onValueChange = onDayChanged)
            DateValuePicker(labelText = "Month", value = month, onValueChange = onMonthChanged)
            DateValuePicker(labelText = "Year", value = year, onValueChange = onYearChanged)
        }
    }
}

@Composable
private fun RowScope.DateValuePicker(labelText: String, value: Int?, onValueChange: (Int?) -> Unit) {
    OutlinedTextField(
        value = value?.toString().orEmpty(),
        onValueChange = { onValueChange(it.toIntOrNull()) },
        maxLines = 1,
        singleLine = true,
        label = {
            Text(text = labelText)
        },
        modifier = Modifier
            .weight(1.0f)
            .padding(horizontal = 8.dp),
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
    )
}

@Composable
private fun PrideLevelPicker(
    medalsNumber: Int,
    onDecreaseClicked: () -> Unit,
    onIncreaseClicked: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Pride level"
        )

        PrideLevelSwitch(medalsNumber, onDecreaseClicked, onIncreaseClicked)
    }
}

@Composable
private fun PrideLevelSwitch(
    medalsNumber: Int,
    onRemoveClicked: () -> Unit,
    onAddClicked: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        PrideLevelButton(painterResId = R.drawable.ic_baseline_remove_24, tint = Color.Red, onClick = onRemoveClicked)

        MedalsRow(
            medalsNumber = medalsNumber
        )

        PrideLevelButton(painterResId = R.drawable.ic_baseline_add_24, tint = Color.Green, onClick = onAddClicked)
    }
}

@Composable
private fun PrideLevelButton(
    @DrawableRes painterResId: Int,
    tint: Color,
    onClick: () -> Unit
) {
    IconButton(onClick = onClick) {
        Icon(
            painter = painterResource(id = painterResId),
            tint = tint,
            contentDescription = ""
        )
    }
}

@Composable
private fun RowScope.MedalsRow(
    medalsNumber: Int
) {
    LazyRow(
        modifier = Modifier.weight(1.0f, true),
        verticalAlignment = Alignment.CenterVertically
    ) {
        items(medalsNumber) {
            Image(painter = painterResource(id = R.drawable.medal), contentDescription = "")
        }
    }
}

@Preview
@Composable
private fun DatePickerPreview() {
    DatePicker(
        day = 0,
        month = 0,
        year = 0,
        onDayChanged = {},
        onMonthChanged = {},
        onYearChanged = {}
    )
}

@Preview
@Composable
private fun PrideLevelPickerPreview() {
    PrideLevelPicker(30, {}, {})
}