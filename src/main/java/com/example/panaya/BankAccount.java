package com.example.panaya;
public class BankAccount {
    private String accountHolderName;
    private String accountNumber;
    private double balance;
    private String statusMessage;

    public BankAccount(String name, String number, double initialBalance) {
        this.accountHolderName = name;
        this.accountNumber = number;
        this.balance = initialBalance;
        this.statusMessage = "Welcome to the Web Bank!";
    }

    // Getters
    public String getAccountHolderName() { return accountHolderName; }
    public String getAccountNumber() { return accountNumber; }
    public double getBalance() { return balance; }
    public String getStatusMessage() { return statusMessage; }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            statusMessage = String.format("Successfully deposited: $%.2f", amount);
        } else {
            statusMessage = "Invalid amount. Deposit must be greater than 0.";
        }
    }

    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            statusMessage = String.format("Successfully withdrew: $%.2f", amount);
        } else if (amount > balance) {
            statusMessage = String.format("Insufficient funds! Current balance: $%.2f", balance);
        } else {
            statusMessage = "Invalid amount. Withdrawal must be greater than 0.";
        }
    }
}