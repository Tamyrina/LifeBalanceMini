\# Architecture

Last updated: 03.07.2026

\## Overview



LifeBalance Mini is a modular Java CLI application.



The application is divided into separate modules with clear responsibilities.



The current core modules are:



\- Dashboard

\- Projects

\- Tasks

\- Calendar

\- Persistence

\- Console UI



The goal is to keep the architecture simple, understandable and easy to extend.



\---



\# Architectural Principle



Each class should have one clear responsibility.



The application follows a simple layered structure:



```text

ConsoleMenu

&#x20;   ↓

Services

&#x20;   ↓

Repositories

&#x20;   ↓

Text files

```



Models store data.



Services contain business logic.



Repositories handle saving and loading.



Console menus handle user interaction.



\---



\# Models



Models describe single objects.



Examples:



\- Project

\- Task



A model contains:



\- private attributes

\- constructor

\- getters

\- setters

\- toString()



Models should not contain complex business logic.



\---



\# Services



Services contain the main application logic.



Examples:



\- ProjectService

\- TaskService

\- CalendarService

\- DashboardService



Services are responsible for:



\- creating objects

\- updating objects

\- deleting objects

\- returning lists

\- checking rules

\- calling repositories after data changes



A Service owns the relevant ArrayList.



Example:



```text

TaskService owns the taskList.

ProjectService owns the projectList.

```



\---



\# Repositories



Repositories are responsible only for persistence.



Examples:



\- TaskRepository

\- ProjectRepository



Repositories:



\- save data to text files

\- load data from text files

\- do not contain user interface code

\- do not contain business logic



Data is stored as text.



One object equals one line.



Attributes are separated by semicolons.



Example:



```text

Learn Java;Softwaretechnik;02.07.2026;09:00;120;high;false

```



\---



\# Console UI



The Console UI is responsible only for interaction with the user.



It may:



\- display menus

\- read user input

\- call service methods

\- display results



It must not:



\- save files directly

\- load files directly

\- change repositories directly

\- contain complex business logic



Possible menu classes:



\- ConsoleMenu

\- ProjectMenu

\- TaskMenu

\- CalendarMenu

\- DashboardMenu



\---



\# Module Responsibilities



\## Dashboard Module



The Dashboard is the start page of the application.



It displays:



\- active projects

\- project progress

\- today's calendar extract

\- warnings, for example overlapping tasks



The Dashboard does not store data.



It uses:



\- ProjectService

\- TaskService

\- CalendarService



\---



\## Project Module



Projects represent larger activities or goals.



Examples:



\- Software Engineering Exam

\- Living Room Renovation

\- Vacation Planning



A Project has:



\- title

\- category

\- due date

\- due time

\- completed



A project can be:



\- created

\- edited

\- deleted

\- displayed

\- saved

\- loaded



Project progress is calculated from the tasks assigned to the project.



A project should be marked as completed when all assigned tasks are completed.



\---



\## Task Module



Tasks are concrete steps that belong to one project.



A Task has:



\- title

\- project

\- due date

\- start time

\- estimated duration in minutes

\- repeat

\- priority

\- completed



The end time is calculated from start time and estimated duration.



Tasks can be:



\- created

\- edited

\- deleted

\- displayed

\- marked as completed

\- saved

\- loaded



Tasks do not need their own category because they belong to a project.



\---



\## Calendar Module



The Calendar is the planning view.



It displays scheduled tasks in chronological order.



The Calendar can:



\- show today's tasks

\- show a specific day

\- show a week

\- move a task

\- change task duration

\- detect overlapping tasks



The Calendar does not store its own data.



All changes are delegated to TaskService.



\---



\# Data Flow



\## Adding a task



```text

User

&#x20;   ↓

TaskMenu / ConsoleMenu

&#x20;   ↓

TaskService.addTask()

&#x20;   ↓

TaskRepository.saveTasks()

&#x20;   ↓

tasks.txt

```



\## Loading tasks



```text

Program starts

&#x20;   ↓

TaskService constructor

&#x20;   ↓

TaskRepository.loadTasks()

&#x20;   ↓

taskList

```



\## Displaying the calendar



```text

User

&#x20;   ↓

CalendarMenu

&#x20;   ↓

CalendarService

&#x20;   ↓

TaskService

&#x20;   ↓

Task list

```



\---



\# Important Rules



Do not duplicate data unnecessarily.



The TaskService owns tasks.



The ProjectService owns projects.



The CalendarService only works with existing tasks and projects.



The DashboardService only summarizes existing data.



Repositories only save and load.



Console menus only interact with the user.



\---



\# Development Style



Keep the code simple.



Use small methods.



Avoid frameworks.



Do not add a graphical user interface.



The application remains a Java CLI application.



Each new module should be implemented step by step.



Every new feature must fit into the existing architecture.

