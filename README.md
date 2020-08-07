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

*[Clean Architecture]*​
![Clean Architecture + Modulization](https://user-images.githubusercontent.com/58249793/89202291-b0e53c00-d5ed-11ea-9dc1-5073441d5314.png)
- `Clean Architecture`의 각 레이어에 맞춰 `Multi Module`로 구성
- `app`: Application, DI
- `common`: Common Resource
- `presentation`: UI(Activity&Fragment), ViewModel
- `domain`: UseCase, Mapper
- `data`: Repository, Local(Room), Mapper

*[MVVM]*
![design_pattern_mvvm](https://user-images.githubusercontent.com/60678606/76587741-42adb900-6528-11ea-80b3-a362dc1311d2.png)
- `ACC ViewModel`을 활용한 `MVVM`
- ViewModel(Presentation Layer)의 데이터를 UI로 넘겨줄때는 `DataBinding`과 `LiveData`를 사용
- Repository(Data Layer)와 ViewModel 간 데이터스트림은 `RxJava`를 사용
​
## Package Structure
Clean Architecture와 MVVM을 구현한 `Multi Module` 프로젝트
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

## ShortCuts
- [app Module](https://google.com, "app Module")
- [common Module](https://google.com, "common Module")
- [presentation Module](https://google.com, "presentation Module")
- [domain Module](https://google.com, "domain Module")
- [data Module](https://google.com, "data Module")
- [dependencies.gradle](https://google.com, "dependencies.gradle")