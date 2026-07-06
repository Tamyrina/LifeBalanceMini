// Stellt alle Daten für das Dashboard bereit.
// Berechnet Statistiken, Fortschritt und die heutigen Aufgaben.
import java.time.LocalDate;
import java.util.ArrayList;

public class DashboardService {

    private TaskService taskService;
    private ProjectService projectService;

    public DashboardService(TaskService taskService, ProjectService projectService) {
        this.taskService = taskService;
        this.projectService = projectService;
    }
    // Gibt eine Liste aller aktiven Projekte zurück, d.h. Projekte, die noch nicht abgeschlossen sind.
    public ArrayList<Project> getActiveProjects() {
        ArrayList<Project> active = new ArrayList<>();
        for (Project project : projectService.getAllProjects()) {
            if (!project.isCompleted()) {
                active.add(project);
            }
        }
        return active;
    }
    // Gibt eine Liste aller Aufgaben zurück, die heute fällig sind.
    public ArrayList<Task> getTodaysTasks() {
        ArrayList<Task> todaysTasks = new ArrayList<>();
        LocalDate today = LocalDate.now();
        String todayString = String.format("%02d.%02d.%04d", 
            today.getDayOfMonth(), 
            today.getMonthValue(), 
            today.getYear());

        for (Task task : taskService.getAllTasks()) {
            if (task.getDueDate().equals(todayString)) {
                todaysTasks.add(task);
            }
        }
        return todaysTasks;
    }
    // Gibt den Projektfortschritt in Prozent zurück, basierend auf der Anzahl der abgeschlossenen Aufgaben im Verhältnis zur Gesamtzahl der Aufgaben im Projekt.
    public int getProjectProgress(String projectTitle) {
        int[] counts = getTaskCountsForProject(projectTitle);
        int total = counts[0];
        int completed = counts[1];
             if (total == 0) {
                return 0;
             } 
                return completed * 100 / total;
         }
    // Gibt die Anzahl der offenen und der abgeschlossenen Aufgaben für ein bestimmtes Projekt zurück.
    public int[] getTaskCountsForProject(String projectTitle) {
        int total = 0;
        int completed = 0;
        for (Task task : taskService.getAllTasks()) {
            if (task.getProject().equals(projectTitle)) {
                total++;
                if (task.isCompleted()) {
                    completed++;
                }
            }
        }
        return new int[]{total, completed};
    }
    // Erstellt eine Fortschrittsanzeige (Progress Bar) basierend auf dem übergebenen Fortschrittswert (0-100).
    public String getProgressBar(int progress) {
        StringBuilder bar = new StringBuilder();
        int filled = progress / 10;
        for (int i = 0; i < filled; i++) {
            bar.append("█");
        }
        for (int i = filled; i < 10; i++) {
            bar.append("░");
        }
        return bar.toString();
    }
}