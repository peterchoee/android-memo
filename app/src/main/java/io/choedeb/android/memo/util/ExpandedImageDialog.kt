package io.choedeb.android.memo.util

import android.app.Activity
import android.app.AlertDialog
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import io.choedeb.android.memo.R
import io.choedeb.android.memo.databinding.DialogImageExpandedBinding

class ExpandedImageDialog(private val activity: Activity) {

    private val linkDialog = AlertDialog.Builder(activity)

    fun show(imageUri: String) {

        val binding: DialogImageExpandedBinding = DataBindingUtil.inflate(
            LayoutInflater.from(activity), R.layout.dialog_image_expanded, null, false)
        binding.image = imageUri

        linkDialog.setView(binding.root)
        linkDialog.setCancelable(true)
        linkDialog.show()
    }
}