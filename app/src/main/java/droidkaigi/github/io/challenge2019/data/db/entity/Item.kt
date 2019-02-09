package droidkaigi.github.io.challenge2019.data.db.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "item")
data class Item(
    @PrimaryKey
    val id: Long = NO_ID,
    @ColumnInfo(name = "deleted")
    val deleted: Boolean = false,
    @ColumnInfo(name = "type")
    val type: String = "",
    @ColumnInfo(name = "by")
    val author: String,
    @ColumnInfo(name = "time")
    val time: Long = 0L,
    @ColumnInfo(name = "text")
    val text: String? = "",
    @ColumnInfo(name = "dead")
    val dead: Boolean = false,
    @ColumnInfo(name = "parent")
    val parent: Long = NO_ID,
    @ColumnInfo(name = "poll")
    val poll: Long = NO_ID,
    // TODO
//    @ColumnInfo(name = "kids")
//    val kids: List<Long> = emptyList(),
    @ColumnInfo(name = "url")
    val url: String = "",
    @ColumnInfo(name = "score")
    val score: Int = 0,
    @ColumnInfo(name = "title")
    val title: String = "",
    // TODO
//    @ColumnInfo(name = "parts")
//    val parts: List<Long> = emptyList(),
    @ColumnInfo(name = "descendants")
    val descendants: Int = 0
) {
    companion object {
        const val NO_ID = -1L
    }
}
