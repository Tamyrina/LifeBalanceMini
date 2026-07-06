// Aufgabenmenü der Anwendung.
// Ermöglicht das Verwalten aller Aufgaben.
import java.util.Scanner;
import java.util.ArrayList;

public class TaskMenu {
    private TaskService taskService;
    private ProjectService projectService;
    private Scanner scanner;

    public TaskMenu(TaskService taskService, ProjectService projectService) {
        this.taskService = taskService;
        this.projectService = projectService;
        this.scanner = new Scanner(System.in);
    }

    public void show() {
        boolean running = true;
        // Zeigt das Aufgabennavigationsmenü an.
        while (running) {
            System.out.println();
            System.out.println("=== Aufgaben ===");
            System.out.println("1. Aufgabe hinzufügen");
            System.out.println("2. Aufgaben anzeigen");
            System.out.println("3. Aufgabe löschen");
            System.out.println("4. Aufgabe als erledigt markieren");
            System.out.println("5. Aufgabe bearbeiten");
            System.out.println("0. Zurück");
            System.out.print("Auswahl: ");
            // Liest die Auswahl des Benutzers ein.
            String input = scanner.nextLine();
            // Benutzereingabe auswerten und entsprechende Methode aufrufen
            if (input.equals("1")) {
                addTask();
            } else if (input.equals("2")) {
                showTasks();
            } else if (input.equals("3")) {
                deleteTask();
            } else if (input.equals("4")) {
                completeTask();
            } else if (input.equals("5")) {
                editTask();
            } else if (input.equals("0")) {
                running = false;
            } else {
                System.out.println("Ungültige Eingabe.");
            }
        }
    }
    // Methode zum Hinzufügen einer neuen Aufgabe. Liest die Eingaben des Benutzers ein und erstellt eine neue Aufgabe.
    private void addTask() {
        System.out.println();
        System.out.println("Neue Aufgabe anlegen");

        System.out.print("Titel: ");
        String title = scanner.nextLine();
        // Liest das Fälligkeitsdatum und die Startzeit ein und überprüft das Format.
        System.out.print("Fälligkeitsdatum (TT.MM.JJJJ): ");
        String dueDate = readValidDate();
        // Liest die Startzeit ein und überprüft das Format.
        System.out.print("Startzeit (HH:MM): ");
        String startTime = readValidTime();
        // Liest die geschätzte Dauer in Minuten ein und überprüft, ob es eine positive Zahl ist.
        int estimatedDuration = readPositiveInt("Geschätzte Dauer (Minuten): ");
        // Wählt ein Projekt für die Aufgabe aus. Wenn kein Projekt ausgewählt wird, wird die Aufgabe nicht erstellt.
        String project = selectProject();
        if (project == null) {
            System.out.println("Aufgabe wurde nicht erstellt.");
            return;
        }
        // Erstellt die neue Aufgabe und fügt sie über den TaskService hinzu.
        Task task = new Task(title, dueDate, startTime, estimatedDuration, "", project);
        taskService.addTask(task);

        System.out.println("Aufgabe wurde hinzugefügt.");
    }
    // Methode zeigt die Liste aller Aufgaben an, wenn welche vorhanden sind.
    private void showTasks() {
        System.out.println();
        System.out.println("Alle Aufgaben:");

        if (taskService.getAllTasks().isEmpty()) {
            System.out.println("Es gibt noch keine Aufgaben.");
        } else {
            for (int i = 0; i < taskService.getAllTasks().size(); i++) {
                System.out.println("--------------------");
                System.out.println((i + 1) + ".");
                System.out.println(taskService.getAllTasks().get(i));
            }
        }
    }
    // Methode zum Löschen einer Aufgabe. 
    private void deleteTask() {
        System.out.println();
        System.out.println("Aufgabe löschen");
        // Überprüft, ob Aufgaben vorhanden sind, bevor versucht wird, eine Aufgabe zu löschen.
        if (taskService.getAllTasks().isEmpty()) {
            System.out.println("Es gibt keine Aufgaben zum Löschen.");
            return;
        }
        // Zeigt die Liste der Aufgaben an, damit der Benutzer die zu löschende Aufgabe auswählen kann.
        showTasks();
        
        int index = selectTask("Aufgabe löschen");
        if (index < 0) return;
        // Löscht die ausgewählte Aufgabe über den TaskService.
        taskService.removeTask(index);
        System.out.println("Aufgabe " + (index + 1) + " wurde gelöscht.");
    }
    // Methode zum Markieren einer Aufgabe als erledigt.
    private void completeTask() {
        System.out.println();
        System.out.println("Aufgabe als erledigt markieren:");
        // Überprüft, ob Aufgaben vorhanden sind
        if (taskService.getAllTasks().isEmpty()) {
            System.out.println("Es gibt keine Aufgaben.");
            return;
        }
        // Zeigt die Liste der Aufgaben an, damit der Benutzer die zu markierende Aufgabe auswählen kann.
        showTasks();

        int index = selectTask("Aufgabe erledigen");
        if (index < 0) return;
        // Markiert die ausgewählte Aufgabe als erledigt über den TaskService.
        taskService.markTaskAsCompleted(index);
        System.out.println("Aufgabe " + (index + 1) + " wurde als erledigt markiert.");
    }
    // Methode zum Bearbeiten einer bestehenden Aufgabe.
    private void editTask() {
        System.out.println();
        System.out.println("Aufgabe bearbeiten");
        // Überprüft, ob Aufgaben vorhanden sind
        if (taskService.getAllTasks().isEmpty()) {
            System.out.println("Es gibt keine Aufgaben.");
            return;
        }
        // Zeigt die Liste der Aufgaben an, damit der Benutzer die zu bearbeitende Aufgabe auswählen kann.
        showTasks();

        int index = selectTask("Aufgabe bearbeiten");
        if (index < 0) return;
        // Holt die aktuelle Aufgabe, um ihre Attribute zu bearbeiten.
        Task currentTask = taskService.getAllTasks().get(index);

        System.out.print("Neuer Titel: ");
        String newTitle = scanner.nextLine();
        // Liest das neue Fälligkeitsdatum und die neue Startzeit ein und überprüft das Format.
        System.out.print("Neues Fälligkeitsdatum (TT.MM.JJJJ): ");
        String newDueDate = readValidDate();
        // Liest die neue Startzeit ein und überprüft das Format.
        System.out.print("Neue Startzeit (HH:MM): ");
        String newStartTime = readValidTime();
        // Liest die neue geschätzte Dauer in Minuten ein und überprüft, ob es eine positive Zahl ist.
        int newEstimatedDuration = readPositiveInt("Neue geschätzte Dauer (Minuten): ");
        // Wählt ein neues Projekt für die Aufgabe aus. Wenn kein Projekt ausgewählt wird, wird die Bearbeitung abgebrochen.
        String newProject = selectProject();
        if (newProject == null) {
            System.out.println("Aufgabe wurde nicht bearbeitet.");
            return;
        }
        // Aktualisiert die Aufgabe über den TaskService mit den neuen Werten.
        taskService.updateTask(index, newTitle, newDueDate, newStartTime, newEstimatedDuration, currentTask.getRepeat(), newProject);

        System.out.println("Aufgabe " + (index + 1) + " wurde bearbeitet.");
    }
    // Methode zum Auswählen eines Projekts aus der Liste der vorhandenen Projekte.
    private String selectProject() {
        // Holt die Liste aller Projekte über den ProjectService.
        ArrayList<Project> projects = projectService.getAllProjects();
        // Überprüft, ob Projekte vorhanden sind, bevor versucht wird, eines auszuwählen.
        if (projects.isEmpty()) {
            System.out.println("Es gibt noch keine Projekte. Bitte erstelle zuerst ein Projekt.");
            return null;
        }
        // Zeigt die Liste der Projekte an, damit der Benutzer ein Projekt auswählen kann.
        System.out.println();
        System.out.println("Wähle ein Projekt:");
        for (int i = 0; i < projects.size(); i++) {
            System.out.println((i + 1) + ". " + projects.get(i).getTitle());
        }
        System.out.println("0. Abbrechen");
        System.out.print("Projektnummer: ");
        
        while (true) {
            String input = scanner.nextLine();
            
            try {
                // Wenn die Eingabe 0 ist, wird die Auswahl abgebrochen.
                int number = Integer.parseInt(input);
                if (number == 0) {
                    return null;
                }
                // Wenn die Eingabe eine ungültige Projektnummer ist, wird der Benutzer aufgefordert, erneut eine gültige Nummer einzugeben.
                int index = number - 1;
                if (index < 0 || index >= projects.size()) {
                    System.out.println("Ungültige Nummer. Bitte erneut versuchen.");
                    System.out.print("Projektnummer: ");
                    continue;
                }   
                // Wenn die Eingabe gültig ist, wird der Titel des ausgewählten Projekts zurückgegeben.
                return projects.get(index).getTitle();
                // Wenn die Eingabe keine Zahl ist, wird der Benutzer aufgefordert, erneut eine gültige Nummer einzugeben.
            } catch (NumberFormatException e) {
                System.out.println("Ungültige Eingabe. Bitte erneut versuchen.");
                System.out.print("Projektnummer: ");
            }
        }
    }
    // Methode zum Auswählen einer Aufgabe aus der Liste der vorhandenen Aufgaben.
    private int selectTask(String actionDescription) {
        ArrayList<Task> tasks = taskService.getAllTasks();
        // Überprüft, ob Aufgaben vorhanden sind, bevor versucht wird, eine auszuwählen.
        if (tasks.isEmpty()) {
            return -1;
        }

        System.out.println();
        System.out.println(actionDescription);
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ". " + tasks.get(i).getTitle());
        }
        System.out.println("0. Abbrechen");
        System.out.print("Aufgabennummer: ");

        while (true) {
            String input = scanner.nextLine();
            
            try {
                // Wenn die Eingabe 0 ist, wird die Auswahl abgebrochen.
                int number = Integer.parseInt(input);
                if (number == 0) {
                    return -1;
                }
                // Wenn die Eingabe eine ungültige Aufgabennummer ist, wird der Benutzer aufgefordert, erneut eine gültige Nummer einzugeben.
                int index = number - 1;
                if (index < 0 || index >= tasks.size()) {
                    System.out.println("Ungültige Nummer. Bitte erneut versuchen.");
                    System.out.print("Aufgabennummer: ");
                    continue;
                }
                // Wenn die Eingabe gültig ist, wird der Index der ausgewählten Aufgabe zurückgegeben.
                return index;
                // Wenn die Eingabe keine Zahl ist, wird der Benutzer aufgefordert, erneut eine gültige Nummer einzugeben.
            } catch (NumberFormatException e) {
                System.out.println("Ungültige Eingabe. Bitte erneut versuchen.");
                System.out.print("Aufgabennummer: ");
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
    // Methode zum Prüfen einer vom Benutzer eingegebenen Zeit. 
    // Wenn das Format ungültig ist, wird der Benutzer aufgefordert, erneut eine Zeit einzugeben.
    private String readValidTime() {
        while (true) {
            String time = scanner.nextLine();
            if (isValidTime(time)) {
                return time;
            }
            System.out.println("Ungültiges Format. Bitte im Format HH:MM eingeben.");
            System.out.print("Startzeit (HH:MM): ");
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
    // Liest eine positive ganze Zahl vom Benutzer ein. 
    // Wenn die Eingabe ungültig ist, wird der Benutzer aufgefordert, erneut eine Zahl einzugeben.
    private int readPositiveInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            try {
                int value = Integer.parseInt(input);
                if (value > 0) {
                    return value;
                }
            } catch (NumberFormatException e) {
            }
            System.out.println("Ungültige Eingabe. Bitte eine positive Zahl eingeben.");
        }
    }
}