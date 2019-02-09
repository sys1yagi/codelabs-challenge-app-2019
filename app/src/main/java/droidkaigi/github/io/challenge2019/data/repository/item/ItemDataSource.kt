package droidkaigi.github.io.challenge2019.data.repository.item

import droidkaigi.github.io.challenge2019.data.entity.ItemEntity

interface ItemDataSource {
    
    suspend fun getAllIds(): List<Long>

    suspend fun getItemWithIds(itemIds: IntArray): List<ItemEntity>

    suspend fun getIdWithIds(itemIds: IntArray): List<Long>

}
