package droidkaigi.github.io.challenge2019

import android.app.Application
import com.facebook.stetho.Stetho
import droidkaigi.github.io.challenge2019.di.mainModule
import droidkaigi.github.io.challenge2019.di.singletonModule
import org.koin.core.context.startKoin
import timber.log.Timber
import timber.log.Timber.DebugTree


class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            modules(
                singletonModule("https://hacker-news.firebaseio.com/v0/"),
                mainModule
            )
        }

        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
            Stetho.initializeWithDefaults(this)
        }

    }
}
