package com.gottlicher.snoolite

import android.app.Application
import com.gottlicher.snoolite.di.AppModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin


class SnooLiteApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            // Android context
            androidLogger()
            androidContext(this@SnooLiteApplication)
            // modules
            modules(AppModule.create())
        }
    }
}