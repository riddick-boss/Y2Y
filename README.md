# Y2Y
#### Android app which helps with tracking and organizing personal goals and achievements.

Used:
- Kotlin
- Hilt
- Jetpack Compose
- Room
- Git flow workflow

## General info

App is organized in MVVM architecture. User is able to enter his achievement, which is stored locally with usage of Room database. Kotlin Flows are used to achieve reactive approach and react for database changes. Jetpack Compose is used for building whole UI and supports light/dark theme.

## Screenshots

| ![Achievements list light](/screenshots/list_light_theme.png) | ![Achievements list dark](/screenshots/list_dark_theme.png) | ![Add achievement bottom sheet](/screenshots/bottom_sheet_light.png) |
|-|-|-|

## Features

- light/dark theme
- achievements order by date
- Compose bottom sheet layout
- unit tests (also instrumented)

## Tests

Unit tests and instrumented unit tests are created. Instrumented tests were necessary for testing Room dao, which needs in-memory database.
Frameworks used:
- JUnit4
- [Truth](https://truth.dev/)
- [Mockito](https://site.mockito.org/)
- [Turbine](https://github.com/cashapp/turbine)

Sample test:
```kotlin
    @Test
    fun `onCloseBottomSheet emits after achievement addition`() = runBlocking {
        doReturn(Unit).`when`(repository).insert(any(), any(), any(), any())
        viewModel.onCloseBottomSheet.test {
            viewModel.onTitleChanged("title")
            viewModel.onDescriptionChanged("description")
            viewModel.onAddAchievement()
            Truth.assertThat(awaitItem()).isNotNull()
        }
    }
```
