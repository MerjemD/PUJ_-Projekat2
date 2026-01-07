package services.water;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import database.mongoDBConnection;
import models.water.WaterRecord;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;

public class WaterManager {

    private final MongoCollection<Document> collection;

    public WaterManager() {
        MongoDatabase db = mongoDBConnection.getDatabase();
        collection = db.getCollection("water");
    }

    public void addRecord(WaterRecord r) {
        collection.insertOne(r.toDocument());
    }

    public ArrayList<WaterRecord> getAllRecords(String username) {
        ArrayList<WaterRecord> list = new ArrayList<>();
        MongoCursor<Document> cursor =
                collection.find(new Document("username", username)).iterator();

        while (cursor.hasNext()) {
            Document d = cursor.next();
            String id = d.getObjectId("_id").toHexString();

            list.add(new WaterRecord(
                    d.getString("username"),
                    d.getString("date"),
                    d.getDouble("liters"),
                    d.getString("note"),
                    id
            ));
        }
        return list;
    }

    public void updateRecord(String id, String date, double liters, String note) {
        collection.updateOne(
                new Document("_id", new ObjectId(id)),
                new Document("$set",
                        new Document("date", date)
                                .append("liters", liters)
                                .append("note", note))
        );
    }

    public void deleteRecord(String id) {
        collection.deleteOne(new Document("_id", new ObjectId(id)));
    }

    public double getAverageLiters(String username) {
        ArrayList<WaterRecord> records = getAllRecords(username);
        if (records.isEmpty()) return 0;

        double total = 0;
        for (WaterRecord r : records) total += r.getLiters();
        return total / records.size();
    }
}

