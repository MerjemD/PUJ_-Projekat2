package models.water;

import org.bson.Document;

public class WaterRecord {

    private String username;
    private String date;
    private double liters;
    private String note;
    private String id;

    public WaterRecord(String username, String date, double liters, String note, String id) {
        this.username = username;
        this.date = date;
        this.liters = liters;
        this.note = note;
        this.id = id;
    }

    public String getUsername() { return username; }
    public String getDate() { return date; }
    public double getLiters() { return liters; }
    public String getNote() { return note; }
    public String getId() { return id; }

    public Document toDocument() {
        return new Document("username", username)
                .append("date", date)
                .append("liters", liters)
                .append("note", note);
    }
}


