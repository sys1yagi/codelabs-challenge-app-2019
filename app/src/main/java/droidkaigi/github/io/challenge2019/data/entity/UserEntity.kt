package droidkaigi.github.io.challenge2019.data.entity

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class UserEntity(
    @Json(name = "id")
    val id: String,
    @Json(name = "delay")
    val delay: Long,
    @Json(name = "created")
    val created: Long,
    @Json(name = "karma")
    val karma: Int,
    @Json(name = "about")
    val about: String,
    @Json(name = "submitted")
    val submitted: List<Long>
) : Parcelable
