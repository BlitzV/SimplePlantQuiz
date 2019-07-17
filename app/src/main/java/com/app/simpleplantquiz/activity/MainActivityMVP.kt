package com.app.simpleplantquiz.activity

import com.app.simpleplantquiz.models.Plant
import com.app.simpleplantquiz.root.BasePresenter
import com.app.simpleplantquiz.root.BaseView

interface MainActivityMVP {

    interface View: BaseView<Presenter> {
        fun getNumber(): Int
        fun getttingPlant(): Plant?
        fun getResponse(response:Boolean, number:Int)

    }

    interface Presenter: BasePresenter<View> {
        fun specifyTheRightAndWrongAnswer(userGuess: Int)
    }
}