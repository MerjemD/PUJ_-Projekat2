package services.sleep;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import models.sleep.SleepRecord;
import org.bson.Document;
import org.bson.types.ObjectId;
import database.mongoDBConnection;

import java.util.ArrayList;

public class SleepManager {

    private final MongoCollection<Document> collection;

    public SleepManager() {
        MongoDatabase db = mongoDBConnection.getDatabase();
        collection = db.getCollection("sleep");
    }

    public void addRecord(SleepRecord r) {
        collection.insertOne(r.toDocument());
    }

    public ArrayList<SleepRecord> getAllRecords() {
        ArrayList<SleepRecord> list = new ArrayList<>();
        MongoCursor<Document> cursor = collection.find().iterator();

        while (cursor.hasNext()) {
            Document d = cursor.next();
            String id = d.getObjectId("_id").toHexString();

            list.add(new SleepRecord(
                    d.getString("date"),
                    d.getDouble("hours"),
                    d.getString("quality"),
                    d.getString("note"),
                    id
            ));
        }
        return list;
    }

    public void updateRecord(String id, String date, double hours, String quality, String note) {
        collection.updateOne(
                new Document("_id", new ObjectId(id)),
                new Document("$set",
                        new Document("date", date)
                                .append("hours", hours)
                                .append("quality", quality)
                                .append("note", note)
                )
        );
    }

    public void deleteRecord(String id) {
        collection.deleteOne(new Document("_id", new ObjectId(id)));
    }

    public double getAverageHours() {
        ArrayList<SleepRecord> records = getAllRecords();
        if (records.isEmpty()) return 0;

        double total = 0;
        for (SleepRecord r : records) total += r.getHours();
        return total / records.size();
    }
}


