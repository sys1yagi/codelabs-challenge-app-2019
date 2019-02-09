package droidkaigi.github.io.challenge2019.data.db.dao

import androidx.room.*
import droidkaigi.github.io.challenge2019.data.db.entity.ItemId

@Dao
interface ItemIdDao {
    @Query("SELECT id FROM item_id")
    suspend fun getAllIds(): List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(item: List<ItemId>)

    @Query("DELETE FROM item_id")
    suspend fun deleteAll()
}
