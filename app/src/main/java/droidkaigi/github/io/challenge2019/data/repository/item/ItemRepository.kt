package droidkaigi.github.io.challenge2019.data.repository.item

import droidkaigi.github.io.challenge2019.data.entity.ItemEntity

class ItemRepository(
    private val local: ItemLocalDataSource,
    private val remote: ItemRemoteDataSource
) : ItemDataSource {

    override suspend fun getItemWithIds(itemIds: List<Long>): List<ItemEntity> {
        // TODO itemIds順にする
        val items = local.getItemWithIds(itemIds)
        val remain = itemIds.toList() - items.map { it.id }
        if (remain.isEmpty()) {
            return items
        } else {
            return items + remote.getItemWithIds(remain)
        }
    }

    override suspend fun getItem(itemId: Long): ItemEntity {
        val item = local.getItemWithIds(listOf(itemId)).firstOrNull()
        return if (item != null) {
            item
        } else {
            remote.getItem(itemId)
        }
    }
}
