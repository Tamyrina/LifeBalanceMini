// Startklasse der Anwendung.
// Erstellt die benötigten Objekte und startet das Hauptmenü.
public class Main {

    // Einstiegspunkt des Programms.
    public static void main(String[] args) {

        TaskService taskService = new TaskService();
        ProjectService projectService = new ProjectService();
        // Erstellt das Hauptmenü und übergibt die benötigten Services.
        ConsoleMenu menu = new ConsoleMenu(taskService, projectService);
        // Startet das Programm.
        menu.start();

    }
}