package services.finance;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import database.mongoDBConnection;
import models.finance.Transaction;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;

public class TransactionManager {

    private final MongoCollection<Document> collection;

    public TransactionManager() {
        MongoDatabase db = mongoDBConnection.getDatabase();
        collection = db.getCollection("transactions");
    }


    public void addTransaction(Transaction t) {
        collection.insertOne(t.toDocument());
    }


    public ArrayList<Transaction> getAllTransactions(String username) {
        ArrayList<Transaction> list = new ArrayList<>();
        MongoCursor<Document> cursor = collection.find(new Document("username", username)).iterator();

        while (cursor.hasNext()) {
            Document d = cursor.next();
            String id = d.getObjectId("_id").toHexString();

            list.add(new Transaction(
                    d.getString("username"),
                    d.getString("type"),
                    d.getDouble("amount"),
                    d.getString("description"),
                    id,
                    d.getString("category")
            ));
        }

        return list;
    }
    public double getTotalIncome(String username) {
        double total = 0;
        for (Transaction t : getAllTransactions(username)) {
            if ("Income".equalsIgnoreCase(t.getType())) {
                total += t.getAmount();
            }
        }
        return total;
    }


    public double getTotalExpense(String username) {
        double total = 0;
        for (Transaction t : getAllTransactions(username)) {
            if ("Expense".equalsIgnoreCase(t.getType())) {
                total += t.getAmount();
            }
        }
        return total;
    }


    public void updateTransaction(String id, String type, double amount, String description, String category) {
        collection.updateOne(
                new Document("_id", new ObjectId(id)),
                new Document("$set",
                        new Document("type", type)
                                .append("amount", amount)
                                .append("description", description)
                                .append("category", category)
                )
        );
    }


    public void deleteTransaction(String id) {
        collection.deleteOne(new Document("_id", new ObjectId(id)));
    }
}


