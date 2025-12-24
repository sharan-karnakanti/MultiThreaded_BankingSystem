// File: src/com/bankingSystem/menu/AtmSimulation.java

package com.bankingSystem.menu;

import com.bankingSystem.model.Account;
import java.util.Scanner;

public class AtmSimulation {

    static class Atm implements Runnable {
        private final String name;
        private final Account account;

        public Atm(String name, Account account) {
            this.name = name;
            this.account = account;
        }

        @Override
        public void run() {
            for (int i = 0; i < 6; i++) {
                double amount = 1000 + Math.random() * 3000;

                if (Math.random() > 0.5) {
                    account.deposit(amount);
                    System.out.printf("%s -> Deposited +Rs%.2f%n", name, amount);
                } else {
                    if (account.getBalance() >= amount) {
                        account.withdraw(amount);
                        System.out.printf("%s -> Withdrew  -Rs%.2f%n", name, amount);
                    }
                }

                try {
                    Thread.sleep(400 + (int)(Math.random() * 400));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    public static void AtmDemo(Account user,Scanner sc) {
        System.out.println("\n" + "=".repeat(55));
        System.out.println("     3 ATMs ATTACKING YOUR ACCOUNT SIMULTANEOUSLY!");
        System.out.println("     Name      : " + user.getCustomerName());
        System.out.println("     Account No: " + user.getAccountNumber());
        System.out.printf ("     Start Balance : Rs%.2f%n", user.getBalance());
        System.out.println("=".repeat(55));

        Thread t1 = new Thread(new Atm("ATM-1", user));
        Thread t2 = new Thread(new Atm("ATM-2", user));
        Thread t3 = new Thread(new Atm("ATM-3", user));

        t1.start();
        t2.start();
        t3.start();

        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("=".repeat(55));
        System.out.println("           ATTACK FINISHED!");
        System.out.printf ("     Final Balance   : Rs%.2f%n", user.getBalance());
        System.out.println("     Your synchronized methods = BULLETPROOF!");
        System.out.println("=".repeat(55));
        System.out.print("\nPress Enter to continue...");
        sc.nextLine();
    }
}