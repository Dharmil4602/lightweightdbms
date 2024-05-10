package com.dbmslightweight.dbmslightweight;

import com.dbmslightweight.dbmslightweight.folderoperation.FolderOperation;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class DbmsLightWeight {
    private static final List<User> registeredUsers = new ArrayList<>();
    private static final Map<String, String> userFolders = new HashMap<>();
    private static final Map<String, List<String>> userTransactionLogs = new HashMap<>();

    public static void main(String[] args) {

        FolderOperation folderOperation = new FolderOperation();
        folderOperation.createFolder("./users");

        Database db = new Database();
        TransactionManager transactionManager = new TransactionManager();
        String activeUser = null;
        Scanner scanner = new Scanner(System.in);
        boolean continueAhead = true;
        while (continueAhead) {
            System.out.println("Select an option below:");
            System.out.println("1. Login");
            System.out.println("2. Signup");
            System.out.println("3. Quit");
            System.out.println("Enter your preference: ");

            int selectedOption = scanner.nextInt();
            scanner.nextLine();

            if (selectedOption == 1) {
                // Login
                System.out.println("Enter your username: ");
                String loginUsername = scanner.nextLine();
                System.out.println("Enter your password: ");
                String loginPassword = scanner.nextLine();
                continueAhead = false;
            } else if (selectedOption == 2) {
                // Signing up:
                System.out.println("Enter a username for registration: ");
                String newUsername = scanner.nextLine();
                System.out.println("Enter password: ");
                String newPassword = scanner.nextLine();
                signUp(newUsername, newPassword);
                continueAhead = false;
            } else if (selectedOption == 3) {
                break;
            } else {
                System.out.println("Invalid Selection");
            }

            if (activeUser != null) {
                System.out.println(activeUser + " Logged In");
                String userFolder = "./users/" + activeUser;
                while (true) {
                    System.out.println("Enter SQL Query (or quit to exit)");
                    String query = scanner.nextLine();
                    if (query.equalsIgnoreCase("quit")) {
                        break;
                    }
                    db.executeSQLQuery(query);
                    transactionManager.addQueryToTransaction(query);

//                    String saveTransaction = userFolder + "/transaction_log.txt";
//                    saveTransactionLog(transactionManager, transactionLogFilePath);
                }
            }
        }

//        if (username.equals(user1.getUsername()) && password.equals(user1.getPassword())) {
//            // Successful authentication for admin
//            System.out.println("Admin logged in.");
//            while (true) {
//                System.out.print("Enter SQL query (or 'quit' to exit): ");
//                String query = scanner.nextLine();
//                if (query.equalsIgnoreCase("quit")) {
//                    break;
//                }
//                transactionManager.addQueryToTransaction(query);
//            }
//            transactionManager.commitTransaction(db);
//        } else if (username.equals(user2.getUsername()) && password.equals(user2.getPassword())) {
//            // Successful authentication for user
//            System.out.println("User logged in.");
//            while (true) {
//                System.out.print("Enter SQL query (or 'quit' to exit): ");
//                String query = scanner.nextLine();
//                if (query.equalsIgnoreCase("quit")) {
//                    break;
//                }
//                transactionManager.addQueryToTransaction(query);
//            }
//            transactionManager.commitTransaction(db);
//        } else {
//            System.out.println("Invalid credentials.");
//        }
    }

    // Signup Code:
    public static void signUp(String newUsername, String newPassword) {
        if (registeredUsers.stream().anyMatch(user -> user.getUsername().equals(newUsername))) {
            System.out.println("User Already Exists");
            return;
        }

        User newUser = new User(newUsername, newPassword);
        registeredUsers.add(newUser);

        //Creating folders for every user individually
        String userFolder = "./users/" + newUsername;
        userFolders.put(newUsername, userFolder);

        userTransactionLogs.put(
                newUsername, new ArrayList<>()
        );
        System.out.println("You are successfully registered.");
    }

    // Login Code:
    public static String login(TransactionManager transactionManager, String username, String password) {
        for (User user : registeredUsers) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                System.out.println(username + " You Successfuly Loggin In");
                String userFolder = userFolders.get(username);
                transactionManager.setActiveUser(username);
                return username;
            }
        }
        System.out.println("Invalid Credentials");
        return null;
    }

    public static void saveTransactionLog(TransactionManager transactionManager, String filePath) {
        try (FileWriter writer = new FileWriter(filePath, true)) {
            for (String query : transactionManager.getTransactionLog()) {
                writer.write(query + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
