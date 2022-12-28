package abandonedspace.android.y2y.domain.presentation.navigation

import com.google.common.truth.Truth
import org.junit.Test

class SCREENSTest {

    @Test
    fun `all routes are different`() {
        val routes = SCREENS.values().map { it.route.value }
        Truth.assertThat(routes).containsNoDuplicates()
    }
}