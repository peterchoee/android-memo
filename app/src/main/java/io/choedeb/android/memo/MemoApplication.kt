package io.choedeb.android.memo

import android.app.Application
import androidx.databinding.library.BuildConfig
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import io.choedeb.android.memo.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MemoApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        //Initialize Koin
        startKoin {
            androidLogger()
            androidContext(this@MemoApplication)
            modules(appModule)
        }

        //Initialize Logger
        val formatStrategy = PrettyFormatStrategy
            .newBuilder()
            .showThreadInfo(true)
            .build()

        Logger.addLogAdapter(object : AndroidLogAdapter(formatStrategy) {
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                return BuildConfig.DEBUG
            }
        })
    }
}