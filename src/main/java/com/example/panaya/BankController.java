package com.example.panaya;



import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BankController {

    // Simulating a database with a single in-memory account
    private BankAccount myAccount = new BankAccount("John Doe", "123456789", 500.00);

    // This handles the main page load
    @GetMapping("/")
    public String viewHomePage(Model model) {
        // Send the account data to the HTML template
        model.addAttribute("account", myAccount);
        return "index"; // This corresponds to index.html
    }

    // This handles the form submission when a user clicks Deposit or Withdraw
    @PostMapping("/transaction")
    public String processTransaction(@RequestParam String action, @RequestParam double amount) {
        if ("deposit".equals(action)) {
            myAccount.deposit(amount);
        } else if ("withdraw".equals(action)) {
            myAccount.withdraw(amount);
        }
        
        // Redirect back to the home page after processing
        return "redirect:/";
    }
}