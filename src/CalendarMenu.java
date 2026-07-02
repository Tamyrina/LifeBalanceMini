import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class CalendarMenu {

    private CalendarService calendarService;
    private Scanner scanner;
    private LocalDate currentDate;
    private String viewMode;

    public CalendarMenu(CalendarService calendarService) {
        this.calendarService = calendarService;
        this.scanner = new Scanner(System.in);
        this.currentDate = LocalDate.now();
        this.viewMode = "DAY";
    }

    public void show() {
        boolean running = true;
        viewMode = "DAY";
        currentDate = LocalDate.now();

        while (running) {
            System.out.println();
            System.out.println("=== Kalender ===");
            System.out.println("1. Heute anzeigen");
            System.out.println("2. Bestimmten Tag anzeigen");
            System.out.println("3. Aktuelle Woche anzeigen");
            System.out.println("4. Vorherige Woche");
            System.out.println("5. Nächste Woche");
            System.out.println("0. Zurück");
            System.out.print("Auswahl: ");

            String input = scanner.nextLine();

            if (input.equals("1")) {
                currentDate = LocalDate.now();
                showToday();
            } else if (input.equals("2")) {
                showDay();
            } else if (input.equals("3")) {
                viewMode = "WEEK";
                showWeek();
            } else if (input.equals("4")) {
                navigatePreviousWeek();
            } else if (input.equals("5")) {
                navigateNextWeek();
            } else if (input.equals("0")) {
                running = false;
            } else {
                System.out.println("Ungültige Eingabe.");
            }
        }
    }

    private void showToday() {
        System.out.println();
        System.out.println("=== Heute ===");
        String todayString = formatDate(LocalDate.now());
        displayTasksForDate(todayString);
    }

    private void showDay() {
        System.out.print("Datum (TT.MM.JJJJ): ");
        String date = scanner.nextLine();
        System.out.println();
        System.out.println("=== Tag ===");
        displayTasksForDate(date);
    }

    private void showWeek() {
        System.out.println();
        System.out.println("=== Woche ===");
        ArrayList<LocalDate> weekDates = calendarService.getWeekDates(currentDate);
        for (LocalDate date : weekDates) {
            String dateString = formatDate(date);
            System.out.println("--------------------");
            System.out.println(dateString);
            displayTasksForDate(dateString);
        }
    }

    private void navigatePreviousWeek() {
        currentDate = currentDate.minusWeeks(1);
        viewMode = "WEEK";
        showWeek();
    }

    private void navigateNextWeek() {
        currentDate = currentDate.plusWeeks(1);
        viewMode = "WEEK";
        showWeek();
    }

    private void displayTasksForDate(String date) {
        ArrayList<Task> tasks = calendarService.getTasksForDate(date);
        if (tasks.isEmpty()) {
            System.out.println("Keine Aufgaben für diesen Tag geplant.");
        } else {
            for (Task task : tasks) {
                System.out.println(task.getStartTime() + " - " + task.getTitle() + " (" + task.getProject() + ")");
            }
        }
    }

    private String formatDate(LocalDate date) {
        return String.format("%02d.%02d.%04d", 
            date.getDayOfMonth(), 
            date.getMonthValue(), 
            date.getYear());
    }
}