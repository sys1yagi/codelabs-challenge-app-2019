package droidkaigi.github.io.challenge2019.di

import droidkaigi.github.io.challenge2019.data.api.HackerNewsApi
import droidkaigi.github.io.challenge2019.util.coroutine.AppCoroutineDispatchers
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

fun singletonModule(url: String) = module {

    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    single<HackerNewsApi> {
        get<Retrofit>().create(HackerNewsApi::class.java)
    }

    single {
        AppCoroutineDispatchers()
    }
}
