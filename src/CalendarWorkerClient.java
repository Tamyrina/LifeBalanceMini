// Verbindet die Hauptanwendung mit dem CalendarWorkerServer.
// Sendet Aufgaben an den Worker und empfängt Warnungen zurück.
import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class CalendarWorkerClient {
    // Sendet Aufgaben an den CalendarWorkerServer und empfängt Warnungen zurück.
    public static ArrayList<String> sendTasksAndGetWarnings(ArrayList<Task> tasks) {
        try (
            Socket socket = new Socket("localhost", 9090);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()))
        ) { // Sendet Aufgaben an den Server
            for (Task task : tasks) {
                String line = "Titel:" + task.getTitle() +
                    "|Datum:" + task.getDueDate() +
                    "|Start:" + task.getStartTime() +
                    "|Dauer:" + task.getEstimatedDuration();
                out.println(line);
            }
            out.println("ENDE");
            out.flush();
            // Liest Warnungen vom Server ein
            ArrayList<String> warnings = new ArrayList<>();
            String line;
            while ((line = in.readLine()) != null) {
                if (line.equals("WEITER")) {
                    break;
                }
                warnings.add(line);
            }
            return warnings;
        } catch (IOException e) {
            return null;
        }
    }
}