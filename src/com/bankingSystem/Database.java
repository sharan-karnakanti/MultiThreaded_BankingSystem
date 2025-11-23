package com.bankingSystem;

import com.bankingSystem.model.Account;

import java.util.HashMap;
import java.io.*;

public class Database {
    public static HashMap<String, Account> accounts = new HashMap<>();

    public static void loadAccounts() {
        try {
            FileInputStream fis = new FileInputStream("accounts.ser");
            ObjectInputStream ois = new ObjectInputStream(fis);
            accounts = (HashMap<String, Account>) ois.readObject();
            fis.close();
            ois.close();
        } catch (Exception e) {
            System.out.println("No previous records found! starting fresh");
        }

    }

    public static void saveAccounts() {
        try{
            FileOutputStream fos = new FileOutputStream("accounts.ser");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(accounts);
            oos.close();
            fos.close();
            System.out.println("Data saved sucessfully!!!");
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public static Account getAccount(String accNo) {
            return accounts.get(accNo);
    }

}
