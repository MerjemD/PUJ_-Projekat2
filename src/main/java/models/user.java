package models;

public class user {
    private String username;
    private String password;
    private String theme;
    private String role;

    public user(String username, String password, String theme,String role) {
        this.username = username;
        this.password = password;
        this.theme = theme;
        this.role = role;
    }

    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getTheme() { return theme; }
    public String getRole() { return role; }
}
