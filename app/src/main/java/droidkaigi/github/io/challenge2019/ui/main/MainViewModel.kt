package droidkaigi.github.io.challenge2019.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import droidkaigi.github.io.challenge2019.data.api.HackerNewsApi
import droidkaigi.github.io.challenge2019.data.api.response.Item
import droidkaigi.github.io.challenge2019.util.coroutine.AppCoroutineDispatchers
import droidkaigi.github.io.challenge2019.util.extension.await
import droidkaigi.github.io.challenge2019.util.extension.swapFirst
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class MainViewModel(
    private val dispatchers: AppCoroutineDispatchers,
    private val api: HackerNewsApi
) : ViewModel() {

    sealed class ViewState {
        object Progress : ViewState()
        object NotProgress : ViewState()
        class Error(e: Exception) : ViewState()
    }

    private val viewState = MutableLiveData<ViewState>().apply { value = ViewState.NotProgress }

    fun viewState(): LiveData<ViewState> = viewState

    private val topStories = MutableLiveData<List<Item>>()

    fun topStories(): LiveData<List<Item>> = topStories

    fun loadTopStories(refresh: Boolean = false) {
        viewModelScope.launch {
            try {
                if (!refresh) {
                    viewState.postValue(ViewState.Progress)
                }

                val storyIds = api
                    .getTopStories()
                    .await()
                    .take(20)

                // 1件でも取り出せなかったら全体のエラーとする
                // エラーを無視する場合はtry-catchしつつ、mapNotNullにする
                val stories = coroutineScope {
                    storyIds
                        .map { id ->
                            // 並行で実行するために2回mapする
                            async(dispatchers.io) { api.getItem(id).await() }
                        }
                        .map { deferred ->
                            deferred.await()
                        }
                }

                topStories.postValue(stories)
                viewState.postValue(ViewState.NotProgress)
            } catch (e: CancellationException) {
                // no op
            } catch (e: Exception) {
                viewState.postValue(ViewState.Error(e))
            }
        }
    }

    fun reloadItem(item: Item) {
        viewModelScope.launch {
            try {
                val reloadedItem = api.getItem(item.id).await()
                topStories.postValue(
                    topStories.value?.swapFirst(reloadedItem) {
                        it.id == item.id
                    }
                )
            } catch (e: CancellationException) {
                // no op
            } catch (e: Exception) {
                // TODO
            }
        }
    }

}
