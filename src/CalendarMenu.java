// Stellt das Kalendermenü der Anwendung bereit.
// Zeigt Aufgaben nach Datum an, warnt vor Überschneidungen
// und ermöglicht das Verschieben von Aufgaben.
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
        // Zeigt das Hauptmenü des Kalenders an
        while (running) {
            System.out.println();
            System.out.println("=== Kalender ===");
            System.out.println("1. Heute anzeigen");
            System.out.println("2. Bestimmten Tag anzeigen");
            System.out.println("3. Aktuelle Woche anzeigen");
            System.out.println("0. Zurück");
            System.out.print("Auswahl: ");

            String input = scanner.nextLine();
            // Verarbeitet die Benutzereingabe und ruft die entsprechenden Methoden auf
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
    // Zeigt die Aufgaben für den aktuellen Tag an, einschließlich Start- und Endzeiten, und warnt vor Überschneidungen.
    private void showToday() {
        System.out.println();
        System.out.println("=== Heute ===");
        String todayString = formatDate(LocalDate.now());
        lastShownTasks = calendarService.getTasksForDate(todayString);
        displayTasks(lastShownTasks);
        showOverlapWarnings(lastShownTasks);
        showActionMenu();
    }

    // Zeigt die Aufgaben für einen bestimmten Tag an, einschließlich Start- und Endzeiten, und warnt vor Überschneidungen.
    private void showDay() {
        while (true) {
            System.out.print("Datum (TT.MM.JJJJ): ");
            String date = scanner.nextLine();
            
            if (isValidDate(date)) {
                System.out.println();
                System.out.println("=== Tag ===");
                viewMode = "DAY";
                currentDate = parseDate(date);
                lastShownTasks = calendarService.getTasksForDate(date);
                displayTasks(lastShownTasks);
                showOverlapWarnings(lastShownTasks);
                showActionMenu();
                return;
            }
            System.out.println("Ungültiges Datum. Bitte im Format TT.MM.JJJJ eingeben.");
        }
    }
    // Zeigt die Aufgaben für die aktuelle Woche an, einschließlich Start- und Endzeiten, und warnt vor Überschneidungen.
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
    // Zeigt das Aktionsmenü an, das es dem Benutzer ermöglicht, zwischen Tagen/Wochen zu navigieren, 
    // Aufgaben zu verschieben oder die Dauer von Aufgaben zu ändern.
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
    // Navigiert zum vorherigen Tag oder zur vorherigen Woche, abhängig vom aktuellen Anzeigemodus.
    private void navigatePrevious() {
        if (viewMode.equals("DAY")) {
            currentDate = currentDate.minusDays(1);
            showDayWithCurrentDate();
        } else {
            currentDate = currentDate.minusWeeks(1);
            showWeek();
        }
    }
    // Navigiert zum nächsten Tag oder zur nächsten Woche, abhängig vom aktuellen Anzeigemodus.
    private void navigateNext() {
        if (viewMode.equals("DAY")) {
            currentDate = currentDate.plusDays(1);
            showDayWithCurrentDate();
        } else {
            currentDate = currentDate.plusWeeks(1);
            showWeek();
        }
    }
    // Zeigt die Aufgaben für den ausgewählten Tag an, einschließlich Start- und Endzeiten, und warnt vor Überschneidungen.
    private void showDayWithCurrentDate() {
        String dateString = formatDate(currentDate);
        System.out.println();
        System.out.println("=== Tag ===");
        lastShownTasks = calendarService.getTasksForDate(dateString);
        displayTasks(lastShownTasks);
        showOverlapWarnings(lastShownTasks);
        showActionMenu();
    }
    // Verschiebt eine ausgewählte Aufgabe zu einem neuen Datum und einer neuen Startzeit.
    private void moveTask() {
        System.out.println();
        System.out.println("=== Aufgabe verschieben ===");
        // Kehrt zum Menü zurück, wenn keine Aufgaben angezeigt werden
        if (lastShownTasks == null || lastShownTasks.isEmpty()) {
            System.out.println("Keine Aufgaben angezeigt.");
            return;
        }
        // Zeigt die Liste der Aufgaben an, die verschoben werden können
        for (int i = 0; i < lastShownTasks.size(); i++) {
            Task t = lastShownTasks.get(i);
            System.out.println((i + 1) + ". " + t.getTitle() + " (" + t.getDueDate() + ")");
        }
        System.out.println("0. Zurück");
        
        while (true) {
            System.out.print("Welche Aufgabe verschieben? Nummer: ");
            String input = scanner.nextLine();
            // Bricht ab, wenn der Benutzer "0" eingibt
            if (input.equals("0")) {
                return;
            }
            //
            try {
                int number = Integer.parseInt(input);
                int index = number - 1;
                
                if (index < 0 || index >= lastShownTasks.size()) {
                    System.out.println("Ungültige Auswahl. Bitte erneut versuchen.");
                    continue;
                }
                
                Task taskToMove = lastShownTasks.get(index);
                int taskListIndex = calendarService.getAllTasks().indexOf(taskToMove);
                // Liest das neue Datum und die neue Startzeit vom Benutzer ein und validiert sie
                String newDueDate = readValidDate("Neues Datum (TT.MM.JJJJ): ");
                String newStartTime = readValidTime("Neue Startzeit (HH:MM): ");
                // Verschiebt die Aufgabe und zeigt eine Bestätigung an
                calendarService.moveTask(taskListIndex, newDueDate, newStartTime);
                System.out.println("Aufgabe verschoben.");
                // Zeigt die aktualisierte Ansicht basierend auf dem aktuellen Anzeigemodus an
                if (viewMode.equals("DAY")) {
                    showDayWithCurrentDate();
                } else {
                    showWeek();
                }
                return;
            } catch (NumberFormatException e) {
                System.out.println("Ungültige Eingabe. Bitte erneut versuchen.");
            }
        }
    }
    // Ändert die Dauer einer ausgewählten Aufgabe.
    private void changeDuration() {
        System.out.println();
        System.out.println("=== Aufgabe Dauer ändern ===");
        // Kehrt zum Menü zurück, wenn keine Aufgaben angezeigt werden
        if (lastShownTasks == null || lastShownTasks.isEmpty()) {
            System.out.println("Keine Aufgaben angezeigt.");
            return;
        }
        // Zeigt die Liste der Aufgaben an, die geändert werden können
        for (int i = 0; i < lastShownTasks.size(); i++) {
            Task t = lastShownTasks.get(i);
            System.out.println((i + 1) + ". " + t.getTitle() + " (Dauer: " + t.getEstimatedDuration() + " min)");
        }
        System.out.println("0. Zurück");
        
        while (true) {
            System.out.print("Welche Aufgabe ändern? Nummer: ");
            String input = scanner.nextLine();
            // Bricht ab, wenn der Benutzer "0" eingibt
            if (input.equals("0")) {
                return;
            }
            // Versucht, die Eingabe in eine Zahl zu parsen und die Dauer der ausgewählten Aufgabe zu ändern
            try {
                int number = Integer.parseInt(input);
                int index = number - 1;
                
                if (index < 0 || index >= lastShownTasks.size()) {
                    System.out.println("Ungültige Auswahl. Bitte erneut versuchen.");
                    continue;
                }
                
                Task taskToChange = lastShownTasks.get(index);
                int taskListIndex = calendarService.getAllTasks().indexOf(taskToChange);
                
                int newDuration = readPositiveInt("Neue Dauer (Minuten): ");
                
                calendarService.changeDuration(taskListIndex, newDuration);
                System.out.println("Dauer geändert.");
                // Zeigt die aktualisierte Ansicht basierend auf dem aktuellen Anzeigemodus an
                if (viewMode.equals("DAY")) {
                    showDayWithCurrentDate();
                } else {
                    showWeek();
                }
                return;
            } catch (NumberFormatException e) {
                System.out.println("Ungültige Eingabe. Bitte erneut versuchen.");
            }
        }
    }
    // Zeigt die Liste der Aufgaben an, sortiert nach Startzeit, und berechnet die Endzeit basierend auf der geschätzten Dauer.
    private void displayTasks(ArrayList<Task> tasks) {
        if (tasks.isEmpty()) {
            System.out.println("Keine Aufgaben geplant.");
        } else {
            ArrayList<Task> sortedTasks = new ArrayList<>(tasks);
            sortedTasks.sort((t1, t2) -> {
                int time1 = parseTimeToMinutes(t1.getStartTime());
                int time2 = parseTimeToMinutes(t2.getStartTime());
                return Integer.compare(time1, time2);
            });
            for (Task task : sortedTasks) {
                String endTime = calculateEndTime(task.getStartTime(), task.getEstimatedDuration());
                System.out.println(task.getStartTime() + " - " + endTime + "   " + task.getTitle() + " (" + task.getProject() + ")");
            }
        }
    }
    // Zeigt Warnungen an, wenn es Überschneidungen zwischen den angezeigten Aufgaben gibt.
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
    // Berechnet die Endzeit einer Aufgabe basierend auf der Startzeit und der geschätzten Dauer in Minuten.
    private String calculateEndTime(String startTime, int estimatedDuration) {
        String[] parts = startTime.split(":");
        int hour = Integer.parseInt(parts[0]);
        int minute = Integer.parseInt(parts[1]);
        
        int totalMinutes = hour * 60 + minute + estimatedDuration;
        int endHour = totalMinutes / 60;
        int endMinute = totalMinutes % 60;
        
        return String.format("%02d:%02d", endHour, endMinute);
    }
    // Formatiert ein LocalDate-Objekt in das Format TT.MM.JJJJ.
    private String formatDate(LocalDate date) {
        return String.format("%02d.%02d.%04d", 
            date.getDayOfMonth(), 
            date.getMonthValue(), 
            date.getYear());
    }
    // Parst ein Datum im Format TT.MM.JJJJ in ein LocalDate-Objekt.
    private LocalDate parseDate(String date) {
        String[] parts = date.split("\\.");
        return LocalDate.of(Integer.parseInt(parts[2]), Integer.parseInt(parts[1]), Integer.parseInt(parts[0]));
    }
    // Prüft die Gültigkeit eines Datums im Format TT.MM.JJJJ. Gibt true zurück, wenn das Datum gültig ist, andernfalls false.
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
    // Prüft die Gültigkeit einer Zeit im Format HH:MM. Gibt true zurück, wenn die Zeit gültig ist, andernfalls false.
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
    // Methode zum Prüfen eines vom Benutzer eingegebenen Datums. 
    // Wenn das Format ungültig ist, wird der Benutzer aufgefordert, erneut ein Datum einzugeben.
    private String readValidDate(String prompt) {
        while (true) {
            System.out.print(prompt);
            String date = scanner.nextLine();
            if (isValidDate(date)) {
                return date;
            }
            System.out.println("Ungültiges Format. Bitte im Format TT.MM.JJJJ eingeben.");
        }
    }
    // Methode zum Prüfen einer vom Benutzer eingegebenen Zeit. 
    // Wenn das Format ungültig ist, wird der Benutzer aufgefordert, erneut eine Zeit einzugeben.
    private String readValidTime(String prompt) {
        while (true) {
            System.out.print(prompt);
            String time = scanner.nextLine();
            if (isValidTime(time)) {
                return time;
            }
            System.out.println("Ungültiges Format. Bitte im Format HH:MM eingeben.");
        }
    }
    // Liest eine positive ganze Zahl vom Benutzer ein. 
    // Wenn die Eingabe ungültig ist, wird der Benutzer aufgefordert, erneut eine Zahl einzugeben.
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
    // Parst eine Zeit im Format HH:MM in die Gesamtzahl der Minuten seit Mitternacht.
    private int parseTimeToMinutes(String time) {
        String[] parts = time.split(":");
        return Integer.parseInt(parts[0]) * 60 + Integer.parseInt(parts[1]);
    }
}