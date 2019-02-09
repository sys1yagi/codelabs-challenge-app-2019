package droidkaigi.github.io.challenge2019.data.repository.item

import droidkaigi.github.io.challenge2019.data.db.dao.ItemDao
import droidkaigi.github.io.challenge2019.data.entity.ItemEntity

class ItemLocalDataSource(
    val dao: ItemDao
) : ItemDataSource {
    override suspend fun getAllIds(): List<Long> = dao.getAllIds()

    override suspend fun getItemWithIds(itemIds: IntArray): List<ItemEntity> =
        dao.getItemWithIds(itemIds).map { ItemEntity.fromDb(it) }

    override suspend fun getIdWithIds(itemIds: IntArray): List<Long> = dao.getIdWithIds(itemIds)
}
