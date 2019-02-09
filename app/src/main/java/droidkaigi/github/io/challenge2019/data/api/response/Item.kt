package droidkaigi.github.io.challenge2019.data.api.response

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

// TODO はずす
// jsonつける
@Parcelize
@JsonClass(generateAdapter = true)
data class Item(
    val id: Long = NO_ID,
    val deleted: Boolean = false,
    val type: String = "",
    @Json(name = "by")
    val author: String,
    val time: Long = 0L,
    val text: String? = "",
    val dead: Boolean = false,
    val parent: Long = NO_ID,
    val poll: Long = NO_ID,
    val kids: List<Long> = emptyList(),
    val url: String = "",
    val score: Int = 0,
    val title: String = "",
    val parts: List<Long> = emptyList(),
    val descendants: Int = 0
): Parcelable {
    companion object {
        const val NO_ID = -1L
    }
}
