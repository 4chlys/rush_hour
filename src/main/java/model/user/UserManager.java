package main.java.model.user;

import main.java.model.exceptions.UserLoginException;
import main.java.model.exceptions.UserRegistrationException;
import main.java.model.exceptions.UserFileException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//reads and writes all the users to the right directory
public class UserManager {
    private static final String USERS_DIRECTORY = "users";
    private static final String USER_FILE_EXTENSION = ".bin";
    private Map<String, User> users;

    public UserManager() {
        users = new HashMap<>();
        createUsersDirectoryIfNotExists();
        loadAllUsers();
    }

    private void createUsersDirectoryIfNotExists() {
        try {
            Path usersDir = Paths.get(USERS_DIRECTORY);
            if (!Files.exists(usersDir)) {
                Files.createDirectory(usersDir);
            }
        } catch (IOException e) {
            throw new UserFileException("Failed to create users directory: " + e.getMessage());
        }
    }

    private void loadAllUsers() {
        try {
            Path usersDir = Paths.get(USERS_DIRECTORY);
            if (!Files.exists(usersDir)) {
                return; //if directory doesn't exist, the method is stopped
            }

            Files.list(usersDir)
                    .filter(path -> path.toString().endsWith(USER_FILE_EXTENSION))
                    .forEach(this::loadUser);

        } catch (IOException e) {
            throw new UserFileException("Failed to load users from directory: " + e.getMessage());
        }
    }

    private void loadUser(Path userFile) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(userFile.toFile()))) {
            User user = (User) ois.readObject();
            users.put(user.getUsername(), user);
        } catch (IOException e) {
            throw new UserFileException("Failed to load user from file " + userFile + ": " + e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new UserFileException("User data format is corrupted in file " + userFile + ": " + e.getMessage());
        }
    }

    private void saveUser(User user) {
        String filename = USERS_DIRECTORY + File.separator + user.getUsername() + USER_FILE_EXTENSION;
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(user);
        } catch (IOException e) {
            throw new UserFileException("Failed to save user data for " + user.getUsername() + ": " + e.getMessage());
        }
    }

    public boolean signUp(String username, String password) {
        if (username == null || username.trim().isEmpty()) {
            throw new UserRegistrationException("Username cannot be empty");
        }

        if (password == null || password.trim().isEmpty()) {
            throw new UserRegistrationException("Password cannot be empty");
        }

        if (username.contains("/") || username.contains("\\") || username.contains(":")) {
            throw new UserRegistrationException("Username contains invalid characters");
        }

        if (users.containsKey(username)) {
            return false;
        }

        try {
            User user = new User(username, password);
            users.put(username, user);
            saveUser(user);
            return true;
        } catch (UserFileException e) {
            throw e;
        } catch (Exception e) {
            throw new UserRegistrationException("Failed to register user: " + e.getMessage());
        }
    }

    public User login(String username, String password) {
        if (username == null || username.trim().isEmpty()) {
            throw new UserLoginException("Username cannot be empty");
        }

        if (password == null || password.trim().isEmpty()) {
            throw new UserLoginException("Password cannot be empty");
        }

        try {
            User user = users.get(username);
            if (user != null && user.getPassword().equals(password)) {
                return user;
            }
            throw new UserLoginException("Invalid username or password");
        } catch (Exception e) {
            throw new UserLoginException("Failed to authenticate user: " + e.getMessage());
        }
    }

    public void updateUser(User user) {
        if (user == null) {
            throw new UserRegistrationException("Cannot update null user");
        }

        try {
            users.put(user.getUsername(), user);
            saveUser(user);
        } catch (UserFileException e) {
            throw e;
        } catch (Exception e) {
            throw new UserRegistrationException("Failed to update user: " + e.getMessage());
        }
    }
}