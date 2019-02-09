package droidkaigi.github.io.challenge2019.data.repository.item

import droidkaigi.github.io.challenge2019.data.api.HackerNewsApi
import droidkaigi.github.io.challenge2019.data.db.dao.ItemDao
import droidkaigi.github.io.challenge2019.data.entity.ItemEntity
import droidkaigi.github.io.challenge2019.util.coroutine.AppCoroutineDispatchers

class ItemRemoteDataSource(
    val dispatchers: AppCoroutineDispatchers,
    val api: HackerNewsApi,
    val dao: ItemDao
) : ItemDataSource {
    override suspend fun getAllIds(): List<Long> =
        dao.getAllIds()

    override suspend fun getItemWithIds(itemIds: IntArray): List<ItemEntity> =
        dao.getItemWithIds(itemIds).map { ItemEntity.fromDb(it) }

    override suspend fun getIdWithIds(itemIds: IntArray): List<Long> = dao.getIdWithIds(itemIds)

    suspend fun insert(item: ItemEntity) {
        dao.insert(item.toDb())
    }

    suspend fun insertAll(items: List<ItemEntity>) {
        dao.insertAll(items.map(ItemEntity::toDb))
    }
}
