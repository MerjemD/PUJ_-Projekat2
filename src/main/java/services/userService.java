package services;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import database.mongoDBConnection;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class userService {

    private static final MongoCollection<Document> users =
            mongoDBConnection.getDatabase().getCollection("users");


    public static boolean loginUser(String username, String password) {
        Document doc = users.find(
                Filters.and(
                        Filters.eq("username", username),
                        Filters.eq("password", password),
                        Filters.eq("role", "USER")
                )
        ).first();
        return doc != null;
    }

    public static boolean loginAdmin(String username, String password) {
        Document doc = users.find(
                Filters.and(
                        Filters.eq("username", username),
                        Filters.eq("password", password),
                        Filters.eq("role", "ADMIN")
                )
        ).first();
        return doc != null;
    }

    public static void createUser(String username, String password, String theme, String role) {
        Document existingUser = users.find(Filters.eq("username", username)).first();
        if (existingUser != null) {
            throw new IllegalArgumentException("Username already exists!");
        }

        Document doc = new Document("username", username)
                .append("password", password)
                .append("theme", theme)
                .append("role", role);

        users.insertOne(doc);
    }

    public static void changeUserPassword(String username, String newPassword) {
        users.updateOne(
                Filters.eq("username", username),
                new Document("$set", new Document("password", newPassword))
        );
    }
    public static void changeAdminPassword(String newPassword) {
        users.updateOne(
                Filters.eq("role", "ADMIN"),
                new Document("$set", new Document("password", newPassword))
        );
    }

    public static void updateUser(String username, String password, String theme, String role) {
        users.updateOne(
                Filters.eq("username", username),
                new Document("$set", new Document("password", password)
                        .append("theme", theme)
                        .append("role", role))
        );
    }


    public static void deleteUser(String username) {
        users.deleteOne(Filters.eq("username", username));
    }


    public static String getUserTheme(String username) {
        Document doc = users.find(Filters.eq("username", username)).first();
        if (doc != null && doc.containsKey("theme")) {
            return doc.getString("theme");
        }
        return "Default";
    }
    public static List<Document> getAllUsers() {
        List<Document> list = new ArrayList<>();
        MongoCursor<Document> cursor = users.find().iterator();
        while (cursor.hasNext()) {
            list.add(cursor.next());
        }
        return list;
    }
}

