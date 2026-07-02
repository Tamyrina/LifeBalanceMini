# LifeBalance Mini

Last updated: 03.07.2026

## Project Vision

LifeBalance Mini is a modular Java CLI application for personal organization.

The application helps users manage projects, tasks and their daily schedule.

The goal is not to replace professional tools like Google Calendar or Microsoft To Do.

Instead, the application focuses on providing a simple and understandable planning tool for learning software architecture and Java development.

The code should remain easy to read and fully understandable.

---

# Main Idea

A user manages larger projects.

Each project contains multiple tasks.

Tasks can be scheduled on specific dates and times.

The calendar provides an overview of scheduled tasks.

The dashboard gives a quick overview of the current situation.

---

# Core Modules

## Dashboard

The application's start page.

Displays:

- active projects
- project progress
- today's schedule
- warnings (for example overlapping tasks)

---

## Projects

Projects represent larger goals or activities.

Examples:

- Software Engineering Exam
- Living Room Renovation
- Vacation Planning

Projects have:

- title
- category
- due date
- due time
- completed

Projects can be:

- created
- edited
- deleted
- displayed
- saved
- loaded

A project is automatically marked as completed when all tasks belonging to it are completed.

---

## Tasks

Tasks belong to exactly one project.

Tasks have:

- title
- due date
- start time
- estimated duration
- repeat
- priority
- completed

The end time is calculated automatically.

Tasks can be:

- created
- edited
- deleted
- displayed
- marked as completed
- saved
- loaded

---

## Calendar

The calendar is the planning view.

It displays scheduled tasks.

Users can:

- display a day
- display a week
- move tasks
- change task duration
- jump to a specific date
- detect overlapping tasks

The calendar itself does not store any data.

---

# General Design

The application consists of independent modules.

Each module has a clear responsibility.

Business logic belongs into Services.

Repositories are responsible only for saving and loading data.

The ConsoleMenus are responsible only for user interaction.

The implementation should remain simple, modular and easy to understand.


# Development Philosophy

Prefer small, incremental improvements.

Do not rewrite the application.

Extend the existing codebase while keeping it understandable.

Every new feature should fit into the existing architecture.