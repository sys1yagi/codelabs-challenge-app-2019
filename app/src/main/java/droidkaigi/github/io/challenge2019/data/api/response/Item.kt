package droidkaigi.github.io.challenge2019.data.api.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

// TODO はずす
@Parcelize
data class Item(
    val id: Long = NO_ID,
    val deleted: Boolean = false,
    val type: String = "",
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
