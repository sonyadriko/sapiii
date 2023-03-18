package com.example.sapiii.feature.detail.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.sapiii.base.BaseViewModel
import com.example.sapiii.constanst.Constant.DEEP_LINK_ROOT
import com.example.sapiii.constanst.Constant.NAMA_KAMBING_QUERY_PARAM
import com.example.sapiii.constanst.Constant.NAMA_SAPI_QUERY_PARAM

class DetailViewModel : BaseViewModel() {
    companion object {
        enum class DetailFeature(val queryName: String) {
            SAPI(NAMA_SAPI_QUERY_PARAM),
            KAMBING(NAMA_KAMBING_QUERY_PARAM)
        }
    }

    private val _viewState = MutableLiveData<ViewState>()
    val viewState: LiveData<ViewState> get() = _viewState

    private val _viewEffect = MutableLiveData<ViewEffect>()
    val viewEffect: LiveData<ViewEffect> get() = _viewEffect

    private lateinit var feature: DetailFeature
    private var namaHewan: String? = null

    fun initBundle(data: Uri?) {
        parseLink(data)
    }

    fun initBundle(newFeature: DetailFeature, newNamaHewan: String) {
        feature = newFeature
        namaHewan = newNamaHewan
    }

    private fun parseLink(data: Uri?) {
        if (data == null) {
            setViewEffect(ViewEffect.ShowToast("Broken Link"))
            setViewState(ViewState.FAIL)
            return
        }

        try {
            feature = DetailFeature.valueOf(data.lastPathSegment ?: "")
            namaHewan = data.getQueryParameter(feature.queryName)
            if (namaHewan == null) throw IllegalArgumentException("Name not specified")
        } catch (e: Exception) {
            setViewEffect(ViewEffect.ShowToast("Broken Link"))
            setViewState(ViewState.FAIL)
        }
    }

    fun getDataHewan() {
        try {
            generateQrCode()
            when (feature) {
                DetailFeature.SAPI -> getSapi(namaHewan!!)
                DetailFeature.KAMBING -> getKambing(namaHewan!!)
            }

            setViewState(ViewState.INITIAL)
        } catch (e: Exception) {
            setViewEffect(ViewEffect.ShowToast(e.message))
            setViewState(ViewState.FAIL)
        }
    }

    private fun generateQrCode() {
        val content =
            DEEP_LINK_ROOT + "/detail/${feature.name.lowercase()}?${feature.queryName}=$namaHewan"
        setViewEffect(ViewEffect.ShowQrCode(content.replace(" ", "%20")))
    }

    fun deleteHewan() {
        try {
            setViewState(ViewState.LOADING)
            when (feature) {
                DetailFeature.SAPI -> {
                    sapiRepository.removeSapi(
                        namaHewan!!,
                        onComplete = { isSuccess ->
                            setViewEffect(ViewEffect.OnDataDeleted(isSuccess))
                        }
                    )
                }
                DetailFeature.KAMBING -> {
                    kambingRepository.removeKambing(
                        namaHewan!!,
                        onComplete = { isSuccess ->
                            setViewEffect(ViewEffect.OnDataDeleted(isSuccess))
                        }
                    )
                }
            }
            setViewState(ViewState.INITIAL)
        } catch (e: Exception) {
            setViewEffect(ViewEffect.ShowToast(e.message))
            setViewState(ViewState.FAIL)
        }
    }

    private fun getSapi(namaSapi: String) {
        setViewState(ViewState.LOADING)
        sapiRepository.getSapiDetail(
            namaSapi,
            onComplete = { sapi ->
                setViewEffect(ViewEffect.OnDataGetResult(sapi))
            },
            onError = { error ->
                setViewEffect(ViewEffect.ShowToast(error.message))
            }
        )
    }

    private fun getKambing(namaKambing: String) {
        setViewState(ViewState.LOADING)
        kambingRepository.getKambingDetail(
            namaKambing,
            onComplete = { kambing ->
                setViewEffect(ViewEffect.OnDataGetResult(kambing))
            },
            onError = { error ->
                setViewEffect(ViewEffect.ShowToast(error.message))
            }
        )
    }

    private fun setViewEffect(effect: ViewEffect) {
        _viewEffect.value = effect
    }

    private fun setViewState(state: ViewState) {
        _viewState.value = state
    }

    enum class ViewState {
        INITIAL,
        LOADING,
        FAIL
    }

    sealed interface ViewEffect {
        class ShowToast(val message: String? = null) : ViewEffect
        class ShowQrCode(val content: String) : ViewEffect
        class OnDataGetResult(val data: Any?) : ViewEffect
        class OnDataDeleted(val isSuccess: Boolean) : ViewEffect
    }
}