package droidkaigi.github.io.challenge2019.data.repository.itemid

import droidkaigi.github.io.challenge2019.data.db.dao.ItemIdDao
import timber.log.Timber

class ItemIdLocalDataSource(
    val dao: ItemIdDao
) : ItemIdDataSource {
    override suspend fun getTopStoriesIds(): List<Long> {
        Timber.d("local itemids")
        return dao.getAllIds()
    }
}
