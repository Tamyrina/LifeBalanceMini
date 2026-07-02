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

        System.out.print("Fälligkeitsdatum: ");
        String dueDate = scanner.nextLine();

        System.out.print("Fälligkeitszeit: ");
        String dueTime = scanner.nextLine();

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

        System.out.print("Welches Projekt soll gelöscht werden? Nummer eingeben: ");
        int number = Integer.parseInt(scanner.nextLine());

        int index = number - 1;
        projectService.removeProject(index);

        System.out.println("Projekt " + number + " wurde gelöscht.");
    }

    private void editProject() {
        System.out.println();
        System.out.println("Projekt bearbeiten");

        if (projectService.getAllProjects().isEmpty()) {
            System.out.println("Es gibt keine Projekte.");
            return;
        }

        showProjects();

        System.out.print("Welches Projekt soll bearbeitet werden? Nummer eingeben: ");
        int number = Integer.parseInt(scanner.nextLine());

        System.out.print("Neuer Titel: ");
        String newTitle = scanner.nextLine();

        System.out.print("Neue Kategorie: ");
        String newCategory = scanner.nextLine();

        System.out.print("Neues Fälligkeitsdatum: ");
        String newDueDate = scanner.nextLine();

        System.out.print("Neue Fälligkeitszeit: ");
        String newDueTime = scanner.nextLine();

        int index = number - 1;
        projectService.updateProject(index, newTitle, newCategory, newDueDate, newDueTime);

        System.out.println("Projekt " + number + " wurde bearbeitet.");
    }
}