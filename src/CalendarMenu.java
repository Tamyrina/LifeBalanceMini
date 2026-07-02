import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class CalendarMenu {

    private CalendarService calendarService;
    private Scanner scanner;

    public CalendarMenu(CalendarService calendarService) {
        this.calendarService = calendarService;
        this.scanner = new Scanner(System.in);
    }

    public void show() {
        boolean running = true;

        while (running) {
            System.out.println();
            System.out.println("=== Kalender ===");
            System.out.println("1. Heute anzeigen");
            System.out.println("2. Ausgewählten Tag anzeigen");
            System.out.println("3. Aktuelle Woche anzeigen");
            System.out.println("0. Zurück");
            System.out.print("Auswahl: ");

            String input = scanner.nextLine();

            if (input.equals("1")) {
                showToday();
            } else if (input.equals("2")) {
                showDay();
            } else if (input.equals("3")) {
                showCurrentWeek();
            } else if (input.equals("0")) {
                running = false;
            } else {
                System.out.println("Ungültige Eingabe.");
            }
        }
    }

    private void showToday() {
        System.out.println();
        System.out.println(calendarService.getTasksForToday().isEmpty() ? 
            "Keine Aufgaben für heute geplant." : 
            "Heutige Aufgaben werden angezeigt.");
        
        for (Task task : calendarService.getTasksForToday()) {
            System.out.println(task);
        }
    }

    private void showDay() {
        System.out.print("Datum (TT.MM.JJJJ): ");
        String date = scanner.nextLine();
        System.out.println();
        
        if (calendarService.getTasksForDate(date).isEmpty()) {
            System.out.println("Keine Aufgaben für diesen Tag geplant.");
        } else {
            for (Task task : calendarService.getTasksForDate(date)) {
                System.out.println(task);
            }
        }
    }

    private void showCurrentWeek() {
    System.out.println();
    System.out.println("=== Aktuelle Woche ===");
    
    ArrayList<LocalDate> weekDates = calendarService.getWeekDatesForCurrentWeek();
    for (LocalDate date : weekDates) {
        String dateString = String.format("%02d.%02d.%04d", 
            date.getDayOfMonth(),
            date.getMonthValue(),
            date.getYear());
        System.out.println("--------------------");
        System.out.println(dateString);
        for (Task task : calendarService.getTasksForDate(dateString)) {
            System.out.println(task.getTitle() + " (" + task.getStartTime() + ")");
        }
    }
}
}