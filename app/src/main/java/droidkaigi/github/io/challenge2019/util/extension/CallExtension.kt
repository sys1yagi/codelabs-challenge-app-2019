package droidkaigi.github.io.challenge2019.util.extension

import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

suspend fun <T> Call<T>.await(): T = suspendCancellableCoroutine { continuation ->
    continuation.invokeOnCancellation {
        cancel()
    }
    enqueue(object : Callback<T> {
        override fun onFailure(call: Call<T>, t: Throwable) {
            if (!continuation.isCancelled) {
                continuation.resumeWithException(t)
            }
        }

        override fun onResponse(call: Call<T>, response: Response<T>) {
            if (continuation.isCancelled) {
                return
            }
            if (response.isSuccessful) {
                response.body()?.let {
                    continuation.resume(it)
                } ?: continuation.resume(Unit as T)
            } else {
                continuation.resumeWithException(HttpException(response))
            }
        }
    })
}
