package com.kartik.grevocab

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.io.File
import java.io.FileNotFoundException
import java.lang.reflect.Type
import java.util.Scanner

/**
 * Created to read responses from a file
 */
object FileReader {

    /**
     * Created to read responses from a file
     */
    fun readStringFromFile(fileName: String): String {

        val sb = StringBuilder()
        try {
            val file = File(javaClass.classLoader.getResource(fileName).file)
            val myReader = Scanner(file)
            while (myReader.hasNextLine()) {
                val data: String = myReader.nextLine()
                sb.append(data)
            }
            myReader.close()
        } catch (e: FileNotFoundException) {
            println("An error occurred.")
            e.printStackTrace()
        }
        return sb.toString()
    }

    /**
     * Note Gson object should be same as one you sent in the retrofit layer
     * So your data gets parsed same way.
     * */
    fun getGson(): Gson {
        return GsonBuilder().create()
    }

    /**
     * Use this function to read data from file and return
     *
     * eg  getData<ArrayList<Transaction>>("transactions_page_1.json")
     * */
    fun <T : Any> getData(fileName: String, type: Type): T {
        val gson = getGson()
        // val type = object : TypeToken<T>() {}.type this works but cant get element out of array if type is array
        return gson.fromJson(readStringFromFile(fileName), type)
    }
}
