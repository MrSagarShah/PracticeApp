package com.practiceapp

import android.app.Application
import com.practiceapp.di.components.DaggerMyComponent
import com.practiceapp.di.components.MyComponent
import com.practiceapp.di.module.DatabaseModule
import com.practiceapp.di.module.NetModule
import com.practiceapp.utils.BASE_URL

class App : Application() {
    companion object {
        lateinit var myComponent: MyComponent
    }

    override fun onCreate() {
        super.onCreate()
        myComponent = DaggerMyComponent.builder()
                .netModule(NetModule(BASE_URL))
                .databaseModule(DatabaseModule(this))
                .build()
    }
}