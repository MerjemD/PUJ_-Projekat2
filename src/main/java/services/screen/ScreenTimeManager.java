package services.screen;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import models.screen.ScreenRecord;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;

public class ScreenTimeManager {

    private final MongoCollection<Document> collection;

    public ScreenTimeManager() {
        MongoDatabase db = database.mongoDBConnection.getDatabase();
        collection = db.getCollection("screen");
    }

    public void addRecord(ScreenRecord r) {
        collection.insertOne(r.toDocument());
    }

    public ArrayList<ScreenRecord> getAllRecords(String username) {
        ArrayList<ScreenRecord> list = new ArrayList<>();
        MongoCursor<Document> cursor = collection.find(new Document("username", username)).iterator();

        while (cursor.hasNext()) {
            Document d = cursor.next();
            String id = d.getObjectId("_id").toHexString();
            list.add(new ScreenRecord(
                    d.getString("username"),
                    d.getString("date"),
                    d.getDouble("hours"),
                    d.getString("app"),
                    d.getString("note"),
                    id
            ));
        }
        return list;
    }

    public void updateRecord(String id, String date, double hours, String app, String note) {
        collection.updateOne(
                new Document("_id", new ObjectId(id)),
                new Document("$set", new Document("date", date)
                        .append("hours", hours)
                        .append("app", app)
                        .append("note", note))
        );
    }

    public void deleteRecord(String id) {
        collection.deleteOne(new Document("_id", new ObjectId(id)));
    }

    public double getTotalHours(String username) {
        double total = 0;
        for (ScreenRecord r : getAllRecords(username)) total += r.getHours();
        return total;
    }
}

