import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class CalendarMenu {

    private CalendarService calendarService;
    private Scanner scanner;
    private LocalDate currentDate;
    private String viewMode;
    private ArrayList<Task> lastShownTasks;

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
            System.out.println("0. Zurück");
            System.out.print("Auswahl: ");

            String input = scanner.nextLine();

            if (input.equals("1")) {
                currentDate = LocalDate.now();
                viewMode = "DAY";
                showToday();
            } else if (input.equals("2")) {
                showDay();
            } else if (input.equals("3")) {
                viewMode = "WEEK";
                showWeek();
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
        lastShownTasks = calendarService.getTasksForDate(todayString);
        displayTasks(lastShownTasks);
        showOverlapWarnings(lastShownTasks);
        showActionMenu();
    }

    private void showDay() {
        System.out.print("Datum (TT.MM.JJJJ): ");
        String date = scanner.nextLine();
        
        if (!isValidDate(date)) {
            System.out.println("Ungültiges Datum. Bitte im Format TT.MM.JJJJ eingeben.");
            showDay();
            return;
        }
        
        System.out.println();
        System.out.println("=== Tag ===");
        viewMode = "DAY";
        currentDate = parseDate(date);
        lastShownTasks = calendarService.getTasksForDate(date);
        displayTasks(lastShownTasks);
        showOverlapWarnings(lastShownTasks);
        showActionMenu();
    }

    private void showWeek() {
        System.out.println();
        System.out.println("=== Woche ===");
        ArrayList<LocalDate> weekDates = calendarService.getWeekDates(currentDate);
        lastShownTasks = new ArrayList<>();
        for (LocalDate date : weekDates) {
            String dateString = formatDate(date);
            System.out.println("--------------------");
            System.out.println(dateString);
            ArrayList<Task> dayTasks = calendarService.getTasksForDate(dateString);
            displayTasks(dayTasks);
            lastShownTasks.addAll(dayTasks);
        }
        showOverlapWarnings(lastShownTasks);
        showActionMenu();
    }

    private void showActionMenu() {
        boolean running = true;
        while (running) {
            System.out.println();
            if (viewMode.equals("DAY")) {
                System.out.println("=== Tag Aktionen ===");
                System.out.println("1. Vorheriger Tag");
                System.out.println("2. Nächster Tag");
            } else {
                System.out.println("=== Woche Aktionen ===");
                System.out.println("1. Vorherige Woche");
                System.out.println("2. Nächste Woche");
            }
            System.out.println("3. Aufgabe verschieben");
            System.out.println("4. Aufgabe Dauer ändern");
            System.out.println("0. Zurück zum Kalender");
            System.out.print("Auswahl: ");

            String input = scanner.nextLine();

            if (input.equals("1")) {
                navigatePrevious();
            } else if (input.equals("2")) {
                navigateNext();
            } else if (input.equals("3")) {
                moveTask();
            } else if (input.equals("4")) {
                changeDuration();
            } else if (input.equals("0")) {
                running = false;
            } else {
                System.out.println("Ungültige Eingabe.");
            }
        }
    }

    private void navigatePrevious() {
        if (viewMode.equals("DAY")) {
            currentDate = currentDate.minusDays(1);
            showDayWithCurrentDate();
        } else {
            currentDate = currentDate.minusWeeks(1);
            showWeek();
        }
    }

    private void navigateNext() {
        if (viewMode.equals("DAY")) {
            currentDate = currentDate.plusDays(1);
            showDayWithCurrentDate();
        } else {
            currentDate = currentDate.plusWeeks(1);
            showWeek();
        }
    }

    private void showDayWithCurrentDate() {
        String dateString = formatDate(currentDate);
        System.out.println();
        System.out.println("=== Tag ===");
        lastShownTasks = calendarService.getTasksForDate(dateString);
        displayTasks(lastShownTasks);
        showOverlapWarnings(lastShownTasks);
        showActionMenu();
    }

    private void moveTask() {
        System.out.println();
        System.out.println("=== Aufgabe verschieben ===");
        
        if (lastShownTasks == null || lastShownTasks.isEmpty()) {
            System.out.println("Keine Aufgaben angezeigt.");
            return;
        }
        
        for (int i = 0; i < lastShownTasks.size(); i++) {
            Task t = lastShownTasks.get(i);
            System.out.println((i + 1) + ". " + t.getTitle() + " (" + t.getDueDate() + ")");
        }
        System.out.println("0. Zurück");
        
        System.out.print("Welche Aufgabe verschieben? Nummer: ");
        String input = scanner.nextLine();
        
        if (input.equals("0")) {
            return;
        }
        
        try {
            int number = Integer.parseInt(input);
            int index = number - 1;
            
            if (index < 0 || index >= lastShownTasks.size()) {
                System.out.println("Ungültige Auswahl. Bitte erneut versuchen.");
                moveTask();
                return;
            }
            
            Task taskToMove = lastShownTasks.get(index);
            int taskListIndex = calendarService.getAllTasks().indexOf(taskToMove);
            
            String newDueDate = readValidDate("Neues Datum (TT.MM.JJJJ): ");
            
            String newStartTime = readValidTime("Neue Startzeit (HH:MM): ");
            
            calendarService.moveTask(taskListIndex, newDueDate, newStartTime);
            System.out.println("Aufgabe verschoben.");
            
            if (viewMode.equals("DAY")) {
                showDayWithCurrentDate();
            } else {
                showWeek();
            }
        } catch (NumberFormatException e) {
            System.out.println("Ungültige Eingabe. Bitte erneut versuchen.");
            moveTask();
        }
    }

    private void changeDuration() {
        System.out.println();
        System.out.println("=== Aufgabe Dauer ändern ===");
        
        if (lastShownTasks == null || lastShownTasks.isEmpty()) {
            System.out.println("Keine Aufgaben angezeigt.");
            return;
        }
        
        for (int i = 0; i < lastShownTasks.size(); i++) {
            Task t = lastShownTasks.get(i);
            System.out.println((i + 1) + ". " + t.getTitle() + " (Dauer: " + t.getEstimatedDuration() + " min)");
        }
        System.out.println("0. Zurück");
        
        System.out.print("Welche Aufgabe ändern? Nummer: ");
        String input = scanner.nextLine();
        
        if (input.equals("0")) {
            return;
        }
        
        try {
            int number = Integer.parseInt(input);
            int index = number - 1;
            
            if (index < 0 || index >= lastShownTasks.size()) {
                System.out.println("Ungültige Auswahl. Bitte erneut versuchen.");
                changeDuration();
                return;
            }
            
            Task taskToChange = lastShownTasks.get(index);
            int taskListIndex = calendarService.getAllTasks().indexOf(taskToChange);
            
            int newDuration = readPositiveInt("Neue Dauer (Minuten): ");
            
            calendarService.changeDuration(taskListIndex, newDuration);
            System.out.println("Dauer geändert.");
            
            if (viewMode.equals("DAY")) {
                showDayWithCurrentDate();
            } else {
                showWeek();
            }
        } catch (NumberFormatException e) {
            System.out.println("Ungültige Eingabe. Bitte erneut versuchen.");
            changeDuration();
        }
    }

    private void displayTasks(ArrayList<Task> tasks) {
        if (tasks.isEmpty()) {
            System.out.println("Keine Aufgaben geplant.");
        } else {
            for (Task task : tasks) {
                String endTime = calculateEndTime(task.getStartTime(), task.getEstimatedDuration());
                System.out.println(task.getStartTime() + " - " + endTime + "   " + task.getTitle() + " (" + task.getProject() + ")");
            }
        }
    }

    private void showOverlapWarnings(ArrayList<Task> tasks) {
        ArrayList<String> warnings = calendarService.detectOverlaps(tasks);
        if (!warnings.isEmpty()) {
            System.out.println();
            System.out.println("Hinweis:");
            for (String warning : warnings) {
                System.out.println(warning);
            }
        }
    }

    private String calculateEndTime(String startTime, int estimatedDuration) {
        String[] parts = startTime.split(":");
        int hour = Integer.parseInt(parts[0]);
        int minute = Integer.parseInt(parts[1]);
        
        int totalMinutes = hour * 60 + minute + estimatedDuration;
        int endHour = totalMinutes / 60;
        int endMinute = totalMinutes % 60;
        
        return String.format("%02d:%02d", endHour, endMinute);
    }

    private String formatDate(LocalDate date) {
        return String.format("%02d.%02d.%04d", 
            date.getDayOfMonth(), 
            date.getMonthValue(), 
            date.getYear());
    }

    private LocalDate parseDate(String date) {
        String[] parts = date.split("\\.");
        return LocalDate.of(Integer.parseInt(parts[2]), Integer.parseInt(parts[1]), Integer.parseInt(parts[0]));
    }

    private boolean isValidDate(String date) {
        if (date == null || date.isEmpty()) return false;
        String[] parts = date.split("\\.");
        if (parts.length != 3) return false;
        try {
            int day = Integer.parseInt(parts[0]);
            int month = Integer.parseInt(parts[1]);
            int year = Integer.parseInt(parts[2]);
            return day >= 1 && day <= 31 && month >= 1 && month <= 12 && year >= 1900 && year <= 2100;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isValidTime(String time) {
        if (time == null || time.isEmpty()) return false;
        String[] parts = time.split(":");
        if (parts.length != 2) return false;
        try {
            int hour = Integer.parseInt(parts[0]);
            int minute = Integer.parseInt(parts[1]);
            return hour >= 0 && hour <= 23 && minute >= 0 && minute <= 59;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private String readValidDate(String prompt) {
        System.out.print(prompt);
        String date = scanner.nextLine();
        if (isValidDate(date)) {
            return date;
        }
        System.out.println("Ungültiges Format. Bitte im Format TT.MM.JJJJ eingeben.");
        return readValidDate(prompt);
    }

    private String readValidTime(String prompt) {
        System.out.print(prompt);
        String time = scanner.nextLine();
        if (isValidTime(time)) {
            return time;
        }
        System.out.println("Ungültiges Format. Bitte im Format HH:MM eingeben.");
        return readValidTime(prompt);
    }

    private int readPositiveInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            try {
                int value = Integer.parseInt(input);
                if (value > 0) {
                    return value;
                }
            } catch (NumberFormatException e) {
            }
            System.out.println("Ungültige Eingabe. Bitte eine positive Zahl eingeben.");
        }
    }
}