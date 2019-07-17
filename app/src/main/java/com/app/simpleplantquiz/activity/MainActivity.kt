package com.app.simpleplantquiz.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.os.AsyncTask
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.support.v7.app.AppCompatActivity;
import android.view.View
import android.view.WindowManager
import com.app.simpleplantquiz.helpers.DialogsHelpers.Companion.DialogSimpleOkButton
import com.app.simpleplantquiz.helpers.helperfun
import com.app.simpleplantquiz.helpers.helperfun.Companion.checkForInternetConnection
import com.app.simpleplantquiz.models.ParsePlantUtility
import com.app.simpleplantquiz.models.Plant
import com.example.plantquiz.R
import dagger.android.AndroidInjection

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import java.lang.Exception
import javax.inject.Inject

class MainActivity : AppCompatActivity(),MainActivityMVP.View {

//    @BindView(R.id.button1)
//    lateinit var OptionOne: Button
//
//    @BindView(R.id.button2)
//    lateinit var OptionTwo: Button
//
//    @BindView(R.id.button3)
//    lateinit var OptionThree: Button
//
//    @BindView(R.id.button4)
//    var OptionFour: Button? = null
//
//    @BindView(R.id.imgRightAnswers)
//    lateinit var imgRight: ImageView
//
//    @BindView(R.id.imgWrongAnswers)
//    lateinit var imgWrong: ImageView
//
//    @BindView(R.id.txtState)
//    lateinit var state: TextView

    @Inject
    lateinit var presenter: MainActivityMVP.Presenter

    val OPEN_PHOTO_GALLERY_BUTTON_REQUEST_ID = 100
    val OPEN_CAMERA_BUTTON_REQUEST_ID = 101

    // Instance Variables
    var correctAnswerIndex: Int = 0
    var correctPlant: Plant? = null

    var numberOfTimesUserAnsweredCorrectly: Int = 0
    var numberOfTimesUserAnsweredInCorrectly: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

     //   ButterKnife.bind(this)

        setProgressBar(false)

        btnOpenPhotoGallery.setOnClickListener {

            val intent = Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, OPEN_PHOTO_GALLERY_BUTTON_REQUEST_ID)

        }

        btnOpenCamera.setOnClickListener {

            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(cameraIntent, OPEN_CAMERA_BUTTON_REQUEST_ID)


        }

        floatingbutton.setOnClickListener {

            if(checkForInternetConnection(this)){
                setProgressBar(true)

                try {
                    val classObject = DownloadingPlantTask()

                    classObject.execute()
                } catch (e:Exception){

                }
            } else {
                DialogSimpleOkButton(this, getString(R.string.dialog_title), getString(R.string.dialog_content),DialogInterface.OnClickListener { dialog, _ ->
                    startActivity(Intent(Settings.ACTION_SETTINGS))
                    dialog.cancel()
                }).show()
            }

        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == OPEN_PHOTO_GALLERY_BUTTON_REQUEST_ID){
            if(resultCode == Activity.RESULT_OK) {
                val contetURI = data?.data
                val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, contetURI)
                imgTaken.setImageBitmap(bitmap)
            }
        }

        if(requestCode == OPEN_CAMERA_BUTTON_REQUEST_ID){
            if(resultCode == Activity.RESULT_OK) {
                val imgData = data?.extras?.get("data") as Bitmap
                imgTaken.setImageBitmap(imgData)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        presenter.setView(this)
    }

    override fun fetchContext(): Context {
        return this
    }

    fun setProgressBar(show: Boolean) {
        if(show){
            linearLayoutProgress?.visibility = View.VISIBLE
            progressBar?.visibility = View.VISIBLE
            window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        } else if (!show) {
            linearLayoutProgress?.visibility = View.GONE
            progressBar?.visibility = View.GONE
            window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        }
    }

    @SuppressLint("StaticFieldLeak")
    inner class DownloadingPlantTask: AsyncTask<String, Int, List<Plant>>() {
        override fun doInBackground(vararg params: String?): List<Plant>? {
            val parsePlant = ParsePlantUtility()

            return parsePlant.parsePlantObjectsFromJSONData()
        }

        override fun onPostExecute(result: List<Plant>?) {
            super.onPostExecute(result)

            // Can access user interface thread. Not background thread

            var numberOfPlants = result?.size ?: 0

            if (numberOfPlants > 0) {

                var randomPlantIndexForButton1: Int = (Math.random() * result!!.size).toInt()
                var randomPlantIndexForButton2: Int = (Math.random() * result.size).toInt()
                var randomPlantIndexForButton3: Int = (Math.random() * result.size).toInt()
                var randomPlantIndexForButton4: Int = (Math.random() * result.size).toInt()

                var allRandomPlants = ArrayList<Plant>()
                allRandomPlants.add(result[randomPlantIndexForButton1])
                allRandomPlants.add(result[randomPlantIndexForButton2])
                allRandomPlants.add(result[randomPlantIndexForButton3])
                allRandomPlants.add(result[randomPlantIndexForButton4])

                button1.text = result[randomPlantIndexForButton1].toString()
                button2.text = result[randomPlantIndexForButton2].toString()
                button3.text = result[randomPlantIndexForButton3].toString()
                button4.text = result[randomPlantIndexForButton4].toString()

                correctAnswerIndex = (Math.random() * allRandomPlants.size).toInt()
                correctPlant = allRandomPlants[correctAnswerIndex]

                val downloadingImageTask = DownloadingImageTask()
                downloadingImageTask.execute(allRandomPlants[correctAnswerIndex].pictureName)
            }
        }
    }

    //Download Image Process
    @SuppressLint("StaticFieldLeak")
    inner class DownloadingImageTask: AsyncTask<String, Int, Bitmap?>(){

        override fun doInBackground(vararg params: String?): Bitmap? {

            try {
                val plantBitmap: Bitmap? = helperfun.downloadPlanPicture(params[0])
                return plantBitmap
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return null
        }

        override fun onPostExecute(result: Bitmap?) {
            super.onPostExecute(result)

            setProgressBar(false)

            imgTaken.setImageBitmap(result)
        }
    }
}


