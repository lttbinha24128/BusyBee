package busybee.auth;

import busybee.model.UserAccount;

//Session dùng để lưu thông tin người dùng đang đăng nhập
 

public class Session {

    // User hiện tại (static → toàn app dùng chung)
    private static UserAccount currentUser;

    /**
     * Gán user khi login thành công
     */
    public static void setCurrentUser(UserAccount user) {
        currentUser = user;
    }

    /**
     * Lấy user hiện tại
     */
    public static UserAccount getCurrentUser() {
        return currentUser;
    }

    /**
     * Kiểm tra đã login chưa
     */
    public static boolean isLoggedIn() {
        return currentUser != null;
    }

    /**
     * Xóa session khi logout
     */
    public static void clear() {
        currentUser = null;
    }
}