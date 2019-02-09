package droidkaigi.github.io.challenge2019.data.repository.itemid

interface ItemIdDataSource {
    suspend fun getTopStoriesIds(): List<Long>
}
