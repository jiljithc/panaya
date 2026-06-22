package com.example.panaya;

import java.util.ArrayList;
import java.util.List;

public class BankAccount {
    private String accountHolderName;
    private String accountNumber;
    private double balance;
    private String statusMessage;
    
    // New: List to hold our transaction history
    private List<Transaction> transactionHistory;

    public BankAccount(String name, String number, double initialBalance) {
        this.accountHolderName = name;
        this.accountNumber = number;
        this.balance = initialBalance;
        this.statusMessage = "Welcome back, " + name + "!";
        this.transactionHistory = new ArrayList<>();
        
        // Record the initial deposit
        if (initialBalance > 0) {
            this.transactionHistory.add(new Transaction("Initial Deposit", initialBalance));
        }
    }

    // Getters
    public String getAccountHolderName() { return accountHolderName; }
    public String getAccountNumber() { return accountNumber; }
    public double getBalance() { return balance; }
    public String getStatusMessage() { return statusMessage; }
    public List<Transaction> getTransactionHistory() { return transactionHistory; }

    public void setStatusMessage(String message) { this.statusMessage = message; }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            transactionHistory.add(new Transaction("Deposit", amount));
            statusMessage = String.format("Successfully deposited: $%.2f", amount);
        } else {
            statusMessage = "Invalid amount. Deposit must be greater than 0.";
        }
    }

    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            transactionHistory.add(new Transaction("Withdrawal", amount));
            statusMessage = String.format("Successfully withdrew: $%.2f", amount);
        } else if (amount > balance) {
            statusMessage = String.format("Insufficient funds! Current balance: $%.2f", balance);
        } else {
            statusMessage = "Invalid amount. Withdrawal must be greater than 0.";
        }
    }

    // New: Method to handle transfers to another account
    public void transfer(BankAccount targetAccount, double amount) {
        if (amount > 0 && amount <= balance) {
            this.balance -= amount;
            targetAccount.balance += amount;
            
            // Log the transaction for both users
            this.transactionHistory.add(new Transaction("Transfer OUT to " + targetAccount.getAccountHolderName(), amount));
            targetAccount.transactionHistory.add(new Transaction("Transfer IN from " + this.accountHolderName, amount));
            
            this.statusMessage = String.format("Successfully transferred $%.2f to %s", amount, targetAccount.getAccountHolderName());
        } else if (amount > balance) {
            this.statusMessage = "Transfer failed: Insufficient funds.";
        } else {
            this.statusMessage = "Transfer failed: Invalid amount.";
        }
    }
}