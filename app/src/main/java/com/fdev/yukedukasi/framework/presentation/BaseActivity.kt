package com.fdev.yukedukasi.framework.presentation

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.callbacks.onDismiss
import com.fdev.yukedukasi.R
import com.fdev.yukedukasi.business.domain.state.*
import com.google.android.material.snackbar.Snackbar
import www.sanju.motiontoast.MotionToast

abstract class BaseActivity : AppCompatActivity(), UIController {

    private var dialogInView: MaterialDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN , WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }



    private fun getRootView(): View {
        val contentViewGroup: ViewGroup? = findViewById(android.R.id.content) as ViewGroup

        var rootView: View? = null

        if (contentViewGroup != null) {
            rootView = contentViewGroup.getChildAt(0)
        }

        if (rootView == null)
            rootView = window.decorView.rootView

        return rootView ?: throw Exception("Failed to get root view")
    }

    override fun displayProgressBar(isDisplayed: Boolean) {

    }

    override fun basicUIInteraction(
            message: String,
            uiComponentType: UIComponentType
    ) {
        when (uiComponentType) {
            is UIComponentType.SnackBar -> {
                displaySnackbar(
                        message
                )
            }


            is UIComponentType.ColorMotionToast -> {
                displayColorMotionToast(
                        message,
                        uiComponentType.type
                )
            }

            is UIComponentType.Toast -> {
                displayToast(
                        message
                )
            }

            is UIComponentType.AreYouSureDialog -> {
                dialogInView = areYouSureDialog(
                        message,
                        uiComponentType.callback
                )

            }

            is UIComponentType.TryAgainDialog -> {
                dialogInView = areYouSureDialog(
                        message,
                        uiComponentType.callback
                )

            }
        }
    }

    private fun displayColorMotionToast(message: String, type: MessageType) {
        when (type){
            is MessageType.Success -> {
                MotionToast.createColorToast(this,message,
                        MotionToast.TOAST_SUCCESS,
                        MotionToast.GRAVITY_BOTTOM,
                        MotionToast.SHORT_DURATION,
                        ResourcesCompat.getFont(this,R.font.helvetica_regular))
            }
            is MessageType.Error -> {
                MotionToast.createColorToast(this,message,
                        MotionToast.TOAST_ERROR,
                        MotionToast.GRAVITY_BOTTOM,
                        MotionToast.SHORT_DURATION,
                        ResourcesCompat.getFont(this,R.font.helvetica_regular))
            }
        }
    }

    override fun onResponseReceived(
            response: Response,
            stateMessageCallback: StateMessageCallback
    ) {
        when (response.uiComponentType) {
            is UIComponentType.SnackBar -> {
                response.message?.let { msg ->
                    displaySnackbar(
                            msg,
                            stateMessageCallback
                    )

                }
            }


            is UIComponentType.Toast -> {
                response.message?.let { msg ->
                    displayToast(
                            msg,
                            stateMessageCallback
                    )
                }
            }

            is UIComponentType.Dialog -> {
                displayDialog(
                        response,
                        stateMessageCallback
                )
            }

            is UIComponentType.AreYouSureDialog -> {
                response.message?.let{msg->
                    dialogInView = areYouSureDialog(
                            msg,
                            response.uiComponentType.callback,
                            stateMessageCallback
                    )
                }
            }
            is UIComponentType.TryAgainDialog -> {
                response.message?.let{msg->
                    dialogInView = areYouSureDialog(
                            msg,
                            response.uiComponentType.callback,
                            stateMessageCallback
                    )
                }

            }
            is UIComponentType.None -> {
                stateMessageCallback.removeMessageFromStack()
            }
        }
    }

    private fun displayDialog(
            response: Response,
            stateMessageCallback: StateMessageCallback
    ) {
        response.message?.let { msg ->
            when (response.messageType) {

                is MessageType.Error -> {
                    displayErrorDialog(
                            message = msg,
                            stateMessageCallback = stateMessageCallback
                    )
                }

                is MessageType.Success -> {
                    displaySuccessDialog(
                            message = msg,
                            stateMessageCallback = stateMessageCallback
                    )
                }

                is MessageType.Info -> {
                    displayInfoDialog(
                            message = msg,
                            stateMessageCallback = stateMessageCallback
                    )
                }


                else -> {
                    stateMessageCallback.removeMessageFromStack()
                }
            }
        } ?: stateMessageCallback.removeMessageFromStack()

    }

    private fun displayInfoDialog(
            message: String?,
            stateMessageCallback: StateMessageCallback? = null
    ): MaterialDialog {
        return MaterialDialog(this)
                .show {
                    title(R.string.info)
                    message(text = message)
                    positiveButton(R.string.text_ok) {
                        stateMessageCallback?.removeMessageFromStack()
                        dismiss()
                    }
                    onDismiss {
                        dialogInView = null
                    }
                    cancelable(false)
                }
    }

    private fun displayErrorDialog(
            message: String?,
            stateMessageCallback: StateMessageCallback? = null
    ): MaterialDialog {
        return MaterialDialog(this)
                .show {
                    title(R.string.text_error)
                    message(text = message)
                    positiveButton(R.string.text_ok) {
                        stateMessageCallback?.removeMessageFromStack()
                        dismiss()
                    }
                    onDismiss {
                        dialogInView = null
                    }
                    cancelable(false)
                }
    }


    private fun displaySuccessDialog(
            message: String?,
            stateMessageCallback: StateMessageCallback? = null
    ): MaterialDialog {
        return MaterialDialog(this)
                .show {
                    title(R.string.text_success)
                    message(text = message)
                    positiveButton(R.string.text_ok) {
                        stateMessageCallback?.removeMessageFromStack()
                        dismiss()
                    }
                    onDismiss {
                        dialogInView = null
                    }
                    cancelable(false)
                }
    }


    private fun displaySnackbar(
            message: String,
            stateMessageCallback: StateMessageCallback? = null
    ) {
        val snackBar = Snackbar.make(
                getRootView(),
                message,
                Snackbar.LENGTH_LONG
        )
        snackBar.show()
        stateMessageCallback?.removeMessageFromStack()
    }

    private fun displayToast(
            messasge: String,
            stateMessageCallback: StateMessageCallback? = null
    ) {
        Toast.makeText(
                this,
                messasge,
                Toast.LENGTH_LONG
        ).show()
        stateMessageCallback?.removeMessageFromStack()
    }

    private fun areYouSureDialog(
            message: String,
            callback: AreYouSureCallback,
            stateMessageCallback: StateMessageCallback? = null
    ): MaterialDialog {
        return MaterialDialog(this)
                .show{
                    title(R.string.are_you_sure)
                    message(text = message)
                    val negativeButton = negativeButton(R.string.text_cancel) {
                        stateMessageCallback?.removeMessageFromStack()
                        callback.cancel()
                        dismiss()
                    }
                    positiveButton(R.string.text_yes){
                        stateMessageCallback?.removeMessageFromStack()
                        callback.proceed()
                        dismiss()
                    }
                    onDismiss {
                        dialogInView = null
                    }
                    cancelable(false)
                }
    }

    private fun tryAgainDialog(
            message: String,
            callback: AreYouSureCallback,
            stateMessageCallback: StateMessageCallback? = null
    ): MaterialDialog {
        return MaterialDialog(this)
                .show{
                    title(R.string.try_again)
                    message(text = message)
                    val negativeButton = negativeButton(R.string.text_cancel) {
                        stateMessageCallback?.removeMessageFromStack()
                        callback.cancel()
                        dismiss()
                    }
                    positiveButton(R.string.text_yes){
                        stateMessageCallback?.removeMessageFromStack()
                        callback.proceed()
                        dismiss()
                    }
                    onDismiss {
                        dialogInView = null
                    }
                    cancelable(false)
                }
    }

    override fun onPause() {
        super.onPause()
        dialogInView?.let{
            (dialogInView as MaterialDialog).dismiss()
            dialogInView = null
        }
    }

}
