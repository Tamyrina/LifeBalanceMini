# Task Module

Last updated: 03.07.2026

## Purpose

The Task module manages all individual tasks of the application.

Every task belongs to exactly one project.

Tasks represent concrete work that can be scheduled and completed.

Examples:

- Learn for Software Engineering
- Submit Assignment
- Buy Groceries

---

# Task Attributes

Each task contains:

- title
- project
- due date
- start time
- estimated duration (minutes)
- repeat
- priority
- completed

The end time is calculated automatically from the start time and estimated duration.

---

# User Functions

The user can:

- create a task
- edit a task
- delete a task
- mark a task as completed
- display all tasks

Tasks are automatically saved after every change.

Tasks are automatically loaded when the application starts.

---

# Validation

Title must not be empty.

Date format:

DD.MM.YYYY

Time format:

HH:MM

Estimated duration must be greater than zero.

---

# Business Rules

Every task belongs to exactly one project.

Tasks do not have their own category.

The project name is stored as a String.

Changing a task should immediately update the saved data.

Completing or reopening a task may change the completion status of its project.

---

# Architecture

The module consists of:

- Task
- TaskService
- TaskRepository
- TaskMenu

Responsibilities:

Task
Stores task data.

TaskService
Contains business logic.

TaskRepository
Loads and saves tasks.

TaskMenu
Handles user interaction.

---

# Persistence

Tasks are stored in a text file.

Each task is stored in one line.

Fields are separated by semicolons.

---

# Design Philosophy

The implementation should remain simple.

The module should be easy to understand and serve as the reference implementation for future modules.