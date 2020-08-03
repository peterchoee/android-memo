package io.choedeb.android.memo.presentation.util.permission

import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import io.choedeb.android.memo.presentation.util.permission.PermissionStatus

class PermissionUtil(
    private val activity: Activity
) {

    fun getPermissionStatus(permission: String): PermissionStatus {
        return if (ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED) {
            PermissionStatus.GRANTED
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                PermissionStatus.CAN_ASK
            } else {
                PermissionStatus.DENIED
            }
        }
    }

    fun getPermissionRequest(permission: String, requestCode: Int) {
        ActivityCompat.requestPermissions(activity, arrayOf(permission), requestCode)
    }
}