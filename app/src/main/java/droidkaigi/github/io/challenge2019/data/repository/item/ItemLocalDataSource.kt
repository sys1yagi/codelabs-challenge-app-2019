package droidkaigi.github.io.challenge2019.data.repository.item

import droidkaigi.github.io.challenge2019.data.db.dao.ItemDao
import droidkaigi.github.io.challenge2019.data.entity.ItemEntity

class ItemLocalDataSource(
    val dao: ItemDao
) : ItemDataSource {

    override suspend fun getItemWithIds(itemIds: List<Long>): List<ItemEntity> =
        dao.getItemWithIds(itemIds.toTypedArray().toLongArray()).map { ItemEntity.fromDb(it) }

    override suspend fun getItem(itemId: Long): ItemEntity = ItemEntity.fromDb(dao.getItem(itemId))

}
