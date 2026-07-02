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

        System.out.print("Fälligkeitsdatum: ");
        String dueDate = scanner.nextLine();

        System.out.print("Startzeit: ");
        String startTime = scanner.nextLine();

        System.out.print("Geschätzte Dauer (Minuten): ");
        int estimatedDuration = Integer.parseInt(scanner.nextLine());

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

        System.out.print("Welche Aufgabe soll gelöscht werden? Nummer eingeben: ");
        int number = Integer.parseInt(scanner.nextLine());

        int index = number - 1;
        taskService.removeTask(index);

        System.out.println("Aufgabe " + number + " wurde gelöscht.");
    }

    private void completeTask() {
        System.out.println();
        System.out.println("Aufgabe als erledigt markieren:");

        if (taskService.getAllTasks().isEmpty()) {
            System.out.println("Es gibt keine Aufgaben.");
            return;
        }

        showTasks();

        System.out.print("Welche Aufgabe wurde erledigt? Nummer eingeben: ");
        int number = Integer.parseInt(scanner.nextLine());

        int index = number - 1;
        taskService.markTaskAsCompleted(index);

        System.out.println("Aufgabe " + number + " wurde als erledigt markiert.");
    }

    private void editTask() {
        System.out.println();
        System.out.println("Aufgabe bearbeiten");

        if (taskService.getAllTasks().isEmpty()) {
            System.out.println("Es gibt keine Aufgaben.");
            return;
        }

        showTasks();

        System.out.print("Welche Aufgabe soll bearbeitet werden? Nummer eingeben: ");
        int number = Integer.parseInt(scanner.nextLine());

        System.out.print("Neuer Titel: ");
        String newTitle = scanner.nextLine();

        System.out.print("Neues Fälligkeitsdatum: ");
        String newDueDate = scanner.nextLine();

        System.out.print("Neue Startzeit: ");
        String newStartTime = scanner.nextLine();

        System.out.print("Neue geschätzte Dauer (Minuten): ");
        int newEstimatedDuration = Integer.parseInt(scanner.nextLine());

        System.out.print("Neues Projekt: ");
        String newProject = scanner.nextLine();

        System.out.print("Neue Wiederholung: ");
        String newRepeat = scanner.nextLine();

        int index = number - 1;
        taskService.updateTask(index, newTitle, newDueDate, newStartTime, newEstimatedDuration, newRepeat, newProject);

        System.out.println("Aufgabe " + number + " wurde bearbeitet.");
    }
}