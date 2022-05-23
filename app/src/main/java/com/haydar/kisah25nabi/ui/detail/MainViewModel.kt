package com.haydar.kisah25nabi.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.haydar.kisah25nabi.data.KisahResponse
import com.haydar.kisah25nabi.data.network.ApiClient
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.logging.Handler

class MainViewModel : ViewModel() {

    var isLoading = MutableLiveData<Boolean>()
    var isError = MutableLiveData<Throwable>()
    var kisahResponse = MutableLiveData<List<KisahResponse>>()

    fun getData(responseHandler: (List<KisahResponse>) -> Unit, errorHandler: (Throwable) -> Unit) {
        ApiClient.getApiService().getKisahNbi()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                responseHandler(it)
            }, {
                errorHandler(it)
            })
    }

    fun getKisahnabi() {
        isLoading.value = true

        getData({
            isLoading.value = false
            kisahResponse.value = it//success
        }, {
            isLoading.value = false
            isError.value = it
        })
    }
}