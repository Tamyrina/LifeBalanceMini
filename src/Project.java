/*
 * Diese Klasse beschreibt ein Projekt in der LifeBalance-App.
 * Ein Projekt besitzt einen Titel, eine Kategorie, ein Fälligkeitsdatum,
 * eine Fälligkeitszeit und einen Erledigt-Status.
 */
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

    @Override
    public String toString() {
        return "Titel: " + title +
                "\nKategorie: " + category +
                "\nDatum: " + dueDate +
                "\nZeit: " + dueTime +
                "\nErledigt: " + completed;
    }
}