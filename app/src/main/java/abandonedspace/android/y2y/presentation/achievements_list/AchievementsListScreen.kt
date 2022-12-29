package abandonedspace.android.y2y.presentation.achievements_list

import abandonedspace.android.y2y.R
import abandonedspace.android.y2y.domain.model.Achievement
import abandonedspace.android.y2y.domain.model.Date
import androidx.activity.compose.BackHandler
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.chargemap.compose.numberpicker.NumberPicker
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.time.Month
import java.time.Year
import java.time.format.TextStyle
import java.util.*

@OptIn(ExperimentalMaterialApi::class, ExperimentalComposeUiApi::class)
@Composable
fun AchievementsScreen(
    viewModel: AchievementsViewModel = hiltViewModel()
) {
    val achievements by viewModel.achievements.collectAsState()
    val title by viewModel.title.collectAsState()
    val description by viewModel.description.collectAsState()
    val month by viewModel.month.collectAsState()
    val year by viewModel.year.collectAsState()

    val bottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true
    )
    val scope = rememberCoroutineScope()
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    LaunchedEffect(Unit) {
        viewModel.onCloseBottomSheet.onEach {
            bottomSheetState.hide()
            focusManager.clearFocus()
            keyboardController?.hide()
        }.launchIn(this)
    }

    ModalBottomSheetLayout(
        sheetState = bottomSheetState,
        sheetContent = {
            BottomSheetContent(
                title = title,
                description = description,
                month = month,
                year = year,
                onTitleChanged = viewModel::onTitleChanged,
                onDescriptionChanged = viewModel::onDescriptionChanged,
                onMonthChanged = viewModel::onMonthChanged,
                onYearChanged = viewModel::onYearChanged,
                onCreateClicked = viewModel::onAddAchievement
            )
        },

        ) {
        Scaffold(
            floatingActionButtonPosition = FabPosition.Center,
            floatingActionButton = {
                FloatingActionButton(onClick = { scope.launch { bottomSheetState.show() } }) {
                    Icon(Icons.Filled.Add, "")
                }
            },
            isFloatingActionButtonDocked = true,
            bottomBar = {
                BottomAppBar(
                    cutoutShape = MaterialTheme.shapes.small.copy(
                        CornerSize(percent = 50)
                    )
                ) {
                    // nothing here...
                }
            }
        ) { paddingValues ->
            BackHandler(enabled = bottomSheetState.isVisible) {
                scope.launch { bottomSheetState.hide() }
            }

            LazyColumn(
                modifier = Modifier.padding(paddingValues),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(achievements.size, key = { achievements[it].id!! }) {
                    AchievementCard(
                        achievement = achievements[it],
                        onDeleteClicked = viewModel::onAchievementDeleteClicked
                    )
                }
            }
        }
    }
}

@Composable
private fun AchievementCard(achievement: Achievement, onDeleteClicked: (Int) -> Unit) {
    var deleteButtonVisible by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .pointerInput(Unit) {
                detectTapGestures(
                    onLongPress = {
                        deleteButtonVisible = !deleteButtonVisible
                    }
                )
            },
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(text = achievement.date.displayValue, style = MaterialTheme.typography.overline)
            Text(text = achievement.title, style = MaterialTheme.typography.h6)
            achievement.description?.also {
                Text(text = it, style = MaterialTheme.typography.body2)
            }

            if (deleteButtonVisible) {
                IconButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(50))
                        .background(Color.Red),
                    onClick = { onDeleteClicked(achievement.id!!) }
                ) {
                    Icon(Icons.Filled.Delete, "")
                }
            }
        }
    }
}

@Composable
private fun BottomSheetContent(
    title: String,
    description: String,
    month: Int,
    year: Int,
    onTitleChanged: (String) -> Unit,
    onDescriptionChanged: (String) -> Unit,
    onMonthChanged: (Int) -> Unit,
    onYearChanged: (Int) -> Unit,
    onCreateClicked: () -> Unit
) {
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = stringResource(id = R.string.add_achievement))
            IconButton(onClick = { onCreateClicked() }) {
                Icon(Icons.Filled.Add, "")
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            MonthsPicker(value = month, onValueChange = onMonthChanged)
            YearPicker(value = year, onValueChange = onYearChanged)
        }
        BottomSheetTextField(
            labelResId = R.string.achievement_title_label,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            value = title,
            onValueChange = onTitleChanged
        )
        BottomSheetTextField(
            labelResId = R.string.achievement_description_label,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            value = description,
            onValueChange = onDescriptionChanged
        )
    }
}

@Composable
private fun MonthsPicker(value: Int, onValueChange: (Int) -> Unit) {
    NumberPicker(
        label = { Month.of(it).getDisplayName(TextStyle.FULL_STANDALONE, Locale.ENGLISH) },
        value = value,
        onValueChange = onValueChange,
        range = 1..12
    )
}

@Composable
private fun YearPicker(value: Int, onValueChange: (Int) -> Unit) {
    NumberPicker(
        value = value,
        onValueChange = onValueChange,
        range = 1900..Year.now().value
    )
}

@Composable
private fun BottomSheetTextField(
    @StringRes labelResId: Int,
    keyboardOptions: KeyboardOptions,
    value: String,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = keyboardOptions,
        label = {
            Text(text = stringResource(id = labelResId))
        }
    )
}

@Preview
@Composable
private fun AchievementCardPreview() {
    AchievementCard(
        achievement = Achievement(
            id = 1,
            title = "Title",
            description = "Description",
            date = Date(month = Month.JANUARY, year = Year.of(2022))
        ),
        onDeleteClicked = {}
    )
}