package com.bankingSystem.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Account implements Serializable {
    private static final long serialVersionUID = 1L;
    private String accountNumber;
    private String customerName;
    private String email;
    private double balance;
    private String pin;
    private List<String> miniStatement = new ArrayList<>();
    private int otpAttempts;
    private boolean isLocked;
    private long lockTime;

    public Account(String accountNumber, String Name, String email, double initialBalance, String pin) {
        this.accountNumber = accountNumber;
        this.customerName = Name;
        this.email = email;
        this.balance = initialBalance;
        this.pin = pin;
    }

    public void setPin(String newPin) {
        this.pin = newPin;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getPin(){
        return pin;
    }
    public List getMiniStatement() {
        return miniStatement;
    }

    public String getCustomerName() {
        return customerName;
    }

    public double getBalance() {
        return balance;
    }

    public String getEmail() {
        return email;
    }

    public synchronized void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            miniStatement.add("Deposited: +Rs" + String.format("%.2f", amount) + " | Balance: Rs "
                    + String.format("%.2f", balance));
            if (miniStatement.size() > 5) {
                miniStatement.remove(0);
            }
            System.out.printf("Deposit successful! New balance: Rs  %.2f%n", balance);
        } else {
            System.out.println("Invalid amount! Must be greater than 0");
        }
    }

    public synchronized void withdraw(double amount) {
        if (amount > 0 && balance > amount) {
            balance -= amount;
            miniStatement.add("Withdraw: -Rs" + String.format("%.2f", amount) + " | Balance : Rs "
                    + String.format("%.2f", balance));
            if (miniStatement.size() > 5) {
                miniStatement.remove(0);
            }
            System.out.printf("WithDraw Sucessfull! New Balance : Rs %.2f%n", balance);
        } else {
            System.out.println("Insufficient amount! pls enter a within the range of amount");
        }

    }

    public synchronized void Transfer(Account self, Account frndacc, double amount) {
        if (amount > 0) {
            balance += amount;
            miniStatement.add("Deposited: +Rs " + String.format("%.2f", amount) + " | Balance: Rs "
                    + String.format("%.2f", balance));
            if (miniStatement.size() > 5) {
                miniStatement.remove(0);
            }
            System.out.printf("Deposit successful! New balance: Rs  %.2f%n", balance);
        } else {
            System.out.println("Invalid amount! Must be greater than 0");
        }
    }

    // Sender's side
    public synchronized void transferOut(Account toAccount, double amount) {
        if (amount <= 0) {
            System.out.println("Amount must be positive!");
            return;
        }
        if (balance < amount) {
            System.out.println("Insufficient balance for transfer!");
            return;
        }

        balance -= amount;
        miniStatement.add("Transferred -> " + toAccount.getCustomerName() +
                " -Rs " + String.format("%.2f", amount) +
                " | Bal: Rs " + String.format("%.2f", balance));
        if (miniStatement.size() > 5)
            miniStatement.remove(0);

        System.out.println("You sent Rs " + String.format("%.2f", amount) +
                " to " + toAccount.getCustomerName());
    }

    // Receiver's side
    public synchronized void transferIn(Account fromAccount, double amount) {
        if (amount <= 0)
            return;

        balance += amount;
        miniStatement.add("Received <- " + fromAccount.getCustomerName() +
                " +Rs " + String.format("%.2f", amount) +
                " | Bal: Rs " + String.format("%.2f", balance));
        if (miniStatement.size() > 5)
            miniStatement.remove(0);

        System.out.println("You received Rs " + String.format("%.2f", amount) +
                " from " + fromAccount.getCustomerName());
    }
}
