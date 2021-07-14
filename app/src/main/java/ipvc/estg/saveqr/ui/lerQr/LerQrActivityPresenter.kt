package ipvc.estg.saveqr.ui.lerQr

import android.util.Patterns
import ipvc.estg.saveqr.History
import ipvc.estg.saveqr.R
import ipvc.estg.saveqr.mvp.BaseMvpPresenterImpl
import ipvc.estg.saveqr.ui.LerQr.LerQrActivityContract

open class LerQrActivityPresenter: BaseMvpPresenterImpl<LerQrActivityContract.View>(),
        LerQrActivityContract.Presenter {

    private val preUrl: String = "http://www.google.com/#q="

    override fun searchByResultBtnPressed(result: String) {
        var url: String = result
        if (!Patterns.WEB_URL.matcher(result).matches())
            url = preUrl + result
        mView?.continueScanning()
        mView?.searchInWWW(url)
    }

    override fun copyResultBtnPressed(result: String) {
        mView?.copyToClipboard(result)
        mView?.continueScanning()
        mView?.showMessage(R.string.text_copied)
    }

    override fun shareResultBtnPressed(result: String) {
        mView?.continueScanning()
        mView?.shareResultViewSharingIntent(result)
    }

    override fun qrCodeScanned(history: History) {
        mView?.showSuccessScanningDialog(history.context)
    }
    override fun GravarQr(result: String) {
        mView?.GravarQr(result)

        mView?.continueScanning()
    }
}