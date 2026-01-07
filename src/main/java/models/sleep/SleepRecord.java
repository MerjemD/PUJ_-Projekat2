package models.sleep;

import org.bson.Document;

public class SleepRecord {

    private String date;
    private double hours;
    private String quality;
    private String note;
    private String id;

    public SleepRecord(String date, double hours, String quality, String note, String id) {
        this.date = date;
        this.hours = hours;
        this.quality = quality;
        this.note = note;
        this.id = id;
    }

    public String getDate() { return date; }
    public double getHours() { return hours; }
    public String getQuality() { return quality; }
    public String getNote() { return note; }
    public String getId() { return id; }

    public Document toDocument() {
        return new Document("date", date)
                .append("hours", hours)
                .append("quality", quality)
                .append("note", note);
    }
}

