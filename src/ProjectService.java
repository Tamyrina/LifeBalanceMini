// ProjectService verwaltet die Projekte. 
// Er enthält die Logik zum Hinzufügen, Bearbeiten, Löschen von Projekten. 
// Nach jeder Änderung speichert er die aktuelle Liste über das ProjectRepository.
import java.util.ArrayList;

public class ProjectService {
    // Erstellt den ProjectService und lädt beim Programmstart
    // alle bereits gespeicherten Projekte aus dem Repository.
    private ArrayList<Project> projectList;
    private ProjectRepository repository;
    
    public ProjectService() {
        repository = new ProjectRepository();
        projectList = repository.loadProjects();
    }
    // Fügt ein neues Projekt hinzu und speichert es über das Repository.
    public void addProject(Project project) {
        projectList.add(project);
        repository.saveProjects(projectList);
    }
    // Gibt die Liste aller Projekte zurück.
    public ArrayList<Project> getAllProjects() {
        return projectList;
    }
    // Aktualisiert ein bestehendes Projekt.
    public void updateProject(int index, String newTitle, String newCategory, String newDueDate, String newDueTime) {
        if (index >= 0 && index < projectList.size()) {
            Project project = projectList.get(index);

            project.setTitle(newTitle);
            project.setCategory(newCategory);
            project.setDueDate(newDueDate);
            project.setDueTime(newDueTime);

            repository.saveProjects(projectList);
        }
    }
    // Löscht ein Projekt aus der Liste und speichert die Änderungen im Repository.
    public void removeProject(int index) {
        if (index >= 0 && index < projectList.size()) {
            projectList.remove(index);
            repository.saveProjects(projectList);
        }
    }
}