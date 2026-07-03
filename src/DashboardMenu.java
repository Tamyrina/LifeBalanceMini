import java.util.ArrayList;
import java.util.Scanner;

public class DashboardMenu {
    private DashboardService dashboardService;
    private Scanner scanner;
    private CalendarService calendarService;

    public DashboardMenu(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
        this.scanner = new Scanner(System.in);
    }

    public DashboardMenu(DashboardService dashboardService, CalendarService calendarService) {
        this.dashboardService = dashboardService;
        this.calendarService = calendarService;
        this.scanner = new Scanner(System.in);
    }

    public void show() {
        System.out.println();
        System.out.println("=== Dashboard ===");
        System.out.println();
        showActiveProjects();
        System.out.println();
        showTodaysTasks();
        System.out.println();
        showOverlapWarnings();
    }

    private void showActiveProjects() {
        System.out.println("Aktive Projekte:");
        if (dashboardService.getActiveProjects().isEmpty()) {
            System.out.println("Keine aktiven Projekte.");
        } else {
            for (Project project : dashboardService.getActiveProjects()) {
                System.out.println(project.getTitle());
                int progress = dashboardService.getProjectProgress(project.getTitle());
                String bar = dashboardService.getProgressBar(progress);
                int[] counts = dashboardService.getTaskCountsForProject(project.getTitle());
                System.out.println(bar + " " + counts[1] + "/" + counts[0] + " Aufgaben erledigt");
            }
        }
    }

    private void showTodaysTasks() {
        System.out.println("Heutige Aufgaben:");
        ArrayList<Task> todaysTasks = dashboardService.getTodaysTasks();
        if (todaysTasks.isEmpty()) {
            System.out.println("Keine Aufgaben für heute geplant.");
        } else {
            ArrayList<Task> sortedTasks = new ArrayList<>(todaysTasks);
            sortedTasks.sort((t1, t2) -> {
                int time1 = CalendarService.parseTimeToMinutesStatic(t1.getStartTime());
                int time2 = CalendarService.parseTimeToMinutesStatic(t2.getStartTime());
                return Integer.compare(time1, time2);
            });
            for (Task task : sortedTasks) {
                String endTime = calculateEndTime(task.getStartTime(), task.getEstimatedDuration());
                System.out.println(task.getStartTime() + " - " + endTime + "   " + task.getTitle() + " (" + task.getProject() + ")");
            }
        }
    }

    private void showOverlapWarnings() {
        if (calendarService != null) {
            ArrayList<Task> todaysTasks = dashboardService.getTodaysTasks();
            ArrayList<String> warnings = CalendarService.detectOverlapsStatic(todaysTasks);
            if (!warnings.isEmpty()) {
                System.out.println();
                System.out.println("Hinweis:");
                for (String warning : warnings) {
                    System.out.println(warning);
                }
            }
        }
    }

    private String calculateEndTime(String startTime, int estimatedDuration) {
        String[] parts = startTime.split(":");
        int hour = Integer.parseInt(parts[0]);
        int minute = Integer.parseInt(parts[1]);
        
        int totalMinutes = hour * 60 + minute + estimatedDuration;
        int endHour = totalMinutes / 60;
        int endMinute = totalMinutes % 60;
        
        return String.format("%02d:%02d", endHour, endMinute);
    }
}