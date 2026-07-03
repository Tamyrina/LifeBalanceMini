# Dashboard Module

Last updated: 03.07.2026

## Purpose

The Dashboard is the application's start page.

Its purpose is to give the user a quick overview of the current situation without navigating through multiple menus.

The Dashboard does not store any data.

It summarizes information from the other modules.

---

# Responsibilities

The Dashboard displays:

- active projects
- project progress
- today's scheduled tasks including calculated end times
- warnings (for example overlapping tasks)

The Dashboard does not:

- create tasks
- edit tasks
- delete tasks
- create projects
- save files

---

# Active Projects

Only active projects are displayed.

Completed projects are hidden.

Each active project displays:

- project title
- progress bar

Example:

Software Engineering

███████░░░   7/10

The progress is calculated automatically from completed tasks.

---

# Today's Schedule

The Dashboard displays today's tasks.

Tasks are sorted by start time.

Display format:

09:00 - 11:00   Learn Java             (Software Engineering)

11:30 - 12:30   Submit Assignment      (Software Engineering)

15:00 - 15:45   Buy Groceries          (Household)

If there are no scheduled tasks:

No tasks scheduled for today.

---

# Warnings

The Dashboard should display important warnings.

Examples:

- overlapping tasks
- overdue tasks

Warnings are informational only.

They do not prevent the user from creating or modifying tasks.

The current implementation displays warnings for overlapping tasks.

---

# Navigation

The Dashboard is shown automatically when the application starts.

From the Dashboard the user can navigate to:

- Projects
- Tasks
- Calendar
- Exit

---

# Architecture

The Dashboard module consists of:

- DashboardService
- DashboardMenu

No DashboardRepository is required.

No Dashboard model is required.

DashboardService uses:

- ProjectService
- TaskService
- CalendarService

DashboardMenu only displays information and handles navigation.

DashboardService gathers information from the other modules.

It does not calculate or store its own data.

---

# Design Philosophy

The Dashboard should remain clean and simple.

It should answer one question:

"What is important today?"

The Dashboard should not replace the Project or Calendar modules.

Its purpose is to provide a quick overview of the application's current state.