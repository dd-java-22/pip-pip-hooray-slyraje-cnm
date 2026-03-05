## 1. Project summary (for humans, not machines)
Pip Pip Hooray is a chicken egg incubation logbook for Android that helps users manage and track the incubation process. It is designed for poultry enthusiasts and small-scale farmers to maintain precise records of their hatchings.

*   Define and manage multiple incubators.
*   Create and track incubation batches.
*   Log per-egg candling results at different stages.
*   Record final hatch results for each egg.
*   Export incubation records for printing and long-term storage.

## 2. Module and package structure
*   `app`: The primary Android application module containing all UI, business logic, and data persistence code.

```text
edu.cnm.deepdive.pippiphooray
  (empty)
```
*   `edu.cnm.deepdive.pippiphooray`: Base package for the application. Currently contains only the `Application` subclass and package-level documentation.

## 3. Architecture and patterns
The project is set up to follow the standard Android MVVM (Model-View-ViewModel) architecture with the following patterns intended (based on dependencies):
*   **Dependency Injection**: Hilt is used for managing dependencies across the app.
*   **Persistence**: Room is configured for local SQLite data storage.
*   **Networking**: Retrofit is included for potential external API integration.
*   **Navigation**: Android Jetpack Navigation component is used for UI flow.
*   **Reactive Programming**: LiveData is used for observing data changes in the UI.

**Key Types:**
*   **Main Application**: `PipPipHoorayApplication` (annotated with `@HiltAndroidApp`).
*   **Host Activity**: (Not yet implemented)
*   **ViewModels**: (Not yet implemented)
*   **Repositories**: (Not yet implemented)

## 4. Domain model overview
Based on the project summary, the following domain entities are planned (though classes are not yet present in the source):
*   **Incubator**: Represents the physical hardware used for incubation.
    *   Fields: `id`, `name`, `type/model`, `capacity`.
*   **Batch**: Represents a single group of eggs being incubated together.
    *   Fields: `id`, `incubatorId`, `startDate`, `eggCount`, `species`.
*   **Egg**: Represents an individual egg within a batch.
    *   Fields: `id`, `batchId`, `position/identifier`.
*   **CandlingRecord**: Represents the results of an egg inspection.
    *   Fields: `id`, `eggId`, `date`, `status` (fertile, clear, quit, etc.).

**Persistence Details:**
*   Room is configured in `app/build.gradle.kts`.
*   The Room database is intended to be `edu.cnm.deepdive.myproject.service.LocalDatabase` (currently a placeholder in the Gradle DDL task).

## 5. Persistence and external/device services
*   **Storage**: Primary storage will be Room (SQLite).
*   **External Services**:
    *   **Google Sign-in**: Included in dependencies (`play-services-auth`) for user authentication.
    *   **External Storage**: Potential use for exporting records.
*   **Fallback**: The app is expected to function offline for local tracking, requiring external services only for cloud sync or export (if implemented).

## 6. Navigation and UI flow
*   **Implementation**: Navigation is configured using the Jetpack Navigation component (`nav_graph.xml` is expected but not yet present).
*   **Main Screens (Planned)**:
    *   **Incubator List**: View and manage incubators.
    *   **Batch List**: View active and past incubation batches.
    *   **Batch Detail**: View and edit specific batch information and egg records.
    *   **Candling/Hatch Entry**: Log results for individual eggs.

## 7. Key files for deep dives
*   `app/src/main/java/edu/cnm/deepdive/pippiphooray/PipPipHoorayApplication.java`: The entry point for the application and Hilt dependency injection.
*   `app/build.gradle.kts`: Contains the project configuration, SDK versions, and all major library dependencies.
*   `app/src/main/AndroidManifest.xml`: The manifest file defining app components (currently only the Application class).

## 8. Notable design decisions and constraints
*   **Single-Activity Architecture**: The presence of the Navigation component and Hilt suggests a modern single-activity architecture.
*   **Dependency Injection**: Use of Hilt ensures a decoupled and testable architecture.
*   **Limitations**: The project is currently in the initial setup phase. Most UI and domain logic are yet to be implemented.

## 9. Build and environment notes
*   **Languages**: Java (source files), Kotlin (Gradle build scripts).
*   **SDK Versions**:
    *   Minimum SDK: 30
    *   Target SDK: 36
*   **Key Libraries**:
    *   `com.google.dagger:hilt-android:2.55` (Dependency Injection)
    *   `androidx.room:room-runtime:2.6.1` (Persistence)
    *   `com.squareup.retrofit2:retrofit:2.11.0` (Networking)
    *   `androidx.navigation:navigation-fragment:2.8.8` (Navigation)
    *   `com.google.android.gms:play-services-auth:21.3.0` (Authentication)
