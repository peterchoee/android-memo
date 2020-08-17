package io.choedeb.android.memo.presentation.util.permission

import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build

class PermissionUtil(
    private val activity: Activity
) {

    private fun usePermission(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
    }

    fun getPermissionStatus(permission: String): PermissionStatus {
        return if (usePermission()) {
            if (activity.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED) {
                PermissionStatus.GRANTED
            } else {
                PermissionStatus.CAN_ASK
            }
        } else {
            PermissionStatus.GRANTED
        }
    }

    fun getPermissionRequest(permission: String, requestCode: Int) {
        if (usePermission()) {
            activity.requestPermissions(arrayOf(permission), requestCode)
        }
        return
    }
}