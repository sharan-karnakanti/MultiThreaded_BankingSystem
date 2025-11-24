
package com.bankingSystem.menu;

import com.bankingSystem.Database;
import com.bankingSystem.model.Account;
import java.util.Scanner;

public class AdminMenu {

    private static final String ADMIN_EMAIL = "admin@gmail.com";
    private static final String ADMIN_PIN = "0000"; // Change if you want

    public static void showAdminPanel(Scanner sc) {
        System.out.print("Enter Admin Email: ");
        String email = sc.next();
        sc.nextLine();
        System.out.print("Enter Admin PIN: ");
        String pin = sc.next();

        if (!email.equals(ADMIN_EMAIL) || !pin.equals(ADMIN_PIN)) {
            System.out.println("Access Denied! Wrong credentials.");
            return;
        }

        System.out.println("\nADMIN LOGIN SUCCESSFUL!");
        while (true) {
            System.out.println("\n" + "=".repeat(50));
            System.out.println("           SECURETHREAD BANK - ADMIN PANEL");
            System.out.println("=".repeat(50));
            System.out.println("1 -> View All Customers");
            System.out.println("2 -> Delete Customer Account");
            System.out.println("3 -> Total Bank Money");
            System.out.println("4 -> Change User PIN"); 
            System.out.println("5 -> Logout");
            System.out.print("Choose option: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1 -> viewAllCustomers();
                case 2 -> deleteCustomer(sc);
                case 3 -> showTotalBankMoney();
                case 4 -> changeUserPin(sc);
                case 5 -> {
                    System.out.println("Admin Logged Out.");
                    return;
                }
                default -> System.out.println("Invalid option!");
            }
        }
    }

    private static void viewAllCustomers() {
        System.out.println("\nALL CUSTOMERS (" + Database.accounts.size() + ")");
        System.out.println("-".repeat(60));
        System.out.printf("%-12s %-20s %-15s %s%n", "Acc No", "Name", "Email", "Balance");
        System.out.println("-".repeat(60));

        for (Account acc : Database.accounts.values()) {
            System.out.printf("%-12s %-20s %-15s Rs %.2f%n",
                    acc.getAccountNumber(),
                    acc.getCustomerName(),
                    acc.getEmail(),
                    acc.getBalance());
        }
        System.out.println("-".repeat(60));
    }

    private static void deleteCustomer(Scanner sc) {
        System.out.print("Enter Account Number to DELETE: ");
        String accNo = sc.next();
        Account acc = Database.accounts.get(accNo);

        if (acc == null) {
            System.out.println("Account not found!");
            return;
        }

        System.out.println("Customer Found: " + acc.getCustomerName());
        System.out.print("Type 'DELETE' to confirm: ");
        sc.nextLine();
        String confirm = sc.nextLine();

        if (confirm.equalsIgnoreCase("DELETE")) {
            Database.accounts.remove(accNo);
            System.out.println("ACCOUNT DELETED SUCCESSFULLY!");
        } else {
            System.out.println("Deletion cancelled.");
        }
    }

    private static void showTotalBankMoney() {
        double total = 0;
        for (Account acc : Database.accounts.values()) {
            total += acc.getBalance();
        }
        System.out.println("\nTOTAL MONEY IN BANK: Rs " + String.format("%.2f", total));
    }
    private static void changeUserPin(Scanner sc) {
    System.out.print("Enter Account Number: ");
    String accNo = sc.next();
    
    Account user = Database.accounts.get(accNo);
    
    if (user == null) {
        System.out.println("Account not found!");
        return;
    }

    System.out.println("Customer Found: " + user.getCustomerName());
    System.out.println("Current PIN: **** (hidden)");

    System.out.print("Enter NEW 4-digit PIN: ");
    String newPin = sc.next();

    if (!newPin.matches("\\d{4}")) {
        System.out.println("PIN must be exactly 4 digits!");
        return;
    }

    System.out.print("Confirm NEW PIN: ");
    String confirm = sc.next();

    if (!newPin.equals(confirm)) {
        System.out.println("PINs do not match!");
        return;
    }


    user.setPin(newPin);  

    System.out.println("PIN CHANGED SUCCESSFULLY FOR " + user.getCustomerName());
    System.out.println("New PIN: " + newPin + " (Keep it secret!)");
}
}