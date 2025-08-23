package org.example;


import org.apache.commons.validator.routines.EmailValidator;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        EmailValidator validator = EmailValidator.getInstance();

        DatabaseManager.createUsersDatabase();

        System.out.println("1. Register\n2. Login");
        int userChoice = scanner.nextInt();
        scanner.nextLine();
        switch (userChoice) {
            case 1 -> {
                String firstName;
                String lastName;
                String email;
                String password;


                do {
                    clearConsole();
                    System.out.println("Please fill in all the fields and enter a valid email.");
                    System.out.println();
                    System.out.print("Enter First Name: ");
                    firstName = scanner.nextLine();
                    System.out.print("Enter Last Name: ");
                    lastName = scanner.nextLine();
                    System.out.print("Enter Email: ");
                    email = scanner.nextLine();
                    password = passwordValidator(scanner);
                } while (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || !validator.isValid(email));

                DatabaseManager.insertUserToUsers(firstName, lastName, email, password);
                java.util.Arrays.fill(password.toCharArray(), '\0');
            }
            case 2 -> {
                clearConsole();

                System.out.print("Enter email: ");
                String email = scanner.nextLine();
                System.out.print("Enter password: ");
                String password = scanner.nextLine();

                String name = DatabaseManager.verifyUserPassword(password, email);
                if (name != null && !name.isEmpty()) System.out.println(name + " has been logged in.");
                else
                    System.out.println("Incorrect email or password");
                java.util.Arrays.fill(password.toCharArray(), '\0');
            }
            default -> System.out.println("Enter a valid option.");
        }
    }

    /**
     * method used to clear the console for user readability
     * when exception is thrown, it prints stack trace
     */
    public static void clearConsole() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * method to validate if the password entered by the user is strong
     * checks if the password is long enough
     * has at least one uppercase letter
     * has at least one lowercase letter
     * has at least one digit
     * has at least one special character
     * wipes password data
     */
    public static String passwordValidator(Scanner scanner) {
        String password = "";
        String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).{8,}$";

        try {
            while (true) {
                System.out.print("Enter password: ");
                password = scanner.nextLine();

                if (password.matches(regex)) {
                    return password;
                } else {
                    System.out.println("Weak password. Must be at least 8 characters, contain uppercase, lowercase, digit, and special character.\n ");
                }
            }
        } finally {
            java.util.Arrays.fill(password.toCharArray(), '\0');
        }
    }
}