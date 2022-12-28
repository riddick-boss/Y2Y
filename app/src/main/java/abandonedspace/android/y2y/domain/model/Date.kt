package abandonedspace.android.y2y.domain.model

import java.time.Month
import java.time.Year
import java.time.format.TextStyle
import java.util.*

data class Date(
    private val month: Month,
    private val year: Year
) {
    val displayValue: String
        get() = "${month.getDisplayName(TextStyle.SHORT, Locale.ENGLISH)}/${year.value}"
}
