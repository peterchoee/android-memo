package io.choedeb.android.memo.di

import android.app.Activity
import io.choedeb.android.memo.util.ExpandedImageDialog
import io.choedeb.android.memo.util.permission.PermissionUtil
import org.koin.dsl.module

val utilModule = module {

    // 권한 유틸리티
    single { (activity: Activity) -> PermissionUtil(activity) }

    // 이미지 상세(확대) 다이얼로그 유틸리티
    factory { (activity: Activity) -> ExpandedImageDialog(activity) }
}