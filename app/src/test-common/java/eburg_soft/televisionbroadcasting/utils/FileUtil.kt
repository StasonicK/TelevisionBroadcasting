package eburg_soft.televisionbroadcasting.utils

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import timber.log.Timber
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets

object FileUtil {

    fun getJsonFromAssets(
//        context: Context,
        fileName: String?
    ): String? {
        val jsonString: String
        jsonString = try {
            val context = ApplicationProvider.getApplicationContext<Context>()
            val `is` = context.assets.open(fileName!!)
            val size = `is`.available()
            val buffer = ByteArray(size)
            val charset = StandardCharsets.UTF_8
            `is`.read(buffer)
            `is`.close()
            String(buffer, charset)
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }
        return jsonString
    }

    fun loadResource(resource_name: String): String {
        try {
            val context = ApplicationProvider.getApplicationContext<Context>()
            val `in` = context.classLoader.getResourceAsStream(resource_name)
            if (`in` != null) {
                return convertStreamToString(`in`)
            } else {
                Timber.d("*** loadResource: UNABLE TO OPEN RESOURCE FILE: " + resource_name)
            }
        } catch (e: Exception) {
            Timber.e("*** ERROR: loadResource(" + resource_name + ") error=" + e)
        }
        return ""
    }

    private fun convertStreamToString(`is`: InputStream): String {
        val buf = StringBuilder()
        val reader = BufferedReader(InputStreamReader(`is`))
        var str: String?
        try {
            while (reader.readLine().also { str = it } != null) {
                buf.append(str).append("\n")
            }
        } catch (e: IOException) {
            Timber.e("*** convertStreamToString: unable to read JSON test data! error=" + e)
        }
        return buf.toString()
    }
}