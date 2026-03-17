package busybee.auth;

public class Session {

    private static UserAccount currentUser;

    // =========================
    // SET / GET USER
    // =========================
    public static void setCurrentUser(UserAccount user) {
        currentUser = user;
    }

    public static UserAccount getCurrentUser() {
        return currentUser;
    }

    // =========================
    // ROLE
    // =========================
    public static Role getRole() {
        return currentUser != null ? currentUser.getRole() : null;
    }

    public static boolean hasRole(Role role) {
        return currentUser != null && currentUser.getRole() == role;
    }

    // =========================
    // LOGIN STATE
    // =========================
    public static boolean isLoggedIn() {
        return currentUser != null;
    }

    // =========================
    // LOGOUT
    // =========================
    public static void clear() {
        currentUser = null;
    }
}