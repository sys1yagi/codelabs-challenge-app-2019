package droidkaigi.github.io.challenge2019.data.repository.item

import droidkaigi.github.io.challenge2019.data.entity.ItemEntity

interface ItemDataSource {

    suspend fun getItemWithIds(itemIds: List<Long>): List<ItemEntity>
    suspend fun getItem(itemId: Long): ItemEntity
}
