package droidkaigi.github.io.challenge2019.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "user")
data class User(
    @PrimaryKey
    val id: String,
    @ColumnInfo(name = "delay")
    val delay: Long,
    @ColumnInfo(name = "created")
    val created: Long,
    @ColumnInfo(name = "karma")
    val karma: Int,
    @ColumnInfo(name = "about")
    val about: String
    // TODO
//    @ColumnInfo(name = "submitted")
//    val submitted: List<Long>
)
