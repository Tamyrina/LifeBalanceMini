public class Main {

    public static void main(String[] args) {

        TaskService taskService = new TaskService();
        ProjectService projectService = new ProjectService();

        ConsoleMenu menu = new ConsoleMenu(taskService, projectService);

        menu.start();

    }
}