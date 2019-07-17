package com.app.simpleplantquiz.activity

import com.app.simpleplantquiz.root.BasePresenter
import com.app.simpleplantquiz.root.BaseView

interface MainActivityMVP {

    interface View: BaseView<Presenter> {}

    interface Presenter: BasePresenter<View> {}
}