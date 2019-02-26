package com.example.commonlib.dialog

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.example.commonlib.R

/**
 *
 * Class: com.example.commonlib.dialog.VideoNetWorkDialog
 *
 * Description:
 * <pre>
 *
</pre> *
 *
 * @author lujunjie
 * @date 2019/2/26/13:49
 */

class VideoNetWorkDialog (context:Context,themeResId: Int): Dialog(context,themeResId), DialogInterface {

     class Builder(private val context: Context) {

        private var isCancelable: Boolean = true
        private var root: LinearLayout =
            LayoutInflater.from(context).inflate(R.layout.video_network_dialog_layout, null) as LinearLayout
        private var cancelTv: View = root.findViewById(R.id.cancel_textView)
        private var confirmTv: View = root.findViewById(R.id.confirm_textView)
        private var cancelListener: DialogInterface.OnClickListener? = null
        private var confirmListener: DialogInterface.OnClickListener? = null

        fun setLayout(layoutId: Int): VideoNetWorkDialog.Builder {
            root = LayoutInflater.from(context).inflate(layoutId, null) as LinearLayout
            cancelTv = root.findViewById(R.id.cancel_textView)
            confirmTv = root.findViewById(R.id.confirm_textView)
            context.resources
            return this
        }

        fun setCancelable(isCancelable: Boolean): VideoNetWorkDialog.Builder {
            this.isCancelable = isCancelable
            return this
        }

        fun setCancelListener(cancelListener: DialogInterface.OnClickListener?): VideoNetWorkDialog.Builder {
            this.cancelListener = cancelListener
            return this
        }

        fun setConfirmListener(confirmListener: DialogInterface.OnClickListener): VideoNetWorkDialog.Builder {
            this.confirmListener = confirmListener
            return this
        }

        fun create(): VideoNetWorkDialog {
            val dialog = VideoNetWorkDialog(context,
                R.style.dialog)

            dialog.setContentView(root)

            if (cancelListener != null) {
                (cancelTv as TextView)
                    .setOnClickListener { cancelListener!!.onClick(dialog, -1) }
            }

            if (confirmListener != null) {
                (confirmTv as TextView)
                    .setOnClickListener { confirmListener!!.onClick(dialog, -1) }
            }

            dialog.setCancelable(isCancelable)
            return dialog
        }
    }



}
