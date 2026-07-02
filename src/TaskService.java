import java.util.ArrayList;

public class TaskService {

    private ArrayList<Task> taskList;
    private TaskRepository repository;

    public TaskService() {
        repository = new TaskRepository();
        taskList = repository.loadTasks();
    }

    public void addTask(Task task) {
        taskList.add(task);
        repository.saveTasks(taskList);
    }

    public ArrayList<Task> getAllTasks() {
        return taskList;
    }

    public void updateTask(int index, String newTitle, String newDueDate, String newStartTime, int newEstimatedDuration, String newRepeat, String newProject) {
        if (index >= 0 && index < taskList.size()) {
            Task task = taskList.get(index);

            task.setTitle(newTitle);
            task.setDueDate(newDueDate);
            task.setStartTime(newStartTime);
            task.setEstimatedDuration(newEstimatedDuration);
            task.setRepeat(newRepeat);
            task.setProject(newProject);

            repository.saveTasks(taskList);
        }
    }

    public void moveTask(int index, String newDueDate, String newStartTime) {
        if (index >= 0 && index < taskList.size()) {
            Task task = taskList.get(index);
            task.setDueDate(newDueDate);
            task.setStartTime(newStartTime);
            repository.saveTasks(taskList);
        }
    }

    public void changeDuration(int index, int newEstimatedDuration) {
        if (index >= 0 && index < taskList.size()) {
            Task task = taskList.get(index);
            task.setEstimatedDuration(newEstimatedDuration);
            repository.saveTasks(taskList);
        }
    }

    public void markTaskAsCompleted(int index) {
        if (index >= 0 && index < taskList.size()) {
            taskList.get(index).setCompleted(true);
            repository.saveTasks(taskList);
        }
    }

    public void removeTask(int index) {
        if (index >= 0 && index < taskList.size()) {
            taskList.remove(index);
            repository.saveTasks(taskList);
        }
    }
}