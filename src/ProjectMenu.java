import java.util.Scanner;

public class ProjectMenu {
    private ProjectService projectService;
    private Scanner scanner;

    public ProjectMenu(ProjectService projectService) {
        this.projectService = projectService;
        this.scanner = new Scanner(System.in);
    }

    public void show() {
        boolean running = true;

        while (running) {
            System.out.println();
            System.out.println("=== Projekte ===");
            System.out.println("1. Projekt hinzufügen");
            System.out.println("2. Projekte anzeigen");
            System.out.println("3. Projekt löschen");
            System.out.println("4. Projekt bearbeiten");
            System.out.println("0. Zurück");
            System.out.print("Auswahl: ");

            String input = scanner.nextLine();

            if (input.equals("1")) {
                addProject();
            } else if (input.equals("2")) {
                showProjects();
            } else if (input.equals("3")) {
                deleteProject();
            } else if (input.equals("4")) {
                editProject();
            } else if (input.equals("0")) {
                running = false;
            } else {
                System.out.println("Ungültige Eingabe.");
            }
        }
    }

    private void addProject() {
        System.out.println();
        System.out.println("Neues Projekt anlegen");

        System.out.print("Titel: ");
        String title = scanner.nextLine();

        System.out.print("Kategorie: ");
        String category = scanner.nextLine();

        System.out.print("Fälligkeitsdatum (TT.MM.JJJJ): ");
        String dueDate = readValidDate();

        System.out.print("Fälligkeitszeit (HH:MM): ");
        String dueTime = readValidTime();

        Project project = new Project(title, category, dueDate, dueTime);
        projectService.addProject(project);

        System.out.println("Projekt wurde hinzugefügt.");
    }

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

    private void deleteProject() {
        System.out.println();
        System.out.println("Projekt löschen");

        if (projectService.getAllProjects().isEmpty()) {
            System.out.println("Es gibt keine Projekte zum Löschen.");
            return;
        }

        showProjects();

        int index = readProjectIndex("Welches Projekt soll gelöscht werden? Nummer eingeben (0 für zurück): ");
        if (index < 0) return;

        projectService.removeProject(index);

        System.out.println("Projekt " + (index + 1) + " wurde gelöscht.");
    }

    private void editProject() {
        System.out.println();
        System.out.println("Projekt bearbeiten");

        if (projectService.getAllProjects().isEmpty()) {
            System.out.println("Es gibt keine Projekte.");
            return;
        }

        showProjects();

        int index = readProjectIndex("Welches Projekt soll bearbeitet werden? Nummer eingeben (0 für zurück): ");
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

    private int readProjectIndex(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            
            try {
                int number = Integer.parseInt(input);
                if (number == 0) {
                    return -1;
                }
                int index = number - 1;
                if (index < 0 || index >= projectService.getAllProjects().size()) {
                    System.out.println("Ungültige Nummer. Bitte erneut versuchen.");
                    continue;
                }
                return index;
            } catch (NumberFormatException e) {
                System.out.println("Ungültige Eingabe. Bitte erneut versuchen.");
            }
        }
    }

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