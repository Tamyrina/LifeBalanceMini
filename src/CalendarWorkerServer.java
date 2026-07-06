import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class CalendarWorkerServer {

    private static final int PORT = 9090;

    public static void main(String[] args) {
        System.out.println("CalendarWorkerServer startet auf Port " + PORT);

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Verbindung von Client akzeptiert");
                handleClient(clientSocket);
            }
        } catch (IOException e) {
            System.out.println("Server-Fehler: " + e.getMessage());
        }
    }

    private static void handleClient(Socket clientSocket) {
        try (
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()))
        ) {
            ArrayList<Task> tasks = new ArrayList<>();
            String line;

            while ((line = in.readLine()) != null) {
                if (line.equals("ENDE")) {
                    break;
                }
                Task task = parseTask(line);
                if (task != null) {
                    tasks.add(task);
                }
            }

            ArrayList<String> warnings = CalendarService.detectOverlapsStatic(tasks);

            for (String warning : warnings) {
                out.println(warning);
            }
            out.println("WEITER");
            out.flush();

        } catch (IOException e) {
            System.out.println("Client-Fehler: " + e.getMessage());
        } finally {
            try {
                clientSocket.close();
                System.out.println("Verbindung geschlossen");
            } catch (IOException e) {
                System.out.println("Fehler beim Schließen: " + e.getMessage());
            }
        }
    }

    private static Task parseTask(String line) {
        String[] parts = line.split("\\|");
        String title = "";
        String date = "";
        String startTime = "";
        int duration = 0;

        for (String part : parts) {
            if (part.startsWith("Titel:")) {
                title = part.substring(6);
            } else if (part.startsWith("Datum:")) {
                date = part.substring(5);
            } else if (part.startsWith("Start:")) {
                startTime = part.substring(6);
            } else if (part.startsWith("Dauer:")) {
                try {
                    duration = Integer.parseInt(part.substring(5));
                } catch (NumberFormatException e) {
                }
            }
        }

        return new Task(title, date, startTime, duration, "", "");
    }
}