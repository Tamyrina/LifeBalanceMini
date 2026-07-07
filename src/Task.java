// Repräsentiert eine einzelne Aufgabe der Anwendung.
// Speichert alle Informationen einer Aufgabe, wie Titel,
// Datum, Uhrzeit, Dauer, Projekt und Bearbeitungsstatus.
public class Task { 
    private String title;
    private String dueDate;
    private String repeat;
    private boolean completed;
private String project;
    private String startTime;
    private int estimatedDuration;
    
    public Task(String title, String dueDate, String startTime, int estimatedDuration, String repeat, String project) {
        this.title = title;
        this.dueDate = dueDate;
        this.startTime = startTime;
        this.estimatedDuration = estimatedDuration;
        this.repeat = repeat;
        this.project = project;
        this.completed = false;
    }

    // Getter
    public String getTitle() {
        return title;
    }

    public String getDueDate() {
        return dueDate;
    }

    public String getRepeat() {
        return repeat;
    }

    public String getProject() {
    return project;
    }

    public boolean isCompleted() {
        return completed;
    }

    public String getStartTime() {
        return startTime;
    }

    public int getEstimatedDuration() {
        return estimatedDuration;
    }

    // Setter
    public void setTitle(String title) {
        this.title = title;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public void setRepeat(String repeat) {
        this.repeat = repeat;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setEstimatedDuration(int estimatedDuration) {
        this.estimatedDuration = estimatedDuration;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
    // Gibt die wichtigsten Informationen einer Aufgabe als formatierten Text zurück.
    @Override
    public String toString() {
        return "Titel: " + title +
                "\nDatum: " + dueDate +
                "\nProjekt: " + project +
                "\nErledigt: " + completed;

    }
}