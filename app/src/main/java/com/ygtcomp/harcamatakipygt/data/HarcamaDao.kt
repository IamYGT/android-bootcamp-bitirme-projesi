package com.ygtcomp.harcamatakipygt.data

import androidx.room.*
import com.ygtcomp.harcamatakipygt.model.HarcamaModel

@Dao
interface HarcamaDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun harcamaEkle(harcama: HarcamaModel)

    @Query("SELECT * FROM harcama_tablo ORDER BY harcamaId DESC")
    fun tumVeriyiOku(): List<HarcamaModel>

    @Delete
    fun harcamaSil(harcama: HarcamaModel)

    @Query("SELECT * FROM harcama_tablo WHERE harcamaId = :harcamaId")
    fun harcamaGetir(harcamaId: Int): HarcamaModel

}