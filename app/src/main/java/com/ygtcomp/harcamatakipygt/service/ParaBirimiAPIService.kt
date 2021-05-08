package com.ygtcomp.harcamatakipygt.service

import com.ygtcomp.harcamatakipygt.model.ParaBirimModel
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ParaBirimiAPIService {

    ///http://api.exchangeratesapi.io/v1/latest?access_key=ab97249d5180a6badf3ed720a92698d0

    private val BASE_URL = "http://api.exchangeratesapi.io/v1/"
    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build().create(ParaBirimiAPI::class.java)

    fun loadData(): Single<ParaBirimModel> {
        return api.getData()
    }
}