package com.practiceapp.di.components

import com.practiceapp.di.module.DatabaseModule
import com.practiceapp.di.module.NetModule
import com.practiceapp.ui.adapters.ImagesAdapter
import com.practiceapp.ui.controllers.MainController
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetModule::class, DatabaseModule::class])
interface MyComponent {

    fun inject(controller: MainController)

    fun inject(controller: ImagesAdapter)
}