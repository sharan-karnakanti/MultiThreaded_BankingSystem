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

    public String getAccountNumber() {
        return accountNumber;
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
            miniStatement.add("Deposited: +₹" + amount + " | Balance: ₹" + balance);
            if (miniStatement.size() > 5) {
                miniStatement.remove(0);
            }
            System.out.println("Deposit successful! New balance: ₹" + balance);
        } else {
            System.out.println("Invalid amount! Must be greater than 0");
        }
    }

}
