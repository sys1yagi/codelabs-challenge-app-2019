package droidkaigi.github.io.challenge2019.data.repository.itemid

import droidkaigi.github.io.challenge2019.data.api.HackerNewsApi
import droidkaigi.github.io.challenge2019.data.db.dao.ItemIdDao
import droidkaigi.github.io.challenge2019.data.db.entity.ItemId
import droidkaigi.github.io.challenge2019.util.coroutine.AppCoroutineDispatchers
import droidkaigi.github.io.challenge2019.util.extension.await
import kotlinx.coroutines.withContext
import timber.log.Timber

class ItemIdRemoteDataSource(
    val dispatchers: AppCoroutineDispatchers,
    val api: HackerNewsApi,
    val dao: ItemIdDao
) : ItemIdDataSource {
    override suspend fun getTopStoriesIds(): List<Long> = withContext(dispatchers.io) {
        Timber.d("remote itemids")
        val stories = api.getTopStories().await()
        dao.insertAll(stories.map(::ItemId))
        stories
    }
}
