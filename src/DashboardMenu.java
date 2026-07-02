import java.util.Scanner;

public class DashboardMenu {

    private DashboardService dashboardService;
    private Scanner scanner;

    public DashboardMenu(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
        this.scanner = new Scanner(System.in);
    }

    public void show() {
        boolean running = true;

        while (running) {
            System.out.println();
            System.out.println("=== Dashboard ===");
            System.out.println();
            showActiveProjects();
            System.out.println();
            showTodaysTasks();

            System.out.println();
            System.out.println("0. Zurück");
            System.out.print("Auswahl: ");

            String input = scanner.nextLine();
            if (input.equals("0")) {
                running = false;
            } else {
                System.out.println("Ungültige Eingabe.");
            }
        }
    }

    private void showActiveProjects() {
        System.out.println("Aktive Projekte:");
        if (dashboardService.getActiveProjects().isEmpty()) {
            System.out.println("Keine aktiven Projekte.");
        } else {
            for (Project project : dashboardService.getActiveProjects()) {
                int progress = dashboardService.getProjectProgress(project.getTitle());
                String bar = dashboardService.getProgressBar(progress);
                System.out.println(project.getTitle());
                System.out.println(bar);
            }
        }
    }

    private void showTodaysTasks() {
        System.out.println("Heutige Aufgaben:");
        if (dashboardService.getTodaysTasks().isEmpty()) {
            System.out.println("Keine Aufgaben für heute geplant.");
        } else {
            for (Task task : dashboardService.getTodaysTasks()) {
                System.out.println(task.getStartTime() + " " + task.getTitle() + " (" + task.getProject() + ")");
            }
        }
    }
}