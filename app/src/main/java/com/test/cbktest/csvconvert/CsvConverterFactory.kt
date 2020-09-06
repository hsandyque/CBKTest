package com.test.cbktest.csvconvert

import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.google.gson.reflect.TypeToken
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type


class CsvConverterFactory private constructor(private val gson: Gson) : Converter.Factory() {

    override fun requestBodyConverter(
        type: Type,
        parameterAnnotations: Array<Annotation>,
        methodAnnotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<*, RequestBody> {
        val adapter = gson.getAdapter(TypeToken.get(type))
        return CsvRequestBodyConverter(gson, adapter as TypeAdapter<Any>)
    }

    override fun responseBodyConverter(
        type: Type, annotations: Array<Annotation>, retrofit: Retrofit
    ): Converter<ResponseBody, *> {
        val adapter = gson.getAdapter(TypeToken.get(type))
        return CsvResponseBodyConverter(gson, adapter as TypeAdapter<Any>)
    }

    companion object {
        fun create(): CsvConverterFactory {
            return CsvConverterFactory(Gson())
        }

        fun create(gson: Gson?): CsvConverterFactory {
            if (gson == null) {
                throw NullPointerException("gson == null")
            }
            return CsvConverterFactory(gson)
        }
    }
}