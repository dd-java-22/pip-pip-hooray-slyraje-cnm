---
title: Build Instructions
description: "How to build"
order: 10
---

## Build Instructions

Follow these steps to build the project in IntelliJ IDEA.

1. **Clone or download the repository**

    - Clone the Git repository to your local machine using your preferred Git client, or download a ZIP archive from GitHub and extract it locally.

2. **Open the project in IntelliJ IDEA**

    - Start IntelliJ IDEA.
    - Choose **File → Open…**.
    - Select the root directory of the cloned project and click **OK**.
    - When prompted, confirm that this is a Gradle project and allow IntelliJ to import it.

3. **Wait for Gradle sync to complete**

    - IntelliJ IDEA will automatically download and index dependencies.
    - Wait until Gradle sync finishes and no sync errors are reported.

4. **Create `app/local.properties` with the OAuth client ID**

   This project uses Google Sign-In and reads a required `clientId` value from a module-level properties file.

    - In the project view, locate the `app/` module directory.
    - Inside `app/`, create a new file named:

      ```text
      local.properties
      ```

    - Add the following line to `app/local.properties`:

      ```properties
      clientId={OAuth 2.0 Client ID}
      ```

    - The actual `{OAuth 2.0 Client ID}` value is **not** stored in the repository and will be provided separately via Slack. Replace the placeholder with the value you receive.

5. **Re-sync Gradle (if prompted)**

    - After creating `app/local.properties`, IntelliJ IDEA may prompt you to sync the Gradle project again.
    - If prompted, allow the sync to complete.

6. **Build the project**

    - From the main menu, choose **Build → Make Project**.
    - Alternatively, open the **Gradle** tool window, expand the `app` module, and run the `assembleDebug` task.

   The build should complete without errors if the steps above have been followed.

7. **Run the app on an emulator or device**

    - Connect an Android device or start an Android emulator.
    - In IntelliJ IDEA, select the desired run configuration (the app module) and a target device.
    - Click **Run** to install and launch the app.

### Notes on external configuration

- At minimum, the app requires `app/local.properties` containing a valid `clientId` value for Google Sign-In in order for authentication to function correctly at runtime.
- Depending on how the OAuth client is configured in the Google Cloud Console, additional environment-specific setup may be required for Google Sign-In to succeed on instructor devices (for example, matching SHA fingerprints or package names). The OAuth client and any related sensitive configuration details will be provided to the instructors via Slack.