package yono.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import yono.dto.*;
import yono.service.AccountService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/service")
    public Map<String, String> applyForService(@Valid @RequestBody ServiceRequest request) {
        Map<String, String> response = new HashMap<>();
        response.put("message", accountService.applyForService(request));
        return response;
    }

    @PostMapping("/deposit")
    public Map<String, String> deposit(@Valid @RequestBody DepositRequest request) {
        Map<String, String> response = new HashMap<>();
        response.put("message", accountService.deposit(request));
        return response;
    }

    @PostMapping("/withdraw")
    public Map<String, String> withdraw(@Valid @RequestBody WithdrawRequest request) {
        Map<String, String> response = new HashMap<>();
        response.put("message", accountService.withdraw(request));
        return response;
    }

    @PostMapping("/transfer")
    public Map<String, String> transfer(@Valid @RequestBody TransferRequest request) {
        Map<String, String> response = new HashMap<>();
        response.put("message", accountService.transfer(request));
        return response;
    }

    @PutMapping("/update-contact")
    public Map<String, String> updateContact(@Valid @RequestBody UpdateContactRequest request) {
        Map<String, String> response = new HashMap<>();
        response.put("message", accountService.updateContact(request));
        return response;
    }

    @GetMapping("/balance")
    public Map<String, Object> checkBalance() {
        return accountService.checkBalance();
    }

    @GetMapping("/me")
    public Map<String, Object> getMyProfile() {
        return accountService.getMyProfile();
    }

    @DeleteMapping("/close")
    public Map<String, String> closeAccount() {
        Map<String, String> response = new HashMap<>();
        response.put("message", accountService.closeAccount());
        return response;
    }
}