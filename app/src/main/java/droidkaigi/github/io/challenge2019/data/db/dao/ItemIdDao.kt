package droidkaigi.github.io.challenge2019.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import droidkaigi.github.io.challenge2019.data.db.entity.ItemId

@Dao
interface ItemIdDao {
    @Query("SELECT id FROM item_id")
    suspend fun getAllIds(): List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(item: List<ItemId>)

}
