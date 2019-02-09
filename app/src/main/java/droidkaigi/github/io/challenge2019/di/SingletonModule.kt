package droidkaigi.github.io.challenge2019.di

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import com.facebook.stetho.okhttp3.StethoInterceptor
import droidkaigi.github.io.challenge2019.BuildConfig
import droidkaigi.github.io.challenge2019.MyApplication
import droidkaigi.github.io.challenge2019.data.api.HackerNewsApi
import droidkaigi.github.io.challenge2019.data.db.AppDatabase
import droidkaigi.github.io.challenge2019.data.db.dao.ItemIdDao
import droidkaigi.github.io.challenge2019.data.repository.item.ItemLocalDataSource
import droidkaigi.github.io.challenge2019.data.repository.item.ItemRemoteDataSource
import droidkaigi.github.io.challenge2019.data.repository.itemid.ItemIdLocalDataSource
import droidkaigi.github.io.challenge2019.data.repository.itemid.ItemIdRemoteDataSource
import droidkaigi.github.io.challenge2019.data.repository.itemid.ItemIdRepository
import droidkaigi.github.io.challenge2019.util.coroutine.AppCoroutineDispatchers
import kotlinx.coroutines.Dispatchers
import okhttp3.CipherSuite
import okhttp3.ConnectionSpec
import okhttp3.OkHttpClient
import okhttp3.TlsVersion
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

// TODO 分割したほうがよさそう
fun singletonModule(application: Application, url: String) = module {

    single {
        application
    }

    single<OkHttpClient> {
        OkHttpClient.Builder().apply {
            if (BuildConfig.DEBUG) {
                addNetworkInterceptor(StethoInterceptor())
            }
        }.build()
    }

    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl(url)
            .client(get())
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    single<HackerNewsApi> {
        get<Retrofit>().create(HackerNewsApi::class.java)
    }

    single<AppCoroutineDispatchers> {
        AppCoroutineDispatchers()
    }

    single<AppDatabase> {
        Room.databaseBuilder(get<Application>(), AppDatabase::class.java, "database")
            .build()
    }

    single<ItemIdDao> {
        get<AppDatabase>().itemIdDao()
    }

    single<ItemIdLocalDataSource> {
        ItemIdLocalDataSource(get())
    }
    single {
        ItemIdRemoteDataSource(get(), get(), get())
    }
    single {
        ItemIdRepository(
            get(),
            get(),
            get()
        )
    }
}
