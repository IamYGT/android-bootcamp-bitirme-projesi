package com.ygtcomp.harcamatakipygt.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "harcama_tablo")
data class HarcamaModel(
    @PrimaryKey(autoGenerate = true)
    val harcamaId: Int,

    @ColumnInfo(name = "aciklama")
    val aciklama: String,

    @ColumnInfo(name = "harcamaMiktari")
    val harcamaMiktari: Int,

    @ColumnInfo(name = "faturaTuru")
    val faturaTuru: Int,

    @ColumnInfo(name = "paraCinsi")
    val paraCinsi: Int

)