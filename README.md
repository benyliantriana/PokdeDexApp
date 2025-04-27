# PokeDexApp

## Overview
PokeDexApp is a demo Android application that provides pokemon's name and image. Built with a simple design and implementing modern Android development practices.

## Feature
- **Get Pokemon List**: Fetch pokemon name and image from a public API.

## Tech Stack
- **Programming Language**: Kotlin
- **Architecture**: MVVM (Model-View-ViewModel)
- **UI Components**: Jetpack Compose
- **Networking**: Retrofit with Coroutines
- **Dependency Injection**: Hilt
- **Local Storage**: Room
- **Testing**: JUnit
- **Architecture**: Multi module

## Installation
### Prerequisites
- Android Studio
- Android device or emulator running Android 8.0 (API level 24) or higher

### Steps
1. Clone this repository
2. Open the project in Android Studio.
3. Sync the Gradle files.
4. Build and run the app on an emulator or physical device.

## API Integration
The app fetches pokemon list from the [PokeApi](https://pokeapi.co/). Ensure you have internet access while using the app.

## App Architecture
The application follows the MVVM architecture pattern for better separation of concerns:
- **App**: Contains App, MainActivity, and App setting.
- **Libs**: Lib module that used by feature module. Each library has its own function.
- **Feature**: Features splits into modules.

## UI Architecture
- **Model**: Manages data handling and business logic. Uses Room for local storage and Retrofit for remote data fetching.
- **View**: Composes the UI using Jetpack Compose components.
- **ViewModel**: Acts as a bridge between the Model and View, Flow for UI observation.

## Folder Structure for App
```
project/
├── app/             # application
├── build-src/       # pre-compile dependency for library and feature module
├── features/        # contains feature modules
└── libs/            # contains lib modules
```

## General Folder Structure for Feature
```
feature/
├── api/             # api endpoint interface for retrofit
├── data/            # data model for retrofit response
├── datasource/      # data source for repository, local and remote data source
├── di/              # dependency injection setup using Hilt
├── mediator/        # remote mediator class for bridging between retrofit and room
├── repository/      # repository for view model to get the data
├── ui/              # UI components, screens, view model seprated by screen directory
├── utils/           # Utility classes and extensions
└── tests/           # Unit and UI tests
```

## Contact
For inquiries or feedback, feel free to reach out:
- Email: liantriana.beny@gmail.com
- GitHub: [benyliantriana](https://github.com/benyliantriana)
