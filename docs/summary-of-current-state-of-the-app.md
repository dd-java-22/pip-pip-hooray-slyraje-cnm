## Summary of Current State of the App

### Overall readiness

The current prototype implements the core flow for a signed‑in user to create incubators and batches, view them from a batch‑centric home screen, and record viability updates, but several important features remain incomplete or placeholder. Google Sign‑In is required at launch, bottom navigation connects the Incubator, Home, and Settings screens, and floating action buttons support creating incubator records and batch records with associated metadata (batch number, incubator, breeds, date set, milestone dates, egg count, and notes). Batch cards on the home screen navigate to a batch detail view where viability can be updated via a bulk‑candling flow, and the Settings screen exposes preferences for incubator defaults, temperature scale, notifications, CSV export, and sign‑out (with only sign‑out currently implemented).

### Deficiencies, incomplete features, and known issues

- Several settings features are placeholders rather than implemented functionality: incubator defaults defined in the Settings screen are not yet applied when creating new incubators, temperature scale changes (F/C) do not trigger any conversion or UI updates, the “Enable notifications” toggle is explicitly marked as a placeholder, and “Export batch data to CSV” is also placeholder and non‑functional.
- Editing capabilities on the batch detail screen are incomplete; the edit icon currently displays a toast indicating that editing is not yet implemented instead of allowing users to modify batch properties.
- Milestone handling is limited to displaying descriptive text and dates; there is no persisted representation of milestone progress or completion status in the data model or UI.
- Egg groups are only partially implemented, so each batch can effectively represent only a single egg group, which constrains accurate tracking for batches that contain multiple breeds.
- The Incubator and Batch list screens do not currently offer any filtering or sorting controls in the UI, which will impact usability as the number of records grows.

These items collectively represent the main gaps that would need to be addressed before the app could be considered a fully usable prototype for external testers.

### Aesthetic and cosmetic improvements

- The visual presentation is still quite barebones; a more cohesive theme with stronger typography, color use, and component styling would significantly improve perceived quality.
- Adding clear visual indicators (for example, highlighting or badges) on the batch list for batches with due or overdue milestones would improve scanability and make the home screen feel more informative at a glance.
- A startup splash screen and more polished transitions between screens would help the app feel more finished and aligned with typical production Android apps.

These aesthetic changes are cosmetic rather than functional but would meaningfully improve user experience.

### Functional stretch goals

- Implement full egg‑group support so that multiple breeds can be tracked accurately within a single batch, including per‑group counts and outcomes.
- Add time‑based notifications for lockdown and expected hatch dates using AlarmManager and NotificationManager, with deep‑links that open the relevant batch when a notification is tapped.
- Introduce persistent breed records that can be selected from dropdowns when creating batches, improving data consistency and enabling more accurate reporting by breed.
- Support multiple fowl types with species‑specific default milestones and incubator targets to better accommodate different incubation profiles.
- Add daily environmental logging to record incubator temperature, humidity, and turning activity by date, complementing candling data with a richer history of each batch’s conditions.
- Expand outcome tracking with structured categories (for example, clear, early quitter, late quitter, assisted hatch) and reporting screens that aggregate results by incubator, breed, or season.
- Allow users to attach candling and hatch‑day photos to batches or eggs using the device camera or gallery, improving the app’s usefulness for instruction, recordkeeping, and review.
- Optionally, introduce celebratory audio or animation for batches that achieve high viability rates, along with longer‑term “mega” features such as basic sales/CRM tracking, cost tracking, and customer notifications around hatch milestones.

Together, these stretch goals outline a path from the current prototype to a much more comprehensive incubation‑management tool.
