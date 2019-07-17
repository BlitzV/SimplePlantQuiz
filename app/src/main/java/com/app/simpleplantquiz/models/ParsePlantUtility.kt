package com.app.simpleplantquiz.models

import com.app.simpleplantquiz.helpers.helperfun.Companion.downloadJSONDataFromLink
import org.json.JSONArray
import org.json.JSONObject

class ParsePlantUtility {

    fun parsePlantObjectsFromJSONData() : List<Plant>?{
        val allPlantObjects: ArrayList<Plant> = ArrayList()
        val topLevelPLantJSONData = downloadJSONDataFromLink("http://plantplaces.com/perl/mobile/flashcard.pl")
        val topLevelJSONObject: JSONObject = JSONObject(topLevelPLantJSONData)
        val plantObjectsArray: JSONArray = topLevelJSONObject.getJSONArray("values")

        //downloadJSONDataFromLink

        var index: Int = 0

        while (index < plantObjectsArray.length()) {


            val plantObject: Plant = Plant()
            val jsonObject = plantObjectsArray.getJSONObject(index)


            with(jsonObject) {
                plantObject.genus = getString("genus")
                plantObject.species = getString("species")
                plantObject.cultivar = getString("cultivar")
                plantObject.common = getString("common")
                plantObject.pictureName = getString("picture_name")
                plantObject.description = getString("description")
                plantObject.difficulty = getInt("difficulty")
                plantObject.id = getInt("id")
            }

            allPlantObjects.add(plantObject)

            index++
        }
        return allPlantObjects
    }
}