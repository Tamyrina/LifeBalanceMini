// Projektmenü der Anwendung.
// Ermöglicht das Verwalten aller Projekte.
import java.util.Scanner;
import java.util.ArrayList;

public class ProjectMenu {
    private ProjectService projectService;
    private TaskService taskService;
    private Scanner scanner;

    public ProjectMenu(ProjectService projectService) {
        this.projectService = projectService;
        this.scanner = new Scanner(System.in);
    }

    public ProjectMenu(ProjectService projectService, TaskService taskService) {
        this.projectService = projectService;
        this.taskService = taskService;
        this.scanner = new Scanner(System.in);
    }

    public void show() {
        boolean running = true;
        // Zeigt das Projektnavigationsmenü an.
        while (running) {
            System.out.println();
            System.out.println("=== Projekte ===");
            System.out.println("1. Projekt hinzufügen");
            System.out.println("2. Projekte anzeigen");
            System.out.println("3. Projekt löschen");
            System.out.println("4. Projekt bearbeiten");
            System.out.println("5. Aufgaben eines Projekts anzeigen");
            System.out.println("0. Zurück");
            System.out.print("Auswahl: ");
            // Liest die Auswahl des Benutzers ein.
            String input = scanner.nextLine();
            // Benutzereingabe auswerten und entsprechende Methode aufrufen
            if (input.equals("1")) {
                addProject();
            } else if (input.equals("2")) {
                showProjects();
            } else if (input.equals("3")) {
                deleteProject();
            } else if (input.equals("4")) {
                editProject();
            } else if (input.equals("5")) {
                showProjectTasks();
            } else if (input.equals("0")) {
                running = false;
            } else {
                System.out.println("Ungültige Eingabe.");
            }
        }
    }
    // Methode zum Hinzufügen eines neuen Projekts. Liest die Eingaben des Benutzers ein und erstellt ein neues Projekt.
    private void addProject() {
        System.out.println();
        System.out.println("Neues Projekt anlegen");

        System.out.print("Titel: ");
        String title = scanner.nextLine();

        System.out.print("Kategorie: ");
        String category = scanner.nextLine();
        // Liest das Fälligkeitsdatum und die Startzeit ein und überprüft das Format.
        System.out.print("Fälligkeitsdatum (TT.MM.JJJJ): ");
        String dueDate = readValidDate();
        // Liest die Fälligkeitszeit ein und überprüft das Format.
        System.out.print("Fälligkeitszeit (HH:MM): ");
        String dueTime = readValidTime();
        // Erstellt das neue Projekt und fügt sie über den ProjectService hinzu.
        Project project = new Project(title, category, dueDate, dueTime);
        projectService.addProject(project);

        System.out.println("Projekt wurde hinzugefügt.");
    }
    // Methode zeigt die Liste aller Projekte an, wenn welche vorhanden sind.
    private void showProjects() {
        System.out.println();
        System.out.println("Alle Projekte:");

        if (projectService.getAllProjects().isEmpty()) {
            System.out.println("Es gibt noch keine Projekte.");
        } else {
            for (int i = 0; i < projectService.getAllProjects().size(); i++) {
                System.out.println("--------------------");
                System.out.println((i + 1) + ".");
                System.out.println(projectService.getAllProjects().get(i));
            }
        }
    }
    // Methode zum Löschen eines Projekts.
    private void deleteProject() {
        System.out.println();
        System.out.println("Projekt löschen");
        // Beendet die Methode, wenn keine Projekte vorhanden sind.
        if (projectService.getAllProjects().isEmpty()) {
            System.out.println("Es gibt keine Projekte zum Löschen.");
            return;
        }

        showProjects();
        // Liest die Projektnummer ein und löscht das Projekt über den ProjectService oder beendet den Vorgang, wenn die Eingabe = 0 (-1) ist.
        int index = selectProject("Projekt löschen");
        if (index < 0) return;

        projectService.removeProject(index);

        System.out.println("Projekt " + (index + 1) + " wurde gelöscht.");
    }

