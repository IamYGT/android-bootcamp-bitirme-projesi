package com.ygtcomp.harcamatakipygt.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ygtcomp.harcamatakipygt.model.ParaBirimModel
import com.ygtcomp.harcamatakipygt.service.ParaBirimiAPI
import com.ygtcomp.harcamatakipygt.service.ParaBirimiAPIService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class HomeViewModel : ViewModel() {


    private val service = ParaBirimiAPIService()
    private val compositeDisposable = CompositeDisposable()

    val hataMesaj = MutableLiveData<Boolean>()
    val yukleniyorMesaj = MutableLiveData<Boolean>()
    val data = MutableLiveData<ParaBirimModel>()

    fun getData(){
        getDataFromInternet()
    }

    private fun getDataFromInternet(){
        yukleniyorMesaj.value = true
        compositeDisposable.add(service.loadData()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object: DisposableSingleObserver<ParaBirimModel>(){
                override fun onSuccess(t: ParaBirimModel) {
                    data.value = t
                    hataMesaj.value = false
                    yukleniyorMesaj.value = false
                }
                override fun onError(e: Throwable) {
                    hataMesaj.value = true
                    yukleniyorMesaj.value = false
                }
            }))
    }

}