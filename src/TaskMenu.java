import java.util.Scanner;

public class TaskMenu {
    private TaskService taskService;
    private Scanner scanner;

    public TaskMenu(TaskService taskService) {
        this.taskService = taskService;
        this.scanner = new Scanner(System.in);
    }

    public void show() {
        boolean running = true;

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

            String input = scanner.nextLine();

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

    private void addTask() {
        System.out.println();
        System.out.println("Neue Aufgabe anlegen");

        System.out.print("Titel: ");
        String title = scanner.nextLine();

        System.out.print("Fälligkeitsdatum (TT.MM.JJJJ): ");
        String dueDate = readValidDate();

        System.out.print("Startzeit (HH:MM): ");
        String startTime = readValidTime();

        int estimatedDuration = readPositiveInt("Geschätzte Dauer (Minuten): ");

        System.out.print("Projekt: ");
        String project = scanner.nextLine();

        System.out.print("Wiederholung: ");
        String repeat = scanner.nextLine();

        Task task = new Task(title, dueDate, startTime, estimatedDuration, repeat, project);
        taskService.addTask(task);

        System.out.println("Aufgabe wurde hinzugefügt.");
    }

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

    private void deleteTask() {
        System.out.println();
        System.out.println("Aufgabe löschen");

        if (taskService.getAllTasks().isEmpty()) {
            System.out.println("Es gibt keine Aufgaben zum Löschen.");
            return;
        }

        showTasks();

        int index = readTaskIndex("Welche Aufgabe soll gelöscht werden? Nummer eingeben (0 für zurück): ");
        if (index < 0) return;

        taskService.removeTask(index);
        System.out.println("Aufgabe " + (index + 1) + " wurde gelöscht.");
    }

    private void completeTask() {
        System.out.println();
        System.out.println("Aufgabe als erledigt markieren:");

        if (taskService.getAllTasks().isEmpty()) {
            System.out.println("Es gibt keine Aufgaben.");
            return;
        }

        showTasks();

        int index = readTaskIndex("Welche Aufgabe wurde erledigt? Nummer eingeben (0 für zurück): ");
        if (index < 0) return;

        taskService.markTaskAsCompleted(index);
        System.out.println("Aufgabe " + (index + 1) + " wurde als erledigt markiert.");
    }

    private void editTask() {
        System.out.println();
        System.out.println("Aufgabe bearbeiten");

        if (taskService.getAllTasks().isEmpty()) {
            System.out.println("Es gibt keine Aufgaben.");
            return;
        }

        showTasks();

        int index = readTaskIndex("Welche Aufgabe soll bearbeitet werden? Nummer eingeben (0 für zurück): ");
        if (index < 0) return;

        System.out.print("Neuer Titel: ");
        String newTitle = scanner.nextLine();

        System.out.print("Neues Fälligkeitsdatum (TT.MM.JJJJ): ");
        String newDueDate = readValidDate();

        System.out.print("Neue Startzeit (HH:MM): ");
        String newStartTime = readValidTime();

        int newEstimatedDuration = readPositiveInt("Neue geschätzte Dauer (Minuten): ");

        System.out.print("Neues Projekt: ");
        String newProject = scanner.nextLine();

        System.out.print("Neue Wiederholung: ");
        String newRepeat = scanner.nextLine();

        taskService.updateTask(index, newTitle, newDueDate, newStartTime, newEstimatedDuration, newRepeat, newProject);

        System.out.println("Aufgabe " + (index + 1) + " wurde bearbeitet.");
    }

    private int readTaskIndex(String prompt) {
        System.out.print(prompt);
        String input = scanner.nextLine();
        
        try {
            int number = Integer.parseInt(input);
            if (number == 0) {
                return -1;
            }
            int index = number - 1;
            if (index < 0 || index >= taskService.getAllTasks().size()) {
                System.out.println("Ungültige Nummer. Bitte erneut versuchen.");
                return readTaskIndex(prompt);
            }
            return index;
        } catch (NumberFormatException e) {
            System.out.println("Ungültige Eingabe. Bitte erneut versuchen.");
            return readTaskIndex(prompt);
        }
    }

    private String readValidDate() {
        String date = scanner.nextLine();
        if (isValidDate(date)) {
            return date;
        }
        System.out.println("Ungültiges Format. Bitte im Format TT.MM.JJJJ eingeben.");
        return readValidDate();
    }

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

    private String readValidTime() {
        String time = scanner.nextLine();
        if (isValidTime(time)) {
            return time;
        }
        System.out.println("Ungültiges Format. Bitte im Format HH:MM eingeben.");
        return readValidTime();
    }

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