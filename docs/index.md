---
title: Overview
description: "Summary of in-progress or completed project."
order: 0
---

{% include ddc-abbreviations.md %}

## Page contents
- ToC
  {:toc}

## Summary

Pip Pip Hooray is an Android app that serves as a structured logbook for chicken egg incubation. It helps backyard keepers, hobby breeders, and students track each incubation batch from set to hatch by recording incubator settings, key dates, per‑egg candling results, and final hatch outcomes. By replacing ad‑hoc notes and paper charts with searchable, data‑driven records, the app makes it easier to review past hatches, compute simple metrics such as hatch rate, and adjust future incubation practices accordingly.

The app emphasizes a clean, incubation‑specific workflow: users define incubators and their default targets, create batches tied to those incubators, add individual eggs, and record observations at typical candling checkpoints (for example, days 7, 14, and 18) and at hatch. Batch and egg lists present this information in an organized way on the device, and selected batches can be exported as CSV files for printing or long‑term archiving.

## Intended users and user stories

- **Backyard chicken keepers**  
  As a backyard chicken keeper running a small tabletop incubator, I log each batch’s dates, breed, and per‑egg candling results so that I can learn from past mistakes and steadily improve my hatch rate over time.

- **Hobby breeders managing multiple lines**  
  As a hobby breeder with several breeds and incubators, I track each batch by incubator, breed, and settings so that I can identify which incubator and configuration combinations produce the strongest, most reliable chicks and highest hatch percentages.

- **Students in agriculture or classroom incubation projects**  
  As a student completing an incubation project for an agriculture class or FFA program, I record candling observations and final hatch outcomes so that I can generate a clear summary of my project to share with my instructor or group.

## Functionality

- Define one or more **incubators**, including name, model, and default target temperature and humidity for each device.
- Create **incubation batches** that record date set, lockdown date, expected hatch date, breed, number of eggs set, and the incubator used.
- Add and manage **eggs within a batch**, assigning each an egg number and recording candling observations at common checkpoints (days 7, 14, and 18), a simple “hatched?” status, and optional notes.
- View **batch lists** showing active and completed batches, with at‑a‑glance details such as breed, dates, and simple status (for example, “In progress” or “Complete”).
- Open a **batch detail** screen that summarizes batch information and presents a per‑egg table or list, making it easy to see which eggs developed, which did not, and any notes associated with them.
- Compute and display **basic summary metrics**, such as hatch rate for each batch (number of eggs hatched divided by number set) and simple counts of developed vs. non‑developed eggs.
- **Export batch records** to a CSV file stored in device‑accessible external storage, so that users can print or archive their records in a format similar to common paper incubation charts.
- Optionally **fetch simple environmental context data** (for example, outside temperature or approximate elevation) from a public web API and display it alongside batch targets, helping users interpret how ambient conditions might influence incubation.

## Persistent data

- **Incubators**
    - Name, model, or description
    - Default temperature goal
    - Default humidity goal

- **Batches**
    - Unique batch identifier
    - Date set, lockdown date, expected hatch date
    - Breed or line
    - Number of eggs set
    - Target temperature and humidity for the batch (copied from the incubator by default, but adjustable)
    - Reference to the incubator used

- **Eggs (per‑batch)**
    - Egg number within the batch
    - Candling notes or status for day 7, day 14, and day 18
    - Hatched status (true/false, or a small set of outcome codes in a later refinement)
    - Per‑egg notes (for example, assists, abnormalities, or causes of failure when known)

- **Derived or summary values** (stored or recomputed as needed)
    - Hatch rate per batch
    - Counts of eggs by outcome category (hatched, clear, early quit, late quit) if you choose to represent these as explicit values rather than free‑text notes.

All of this data is stored locally on the device using SQLite so that users can access their records across multiple sessions without needing a network connection.

## Device/external services

- **Android external storage (via Storage Access Framework)**
    - Service/source name: Android external storage via Storage Access Framework (SAF)
    - URL: https://developer.android.com/training/data-storage
    - How Pip Pip Hooray uses it:  
      - The app exports a selected incubation batch’s data—batch metadata and per‑egg records—as a CSV file to user‑accessible storage (for example, the Downloads folder or a user‑chosen directory) using the Storage Access Framework. This lets users print or archive their incubation records in a spreadsheet‑friendly format that matches common hatch record sheets.
    - Dependence:
      -  CSV export is an optional convenience feature. The core functionality of Pip Pip Hooray remains fully usable without access to external storage.

- **Device location and elevation (optional helper data)**
    - Service/source name: Android location services for approximate elevation
    - URL: https://developer.android.com/develop/sensors-and-location/location
    - How Pip Pip Hooray uses it:
      - The app can optionally read the user’s coarse location once (for example, via a location picker or last known location) and use that to look up an approximate elevation. Elevation is stored with batches as reference context, helping users correlate hatch results with broad environmental conditions (lowland vs. high‑altitude incubation) without requiring precise or continuous tracking.
    - Dependence:  
      - Elevation support is optional. If location permissions are denied or unavailable, the app still fully supports defining incubators, recording batches and eggs, logging candling, and viewing summaries; elevation is simply omitted from batch records.

- **Android notifications (AlarmManager and NotificationManager)** (Stretch goal)
    - Service/source name: Android notification scheduling via AlarmManager and NotificationManager
    - URL: https://developer.android.com/training/notify-user/build-notification
    - How Pip Pip Hooray uses it:
      - The app schedules simple time‑based notifications for key incubation milestones, such as lockdown date and expected hatch date. When a notification is tapped, the app opens the associated batch detail screen so the user can quickly check status or update candling and hatch information.
    - Dependence:  
      - Notifications are optional and are currently planned as a stretch goal; if they are not implemented, the app’s core data entry and review features still work fully without notification permissions or background scheduling.

## Stretch goals and possible enhancements

- **Time‑based notifications for incubation milestones**
    - Implement time‑based notifications for incubation milestones using Android’s AlarmManager and NotificationManager APIs, reminding users of upcoming lockdown and expected hatch dates and navigating into the relevant batch when a notification is tapped.

- **Richer daily environmental logging**
    - Add a daily log feature to record incubator temperature, humidity, and turning activity by date, complementing the per‑egg candling record and giving users a more detailed picture of each hatch.

- **More detailed outcome tracking and reporting**
    - Introduce structured outcome categories (for example, clear, early quitter, late quitter, assisted hatch) and additional report screens that aggregate outcomes by incubator, breed, or season.

- **Photo attachments for batches or eggs**
    - Allow users to attach photos from candling or hatch day to batches or eggs using the device camera or gallery, enriching the record for teaching or review purposes.

These stretch goals are intentionally listed from the simplest (notifications) to more complex enhancements (detailed reporting and media support) and will only be tackled if core functionality and course requirements are met early.
