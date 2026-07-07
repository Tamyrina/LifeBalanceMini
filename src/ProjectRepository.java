// Verwaltet das Speichern und Laden der Projekte.
// Stellt die Verbindung zwischen der Anwendung und der gespeicherten Datei her.
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class ProjectRepository {
    // Dateiname, unter dem die Projekte gespeichert werden.
    private String fileName = "projects.txt";
    // Speichert die Liste der Projekte in einer Datei. 
    // Jede Projekt wird in einer neuen Zeile gespeichert, wobei die Attribute durch Semikolons getrennt sind.
    public void saveProjects(ArrayList<Project> projectList) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Project project : projectList) {
                String line = project.getTitle() + ";" + project.getCategory() + ";" + project.getDueDate() + ";" + project.getDueTime() + ";" + project.isCompleted();
                writer.write(line);
                writer.newLine();
            }
        } 
        // Fängt IOExceptions ab, die beim Schreiben in die Datei auftreten können, und gibt eine Fehlermeldung aus.
        catch (IOException e) {
            System.out.println("Fehler beim Speichern der Projekte.");
        }
    }
    // Lädt die Liste der Projekte aus der Datei.
    // Jede Zeile wird in ein Project-Objekt umgewandelt und zur Liste hinzugefügt.
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