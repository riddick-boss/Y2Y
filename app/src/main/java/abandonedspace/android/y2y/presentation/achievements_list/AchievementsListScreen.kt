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
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.time.Month
import java.time.Year

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AchievementsScreen(
    viewModel: AchievementsViewModel = hiltViewModel()
) {
    val achievements by viewModel.achievements.collectAsState()

    val bottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true
    )
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        viewModel.onCloseBottomSheet.onEach {
            bottomSheetState.hide()
        }.launchIn(this)
    }

    ModalBottomSheetLayout(
        sheetState = bottomSheetState,
        sheetContent = { BottomSheetContent(onCreateClicked = viewModel::onAddAchievement) },

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
                    AchievementCard(achievement = achievements[it], onDeleteClicked = viewModel::onAchievementDeleteClicked)
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
            Text(text = achievement.date.displayValue)
            Text(text = achievement.title)
            achievement.description?.also {
                Text(text = it)
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
private fun BottomSheetContent(onCreateClicked: (String, String, Int, Int) -> Unit) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var month by remember { mutableStateOf(1) }
    var year by remember { mutableStateOf(Year.now().value) }

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
            IconButton(onClick = { onCreateClicked(title, description, month, year) }) {
                Icon(Icons.Filled.Add, "")
            }
        }
        BottomSheetTextField(labelResId = R.string.achievement_title_label, value = title, onValueChange = { title = it })
        BottomSheetTextField(labelResId = R.string.achievement_description_label, value = description, onValueChange = { description = it })
    }
}

@Composable
private fun BottomSheetTextField(@StringRes labelResId: Int, value: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier.fillMaxWidth(),
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