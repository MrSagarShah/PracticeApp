package com.practiceapp.di.module

import android.app.Application
import com.practiceapp.data.MyObjectBox
import dagger.Module
import dagger.Provides
import io.objectbox.BoxStore
import javax.inject.Singleton

@Module
class DatabaseModule(private var applicationContext: Application) {

    @Provides
    @Singleton
    internal fun providesDatabase(): BoxStore {
        return MyObjectBox.builder().androidContext(applicationContext).build()
    }
}