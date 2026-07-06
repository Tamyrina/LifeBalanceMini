// TaskService verwaltet die Aufgabenliste. 
// Er enthält die Logik zum Hinzufügen, Bearbeiten, Verschieben, Abschließen und Löschen von Aufgaben. 
// Nach jeder Änderung speichert er die aktuelle Liste über das TaskRepository.
import java.util.ArrayList;

public class TaskService {

    private ArrayList<Task> taskList;
    private TaskRepository repository;

    // Erstellt den TaskService und lädt beim Programmstart
    // alle bereits gespeicherten Aufgaben aus dem Repository.
    public TaskService() {
        repository = new TaskRepository();
        taskList = repository.loadTasks();
    }
    // Fügt eine neue Aufgabe hinzu und speichert sie über das Repository.
    public void addTask(Task task) {
        taskList.add(task);
        repository.saveTasks(taskList);
    }
    // Gibt die Liste aller Aufgaben zurück.
    public ArrayList<Task> getAllTasks() {
        return taskList;
    }
    // Gibt die Liste aller Aufgaben für ein bestimmtes Projekt zurück.
    public ArrayList<Task> getTasksForProject(String projectName) {
        ArrayList<Task> projectTasks = new ArrayList<>();
        for (Task task : taskList) {
            if (task.getProject().equals(projectName)) {
                projectTasks.add(task);
            }
        }
        return projectTasks;
    }
    // Gibt die Liste aller Aufgaben für ein bestimmtes Datum zurück.
    public ArrayList<Task> getTasksForDate(String dueDate) {
        ArrayList<Task> dateTasks = new ArrayList<>();
        for (Task task : taskList) {
            if (task.getDueDate().equals(dueDate)) {
                dateTasks.add(task);
            }
        }
        return dateTasks;
    }
    // Aktualisiert eine bestehende Aufgabe.
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
    // Verschiebt eine Aufgabe auf ein neues Fälligkeitsdatum und eine neue Startzeit.
    public void moveTask(int index, String newDueDate, String newStartTime) {
        if (index >= 0 && index < taskList.size()) {
            Task task = taskList.get(index);
            task.setDueDate(newDueDate);
            task.setStartTime(newStartTime);
            repository.saveTasks(taskList);
        }
    }
    // Ändert die geschätzte Dauer einer Aufgabe.
    public void changeDuration(int index, int newEstimatedDuration) {
        if (index >= 0 && index < taskList.size()) {
            Task task = taskList.get(index);
            task.setEstimatedDuration(newEstimatedDuration);
            repository.saveTasks(taskList);
        }
    }
    // Markiert eine Aufgabe als abgeschlossen.
    public void markTaskAsCompleted(int index) {
        if (index >= 0 && index < taskList.size()) {
            taskList.get(index).setCompleted(true);
            repository.saveTasks(taskList);
        }
    }
    // Löscht eine Aufgabe aus der Liste und speichert die Änderungen im Repository.
    public void removeTask(int index) {
        if (index >= 0 && index < taskList.size()) {
            taskList.remove(index);
            repository.saveTasks(taskList);
        }
    }
}