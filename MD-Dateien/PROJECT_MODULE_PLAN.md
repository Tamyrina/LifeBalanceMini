\# Project Module



Last updated: 03.07.2026



\## Purpose



The Project module manages larger activities.



A project groups multiple related tasks.



Examples:



\- Software Engineering Exam

\- Living Room Renovation

\- Vacation Planning



\---



\# Project Attributes



Each project contains:



\- title

\- category

\- due date

\- due time

\- completed



The completion status is calculated automatically.



\---



\# User Functions



The user can:



\- create a project

\- edit a project

\- delete a project

\- display all projects



Projects are automatically saved and loaded.



\---



\# Business Rules



Every task belongs to exactly one project.



A project can contain zero or more tasks.



A project is automatically marked as completed when all assigned tasks are completed.



If at least one task is not completed, the project becomes open again.



The project progress is calculated from completed tasks.



Example:



7 completed tasks

10 total tasks



→ Progress = 70%



\---



\# Dashboard



The Dashboard displays only active projects.



Completed projects are not shown there.



The Dashboard displays:



Project Name

Progress Bar



Example:



Software Engineering ███████░░░



\---



\# Future Extensions



Possible future improvements:



\- project notes

\- project color

\- project archive

\- project statistics

