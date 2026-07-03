import java.util.Scanner;

public class ConsoleMenu {

    private TaskService taskService;
    private ProjectService projectService;
    private Scanner scanner;
    private boolean dashboardShown = false;

    public ConsoleMenu(TaskService taskService, ProjectService projectService) {
        this.taskService = taskService;
        this.projectService = projectService;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        boolean running = true;

        while (running) {
            // Dashboard nur beim ersten Start anzeigen
            if (!dashboardShown) {
                CalendarService calendarService = new CalendarService(taskService, projectService);
                DashboardService dashboardService = new DashboardService(taskService, projectService);
                DashboardMenu dashboardMenu = new DashboardMenu(dashboardService, calendarService);
                dashboardMenu.show();
                dashboardShown = true;
            }

            // Navigationsmenü
            System.out.println();
            System.out.println("=== Navigation ===");
            System.out.println("1. Projekte");
            System.out.println("2. Aufgaben");
            System.out.println("3. Kalender");
            System.out.println("0. Beenden");
            System.out.print("Auswahl: ");

            String input = scanner.nextLine();

            if (input.equals("1")) {
                showProjectMenu();
            } else if (input.equals("2")) {
                showTaskMenu();
            } else if (input.equals("3")) {
                showCalendar();
            } else if (input.equals("0")) {
                running = false;
                System.out.println("Programm beendet.");
            } else {
                System.out.println("Ungültige Eingabe.");
            }
        }
    }

    private void showProjectMenu() {
        ProjectMenu projectMenu = new ProjectMenu(projectService);
        projectMenu.show();
    }

    private void showTaskMenu() {
        TaskMenu taskMenu = new TaskMenu(taskService);
        taskMenu.show();
    }

    private void showCalendar() {
        CalendarService calendarService = new CalendarService(taskService, projectService);
        CalendarMenu calendarMenu = new CalendarMenu(calendarService);
        calendarMenu.show();
    }
}