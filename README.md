# 수정중!!! 
사진 촬영, 갤러리 이미지 선택, 이미지 링크 삽입 기능을 가진 메모 앱  
​
## Environment
- IDE: Android Studio 3.5.3
- Language: `kotlin` 1.3.61
- minSdkVersion: 21
- targetSdkVersion: 29
​
## Architecture
Design Pattern: `MVVM`  
​
*[MVVM]*
![design_pattern_mvvm](https://user-images.githubusercontent.com/60678606/76587741-42adb900-6528-11ea-80b3-a362dc1311d2.png)
- ACC ViewModel을 활용한 MVVM
- ViewModel(Presenter Layer)의 데이터를 UI로 넘겨줄때는 `DataBinding`과 `LiveData`를 사용
- Repository(Data Layer)와 ViewModel간 데이터스트림은 `RxJava`를 사용
​
## Package Structure
```
app
├── ...
├── src
│   ├── main
│   │   ├── data                    # [Data Layer]
│   │   ├── di                      # Dependency Injection by Koin
│   │   ├── ui                      # [UI & Presenter Layer]
│   │   │   ├── base                # Base View, ViewModel & etc...
│   │   │   ├── ...                 # View UI & Presenter & Adapter
│   │   ├── util                    # Utility
│   │   └── MemoApplication.kt
│   ├── res
│   │   ├── ...
│   │   ├── drawable                # SVG, Image resources
│   │   ├── font                    # NanumSquare regular, bold, extrabold
│   │   ├── values                  # color, dimen, string, style...
│   │   │   ├── colors.xml
│   │   │   ├── dimens.xml
│   │   │   ├── strings.xml
│   │   │   ├── styles.xml
│   │   ├── ...
│   └── AndroidManifest.xml
```
​
## Gradle Dependency (Library)
```
[build.gradle (project)]
​
buildscript {
    ext {
        kotlin_version = '1.3.61'
        appcompat_version = '1.1.0'
        ktx_version = '1.2.0'
        constraintlayout_version = '1.1.3'
        material_version = '1.2.0-alpha04'
        lifecycle_version = '2.2.0'
        arch_version = '2.1.0'
        koin_version = '2.0.1'
        room_version = '2.2.3'
        rxjava_version = '3.0.0'
        glide_version = '4.11.0'
        logger_version = '2.2.0'                
        junit_version = '4.12'
        junit_ext_version = '1.1.1'
        androidx_test_version = '1.2.0'
        robolectric_version = '4.2.1'
    }
}
​
​
[build.gradle (app)]

android {
    compileOptions {
        targetCompatibility = "8"
        sourceCompatibility = "8"
    }
    dataBinding {
        enabled = true
    }
}

dependencies {
    implementation fileTree(dir: 'libs', include: ['*.jar'])
    implementation "org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlin_version"
    implementation "androidx.appcompat:appcompat:$appcompat_version"
    implementation "androidx.core:core-ktx:$ktx_version"
    implementation "androidx.constraintlayout:constraintlayout:$constraintlayout_version"
    implementation "com.google.android.material:material:$material_version"
    /* Lifecycle */
    implementation "androidx.lifecycle:lifecycle-extensions:$lifecycle_version"
    implementation "androidx.lifecycle:lifecycle-common-java8:$lifecycle_version"
    testImplementation "androidx.arch.core:core-testing:$arch_version"
    /* Room */
    implementation "androidx.room:room-runtime:$room_version"
    implementation "androidx.room:room-rxjava2:$room_version"
    kapt "androidx.room:room-compiler:$room_version"
    testImplementation "androidx.room:room-testing:$room_version"
    /* Koin */
    implementation "org.koin:koin-core:$koin_version"
    implementation "org.koin:koin-core-ext:$koin_version"
    implementation "org.koin:koin-android:$koin_version"
    implementation "org.koin:koin-androidx-viewmodel:$koin_version"
    implementation "org.koin:koin-androidx-ext:$koin_version"
    testImplementation "org.koin:koin-test:$koin_version"
    /* RxJava3 */
    implementation "io.reactivex.rxjava3:rxjava:$rxjava_version"
    implementation "io.reactivex.rxjava3:rxandroid:$rxjava_version"
    implementation "com.github.akarnokd:rxjava3-bridge:$rxjava_version"
    /* Glide */
    implementation "com.github.bumptech.glide:glide:$glide_version"
    annotationProcessor "com.github.bumptech.glide:compiler:$glide_version"
    /* Logger */
    implementation "com.orhanobut:logger:$logger_version"
    /* Testing */
    testImplementation "junit:junit:$junit_version"
    testImplementation "org.robolectric:robolectric:$robolectric_version"
    testImplementation "androidx.test.ext:junit:$junit_ext_version"
    androidTestImplementation "androidx.test.ext:junit:$junit_ext_version"
    androidTestImplementation "androidx.test.ext:junit-ktx:$junit_ext_version"
    androidTestImplementation "androidx.test:core:$androidx_test_version"
    androidTestImplementation "androidx.test:runner:$androidx_test_version"
    androidTestImplementation "androidx.test:rules:$androidx_test_version"
}
```
​
### Testing
```
[build.gradle (app)]
​
android {
    testOptions {
        unitTests {
            includeAndroidResources = true
        }
    }
}
dependencies {
    // Testing
    testImplementation 'junit:junit:4.12'
    testImplementation 'org.mockito:mockito-core:3.1.0'
    testImplementation 'org.mockito:mockito-inline:2.21.0'
    testImplementation 'org.robolectric:robolectric:4.2.1'
    androidTestImplementation 'androidx.test:runner:1.2.0'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.2.0'
}
```