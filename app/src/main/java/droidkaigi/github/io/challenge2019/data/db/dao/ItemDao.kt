package droidkaigi.github.io.challenge2019.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import droidkaigi.github.io.challenge2019.data.db.entity.Item

@Dao
interface ItemDao {
    @Query("SELECT * FROM item")
    suspend fun getAll(): List<Item>

    @Query("SELECT id FROM item")
    suspend fun getAllIds(): List<Long>

    @Query("SELECT * FROM item WHERE id IN(:itemIds)")
    suspend fun getItemWithIds(itemIds: IntArray): List<Item>

    @Query("SELECT id FROM item WHERE id IN(:itemIds)")
    suspend fun getIdWithIds(itemIds: IntArray): List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: Item)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(item: List<Item>)

}
