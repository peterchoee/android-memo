package io.choedeb.android.memo.di

import android.app.Activity
import io.choedeb.android.memo.presentation.util.ExpandedImageDialog
import io.choedeb.android.memo.presentation.util.permission.PermissionUtil
import io.choedeb.android.memo.presentation.util.PickImageUtil
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val utilModule = module {

    // permission utility
    single { (activity: Activity) -> PermissionUtil(activity) }

    // expanded image dialog utility
    factory { (activity: Activity) -> ExpandedImageDialog(activity) }

    // image picker utility with Camera, Gallery, Link
    single { (activity: Activity) ->
        PickImageUtil(
            activity,
            androidContext()
        )
    }
}