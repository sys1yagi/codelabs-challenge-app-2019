package droidkaigi.github.io.challenge2019.data.entity

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import droidkaigi.github.io.challenge2019.data.db.entity.Item
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class ItemEntity(
    @Json(name = "id")
    val id: Long = NO_ID,
    @Json(name = "deleted")
    val deleted: Boolean = false,
    @Json(name = "type")
    val type: String = "",
    @Json(name = "by")
    val author: String,
    @Json(name = "time")
    val time: Long = 0L,
    @Json(name = "text")
    val text: String? = "",
    @Json(name = "dead")
    val dead: Boolean = false,
    @Json(name = "parent")
    val parent: Long = NO_ID,
    @Json(name = "poll")
    val poll: Long = NO_ID,
    @Json(name = "kids")
    val kids: List<Long> = emptyList(),
    @Json(name = "url")
    val url: String = "",
    @Json(name = "score")
    val score: Int = 0,
    @Json(name = "title")
    val title: String = "",
    @Json(name = "parts")
    val parts: List<Long> = emptyList(),
    @Json(name = "descendants")
    val descendants: Int = 0
) : Parcelable {
    companion object {
        const val NO_ID = -1L

        fun fromDb(item: droidkaigi.github.io.challenge2019.data.db.entity.Item): ItemEntity = ItemEntity(
            item.id,
            item.deleted,
            item.type,
            item.author,
            item.time,
            item.text,
            item.dead,
            item.parent,
            item.poll,
            emptyList(), // TODO
            item.url,
            item.score,
            item.title,
            emptyList(), // TODO
            item.descendants
        )

        fun fromResponse(item: droidkaigi.github.io.challenge2019.data.api.response.Item): ItemEntity = ItemEntity(
            item.id,
            item.deleted,
            item.type,
            item.author,
            item.time,
            item.text,
            item.dead,
            item.parent,
            item.poll,
            item.kids,
            item.url,
            item.score,
            item.title,
            item.parts,
            item.descendants
        )
    }

    fun toDb(): droidkaigi.github.io.challenge2019.data.db.entity.Item =
        droidkaigi.github.io.challenge2019.data.db.entity.Item(
            id,
            deleted,
            type,
            author,
            time,
            text,
            dead,
            parent,
            poll,
            // TODO kids,
            url,
            score,
            title,
            // TODO parts,
            descendants
        )
}
