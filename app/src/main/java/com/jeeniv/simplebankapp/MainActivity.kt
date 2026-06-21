package com.jeeniv.simplebankapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

// 1. The Bank Account Logic (State Holder)
class BankAccount(
    val accountHolderName: String,
    val accountNumber: String,
    initialBalance: Double
) {
    // mutableStateOf tells the UI to redraw whenever these values change
    var balance by mutableStateOf(initialBalance)
        private set

    var statusMessage by mutableStateOf("Welcome to Simple Bank!")
        private set

    // Method to deposit money
    fun deposit(amount: Double) {
        if (amount > 0) {
            balance += amount
            statusMessage = "Successfully deposited: $$amount"
        } else {
            statusMessage = "Invalid amount. Deposit must be greater than 0."
        }
    }

    // Method to withdraw money
    fun withdraw(amount: Double) {
        if (amount > 0 && amount <= balance) {
            balance -= amount
            statusMessage = "Successfully withdrew: $$amount"
        } else if (amount > balance) {
            statusMessage = "Insufficient funds! Current balance: $$balance"
        } else {
            statusMessage = "Invalid amount. Withdrawal must be greater than 0."
        }
    }

    // Method to set custom error messages for bad inputs
    fun setError(message: String) {
        statusMessage = message
    }
}

// 2. The Main Activity
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Create a test account
        val myAccount = BankAccount("John Doe", "123456789", 500.00)

        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Call our UI function
                    BankAppUI(account = myAccount)
                }
            }
        }
    }
}

// 3. The User Interface
@Composable
fun BankAppUI(account: BankAccount) {
    // Variable to hold the text typed into the input field
    var amountInput by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .padding(24.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Account Details Section
        Text(text = "--- Account Details are---", style = MaterialTheme.typography.titleLarge)
        Text(text = "Account Holder: ${account.accountHolderName}")
        Text(text = "Account Number: ${account.accountNumber}")

        // Formatted balance
        Text(
            text = "Current Balance: $${String.format("%.2f", account.balance)}",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Input Field
        OutlinedTextField(
            value = amountInput,
            onValueChange = { amountInput = it },
            label = { Text("Enter Amount ($)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        // Action Buttons
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = {
                    val amount = amountInput.toDoubleOrNull()
                    if (amount != null) {
                        account.deposit(amount)
                        amountInput = "" // Clear input field on success
                    } else {
                        account.setError("Please enter a valid number.")
                    }
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Deposit")
            }

            Button(
                onClick = {
                    val amount = amountInput.toDoubleOrNull()
                    if (amount != null) {
                        account.withdraw(amount)
                        amountInput = "" // Clear input field on success
                    } else {
                        account.setError("Please enter a valid number.")
                    }
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Withdraw")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Status Message (Updates dynamically based on actions)
        Text(
            text = account.statusMessage,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}