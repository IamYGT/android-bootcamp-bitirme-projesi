package com.ygtcomp.harcamatakipygt.service

import com.ygtcomp.harcamatakipygt.model.ParaBirimModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ParaBirimiAPI {


    //http://api.exchangeratesapi.io/v1/latest?access_key=ab97249d5180a6badf3ed720a92698d0

    @GET("latest")
    fun getData(
        @Query("access_key") key: String = "ab97249d5180a6badf3ed720a92698d0",
        @Query("symbols") symbols: String = "USD,TRY,GBP,EUR",
        @Query("format") format: String = "1"
    ) : Single<ParaBirimModel>
}

