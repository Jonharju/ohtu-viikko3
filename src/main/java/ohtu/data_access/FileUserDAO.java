/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohtu.data_access;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import ohtu.domain.User;

/**
 *
 * @author Jonas
 */
public class FileUserDAO implements UserDao{
    
    private File file;
    private Scanner scanner;
    private List<User> users;
    private FileWriter writer;

    public FileUserDAO(String filename) {
        file = new File(filename);
        users = new ArrayList<User>();
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            System.out.println("Tiedostoa ei löytynyt");
        }
       scan();
    }
    
    private void scan(){
        while (scanner.hasNext()) {
            String name = scanner.next();
            String password = scanner.next();
            User user = new User(name, password);
            users.add(user);
        }
        scanner.close();
    }

    @Override
    public List<User> listAll() {
        return users;
    }

    @Override
    public User findByName(String name) {
        for (User user : users) {
            if (user.getUsername().equals(name)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public void add(User user) {
        users.add(user);
        try {
            writer = new FileWriter(file, true);
            writer.append(user.getUsername() + " " + user.getPassword() + "\n");
            writer.close();
        } catch (IOException e) {
            System.out.println("Tietoja ei voitu lisätä");
        }
    }
    
}
