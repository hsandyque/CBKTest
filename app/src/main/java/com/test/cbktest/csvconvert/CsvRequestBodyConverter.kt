package com.test.cbktest.csvconvert

import com.google.gson.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okio.Buffer
import retrofit2.Converter
import java.io.IOException
import java.io.OutputStreamWriter
import java.io.Writer
import java.nio.charset.Charset
import java.util.*


class CsvRequestBodyConverter<T> internal constructor(
    private val mGson: Gson,
    private val mAdapter: TypeAdapter<T>) : Converter<T, RequestBody> {
    @Throws(IOException::class)
    override fun convert(value: T): RequestBody {
        val buffer = Buffer()
        val writer: Writer = OutputStreamWriter(buffer.outputStream(), UTF_8)
        val jsonWriter = mGson.newJsonWriter(writer)
        mAdapter.write(jsonWriter, value)
        jsonWriter.close()
        val json = mGson.fromJson(buffer.readUtf8(), JsonElement::class.java)
        val valuesList = getValuesList(json)
        // check values is equals
        for (i in 1 until valuesList.size) {
            val previousValues = valuesList[i - 1]
            val currentValues = valuesList[i]
            if (previousValues.keys != currentValues.keys) {
                throw IOException("Not equals elements: " + (i - 1).toString() + " and " + i)
            }
        }
        // create csv
        val csv = StringBuilder()
        val titles = ArrayList<String>()
        for ((key) in valuesList[0]) {
            if (csv.isNotEmpty()) {
                csv.append(",")
            }
            titles.add(key)
            csv.append(key)
        }
        csv.append("\n")
        for (values in valuesList) {
            csv.append(values[titles[0]])
            for (i in 1 until titles.size) {
                csv.append(",").append(values[titles[i]])
            }
            csv.append("\n")
        }
        return csv.toString().trim { it <= ' ' }.toRequestBody(MEDIA_TYPE)
    }

    @Throws(IOException::class)
    private fun getValuesList(json: JsonElement): ArrayList<kotlin.collections.Map<String, String>> {
        val valuesList = ArrayList<kotlin.collections.Map<String, String>>()
        if (json.isJsonArray) {
            val jsonArray = json as JsonArray
            for (i in 0 until jsonArray.size()) {
                val jsonElement = jsonArray[i]
                if (jsonElement.isJsonObject) {
                    valuesList.add(getValues(jsonElement as JsonObject))
                } else {
                    throw IOException("Not valid json array element: $jsonElement")
                }
            }
            if (valuesList.size == 0) {
                throw IOException("Not valid json array: $json")
            }
        } else if (json.isJsonObject) {
            valuesList.add(getValues(json as JsonObject))
        } else {
            throw IOException("Not valid json type: $json")
        }
        return valuesList
    }

    @Throws(IOException::class)
    private fun getValues(jsonObject: JsonObject): kotlin.collections.Map<String, String> {
        val values: MutableMap<String, String> = HashMap()
        for ((key, value) in jsonObject.entrySet()) {
            if (value.isJsonPrimitive) {
                val jsonPrimitive = value as JsonPrimitive
                if (jsonPrimitive.isNumber) {
                    values[key] = value.asNumber.toString()
                } else if (jsonPrimitive.isString) {
                    values[key] = "\"" + value.asString + "\""
                } else if (jsonPrimitive.isBoolean) {
                    values[key] = value.asBoolean.toString()
                } else {
                    throw IOException("Not valid json object primitive value: $value")
                }
            } else {
                throw IOException("Not valid json object value: $value")
            }
        }
        if (values.isEmpty()) {
            throw IOException("Not valid json to convert csv: $jsonObject")
        }
        return values
    }

    companion object {
        private val MEDIA_TYPE = "text/csv; charset=UTF-8".toMediaTypeOrNull()
        private val UTF_8 = Charset.forName("UTF-8")
    }
}