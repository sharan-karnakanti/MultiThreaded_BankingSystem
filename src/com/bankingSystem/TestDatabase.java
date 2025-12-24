
package com.bankingSystem;

import com.bankingSystem.model.Account;

public class TestDatabase {
    public static void main(String[] args) {
        
        // Step 1: Load existing data (or start fresh)
        Database.loadAccounts();

        // Step 2: Create your FIRST real account
        Account a1 = new Account("10001", "Aarav Sharma", "aarav@gmail.com", 5000.0, "1234");
        
        // Step 3: Add it to the bank
        Database.accounts.put("10001", a1);
        
        System.out.println("Created account for " + a1.getCustomerName());
        System.out.println("Balance: ₹" + a1.getBalance());

        // Step 4: Save to file
        Database.saveAccounts();

        System.out.println("\nProgram ended. Close it now.");
        System.out.println("Run again → Your account will STILL be there!");
    }
}