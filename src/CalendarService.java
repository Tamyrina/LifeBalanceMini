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

    public ArrayList<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    public void moveTask(int index, String newDueDate, String newStartTime) {
        taskService.moveTask(index, newDueDate, newStartTime);
    }

    public void changeDuration(int index, int newEstimatedDuration) {
        taskService.changeDuration(index, newEstimatedDuration);
    }

    public ArrayList<String> detectOverlaps(ArrayList<Task> tasks) {
        return detectOverlapsStatic(tasks);
    }

    public static ArrayList<String> detectOverlapsStatic(ArrayList<Task> tasks) {
        ArrayList<String> warnings = new ArrayList<>();
        
        for (int i = 0; i < tasks.size(); i++) {
            Task task1 = tasks.get(i);
            if (task1.getDueDate() == null) continue;
            int start1 = parseTimeToMinutes(task1.getStartTime());
            int end1 = start1 + task1.getEstimatedDuration();
            
            for (int j = i + 1; j < tasks.size(); j++) {
                Task task2 = tasks.get(j);
                // Nur Tasks am selben Tag vergleichen
                if (!task1.getDueDate().equals(task2.getDueDate())) {
                    continue;
                }
                
                int start2 = parseTimeToMinutes(task2.getStartTime());
                int end2 = start2 + task2.getEstimatedDuration();
                
                if (timesOverlap(start1, end1, start2, end2)) {
                    warnings.add("\"" + task1.getTitle() + "\" überschneidet sich mit \"" + task2.getTitle() + "\"");
                }
            }
        }
        
        return warnings;
    }

    public static int parseTimeToMinutesStatic(String time) {
        String[] parts = time.split(":");
        return Integer.parseInt(parts[0]) * 60 + Integer.parseInt(parts[1]);
    }

    private static int parseTimeToMinutes(String time) {
        return parseTimeToMinutesStatic(time);
    }

    private static boolean timesOverlap(int start1, int end1, int start2, int end2) {
        return start1 < end2 && start2 < end1;
    }
}