# Calendar Module

Last updated: 04.07.2026

## Purpose

The Calendar module is the planning view of LifeBalance Mini.

It displays scheduled tasks in chronological order and helps the user recognize conflicts in their daily planning.

The calendar does not store its own data.

It uses existing tasks and projects.

---

## Responsibilities

The Calendar module can:

- show today's schedule
- show a specific day
- show a week
- navigate to the previous day or week
- navigate to the next day or week
- show calculated start and end times
- detect overlapping tasks
- move a task to another date or time
- change the estimated duration of a task
- validate user input before applying changes

The Calendar module does not:

- create new tasks
- delete tasks
- create projects
- delete projects
- save files directly

All changes are delegated to the TaskService.

---

## Day View

The day view shows all tasks for one date.

Tasks are sorted by start time.

Display format:

```text
Thursday, 02.07.2026

09:00 - 11:00   Learn Java              (Software Engineering)
11:30 - 12:30   Submit Assignment       (Software Engineering)
15:00 - 15:45   Buy Groceries           (Household)
```

If there are no tasks:

```text
No tasks scheduled for this day.
```

---

## Week View

The week view shows all tasks of one week.

Tasks are grouped by weekday and sorted by start time.

Display format:

```text
Monday, 29.06.2026

09:00 - 11:00   Learn Java              (Software Engineering)

Tuesday, 30.06.2026

14:00 - 15:00   Doctor Appointment      (Health)
```

---

## Task Time Logic

Each task has:

- due date
- start time
- estimated duration in minutes

The end time is calculated automatically.

Example:

```text
Start time: 09:00
Estimated duration: 120 minutes
Displayed time: 09:00 - 11:00
```

The user does not enter the end time manually.

---

## Overlap Detection

The calendar checks if scheduled tasks overlap.

Example:

```text
09:00 - 13:00   Learn Java
12:30 - 13:30   Submit Assignment
```

In this case, the calendar shows a warning:

```text
Warning: "Learn Java" overlaps with "Submit Assignment".
```

Overlaps are warnings only.

The user may still keep the schedule.

Only tasks scheduled on the same date are checked for overlaps.

Tasks on different dates are never considered overlapping.

---

# Calendar Menu

The Calendar menu should offer:

```text
1. Show today
2. Show specific day
3. Show current week
0. Back
```

The main menu intentionally remains simple.

After displaying a day or week view, a context-specific action menu is shown.

The action menu offers:

```text
1. Previous day/week
2. Next day/week
3. Move task
4. Change task duration
0. Back to calendar menu
```

Editing actions are available only after displaying tasks, allowing the user to first inspect the schedule before making changes.

---
## Input Validation

All user input is validated before changes are applied.

The Calendar module validates:

- menu selections
- dates (DD.MM.YYYY)
- times (HH:MM)
- estimated duration (positive integer)

Invalid input does not terminate the application.

Instead, the user is informed and asked to enter the value again.
---

## Architecture

The Calendar module should contain:

- CalendarService
- CalendarMenu

No CalendarRepository is needed.

No Calendar model is needed unless there is a clear reason.

CalendarService uses TaskService and ProjectService.

CalendarMenu uses CalendarService.

---

## Design Philosophy

Keep the calendar simple.

It is not intended to replace Google Calendar or Outlook.

Its purpose is to provide a clear overview of tasks inside LifeBalance Mini.

The implementation should remain easy to understand.

The focus is on clarity, robustness and ease of use rather than implementing every possible calendar feature.