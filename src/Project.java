// Repräsentiert eine einzelne Projekt der Anwendung.
// Speichert alle Informationen eines Projekts, wie Titel,
// Kategorie, Datum, Uhrzeit und Bearbeitungsstatus.
public class Project {
    private String title;
    private String category;
    private String dueDate;
    private String dueTime;
    private boolean completed;

    public Project(String title, String category, String dueDate, String dueTime) {
        this.title = title;
        this.category = category;
        this.dueDate = dueDate;
        this.dueTime = dueTime;
        this.completed = false;
    }
    //Getter
    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }

    public String getDueDate() {
        return dueDate;
    }

    public String getDueTime() {
        return dueTime;
    }

    public boolean isCompleted() {
        return completed;
    }
    // Setter
    public void setTitle(String title) {
        this.title = title;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public void setDueTime(String dueTime) {
        this.dueTime = dueTime;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
    // Gibt die wichtigsten Informationen eines Projekts als formatierten Text zurück.
    @Override
    public String toString() {
        return "Titel: " + title +
                "\nKategorie: " + category +
                "\nDatum: " + dueDate +
                "\nZeit: " + dueTime +
                "\nErledigt: " + completed;
    }
}