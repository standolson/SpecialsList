package com.swiftly.specialsapp.viewmodel

import androidx.lifecycle.MediatorLiveData
import com.google.gson.Gson
import com.swiftly.specialsapp.model.SpecialsList
import com.swiftly.specialsapp.util.ResponseData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject

class SpecialsRepository @Inject constructor() {

    suspend fun getSpecialsList() : MediatorLiveData<ResponseData<SpecialsList>> {

        val liveData = MediatorLiveData<ResponseData<SpecialsList>>()

        withContext(Dispatchers.IO) {
            val client = OkHttpClient()
            val request = Request.Builder().url(ENDPOINT_URL).build()
            var response: Response? = null
            var returnVal: SpecialsList? = null
            var exceptionMessage: String? = null

            try {
                response = client.newCall(request).execute()
                if (response.isSuccessful)
                    returnVal = parseResponse(response)
            }
            catch (e: Exception) {
                exceptionMessage = e.toString()
            }

            withContext(Dispatchers.Main) {
                if (returnVal != null)
                    liveData.value = ResponseData(returnVal)
                else if (exceptionMessage != null)
                    liveData.value = ResponseData(exceptionMessage)
                else
                    liveData.value = ResponseData(response!!.message)
            }
        }

        return liveData
    }

    private fun parseResponse(response: Response) : SpecialsList {
        return Gson().fromJson(response.body?.charStream(), SpecialsList::class.java)
    }

    companion object {
        val ENDPOINT_URL: String = "https://raw.githubusercontent.com/Swiftly-Systems/code-exercise-android/master/backup"
    }
}