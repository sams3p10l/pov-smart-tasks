# SmartTasks
SmartTasks is a modern Proof-of-Concept Android app for simple task management.
It's built entirely with Jetpack Compose, using data from mock endpoint.

## Features
- View tasks by date
- Task details screen with due date, status, and comments
- Update task status
- Optionally leave a comment when resolving a task
- Local data storage with Room
- Task synchronization via REST API

## Tech Stack
- **Kotlin**
- **Jetpack Compose** for UI
- **Hilt** for dependency injection
- **Room** for local database
- **Retrofit + Kotlinx Serialization** for networking
- **Coroutines + Flow** for reactive programming
- **Material 3** for modern design system

## Getting Started
1. Clone the repository:
   ```bash
   git clone https://github.com/your-username/smarttasks.git
2. Open the project in Android Studio
3. Run the app on an emulator or physical device

## Testing
- Unit tests for use cases and view models (MockK, Turbine)
- UI tests for Compose screens.

