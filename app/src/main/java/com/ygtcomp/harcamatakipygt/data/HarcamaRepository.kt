package com.ygtcomp.harcamatakipygt.data

import com.ygtcomp.harcamatakipygt.model.HarcamaModel


class HarcamaRepository(private val harcamaDao: HarcamaDao) {

    fun harcamaEkle(harcama: HarcamaModel){
        harcamaDao.harcamaEkle(harcama)
    }

    fun tumVeriyiOku(): List<HarcamaModel>{
        return harcamaDao.tumVeriyiOku()
    }

    fun harcamaSil(harcama: HarcamaModel){
        harcamaDao.harcamaSil(harcama)
    }

    fun harcamaGetir(harcamaId: Int): HarcamaModel{
        return harcamaDao.harcamaGetir(harcamaId)
    }
}