package models.screen;

import org.bson.Document;

public class ScreenRecord {
    private String username;
    private String date;
    private double hours;
    private String app;
    private String note;
    private String id;

    public ScreenRecord(String username, String date, double hours, String app, String note, String id) {
        this.username = username;
        this.date = date;
        this.hours = hours;
        this.app = app;
        this.note = note;
        this.id = id;
    }

    public String getUsername() { return username; }
    public String getDate() { return date; }
    public double getHours() { return hours; }
    public String getApp() { return app; }
    public String getNote() { return note; }
    public String getId() { return id; }

    public Document toDocument() {
        return new Document("username", username)
                .append("date", date)
                .append("hours", hours)
                .append("app", app)
                .append("note", note);
    }
}

