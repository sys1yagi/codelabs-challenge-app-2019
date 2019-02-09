package droidkaigi.github.io.challenge2019.data.api.response

data class User(
    val id: String,
    val delay: Long,
    val created: Long,
    val karma: Int,
    val about: String,
    val submitted: List<Long>
)
