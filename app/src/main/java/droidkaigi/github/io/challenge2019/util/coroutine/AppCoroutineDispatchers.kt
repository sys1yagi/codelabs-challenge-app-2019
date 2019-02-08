package droidkaigi.github.io.challenge2019.util.coroutine

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

data class AppCoroutineDispatchers(
    val main: CoroutineDispatcher = Dispatchers.Main,
    val io: CoroutineDispatcher = Dispatchers.IO,
    val compute: CoroutineDispatcher = Dispatchers.Default
)
