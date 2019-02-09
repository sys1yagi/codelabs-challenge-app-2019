package droidkaigi.github.io.challenge2019.data.repository.item

import droidkaigi.github.io.challenge2019.data.api.HackerNewsApi
import droidkaigi.github.io.challenge2019.data.db.dao.ItemDao
import droidkaigi.github.io.challenge2019.data.entity.ItemEntity
import droidkaigi.github.io.challenge2019.util.coroutine.AppCoroutineDispatchers
import droidkaigi.github.io.challenge2019.util.extension.await
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext

class ItemRemoteDataSource(
    private val dispatchers: AppCoroutineDispatchers,
    private val api: HackerNewsApi,
    private val dao: ItemDao
) : ItemDataSource {

    override suspend fun getItemWithIds(itemIds: List<Long>): List<ItemEntity> = withContext(dispatchers.io) {
        itemIds
            .map { id ->
                async(dispatchers.io) { api.getItem(id).await() }
            }
            .awaitAll()
            .map {
                ItemEntity.fromResponse(it)
            }
            .also { items ->
                insertAll(items)
            }
    }

    override suspend fun getItem(itemId: Long): ItemEntity = ItemEntity.fromResponse(
        api.getItem(itemId).await()
    )
        .also {
            dao.insert(it.toDb())
        }

    private suspend fun insertAll(items: List<ItemEntity>) {
        dao.insertAll(items.map(ItemEntity::toDb))
    }
}
