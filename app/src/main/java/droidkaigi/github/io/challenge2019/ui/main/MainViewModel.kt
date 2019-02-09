package droidkaigi.github.io.challenge2019.ui.main

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import droidkaigi.github.io.challenge2019.data.api.HackerNewsApi
import droidkaigi.github.io.challenge2019.data.entity.ItemEntity
import droidkaigi.github.io.challenge2019.data.repository.itemid.ItemIdRepository
import droidkaigi.github.io.challenge2019.util.coroutine.AppCoroutineDispatchers
import droidkaigi.github.io.challenge2019.util.extension.await
import droidkaigi.github.io.challenge2019.util.extension.swapFirst
import kotlinx.coroutines.*
import timber.log.Timber

class MainViewModel(
    private val dispatchers: AppCoroutineDispatchers,
    private val api: HackerNewsApi,
    private val itemIdRepository: ItemIdRepository
) : ViewModel() {

    companion object {
        private const val STATE_STORIES = "stories"
    }

    sealed class ViewState {
        object Progress : ViewState()
        object NotProgress : ViewState()
        class Error(e: Exception) : ViewState()
    }

    private val viewState = MutableLiveData<ViewState>().apply { value = ViewState.NotProgress }

    fun viewState(): LiveData<ViewState> = viewState

    private val topStories = MutableLiveData<List<Story>>()

    fun topStories(): LiveData<List<Story>> = topStories

    fun loadTopStories(
        savedInstanceState: Bundle? = null,
        refresh: Boolean = false
    ) {
        viewModelScope.launch {
            try {
                if (!refresh) {
                    viewState.postValue(ViewState.Progress)
                } else {
                    itemIdRepository.refresh()
                }

                val stories = if (savedInstanceState != null) {
                    savedInstanceState.getParcelableArrayList<Story>(STATE_STORIES)?.toList()
                } else {
                    val storyIds = itemIdRepository
                        .getTopStoriesIds()
                        .take(20)

                    // todo get already read ids

                    // 1件でも取り出せなかったら全体のエラーとする
                    // エラーを無視する場合はtry-catchしつつ、mapNotNullにする
                    coroutineScope {
                        storyIds
                            .map { id ->
                                async(dispatchers.io) { api.getItem(id).await() }
                            }
                            .awaitAll()
                            .map {
                                Story(ItemEntity.fromResponse(it), false)
                            }
                    }
                }
                topStories.postValue(stories)
                viewState.postValue(ViewState.NotProgress)
            } catch (e: CancellationException) {
                // no op
            } catch (e: Exception) {
                Timber.e(e)
                viewState.postValue(ViewState.Error(e))
            }
        }
    }

    fun reloadItem(item: ItemEntity) {
        viewModelScope.launch {
            try {
                val reloadedItem = ItemEntity.fromResponse(api.getItem(item.id).await())
                val reloadedStory = Story(reloadedItem, false) // TODO
                topStories.postValue(
                    topStories.value?.swapFirst(reloadedStory) {
                        it.item.id == item.id
                    }
                )
            } catch (e: CancellationException) {
                // no op
            } catch (e: Exception) {
                // TODO
            }
        }
    }

    fun onSaveInstanceState(outState: Bundle) {
        topStories.value?.let {
            outState.putParcelableArrayList(STATE_STORIES, ArrayList(it))
        }
    }
}
