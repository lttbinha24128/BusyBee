package busybee.auth;

import java.util.HashMap;
import java.util.Map;

public class AuthService {

    private final Map<String, UserAccount> users = new HashMap<>();

    // =========================
    // INIT DATA (DUMMY)
    // =========================
    public AuthService(){

        users.put("huuthai",
                new UserAccount("huuthai","a24123", Role.DIRECTOR));

        users.put("ngocsang",
                new UserAccount("ngocsang","a24133", Role.DIRECTOR));

        users.put("thanhbinh",
                new UserAccount("thanhbinh","a24128", Role.RECEPTIONIST));

        users.put("thanhtan",
                new UserAccount("thanhtan", "a24121", Role.OFFICE_STAFF));

        users.put("chivan",
                new UserAccount("chivan","a24130", Role.WAREHOUSE));
    }

    // =========================
    // LOGIN
    // =========================
    public UserAccount login(String username, String password){

        // kiểm tra username tồn tại
        if (!users.containsKey(username)) {
            return null;
        }

        UserAccount user = users.get(username);

        // kiểm tra password
        if (user.getPassword().equals(password)) {
            return user; // ✔ trả về user
        }

        return null;
    }

    // =========================
    // HELPER (OPTIONAL)
    // =========================

    // check user tồn tại
    public boolean exists(String username){
        return users.containsKey(username);
    }

    // lấy user theo username
    public UserAccount getUser(String username){
        return users.get(username);
    }
}