import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class ProjectRepository {

    private String fileName = "projects.txt";

    public void saveProjects(ArrayList<Project> projectList) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Project project : projectList) {
                String line = project.getTitle() + ";" + project.getCategory() + ";" + project.getDueDate() + ";" + project.getDueTime() + ";" + project.isCompleted();
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Fehler beim Speichern der Projekte.");
        }
    }

    public ArrayList<Project> loadProjects() {
        ArrayList<Project> projectList = new ArrayList<>();

        try {
            File file = new File(fileName);
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                String[] parts = line.split(";");

                if (parts.length == 5) {
                    String title = parts[0];
                    String category = parts[1];
                    String dueDate = parts[2];
                    String dueTime = parts[3];
                    boolean completed = Boolean.parseBoolean(parts[4]);

                    Project project = new Project(title, category, dueDate, dueTime);
                    project.setCompleted(completed);

                    projectList.add(project);
                }
            }

            scanner.close();

        } catch (IOException e) {
            System.out.println("Noch keine gespeicherte Projekte-Datei gefunden.");
        }

        return projectList;
    }
}