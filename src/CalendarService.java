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
    public ArrayList<Task> getTasksForWeek(String startDate) {
    ArrayList<Task> weekTasks = new ArrayList<>();
    LocalDate date = LocalDate.parse(startDate.replace(".", "-"));
    LocalDate startOfWeek = date.minusDays(date.getDayOfWeek().getValue() - 1);
    
    for (int i = 0; i < 7; i++) {
        LocalDate currentDay = startOfWeek.plusDays(i);
        String dayString = String.format("%02d.%02d.%04d", 
            currentDay.getDayOfMonth(),
            currentDay.getMonthValue(),
            currentDay.getYear());
        weekTasks.addAll(getTasksForDate(dayString));
    }
    return weekTasks;
}

public ArrayList<LocalDate> getWeekDatesForCurrentWeek() {
    ArrayList<LocalDate> weekDates = new ArrayList<>();
    LocalDate today = LocalDate.now();
    LocalDate startOfWeek = today.minusDays(today.getDayOfWeek().getValue() - 1);
    for (int i = 0; i < 7; i++) {
        weekDates.add(startOfWeek.plusDays(i));
    }
    return weekDates;
}

public ArrayList<Task> getTasksForWeekForCurrentWeek() {
    ArrayList<Task> weekTasks = new ArrayList<>();
    for (LocalDate date : getWeekDatesForCurrentWeek()) {
        String dayString = String.format("%02d.%02d.%04d", 
            date.getDayOfMonth(),
            date.getMonthValue(),
            date.getYear());
        weekTasks.addAll(getTasksForDate(dayString));
    }
    return weekTasks;
}
}