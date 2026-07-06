// Hauptmenü der Anwendung.
// Zeigt das Dashboard und die Navigation an
// und öffnet die einzelnen Menüs.
import java.util.Scanner;

public class ConsoleMenu {
    
    private TaskService taskService;
    private ProjectService projectService;
    private Scanner scanner;
    // Speichert die benötigten Services und erstellt einen Scanner für die Benutzereingabe.
    public ConsoleMenu(TaskService taskService, ProjectService projectService) {
        this.taskService = taskService;
        this.projectService = projectService;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        // Erstellt die benötigten Services und zeigt
        // beim Programmstart einmalig das Dashboard an.
        CalendarService calendarService = new CalendarService(taskService, projectService);
        DashboardService dashboardService = new DashboardService(taskService, projectService);
        DashboardMenu dashboardMenu = new DashboardMenu(dashboardService, calendarService);
        dashboardMenu.show();

        boolean running = true;

        while (running) {
            // Zeigt das Hauptnavigationsmenü an.
            System.out.println();
            System.out.println("=== Navigation ===");
            System.out.println("1. Dashboard");
            System.out.println("2. Projekte");
            System.out.println("3. Aufgaben");
            System.out.println("4. Kalender");
            System.out.println("0. Beenden");
            System.out.print("Auswahl: ");
            // Liest die Auswahl des Benutzers ein.
            String input = scanner.nextLine();
            // Benutzereingabe auswerten und entsprechende Methode aufrufen
            if (input.equals("1")) {
                showDashboard();
            } else if (input.equals("2")) {
                showProjectMenu();
            } else if (input.equals("3")) {
                showTaskMenu();
            } else if (input.equals("4")) {
                showCalendar();
            } else if (input.equals("0")) {
                running = false;
                System.out.println("Programm beendet.");
            } else {
                System.out.println("Ungültige Eingabe.");
            }
        }
    }
    // Erstellt das Dashboard und zeigt die aktuelle Übersicht an.
    private void showDashboard() {
        CalendarService calendarService = new CalendarService(taskService, projectService);
        DashboardService dashboardService = new DashboardService(taskService, projectService);
        DashboardMenu dashboardMenu = new DashboardMenu(dashboardService, calendarService);
        dashboardMenu.show();
    }
    // Methode zum Anzeigen des Projektmenüs
    private void showProjectMenu() {
        ProjectMenu projectMenu = new ProjectMenu(projectService, taskService);
        projectMenu.show();
    }
    // Methode zum Anzeigen des Aufgabenmenüs
    private void showTaskMenu() {
        TaskMenu taskMenu = new TaskMenu(taskService, projectService);
        taskMenu.show();
    }
    // Methode zum Anzeigen des Kalendermenüs
    private void showCalendar() {
        CalendarService calendarService = new CalendarService(taskService, projectService);
        CalendarMenu calendarMenu = new CalendarMenu(calendarService);
        calendarMenu.show();
    }
}