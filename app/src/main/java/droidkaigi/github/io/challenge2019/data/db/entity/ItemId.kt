package droidkaigi.github.io.challenge2019.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "item_id")
data class ItemId(
    @PrimaryKey
    val id: Long = NO_ID
) {
    companion object {
        const val NO_ID = -1L
    }
}
