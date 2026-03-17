package busybee.auth;

public class UserAccount {

    private final String username;
    private final String password;
    private final Role role;

    // =========================
    // CONSTRUCTOR
    // =========================
    public UserAccount(String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // =========================
    // GETTER
    // =========================
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }

    // =========================
    // HELPER
    // =========================
    public boolean isRole(Role role) {
        return this.role == role;
    }

    @Override
    public String toString() {
        return username + " (" + role + ")";
    }
}