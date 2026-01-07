package models.finance;

import org.bson.Document;

public class Transaction {
    private String username;
    private String type;
    private double amount;
    private String description;
    private String id;
    private String category;

    public Transaction(String username, String type, double amount, String description, String id, String category) {
        this.username = username;
        this.type = type;
        this.amount = amount;
        this.description = description;
        this.id = id;
        this.category = category;
    }

    public Document toDocument() {
        return new Document("username", username)
                .append("type", type)
                .append("amount", amount)
                .append("description", description)
                .append("id", id)
                .append("category", category);
    }

    public String getUsername() { return username; }
    public String getType() { return type; }
    public double getAmount() { return amount; }
    public String getDescription() { return description; }
    public String getId() { return id; }
    public String getCategory() { return category; }
}

