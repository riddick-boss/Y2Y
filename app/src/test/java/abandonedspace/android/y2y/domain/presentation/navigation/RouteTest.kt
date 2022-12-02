package abandonedspace.android.y2y.domain.presentation.navigation

import com.google.common.truth.Truth
import org.junit.Test

class RouteTest {

    @Test
    fun `can not create Route with empty value`() {
        val exception =
            try {
                Route("")
                null
            } catch (e: Exception) {
                e
            }
        Truth.assertThat(exception).isNotNull()
    }

    @Test
    fun `can not create Route with blank value`() {
        val exception =
            try {
                Route("       ") // do not change, blank for purpose
                null
            } catch (e: Exception) {
                e
            }
        Truth.assertThat(exception).isNotNull()
    }

    @Test
    fun`can create Route with value`() {
        val exception =
            try {
                Route("some_dummy_route")
                null
            } catch (e: Exception) {
                e
            }
        Truth.assertThat(exception).isNull()
    }
}