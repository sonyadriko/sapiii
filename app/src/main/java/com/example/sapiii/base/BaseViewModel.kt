package com.example.sapiii.base

import androidx.lifecycle.ViewModel
import com.example.sapiii.domain.MonitoringPejantan
import com.example.sapiii.domain.MutasiSapi
import com.example.sapiii.repository.*

abstract class BaseViewModel: ViewModel() {
    val sapiRepository: SapiRepository = SapiRepository().getInstance()
    val artikelRepository : ArtikelRepository = ArtikelRepository.getInstance()
    val kambingRepository : KambingRepository = KambingRepository().getInstance()
    val mutasiSapiRepository : MutasiSapiRepository = MutasiSapiRepository().getInstance()
    val mutasiKambingRepository : MutasiKambingRepository = MutasiKambingRepository().getInstance()
    val monitoringPejantan : PejantanRepository = PejantanRepository().getInstance()
    val monitoringKehamilan : KehamilanRepository = KehamilanRepository().getInstance()

//    val mutasiSapiRepository : MutasiSapiRepository = MutasiSapiRepository().getInstance()
}