package com.app.simpleplantquiz.activity

class MainActivityPresenter: MainActivityMVP.Presenter {

    private var view: MainActivityMVP.View? = null

    var numberOfTimesUserAnsweredCorrectly: Int = 0
    var numberOfTimesUserAnsweredInCorrectly: Int = 0

    override fun setView(view: MainActivityMVP.View) {
        this.view = view
    }

    override fun dropView() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun specifyTheRightAndWrongAnswer(userGuess: Int) {
        // Specify the right or wrong answer

        if (userGuess == view?.getNumber()) {
            numberOfTimesUserAnsweredCorrectly++

            view?.getResponse(true, numberOfTimesUserAnsweredCorrectly)

        } else {
            var correctPlantName = view?.getttingPlant().toString()
            numberOfTimesUserAnsweredInCorrectly++

            view?.getResponse(false, numberOfTimesUserAnsweredInCorrectly)
        }

    }
}