    private void editProject() {
        System.out.println();
        System.out.println("Projekt bearbeiten");
        // Beendet die Methode, wenn keine Projekte vorhanden sind.
        if (projectService.getAllProjects().isEmpty()) {
            System.out.println("Es gibt keine Projekte.");
            return;
        }
        // Zeigt die Liste aller Projekte an.
        showProjects();
        // Liest die Projektnummer ein und bearbeitet das Projekt über den ProjectService oder beendet den Vorgang, wenn die Eingabe = 0 (-1) ist.
        int index = selectProject("Projekt bearbeiten");
        if (index < 0) return;

        System.out.print("Neuer Titel: ");
        String newTitle = scanner.nextLine();

        System.out.print("Neue Kategorie: ");
        String newCategory = scanner.nextLine();

        System.out.print("Neues Fälligkeitsdatum (TT.MM.JJJJ): ");
        String newDueDate = readValidDate();

        System.out.print("Neue Fälligkeitszeit (HH:MM): ");
        String newDueTime = readValidTime();

        projectService.updateProject(index, newTitle, newCategory, newDueDate, newDueTime);

        System.out.println("Projekt " + (index + 1) + " wurde bearbeitet.");
    }
    // Methode zum Anzeigen der Aufgaben eines Projekts.
    private void showProjectTasks() {
        System.out.println();
        System.out.println("Aufgaben eines Projekts anzeigen");
        // Beendet die Methode, wenn keine Projekte vorhanden sind.
        if (projectService.getAllProjects().isEmpty()) {
            System.out.println("Es gibt keine Projekte.");
            return;
        }

        showProjects();
        // Liest die Projektnummer ein und zeigt die Aufgaben des Projekts an oder beendet den Vorgang, wenn die Eingabe = 0 (-1) ist.
        int index = selectProject("Aufgaben anzeigen");
        if (index < 0) return;

        String projectName = projectService.getAllProjects().get(index).getTitle();
        ArrayList<Task> tasks = getTasksForProject(projectName);

        System.out.println();
        System.out.println("Aufgaben für Projekt: " + projectName);
        if (tasks.isEmpty()) {
            System.out.println("Keine Aufgaben vorhanden.");
        } else {
            for (int i = 0; i < tasks.size(); i++) {
                Task task = tasks.get(i);
                String status = task.isCompleted() ? "erledigt" : "offen";
                System.out.println((i + 1) + ". " + task.getTitle() + 
                    " | " + task.getDueDate() + " " + task.getStartTime() + 
                    " | " + task.getEstimatedDuration() + " min" +
                    " | " + status);
            }
        }
    }
    // Methode zum Abrufen der Aufgaben eines Projekts über den TaskService.
    private ArrayList<Task> getTasksForProject(String projectName) {
        if (taskService == null) {
            return new ArrayList<>();
        }
        return taskService.getTasksForProject(projectName);
    }
    // Methode zum Auswählen eines Projekts aus der Liste. 
    private int selectProject(String actionDescription) {
        ArrayList<Project> projects = projectService.getAllProjects();
        // Beendet die Methode, wenn keine Projekte vorhanden sind.
        if (projects.isEmpty()) {
            return -1;
        }
        // Zeigt die Liste aller Projekte an und liest die Projektnummer ein. 
        System.out.println();
        System.out.println(actionDescription);
        for (int i = 0; i < projects.size(); i++) {
            System.out.println((i + 1) + ". " + projects.get(i).getTitle());
        }
        System.out.println("0. Abbrechen");
        System.out.print("Projektnummer: ");

        while (true) {
            String input = scanner.nextLine();
            // Überprüft, ob die Eingabe eine gültige Zahl ist und ob sie innerhalb des gültigen Bereichs liegt. 
            // Gibt den Index des ausgewählten Projekts zurück oder -1, wenn die Eingabe 0 ist.
            try {
                int number = Integer.parseInt(input);
                if (number == 0) {
                    return -1;
                }
                int index = number - 1;
                if (index < 0 || index >= projects.size()) {
                    System.out.println("Ungültige Nummer. Bitte erneut versuchen.");
                    System.out.print("Projektnummer: ");
                    continue;
                }
                return index;
            } catch (NumberFormatException e) {
                System.out.println("Ungültige Eingabe. Bitte erneut versuchen.");
                System.out.print("Projektnummer: ");
            }
        }
    }
    // Methode zum Prüfen eines vom Benutzer eingegebenen Datums. 
    // Wenn das Format ungültig ist, wird der Benutzer aufgefordert, erneut ein Datum einzugeben.
    private String readValidDate() {
        while (true) {
            String date = scanner.nextLine();
            if (isValidDate(date)) {
                return date;
            }
            System.out.println("Ungültiges Format. Bitte im Format TT.MM.JJJJ eingeben.");
            System.out.print("Fälligkeitsdatum (TT.MM.JJJJ): ");
        }
    }
    // Methode zum Prüfen einer vom Benutzer eingegebenen Zeit. 
    // Wenn das Format ungültig ist, wird der Benutzer aufgefordert, erneut eine Zeit einzugeben.
    private String readValidTime() {
        while (true) {
            String time = scanner.nextLine();
            if (isValidTime(time)) {
                return time;
            }
            System.out.println("Ungültiges Format. Bitte im Format HH:MM eingeben.");
            System.out.print("Fälligkeitszeit (HH:MM): ");
        }
    }
    // Prüft die Gültigkeit eines Datums im Format TT.MM.JJJJ. Gibt true zurück, wenn das Datum gültig ist, andernfalls false.
    private boolean isValidDate(String date) {
        if (date == null || date.isEmpty()) return false;
        String[] parts = date.split("\\.");
        if (parts.length != 3) return false;
        try {
            int day = Integer.parseInt(parts[0]);
            int month = Integer.parseInt(parts[1]);
            int year = Integer.parseInt(parts[2]);
            return day >= 1 && day <= 31 && month >= 1 && month <= 12 && year >= 1900 && year <= 2100;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    // Prüft die Gültigkeit einer Zeit im Format HH:MM. Gibt true zurück, wenn die Zeit gültig ist, andernfalls false.
    private boolean isValidTime(String time) {
        if (time == null || time.isEmpty()) return false;
        String[] parts = time.split(":");
        if (parts.length != 2) return false;
        try {
            int hour = Integer.parseInt(parts[0]);
            int minute = Integer.parseInt(parts[1]);
            return hour >= 0 && hour <= 23 && minute >= 0 && minute <= 59;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}