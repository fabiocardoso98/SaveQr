package ipvc.estg.saveqr.ui.LerQr

import ipvc.estg.saveqr.History
import ipvc.estg.saveqr.mvp.BaseMvpPresenter
import ipvc.estg.saveqr.mvp.BaseMvpView

object LerQrActivityContract {

    interface View: BaseMvpView {
        fun showSuccessScanningDialog(result: String)
        fun continueScanning()
    }

    interface Presenter: BaseMvpPresenter<View> {
        fun qrCodeScanned(history: History)
        fun searchByResultBtnPressed(result:String)
        fun copyResultBtnPressed(result: String)
        fun shareResultBtnPressed(result: String)
        fun GravarQr(result:String)

    }
}