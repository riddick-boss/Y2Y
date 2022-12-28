package abandonedspace.android.y2y.domain.model

import com.google.common.truth.Truth
import org.junit.Test
import java.time.Month
import java.time.Year

class DateTest {

    @Test
    fun `displayValue formats month and year properly`() {
        val date = Date(Month.JUNE, Year.of(2022))
        Truth.assertThat(date.displayValue).isEqualTo("June 2022")
    }
}