package abandonedspace.android.y2y.presentation.add_category

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun AddCategoryScreen(
    viewModel: AddCategoryViewModel = hiltViewModel()
) {

    val name by viewModel.categoryName.collectAsState()
    val color by viewModel.categoryColor.collectAsState()
}