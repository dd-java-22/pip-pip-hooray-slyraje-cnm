---
title: Development Roadmap
description: "Phased development roadmap for completing Pip Pip Hooray core features, aesthetic improvements, and stretch goals."
order: 10
---

## Development Roadmap

### Executive Summary

This roadmap outlines the path from the current prototype to a fully-featured, polished incubation management tool. Work is organized into three phases: **Phase 1 (Core Functionality)**, **Phase 2 (Aesthetic Polish)**, and **Phase 3 (Stretch Goals)**.

---

## PHASE 1: CORE FUNCTIONALITY & DEFICIENCIES 🔴

**Priority:** Critical | **Timeline:** ~4-6 weeks

### P1.1: Settings Implementation (Week 1)

- ✅ Implement incubator default settings persistence
- ✅ Add temperature scale conversion (F↔C) with UI refresh
- ✅ Enable "Enable notifications" toggle (backend only, UI ready for Phase 3)
- ✅ Implement CSV export functionality with file picker
- ✅ Refactor settings to use SharedPreferences or DataStore

**Dependencies:** None  
**Tests Required:** Settings persistence, temperature conversion logic

### P1.2: Batch Editing (Week 2)

- ✅ Implement batch detail edit mode (toggle read-only ↔ edit)
- ✅ Allow modification of: batch number, incubator, breeds, notes
- ✅ Add validation and error handling
- ✅ Persist changes to database
- ✅ Add "Save" and "Cancel" buttons with confirmation dialogs

**Dependencies:** P1.1 (for default incubator values)  
**Tests Required:** Edit flow, validation, database persistence

### P1.3: Egg Group Support (Weeks 2-3)

- ✅ Refactor data model to support multiple egg groups per batch
- ✅ Update UI to show egg group breakdown on batch detail
- ✅ Implement per-group candling tracking and outcome recording
- ✅ Update batch cards to display multi-breed indicators
- ✅ Migrate existing single-group batches to new model

**Dependencies:** P1.2 (batch editing)  
**Tests Required:** Data model migrations, egg group queries, UI rendering

### P1.4: Milestone Tracking (Week 3)

- ✅ Add milestone progress/completion states to data model
- ✅ Create milestone status UI indicators (pending, in-progress, completed)
- ✅ Add ability to mark milestones as complete
- ✅ Display milestone alerts/warnings on batch detail
- ✅ Calculate and display days-to-milestone

**Dependencies:** P1.3 (egg group support)  
**Tests Required:** Milestone state transitions, date calculations

### P1.5: List UI Enhancements (Week 4)

- ✅ Add filtering controls (by incubator, status, date range)
- ✅ Add sorting options (by date set, batch number, status)
- ✅ Implement search functionality for batch/incubator names
- ✅ Add "no results" empty states with helpful messaging
- ✅ Persist filter/sort preferences

**Dependencies:** P1.1 through P1.4  
**Tests Required:** Filter logic, search algorithms, preference persistence

### P1.6: Sign-In Error Handling (Week 1)

- ✅ Show Snackbar/error dialog on failed Google Sign-In
- ✅ Add retry mechanism with clear user messaging
- ✅ Log sign-in errors for debugging

**Dependencies:** None  
**Tests Required:** Error handling, user messaging

---

## PHASE 2: AESTHETIC & UX POLISH 🎨

**Priority:** High | **Timeline:** ~2-3 weeks | **Triggered by:** Phase 1 Completion

### P2.1: Visual Theme & Branding

- ✅ Define Material 3 color palette (primary, secondary, tertiary)
- ✅ Implement cohesive typography hierarchy
- ✅ Update all component styling (buttons, cards, dialogs)
- ✅ Create consistent icon set
- ✅ Apply theme to all existing screens

**Dependencies:** Phase 1  
**Tests Required:** Visual consistency checks, accessibility contrast ratios

### P2.2: Home Screen Polish

- ✅ Add status badges for batches (on-track, upcoming milestone, due, overdue)
- ✅ Implement highlight/glow effects for upcoming milestones
- ✅ Add batch count and summary stats
- ✅ Improve card layouts with better spacing
- ✅ Add pull-to-refresh functionality

**Dependencies:** P2.1, P1.4 (milestone tracking)  
**Tests Required:** Visual regression tests, performance tests (large lists)

### P2.3: Transitions & Navigation

- ✅ Add shared element transitions between screens
- ✅ Implement smooth screen fade/slide animations
- ✅ Create splash screen with app logo
- ✅ Add activity startup animation
- ✅ Optimize animation performance

**Dependencies:** P2.1  
**Tests Required:** Animation performance on lower-end devices

### P2.4: Bottom Navigation Polish

- ✅ Add active state indicators
- ✅ Improve icon visibility and clarity
- ✅ Add transition animations
- ✅ Ensure accessibility labels

