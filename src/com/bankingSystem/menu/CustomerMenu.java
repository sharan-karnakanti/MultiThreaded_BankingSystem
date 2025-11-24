package com.bankingSystem.menu;

import java.util.List;
import java.util.Scanner;
import com.bankingSystem.MainMenu;
import com.bankingSystem.Database;
import com.bankingSystem.model.Account;

public class CustomerMenu {

    public static void showCustomerMenu(Account user, Scanner sc) {

        System.out.println("\n=== WELCOME BACK, " + user.getCustomerName().toUpperCase() + "  ===");
        System.out.print("Acccount Number: " + user.getAccountNumber());

        while (true) {
            System.out.println("\n1 -> Check Balance");
            System.out.println("2 -> Deposite Money");
            System.out.println("3 -> Withdraw Money");
            System.out.println("4 -> Transfer Money");
            System.out.println("5 -> Mini Statement");
            System.out.println("6 -> ATM Simulation(Multi-threaded Demo");
            System.out.println("7 -> Logout");

            int n = sc.nextInt();
            System.out.print("\033[H\033[2J");
            System.out.flush();

            switch (n) {
                case 1:
                    System.out.printf("Current Balance : Rs %.2f%n", user.getBalance());
                    break;
                case 2:
                    depositMoney(user, sc);
                    break;
                case 3:
                    withdrawMoney(user, sc);
                    break;

                case 4:
                    transferMoney(user, sc);
                    break;

                case 5:
                    showMiniStatement(user, sc);
                    break;

                case 6:
                    AtmSimulation.AtmDemo(user, sc);
                    break;

                case 7:
                    System.out.println("Logged out sucessfully!!!");
                    return;
                default:
                    System.out.println("Enter Correct Option!!!(1-7)");
                    break;
            }
        }
    }

    public static void showMiniStatement(Account user, Scanner sc) {
        System.out.println("\n=====================================");
        System.out.println("     MINI STATEMENT     " + user.getCustomerName().toUpperCase()+"     Acc No: " + user.getAccountNumber());
        // System.out.println(" " );
        // System.out.println("     Acc No: " + user.getAccountNumber());
        System.out.println("=====================================");

        List<String> stmt = user.getMiniStatement();

        if (stmt.isEmpty()) {
            System.out.println("     No transactions yet.\n");
        } else {
            System.out.println("     Recent Transactions:");
            for (int i = stmt.size() - 1; i >= 0; i--) {
                System.out.println("     Rs " + stmt.get(i));
            }
            System.out.println();
        }

        System.out.println("     Current Balance: Rs" + String.format("%.2f", user.getBalance()));
        System.out.println("=====================================\n");
        System.out.print("Press Enter to continue...");
        sc.nextLine();
        sc.nextLine();
    }

    public synchronized static void withdrawMoney(Account user, Scanner sc) {
        System.out.println("Enter amount to Withdraw :");
        try {
            double amount = sc.nextDouble();
            user.withdraw(amount);
        } catch (Exception e) {
            sc.next();
            // TODO: handle exception
            System.out.println("Invalid input! Only numbers allowed.");
        }

    }

    public synchronized static void depositMoney(Account user, Scanner sc) {

        System.out.println("Enter amount to deposite :");
        try {
            double amount = sc.nextDouble();
            user.deposit(amount);
        } catch (Exception e) {
            sc.next();
            System.out.println("Invalid amount,Enter amount to deposite again:");
        }
    }

    public synchronized static void transferMoney(Account fromUser, Scanner sc) {
        System.out.println("Transfer Money to your frnd!");
        String toAccNo;
        Account toAcc = null;
        try {
            while (true) {
                System.out.println("Enter account Number to Transfer Money: ");
                toAccNo = sc.next();
                for (Account acc : Database.accounts.values()) {
                    if (acc.getAccountNumber().equals(toAccNo)) {
                        toAcc = acc;
                        break;
                    }
                }
                if (toAcc == null) {
                    System.out.println("Invalid User Account Num, plz check and enter again");
                } else {
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("Enter correct Value!!");
        }
        while (true) {
            System.out.println("Enter amount to send: ");
            double amount = sc.nextDouble();
            if (amount > fromUser.getBalance() || amount <= 0) {
                System.out.println("plz enter correct amount!!");
            } else {
                String acc1 = fromUser.getAccountNumber();
                String acc2 = toAcc.getAccountNumber();

                Account first, second;

                // Decide who has smaller account number
                if (acc1.compareTo(acc2) < 0) {
                    first = fromUser;
                    second = toAcc;
                } else {
                    first = toAcc;
                    second = fromUser;
                }

                synchronized (first) {
                    synchronized (second) {
                        // NOW 100% SAFE — no one can interrupt!
                        fromUser.transferOut(toAcc, amount);

                        // Receiver uses transferIn → shows "Received from..."
                        toAcc.transferIn(fromUser, amount); // adds safely
                    }
                }
            }
            System.out.println("\nTRANSFER SUCCESSFUL!");
            System.out.println(
                    "" + amount + " sent to " + toAcc.getCustomerName() + " (" + toAcc.getAccountNumber() + ")");
            break;
        }
    }
}