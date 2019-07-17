package com.app.simpleplantquiz.root

import com.app.simpleplantquiz.activity.MainActivity
import com.app.simpleplantquiz.activity.MainActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class AppBinder {

    @ContributesAndroidInjector(modules = arrayOf(MainActivityModule::class))
    internal abstract fun contributeMainActivity(): MainActivity
}