**Dependencies:** P2.1, P2.3  
**Tests Required:** Accessibility testing, state management

---

## PHASE 3: STRETCH GOALS 🚀

**Priority:** Enhancement | **Timeline:** 6+ weeks | **Triggered by:** Phase 2 Completion

### P3.1: Persistent Breed Registry (Week 5)

- ✅ Add breed master records to data model
- ✅ Create breed management UI (add, edit, delete)
- ✅ Implement breed dropdown selectors in batch creation
- ✅ Enable breed-based reporting/analytics
- ✅ Import common chicken breeds as defaults

**Dependencies:** P1.3 (egg group support)

### P3.2: Advanced Notifications (Weeks 5-6)

- ✅ Implement AlarmManager-based time triggers for lockdown/hatch dates
- ✅ Create notification templates with dynamic content
- ✅ Add deep-linking to batch details from notifications
- ✅ Create notification preferences screen
- ✅ Handle notification permissions (Android 13+)

**Dependencies:** P1.1 (settings), P1.4 (milestones)

### P3.3: Multi-Species Support (Week 6)

- ✅ Add fowl type enum (chickens, quail, ducks, turkeys, etc.)
- ✅ Define species-specific milestone templates
- ✅ Create incubator preset profiles by species (temperature, humidity targets)
- ✅ Auto-populate species defaults on batch creation

**Dependencies:** P3.1 (breed registry)

### P3.4: Environmental Logging (Weeks 7-8)

- ✅ Add daily temperature/humidity/turning log entry data model
- ✅ Create environment tracking UI (calendar-based entry form)
- ✅ Display environment history charts (temperature trends, etc.)
- ✅ Compare actual vs. target environmental conditions

**Dependencies:** P3.3 (species support for targets)

### P3.5: Enhanced Outcome Tracking (Week 8)

- ✅ Implement outcome categories (clear, quitter-early, quitter-late, assisted, dead-in-shell, etc.)
- ✅ Add outcome statistics dashboard
- ✅ Create breed/incubator/species success rate reporting
- ✅ Export outcome reports as PDF

**Dependencies:** P1.3 (egg groups), P3.1 (breed registry)

### P3.6: Photo Attachments (Weeks 9-10)

- ✅ Integrate camera/gallery picker using MediaStore
- ✅ Store photos with batches or individual eggs
- ✅ Create photo gallery view in batch detail
- ✅ Add basic photo editing (crop, rotate)
- ✅ Implement cloud backup for photos (if backend available)

**Dependencies:** P1.3 (egg groups)

### P3.7: Bonus Features

- ✅ Celebratory animations for high-viability batches (80%+ hatch rate)
- ✅ Sales/CRM tracking for customer hatches
- ✅ Cost tracking and profitability analysis per batch
- ✅ Customer notification system around hatch milestones
- ✅ Batch templates for recurring incubation profiles

**Dependencies:** P3.5 (outcome tracking)

---

## Prioritization Summary

| Phase | Focus | Est. Duration | Blocker for Next | Target Launch |
|-------|-------|---------------|------------------|---------------|
| **P1** | Core bugs & missing features | 4-6 weeks | Phase 2 | Beta testing |
| **P2** | UX polish & visual design | 2-3 weeks | Phase 3 | Public beta |
| **P3** | Enhanced features & analytics | 6+ weeks | N/A | v2.0+ |

---

## Known Issues & TODOs to Address

From code review:

- `Batch.java`: Set nullable field to @NotNull when UI requires it (P1.2)
- `EggDetailFragment.java`: Add EggRepository/EggViewModel access (P1.3)
- `EggDetailFragment.java`: Prepare UI for creating new egg entries (P1.3)
- `SignInFragment.java`: Show Snackbar on sign-in error (P1.6)

---

## Recommended Execution Order

**Week 1:** P1.1 + P1.6 (Settings + Error Handling)  
**Week 2:** P1.2 + P1.3 (Batch Editing + Egg Groups)  
**Week 3:** P1.3 continued + P1.4 (Milestones)  
**Week 4:** P1.5 (List Enhancements)  
**Weeks 5-6:** P2.1 through P2.4 (Aesthetic Polish)  
**Weeks 7+:** Phase 3 items in order of priority/dependencies

---

## Success Criteria

### Phase 1 Complete
- All settings are persisted and functional
- Batches can be created and edited with multiple egg groups
- Milestones are tracked with clear status indicators
- Lists can be filtered and sorted
- Error handling is user-friendly

### Phase 2 Complete
- App follows Material 3 design guidelines
- Navigation is smooth with polished transitions
- Splash screen on startup
- Ready for public beta testing

### Phase 3 Complete
- Advanced features significantly extend app value
- Photo attachments for documentation
- Breed registry enables better reporting
- Notifications keep users engaged
- Environmental logging provides detailed batch history
