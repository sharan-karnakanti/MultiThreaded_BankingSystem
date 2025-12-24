# SecureThread Bank 🏦

A **thread-safe, persistent, console-based banking system** developed using **Core Java** to demonstrate real-world banking operations, synchronization, and data persistence.

---

## 📌 Project Overview

**SecureThread Bank** is a Java-based banking application designed to simulate essential banking operations while ensuring **thread safety**, **data consistency**, and **persistence across program restarts**.

This project focuses on:
- Safe concurrent access to shared resources
- Deadlock-free money transfer
- File-based data storage using serialization
- Modular and maintainable code structure

It was built as a **learning-oriented yet production-inspired system**, showcasing practical Java concepts beyond basic CRUD applications.

---

## ✨ Features

| Feature | Description |
|------|------------|
| User Registration | Create an account using email and 4-digit PIN |
| Secure Login | Email + PIN authentication with limited attempts |
| Deposit & Withdraw | Thread-safe balance updates |
| Money Transfer | Deadlock-free transfers using ordered locking |
| Mini Statement | Displays last 5 transactions |
| ATM Simulation | Multi-threaded ATM access to verify synchronization |
| Admin Panel | View users, delete accounts, change PINs |
| Data Persistence | Accounts stored using Java Serialization |
| Unique Account Numbers | Account numbers remain unique across restarts |

---

## 🛡️ Thread Safety & Concurrency

- All balance-modifying operations are synchronized
- Transfers use **consistent lock ordering** to prevent deadlocks
- ATM simulation demonstrates concurrent access safety
- Ensures data consistency under multi-threaded execution

---

## 💾 Data Persistence

- Uses Java **Serialization**
- Account data is stored in `accounts.ser`
- Data remains intact after application restart
- Account numbers are never reused

---

## 📂 Project Structure

src/
└── com/
└── bankingSystem/
├── MainMenu.java
├── Database.java
├── model/
│ └── Account.java
├── menu/
│ ├── CustomerMenu.java
│ └── AdminMenu.java
└── demo/
└── AtmSimulation.java

accounts.ser (auto-generated after first run)



---

## ⚙️ How to Run the Project

### Compile
```bash
javac -d . src/com/bankingSystem/**/*.java
 To Run:
java com.bankingSystem.MainMenu

```

---

Default Admin Credentials

Email: admin@gmail.com

PIN: 0000


🎯 Learning Outcomes

This project helped strengthen understanding of:

Java multithreading and synchronization

Deadlock prevention strategies

File handling and serialization

Object-oriented design

Console-based application flow

👨‍💻 Author

K. Sharan Kumar
Java Developer | Backend & Core Java Enthusiast

GitHub: https://github.com/sharan-karnakanti

📌 Future Enhancements

Database integration (MySQL / PostgreSQL)

Password hashing

GUI or Web interface

Role-based access control

Transaction export

⭐ If you find this project useful, feel free to star the repository!

