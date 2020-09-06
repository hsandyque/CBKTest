package com.test.cbktest.csvconvert

import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader

import java.io.IOException
import java.io.StringReader
import java.util.ArrayList

import okhttp3.ResponseBody
import retrofit2.Converter
import java.nio.charset.Charset


class CsvResponseBodyConverter<T> internal constructor(
    private val mGson: Gson,
    private val mAdapter: TypeAdapter<T>
) :
    Converter<ResponseBody, T> {
    @Throws(IOException::class)
    override fun convert(value: ResponseBody): T {
        val rows = String(value.bytes(), Charset.forName("Big5")).split("\n".toRegex()).toTypedArray()
        value.close()
        if (rows.size < 2) {
            throw IOException("Not valid csv: rows count is less than 2")
        }
        val titles = getValues(rows[0])
        if (titles.size < 1) {
            throw IOException("Not valid csv: columns count is less than 1")
        }
        val array = JsonArray()
        for (i in 1 until rows.size) {
            val values = getValues(rows[i])
            if (values.size != titles.size) {
                //throw IOException("Not valid csv: columns count in row $i is not equals to columns count in header's row")
                break
            }
            val `object` = JsonObject()
            for (j in 0 until values.size) {
                try {
                    `object`.addProperty(titles[j], values[j].toLong())
                } catch (ignored1: NumberFormatException) {
                    try {
                        `object`.addProperty(titles[j], values[j].toDouble())
                    } catch (ignored2: NumberFormatException) {
                        if (values[j].equals("true", ignoreCase = true) ||
                            values[j].equals("false", ignoreCase = true)) {
                            `object`.addProperty(titles[j], values[j].toBoolean())
                        } else {
                            `object`.addProperty(titles[j], values[j])
                        }
                    }
                }
            }
            array.add(`object`)
        }
        val json: String
        json = if (array.size() == 1) {
            array[0].toString()
        } else {
            array.toString()
        }
        val stringReader = StringReader(json)
        val jsonReader: JsonReader = mGson.newJsonReader(stringReader)
        return mAdapter.read(jsonReader)
    }

    private fun getValues(row: String): ArrayList<String> {
        var row = row
        row = row.trim { it <= ' ' }
        val result: ArrayList<String> = ArrayList()
        var value = StringBuilder()
        var open = false
        var backslash = false
        for (element in row) {
            val cur = element
            when (cur) {
                BACKSLASH -> backslash = true
                QUOTATION -> if (backslash) {
                    value.append(cur)
                    backslash = false
                } else {
                    open = !open
                }
                COMMA -> {
                    if (backslash) {
                        value.append(BACKSLASH)
                        backslash = false
                    }
                    if (open) {
                        value.append(cur)
                    } else {
                        result.add(value.toString())
                        value = StringBuilder()
                    }
                }
                else -> {
                    if (backslash) {
                        value.append(BACKSLASH)
                        backslash = false
                    }
                    value.append(cur)
                }
            }
        }
        if (backslash) {
            value.append(BACKSLASH)
        }
        result.add(value.toString())
        return result
    }

    companion object {
        private const val BACKSLASH = '\\'
        private const val COMMA = ','
        private const val QUOTATION = '"'
    }
}