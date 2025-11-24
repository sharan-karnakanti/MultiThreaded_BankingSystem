package com.bankingSystem;

import java.util.Scanner;

import com.bankingSystem.model.Account;
import com.bankingSystem.menu.AdminMenu;
import com.bankingSystem.menu.CustomerMenu;

public class MainMenu {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Database.loadAccounts();

        while (true) {
            System.out.print("\033[H\033[2J");
            System.out.flush();
            System.out.println("=======================================");
            System.out.println("     SECURETHREAD BANKING SYSTEM       ");
            System.out.println("=======================================");
            System.out.println("1 -> New User? Create Account");
            System.out.println("2 -> Existing User? Login (Email + OTP)");
            System.out.println("3 -> Admin Login");
            System.out.println("4 -> Exit");
            System.out.print("Enter your choice: ");

            int choice = 0;
            try {
                while (true) {
                    choice = sc.nextInt();
                    System.out.print("\033[H\033[2J");
                    System.out.flush();
                    break;
                }
            } catch (Exception e) {
                sc.next();
            }
            switch (choice) {
                case 1:
                    createNewAccount(sc);
                    break;
                case 2:
                    customerLogin(sc);
                    break;
                case 3:
                    adminLogin(sc);
                    break;
                case 4:
                    System.out.println("Thank you for using our application! Visit again!!!");
                    Database.saveAccounts();
                    return;
                default:
                    System.out.println("Invalid choice! Try again.");
                    break;
            }
        }
    }

    public static void createNewAccount(Scanner sc) {
        System.out.println("\n=== CREATE NEW ACCOUNT ===");
        System.out.println("Enter name:");
        String name = sc.nextLine();
        String email;
        while (true) {
            System.out.print("Enter Email (must be lowercase@gmail.com): ");
            email = sc.nextLine().trim();
            if (email.matches("^[a-z0-9]+@gmail\\.com$")) {
                break;
            }
            System.out.println("Invalid email! Must be lowercase and end with @gmail.com");
        }
        double initialBalance;
        while (true) {
            System.out.print("Enter Initial Deposit (minimum ₹1000): ");
            initialBalance = sc.nextDouble();
            if (initialBalance >= 1000)
                break;
            System.out.println("Minimum Rs 1000 required!");
        }
        String pin, confirmPin;
        while (true) {
            System.out.print("Set 4-digit PIN: ");
            pin = sc.next();
            System.out.print("Confirm PIN: ");
            confirmPin = sc.next();
            if (pin.equals(confirmPin) && pin.matches("\\d{4}")) {
                break;
            }
            System.out.println("PINs don't match or not 4 digits! Try again.");
        }
        String accNo = String.format("%05d", Database.accounts.size() + 10001);
        Account a1 = new Account(accNo, name, email, initialBalance, pin);
        Database.accounts.put(accNo, a1);

        System.out.println("\nACCOUNT CREATED SUCCESSFULLY!");
        System.out.println("Account Number : " + accNo);
        System.out.println("Name           : " + name);
        System.out.println("Email          : " + email);
        System.out.println("Balance        : Rs" + initialBalance);
        System.out.println("Keep your Account Number & PIN safe!\n");
    }

    public static void customerLogin(Scanner sc) {
        sc.nextLine();

        System.out.println("\n" + "=".repeat(40));
        System.out.println("       SECURETHREAD BANK - LOGIN");
        System.out.println("=".repeat(40));

        System.out.print("Enter your Email: ");
        String email = sc.nextLine().trim();

        Account user = null;
        for (Account acc : Database.accounts.values()) {
            if (acc.getEmail().equalsIgnoreCase(email)) {
                user = acc;
                break;
            }
        }

        if (user == null) {
            System.out.println("Account not found!");
            return;
        }

        System.out.println("Welcome back, " + user.getCustomerName() + "!");
        System.out.println("Account No: " + user.getAccountNumber());

        for (int attempt = 1; attempt <= 3; attempt++) {
            System.out.print("Enter 4-digit PIN: ");
            String enteredPin = sc.next();

            if (enteredPin.equals(user.getPin())) {
                System.out.print("\033[H\033[2J");
                System.out.flush();
                System.out.println("LOGIN SUCCESSFUL!");
                System.out.println("Welcome to your account");
                CustomerMenu.showCustomerMenu(user, sc);
                return;
            } else {
                System.out.println("Wrong PIN! Attempts left: " + (3 - attempt));
                if (attempt == 3) {
                    System.out.println("Too many wrong attempts. Access Blocked.");
                    System.out.println("Contact Admin to reset PIN.");
                }
            }
        }
    }

    public static void adminLogin(Scanner sc) {
        AdminMenu.showAdminPanel(sc);
    }

}
