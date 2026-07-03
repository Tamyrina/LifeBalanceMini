import java.time.LocalDate;
import java.util.ArrayList;

public class DashboardService {

    private TaskService taskService;
    private ProjectService projectService;

    public DashboardService(TaskService taskService, ProjectService projectService) {
        this.taskService = taskService;
        this.projectService = projectService;
    }

    public ArrayList<Project> getActiveProjects() {
        ArrayList<Project> active = new ArrayList<>();
        for (Project project : projectService.getAllProjects()) {
            if (!project.isCompleted()) {
                active.add(project);
            }
        }
        return active;
    }

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

    public int getProjectProgress(String projectTitle) {
        int[] counts = getTaskCountsForProject(projectTitle);
        int total = counts[0];
        int completed = counts[1];
        return total == 0 ? 0 : (completed * 100 / total);
    }

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