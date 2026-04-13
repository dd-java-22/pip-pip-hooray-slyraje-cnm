---
title: Notice
subtitle: "Notice"
order: 110
---

# License and Copyright Notice

## Project License

Copyright (c) 2026 by
Michael Sylvester

Unless otherwise noted, all original source code, assets, and documentation in this repository are the intellectual property of the author and **all rights reserved**.

Permission is granted to Deep Dive Coding instructors and staff to build, run, review, and grade this project as part of the Personal Android Project coursework and associated evaluations.

No other rights are granted. Any reuse, redistribution, or modification of this project’s original code or documentation outside the course context requires prior written permission from the author.

***

## Project Components

This repository contains the following components:

* **app** — Android client application
* **docs** — project documentation (GitHub Pages)

***

## Third-Party Components

This project uses a number of third-party libraries, frameworks, tools, and services. They remain subject to their respective licenses.

Where license information for a component is not listed in full here, it is available from the corresponding project website or distribution.

***

### Android Client

* **Kotlin Standard Library**
License: Apache License 2.0
Role: Kotlin language runtime and core utilities
* **AndroidX AppCompat**
License: Apache License 2.0
Role: Backward-compatible UI support
* **AndroidX Activity / Fragment**
License: Apache License 2.0
Role: Lifecycle and screen/navigation components
* **AndroidX ConstraintLayout**
License: Apache License 2.0
Role: Layout system for responsive UI
* **AndroidX RecyclerView**
License: Apache License 2.0
Role: List/grid UI display for dynamic data
* **AndroidX Navigation (Fragment \& UI)**
License: Apache License 2.0
Role: In-app navigation framework and Safe Args support
* **AndroidX Lifecycle (ViewModel / LiveData)**
License: Apache License 2.0
Role: Lifecycle-aware state management
* **AndroidX Preference**
License: Apache License 2.0
Role: Settings and preferences UI support
* **Material Components for Android**
License: Apache License 2.0
Role: Material Design UI widgets and theming
* **Room Database (runtime \& compiler)**
License: Apache License 2.0
Role: Local SQLite persistence layer with type-safe DAO support
* **Retrofit (core \& Gson converter)**
License: Apache License 2.0
Role: REST client and JSON (de)serialization integration
* **OkHttp Logging Interceptor**
License: Apache License 2.0
Role: HTTP request/response logging
* **Gson**
License: Apache License 2.0
Role: JSON serialization/deserialization
* **Hilt / Dagger (Android \& compiler)**
License: Apache License 2.0
Role: Dependency injection for Android components
* **Android Credential Manager**
(androidx.credentials libraries)
License: Apache License 2.0
Role: Credential handling and sign-in flows

***

### External Services

* **Google Identity Services / Google Sign-In**
License: Subject to Google Terms of Service
Role: User authentication and identity

The use of Google services in this project is subject to the terms and policies published by Google, including but not limited to the Google APIs Terms of Service and relevant product-specific terms.

***

### Build and Tooling

* **Gradle**
License: Apache License 2.0
Role: Build system and dependency management
* **Deep Dive Version Catalog**
(`edu.cnm.deepdive:catalog-…`)
License: Apache License 2.0
Role: Dependency and plugin version catalog
* **Android Gradle Plugin and AndroidX build tooling**
License: Apache License 2.0
Role: Android build, packaging, and tooling support

***

### Testing Libraries

* **JUnit 5**
License: Eclipse Public License 2.0
Role: Unit testing framework
* **AndroidX Test / Espresso**
License: Apache License 2.0
Role: Instrumented and UI testing support
* **Hilt Android Testing**
License: Apache License 2.0
Role: Dependency injection support in Android tests
* **Mannodermaus JUnit 5 Android runner**
License: Apache License 2.0
Role: JUnit 5 test runner integration for Android

***

### Supporting / Transitive Libraries

The project also depends on a number of transitive libraries pulled in by the primary dependencies listed above. These may include, but are not limited to:

* Kotlin Coroutines (Apache License 2.0)
* Google Guava (Apache License 2.0)
* Javax / Jakarta Inject APIs
* Protobuf (BSD-style license)
* JSR‑305 / FindBugs annotations (BSD-style license)
* Additional AndroidX support libraries and tooling components

These libraries are included indirectly via frameworks such as AndroidX, Room, Hilt, Retrofit, and the Android Gradle plugin.

***

## Notes

This notice document identifies the primary third-party components used by the project and their associated licenses to the best of the author’s knowledge at the time of writing.

Some dependencies are included transitively through frameworks and may not be explicitly declared in the project’s build files. This document should be updated if dependencies change or if additional third-party components are introduced.

All third-party components are used in accordance with their respective licenses.

Nothing in this notice overrides or supersedes the terms of any third-party license; in any conflict, the original third-party license terms control.

