import java.time.LocalDate;
import java.util.ArrayList;

public class CalendarService {

    private TaskService taskService;
    private ProjectService projectService;

    public CalendarService(TaskService taskService, ProjectService projectService) {
        this.taskService = taskService;
        this.projectService = projectService;
    }

    public ArrayList<Task> getTasksForToday() {
        LocalDate today = LocalDate.now();
        String todayString = String.format("%02d.%02d.%04d", 
            today.getDayOfMonth(), 
            today.getMonthValue(), 
            today.getYear());

        ArrayList<Task> todaysTasks = new ArrayList<>();
        for (Task task : taskService.getAllTasks()) {
            if (task.getDueDate().equals(todayString)) {
                todaysTasks.add(task);
            }
        }
        return todaysTasks;
    }

    public ArrayList<Task> getTasksForDate(String date) {
        ArrayList<Task> tasksForDate = new ArrayList<>();
        for (Task task : taskService.getAllTasks()) {
            if (task.getDueDate().equals(date)) {
                tasksForDate.add(task);
            }
        }
        return tasksForDate;
    }

    public ArrayList<LocalDate> getWeekDates(LocalDate date) {
        ArrayList<LocalDate> weekDates = new ArrayList<>();
        LocalDate startOfWeek = date.minusDays(date.getDayOfWeek().getValue() - 1);
        for (int i = 0; i < 7; i++) {
            weekDates.add(startOfWeek.plusDays(i));
        }
        return weekDates;
    }
}