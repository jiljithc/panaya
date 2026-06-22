package com.example.panaya;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

@Controller
public class BankController {

    // Simulating a database of accounts
    private Map<String, BankAccount> accountDatabase = new HashMap<>();
    
    // The account you are currently "logged into"
    private String activeAccountNumber = "1001"; 

    public BankController() {
        // Create two accounts when the server starts so we can test transfers
        accountDatabase.put("1001", new BankAccount("John Doe", "1001", 500.00));
        accountDatabase.put("1002", new BankAccount("Jane Smith", "1002", 250.00));
    }

    @GetMapping("/")
    public String viewHomePage(Model model) {
        BankAccount myAccount = accountDatabase.get(activeAccountNumber);
        model.addAttribute("account", myAccount);
        return "index"; 
    }

    @PostMapping("/transaction")
    public String processTransaction(
            @RequestParam String action, 
            @RequestParam double amount,
            @RequestParam(required = false) String targetAccountNumber) {
            
        BankAccount myAccount = accountDatabase.get(activeAccountNumber);

        if ("deposit".equals(action)) {
            myAccount.deposit(amount);
        } else if ("withdraw".equals(action)) {
            myAccount.withdraw(amount);
        } else if ("transfer".equals(action)) {
            BankAccount targetAccount = accountDatabase.get(targetAccountNumber);
            if (targetAccount != null && !targetAccount.getAccountNumber().equals(myAccount.getAccountNumber())) {
                myAccount.transfer(targetAccount, amount);
            } else {
                myAccount.setStatusMessage("Transfer failed: Account not found or invalid.");
            }
        }
        
        return "redirect:/";
    }
}