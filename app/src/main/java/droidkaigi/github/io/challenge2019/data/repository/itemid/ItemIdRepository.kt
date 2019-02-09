package droidkaigi.github.io.challenge2019.data.repository.itemid

import droidkaigi.github.io.challenge2019.util.coroutine.AppCoroutineDispatchers
import kotlinx.coroutines.withContext

class ItemIdRepository(
    private val dispatchers: AppCoroutineDispatchers,
    private val local: ItemIdLocalDataSource,
    private val remote: ItemIdRemoteDataSource
) : ItemIdDataSource {
    override suspend fun getTopStoriesIds(): List<Long> = withContext(dispatchers.io) {
        val ids = local.getTopStoriesIds()
        if (ids.isEmpty()) {
            remote.getTopStoriesIds()
        } else {
            ids
        }
    }

    suspend fun refresh() {
        withContext(dispatchers.io) {
            local.deleteAll()
        }
    }
}
