package gau.nau.springmvcjwt.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import gau.nau.springmvcjwt.entities.User;

@Service
public class UserService {
    
    public static List<User> userList = new ArrayList<>();
    
    static {
        User userGiangNT = new User(1, "GiangNT", "1234Abcd");
        userGiangNT.setRoles(new String[]{"ROLE_ADMIN"});
        
        User userNhiPT = new User(2, "NhiPT", "1234Abcd");
        userNhiPT.setRoles(new String[]{"ROLE_USER"});
        
        userList.add(userGiangNT);
        userList.add(userNhiPT);
    }
    
    public List<User> findAll() {
        return userList;
    }
    
    public User findById(int id) {
        for (User user : userList) {
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
    }
    
    public boolean add(User user) {
        for (User userExist : userList) {
            if (user.getId() == userExist.getId() || StringUtils.equals(user.getUsername(), userExist.getUsername())) {
                return false;
            }
        }
        userList.add(user);
        return true;
    }
    
    public void delete(int id) {
        userList.removeIf(user -> user.getId() == id);
    }
    
    public User loadUserByUsername(String username) {
        for (User user : userList) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }
    
    public boolean checkLogin(User user) {
        for (User userExist : userList) {
            if (StringUtils.equals(user.getUsername(), userExist.getUsername()) &&
                StringUtils.equals(user.getPassword(), userExist.getPassword())) {
                return true;
            }
        }
        return false;
    }
}
