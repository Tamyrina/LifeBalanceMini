import java.util.ArrayList;

public class ProjectService {

    private ArrayList<Project> projectList;
    private ProjectRepository repository;

    public ProjectService() {
        repository = new ProjectRepository();
        projectList = repository.loadProjects();
    }

    public void addProject(Project project) {
        projectList.add(project);
        repository.saveProjects(projectList);
    }

    public ArrayList<Project> getAllProjects() {
        return projectList;
    }

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

    public void removeProject(int index) {
        if (index >= 0 && index < projectList.size()) {
            projectList.remove(index);
            repository.saveProjects(projectList);
        }
    }

    public int getCompletedTaskCount(int projectIndex) {
        return 0;
    }

    public int getTotalTaskCount(int projectIndex) {
        return 0;
    }

    public int getProgress(int projectIndex) {
        return 0;
    }
}