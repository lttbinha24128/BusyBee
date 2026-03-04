package busybee.auth;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import busybee.auth.Role;
import busybee.model.UserAccount;
import java.util.HashMap;
import java.util.Map;
/**
 *
 * @author Binh
 */
public class AuthService {
    private Map<String, UserAccount> users = new HashMap<>();
    
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
    
    public UserAccount login(String username, String password){
        if(!users.containsKey(username)){
            return null;
        }
        
        UserAccount user = users.get(username);
        
        if(user.getPassword().equals(password)){
            return user;
        }
        
       return null;
    }
          
}
