import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class TaskRepository {

    private String fileName = "tasks.txt";

    public void saveTasks(ArrayList<Task> taskList) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Task task : taskList) {
                String line = task.getTitle() + ";" + task.getDueDate() + ";" + task.getStartTime() + ";" + task.getEstimatedDuration() + ";" + task.getRepeat() + ";" + task.getProject() + ";" + task.isCompleted();
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Fehler beim Speichern der Aufgaben.");
        }
    }

    public ArrayList<Task> loadTasks() {
        ArrayList<Task> taskList = new ArrayList<>();

        try {
            File file = new File(fileName);
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                String[] parts = line.split(";");

if (parts.length == 7) {
                    String title = parts[0];
                    String dueDate = parts[1];
                    String startTime = parts[2];
                    int estimatedDuration = Integer.parseInt(parts[3]);
                    String repeat = parts[4];
                    String project = parts[5];
                    boolean completed = Boolean.parseBoolean(parts[6]);

                    Task task = new Task(title, dueDate, startTime, estimatedDuration, repeat, project);
                    task.setCompleted(completed);

                    taskList.add(task);
                }
            }

            scanner.close();

        } catch (IOException e) {
            System.out.println("Noch keine gespeicherte Aufgaben-Datei gefunden.");
        }

return taskList;
    }
}

