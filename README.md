# Movies
A simple app showcasing TMDB Api while using modern Android development technologies.
<p align="center">
  <img src="https://github.com/ralphevmanzano/Movies/assets/18175202/b149dbc9-1677-45c7-a33e-e2addc75443b" width="220" />
  <img src="https://github.com/ralphevmanzano/Movies/assets/18175202/d365b253-04a3-4474-8e88-48ad7a1cb9b3" width="220" /> 
  <img src="https://github.com/ralphevmanzano/Movies/assets/18175202/cf6f8d1b-1922-4a36-b436-f52d50e5292f" width="220" /> 
  <img src="https://github.com/ralphevmanzano/Movies/assets/18175202/92d1f86f-9436-463e-ad7c-ad6859385f02" width="220" /> 
</p>

## Development setup

You require the latest Android Studio Flamingo (for Gradle 8.1), so please do update or download it [here](https://developer.android.com/studio)

First, clone the repository: 
```
https://github.com/ralphevmanzano/Movies.git
```
Import the project in Android Studio and make sure the project uses Gradle JDK version 17 (this comes pre-installed with the latest Android Studio Flamingo)
```
File > Project Structure > SDK Location > Gradle Settings
```
![image_2023-04-28_03-36-28](https://user-images.githubusercontent.com/18175202/234985295-8b50eb82-38bf-44a4-b94a-9318ef40d814.png)
Now you can build and run the app

### Tech Stack
- Single Activity, MVVM Architecture
- Application is written entirely in [Kotlin](https://kotlinlang.org)
- Asynchronous processing using [Coroutines](https://kotlin.github.io/kotlinx.coroutines/), [Sandwich](https://github.com/skydoves/sandwich) and [Retrofit](https://square.github.io/retrofit/)
- Dependency Injection using [Hilt](https://developer.android.com/training/dependency-injection/hilt-android)
- [Room](https://developer.android.com/training/data-storage/room) for local storage
- [Jetpack Navigation](https://developer.android.com/guide/navigation) for in-app navigations
- [Android Paging](https://developer.android.com/topic/libraries/architecture/paging/v3-overview) for endless scrolling 
