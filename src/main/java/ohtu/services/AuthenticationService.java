package ohtu.services;

import ohtu.domain.User;
import java.util.ArrayList;
import java.util.List;
import ohtu.data_access.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationService {

    private UserDao userDao;
    
    @Autowired
    public AuthenticationService(UserDao userDao) {
        this.userDao = userDao;
    }

    public boolean logIn(String username, String password) {
        for (User user : userDao.listAll()) {
            if (userFound(user, username, password)) {
                return true;
            }
        }
        return false;
    }
    private boolean userFound(User user, String username, String password){
        return user.getUsername().equals(username)
                && user.getPassword().equals(password);
    }

    public boolean createUser(String username, String password) {
        if (userDao.findByName(username) != null) {
            return false;
        }
        if (invalid(username, password)) {
            return false;
        }
        userDao.add(new User(username, password));
        return true;
    }

    private boolean invalid(String username, String password) {
        if(!validUsername(username)){
            return true;
        } else if(!validPassword(password)){
            return true;
        }
        return false;
    }
    
    private boolean validPassword(String password){
        return !onlyLetters(password)&&length(password, 8);
    }
    
    private boolean validUsername(String username){
        return onlyLetters(username)&&length(username, 3);
    }
    
    private boolean length(String s, int i){
        return s.length() >= i;
    }
    
    private boolean onlyLetters(String name) {
        char[] chars = name.toCharArray();
        for (char c : chars) {
            if(!Character.isLetter(c)) {
                return false;
            }
        }
        return true;
    }
}
