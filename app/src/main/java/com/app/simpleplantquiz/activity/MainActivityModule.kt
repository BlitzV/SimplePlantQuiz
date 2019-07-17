package com.app.simpleplantquiz.activity

import dagger.Module
import dagger.Provides

@Module
class MainActivityModule {

    @Provides
    fun mainActivityMVPPresenter(): MainActivityMVP.Presenter{
        return MainActivityPresenter()
    }
}