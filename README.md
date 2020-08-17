# SimpleMemo 
`Clean Architecture`, `MVVM`, `Multi Module`을 구현한 간단한 메모장 앱  

## Feature
- 텍스트 메모 작성, 카메라/갤러리 이미지 추가, 링크 이미지 추가
- 메모 추가/삭제/수정
​
## Environment
- IDE: Android Studio 4.0.1
- Language: `kotlin` 1.3.72
- minSdkVersion: 21
- targetSdkVersion: 29
​
## Architecture
`Clean Architecture` + `MVVM`

*[Clean-Architecture]*
![Clean Architecture + Modulization](https://user-images.githubusercontent.com/58249793/89202291-b0e53c00-d5ed-11ea-9dc1-5073441d5314.png)
- `Clean Architecture`의 각 레이어에 맞춰 `Multi Module`로 구성
- `app`: Application, DI
- `common`: Common Resource
- `presentation`: UI(Activity&Fragment), ViewModel
- `domain`: UseCase, Mapper
- `data`: Repository, Local(Room), Mapper

*[MVVM]*
![design_pattern_mvvm](https://user-images.githubusercontent.com/60678606/76587741-42adb900-6528-11ea-80b3-a362dc1311d2.png)
- `ACC ViewModel`을 활용한 `MVVM` 구현
- `DataBinding`, `LiveData` 사용
- `RxJava` 사용
​
## Package Structure
```
app (application)
├── ...
├── src
│   ├── main
│   │   ├── di                      # Dependency Injection by Koin
│   │   └── MemoApplication.kt
│   ├── res
│   │   └── mipmap
│   └── AndroidManifest.xml
│
common
├── ...
├── src
│   ├── main
│   │   ├── RxExtension.kt          # Bridge RxJava2 to RxJava3 & etc
│   │   └── ToastExtension.kt       # Toast Message Extension
│   └── ...
│
data
├── ...
├── src
│   ├── main
│   │   ├── entity                  # Entity for Data layer
│   │   ├── local                   # RoomDatabase & Dao
│   │   ├── mapper                  # Mapper Extension
│   │   └── repository              # Repository
│   └── ...
│
domain
├── ...
├── src
│   ├── main
│   │   ├── entity                  # Entity for Domain layer
│   │   ├── repository              # Repository interface
│   │   └── usecase                 # UseCase
│   └── ...
│
presentation
├── ...
├── src
│   ├── main
│   │   ├── entity                  # Entity for Presentation layer
│   │   ├── mapper                  # Mapper Extension
│   │   ├── ui                      # Activity & ViewModel
│   │   │   ├── base                # BaseActivity, BaseViewModel
│   │   │   ├── detail              
│   │   │   ├── main                
│   │   │   ├── write               
│   │   └── util                    # SingleLiveEvent, Permission, Provider, etc...
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
│
```

## Library & Tool
- Koin `2.0.1`
- ACC ViewModel `2.2.0`
- LiveData `2.2.0`
- Room `2.2.3`
- RxJava3 `3.0.0`
- Glide `4.11.0`
- Logger `2.2.0`
- [More Information](https://github.com/choedeb/SimpleMemo/blob/master/dependencies.gradle)

## ScreenShot
|Main(Empty)|Write|Main(List)|Detail
|---|---|---|---|
|![preview_01](https://user-images.githubusercontent.com/45548673/75117730-7333cd00-56b7-11ea-92e9-f31666ae656c.png)|![preview_02](https://user-images.githubusercontent.com/45548673/75117742-85ae0680-56b7-11ea-9d41-5bc9e28fa58b.png)|![preview_03](https://user-images.githubusercontent.com/45548673/75117744-8cd51480-56b7-11ea-93fa-b217d2538a47.png)|![preview_04](https://user-images.githubusercontent.com/45548673/75117746-8f376e80-56b7-11ea-8e76-ac5cad1bc3af.png)|