package com.app.simpleplantquiz.root

import android.app.Activity
import android.app.Application
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class App: Application(), HasActivityInjector {
    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        DaggerApplicationComponent.builder().application(this).build().inject(this)
    }
    override fun activityInjector(): AndroidInjector<Activity> = activityInjector

}


//class LuaApp : Application(), HasActivityInjector {
//    @Inject
//    lateinit var activityInjector: DispatchingAndroidInjector<Activity>
//
//    override fun onCreate() {
//        super.onCreate()
//        // initialize Dagger
//        DaggerAppComponent.builder().application(this).build().inject(this)
//    }
//
//    // this is required to setup Dagger2 for Activity
//    override fun activityInjector(): AndroidInjector<Activity> = activityInjector
//}