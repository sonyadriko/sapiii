package com.example.sapiii.feature.detail.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.sapiii.base.BaseViewModel
import com.example.sapiii.constanst.Constant.DEEP_LINK_ROOT
import com.example.sapiii.constanst.Constant.NAMA_KAMBING_QUERY_PARAM
import com.example.sapiii.constanst.Constant.NAMA_SAPI_QUERY_PARAM
import com.example.sapiii.domain.Kambing
import com.example.sapiii.domain.Sapi
import com.example.sapiii.feature.detail.viewmodel.DetailViewModel.Companion.DetailFeature.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class DetailViewModel : BaseViewModel() {
    companion object {
        enum class DetailFeature(val queryName: String) {
            SAPI(NAMA_SAPI_QUERY_PARAM),
            KAMBING(NAMA_KAMBING_QUERY_PARAM),
            UNKNOWN("")
        }
    }

    private val _viewState = MutableLiveData<ViewState>()
    val viewState: LiveData<ViewState> get() = _viewState

    private val _viewEffect = MutableLiveData<ViewEffect>()
    val viewEffect: LiveData<ViewEffect> get() = _viewEffect

    private var feature: DetailFeature = UNKNOWN
    private var namaHewan: String? = null

    private var dataKambing: Kambing? = null
    private var dataSapi: Sapi? = null

    fun initBundle(data: Uri?) {
        if (data == null) {
            setViewEffect(ViewEffect.ShowToast("Broken Link"))
            setViewState(ViewState.FAIL)
            return
        }

        try {
            feature = valueOf(data.lastPathSegment?.uppercase() ?: "")
            namaHewan = data.getQueryParameter(feature.queryName)
            if (namaHewan.isNullOrEmpty()) throw IllegalArgumentException("Name not specified")
        } catch (e: Exception) {
            setViewEffect(ViewEffect.ShowToast("Broken Link"))
            setViewState(ViewState.FAIL)
        }
    }

    fun initBundle(newFeature: DetailFeature, newNamaHewan: String) {
        feature = newFeature
        namaHewan = newNamaHewan
    }

    fun getDataHewan() {
        if (dataKambing == null && dataSapi == null) {
            setViewState(ViewState.LOADING)
            viewModelScope.launch {
                delay(500L)
                try {
                    if (namaHewan.isNullOrEmpty()) {
                        throw IllegalArgumentException("Name not specified")
                    }

                    generateQrCode()
                    when (feature) {
                        SAPI -> getSapi(namaHewan!!)
                        KAMBING -> getKambing(namaHewan!!)
                        else -> {
                            // do nothing
                        }
                    }

                    setViewState(ViewState.INITIAL)
                } catch (e: Exception) {
                    setViewEffect(ViewEffect.ShowToast(e.message))
                    setViewState(ViewState.FAIL)
                }
            }
        } else {
            when (feature) {
                SAPI -> setViewEffect(ViewEffect.OnDataGetResult(dataSapi))
                KAMBING -> setViewEffect(ViewEffect.OnDataGetResult(dataKambing))
                UNKNOWN -> {}
            }
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
                SAPI -> {
                    sapiRepository.removeSapi(
                        namaHewan!!,
                        onComplete = { isSuccess ->
                            setViewEffect(ViewEffect.OnDataDeleted(isSuccess))
                        }
                    )
                }
                KAMBING -> {
                    kambingRepository.removeKambing(
                        namaHewan!!,
                        onComplete = { isSuccess ->
                            setViewEffect(ViewEffect.OnDataDeleted(isSuccess))
                        }
                    )
                }
                else -> {
                    // do nothing
                }
            }
            setViewState(ViewState.INITIAL)
        } catch (e: Exception) {
            setViewEffect(ViewEffect.ShowToast(e.message))
            setViewState(ViewState.FAIL)
        }
    }

    private fun getSapi(namaSapi: String) {
        sapiRepository.getSapiDetail(
            namaSapi,
            onComplete = { sapi ->
                dataSapi = sapi
                setViewEffect(ViewEffect.OnDataGetResult(sapi))
            },
            onError = {
                setViewEffect(ViewEffect.ShowToast("Data sapi tidak ditemukan"))
            }
        )
    }

    private fun getKambing(namaKambing: String) {
        kambingRepository.getKambingDetail(
            namaKambing,
            onComplete = { kambing ->
                dataKambing = kambing
                setViewEffect(ViewEffect.OnDataGetResult(kambing))
            },
            onError = {
                setViewEffect(ViewEffect.ShowToast("Data kambing tidak ditemukan"))
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