package abandonedspace.android.y2y.presentation.add_category

import abandonedspace.android.y2y.domain.presentation.components.ProgressIndicatorDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.godaddy.android.colorpicker.ClassicColorPicker

@Composable
fun AddCategoryScreen(
    viewModel: AddCategoryViewModel = hiltViewModel()
) {

    val name by viewModel.categoryName.collectAsState()
    val color by viewModel.categoryColor.collectAsState()
    val selectedColorOption by viewModel.selectedColorOption.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Add category", // TODO: as string res
                style = MaterialTheme.typography.h6,
                fontWeight = FontWeight.Bold
            )

            IconButton(
                onClick = viewModel::onDoneClicked
            ) {
                Icon(Icons.Default.Add, tint = Color.Black, contentDescription = "")
            }
        }

        TextField(
            value = name,
            onValueChange = viewModel::onNameChanged,
            label = {
                Text(text = "Name")
            },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.White,
                textColor = color ?: Color.Black,
                focusedIndicatorColor = color ?: Color.Black,
                unfocusedIndicatorColor = color ?: Color.Black,
                focusedLabelColor = color ?: Color.Black,
                unfocusedLabelColor = color ?: Color.Black,
            )
        )

        ColorSelectionComponent(
            color = color,
            selectedOption = selectedColorOption,
            onOptionChanged = viewModel::onColorSelectedOptionChanged,
            onColorChanged = viewModel::onColorChanged
        )
    }

    if (viewModel.progressBarVisible.value) {
        ProgressIndicatorDialog()
    }
}

@Composable
private fun ColorSelectionComponent(
    color: Color?,
    selectedOption: ColorSelectionOption,
    onOptionChanged: (ColorSelectionOption) -> Unit,
    onColorChanged: (Color) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
    ) {
        Text(
            text = "Color",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.body1
        )

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            TextRadioButton(
                label = "None",
                selected = selectedOption == ColorSelectionOption.NONE
            ) {
                onOptionChanged(ColorSelectionOption.NONE)
            }
            TextRadioButton(
                label = "Choose",
                selected = selectedOption == ColorSelectionOption.SELECTED
            ) {
                onOptionChanged(ColorSelectionOption.SELECTED)
            }
        }

        if (selectedOption == ColorSelectionOption.SELECTED) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
                    .height(48.dp)
                    .clip(RoundedCornerShape(50))
                    .background(color = color ?: Color.Transparent)
            )

            ClassicColorPicker(
                modifier = Modifier.fillMaxWidth(),
                showAlphaBar = false,
                onColorChanged = {
                    onColorChanged(it.toColor())
                }
            )
        }
    }
}

@Composable
private fun TextRadioButton(
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        RadioButton(selected = selected, onClick = onClick)
        Text(text = label)
    }
}

@Preview
@Composable
private fun TextRadioButtonPreview() {
    TextRadioButton(label = "Dummy label", selected = true) {}
}

@Preview
@Composable
private fun ColorSelectionComponentPreview() {
    ColorSelectionComponent(
        color = Color.Blue,
        selectedOption = ColorSelectionOption.SELECTED,
        onOptionChanged = {},
        onColorChanged = {}
    )
}