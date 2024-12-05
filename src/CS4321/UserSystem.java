package src.CS4321;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

public class UserSystem {
    private Map<String, String> users;
    private final String FILE_PATH = "user.dat";

    public UserSystem() {
        users = new HashMap<>();
        loadUsers();
    }

    public String register(String username, String password) {
        if(users.containsKey(username)) {
            return "Username already exists. Please choose a different username.";
        }
        users.put(username, password);
        saveUsers();
        return "registration successful";
    }

    public String login(String username, String password) {
        if(!users.containsKey(username)) {
            return "User does not exist.";
        }
        if(!users.get(username).equals(password)) {
            return "Incorrect Password";
        }
        return "Login successful";
    }

    private void saveUsers() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(users);
        } catch (IOException e) {
            System.err.println("Error saving users: " + e.getMessage());
        }
    }

    void loadUsers() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
            users = (HashMap<String, String>) ois.readObject();
        } catch (FileNotFoundException e) {
            // File doesn't exist yet, which is okay
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading users: " + e.getMessage());
        }
    }
}
