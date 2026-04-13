---
title: Tech stack & setup
description: "Technical Requirements & Dependencies"
order: 5
---

## Technical Requirements & Dependencies

### Android versions, devices, and constraints

- **Minimum SDK / target SDK.** The project is configured via Gradle properties; the current intended configuration is a minimum SDK of API 33 and a target SDK of API 36 (these should be verified against the project’s version catalog and `gradle.properties` before final submission).
- **Tested environments.** The submitted version has been tested on a physical Pixel 6 Pro device and on a Pixel 8 emulator running API 34.
- **Orientation and hardware assumptions.** The app is currently designed and tested for standard phone form factors; it assumes network connectivity at launch in order to complete Google Sign‑In, but does not rely on special sensors, camera access, or other device‑specific hardware.
- **Language/locale.** The prototype has been tested with the default English locale; no language‑specific restrictions beyond that have been identified.


### Third‑party libraries

In addition to standard Android and AndroidX support libraries, the app uses the following third‑party components:

- **AndroidX Navigation (Fragment \& UI, Safe Args plugin)** — in‑app navigation and type‑safe argument passing between screens.
- **AndroidX Lifecycle (ViewModel, LiveData)** — lifecycle‑aware state management.
- **AndroidX Preference** — preferences/settings UI support.
- **Material Components for Android** — Material Design UI widgets and theming.
- **Room (runtime \& compiler)** — SQLite‑backed local persistence.
- **Gson** — JSON serialization/deserialization.
- **Retrofit (core \& Gson converter)** — REST client infrastructure (available for future integrations) with Gson‑based JSON support.
- **OkHttp logging interceptor** — HTTP request/response logging.
- **Hilt / Dagger (Android \& compiler)** — dependency injection for application components.
- **Google Play Services Auth / Google Identity Services / AndroidX Credentials** — Google Sign‑In and credential handling.
- **JUnit 5, AndroidX Test, Espresso, Hilt Android Testing, and related JUnit 5 Android runner** — unit and instrumented testing support.

(Additional AndroidX UI components such as AppCompat, Activity, Fragment, ConstraintLayout, and RecyclerView are also used but are considered part of the standard Android support stack.)[^2]

### Android permissions

- The current version of the app does **not** declare or require any dangerous Android permissions; it does not request runtime access to camera, microphone, location, external storage, or similar features.
- At present, the app runs with only the normal permissions granted to typical installed apps and remains functional without any explicit runtime permission prompts.

If future features such as photo attachments, location‑based elevation lookup, or time‑based notifications are implemented, additional dangerous or special permissions (for example, camera, media access, location, or exact alarms) will need to be requested and documented.

### External and device services

- **External services.** The app currently integrates with **Google Identity Services / Google Sign‑In** to authenticate users at launch; a valid Google account and network connectivity are required to reach the main UI.[^2]
- **Device services.** The app uses standard Android UI components and local persistence (Room over SQLite) but does not yet integrate with device sensors, camera, audio recorder, or other hardware‑specific services.

This list will need to be extended in future iterations if additional integrations (for example, notifications, camera/gallery for photos, or location services) are implemented.