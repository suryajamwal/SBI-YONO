package yono.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import yono.dto.*;
import yono.entity.BankServiceEntity;
import yono.entity.UserAccount;
import yono.exception.DuplicateResourceException;
import yono.exception.InsufficientFundsException;
import yono.exception.InvalidOperationException;
import yono.exception.ResourceNotFoundException;
import yono.repository.BankServiceRepository;
import yono.repository.UserAccountRepository;

import java.util.HashMap;
import java.util.Map;

@Service
public class AccountService {

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private BankServiceRepository bankServiceRepository;

    private UserAccount getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        return userAccountRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Logged in user not found"));
    }

    public String applyForService(ServiceRequest request) {
        UserAccount user = getCurrentUser();

        boolean alreadyExists = bankServiceRepository
                .existsByUserAccountAndServiceType(user, request.getServiceType());

        if (alreadyExists) {
            throw new InvalidOperationException("You already have this service: " + request.getServiceType());
        }

        BankServiceEntity bankService = BankServiceEntity.builder()
                .serviceType(request.getServiceType())
                .userAccount(user)
                .build();

        bankServiceRepository.save(bankService);

        return request.getServiceType() + " activated successfully";
    }

    public String deposit(DepositRequest request) {
        UserAccount user = getCurrentUser();

        user.setBalance(user.getBalance().add(request.getAmount()));
        userAccountRepository.save(user);

        return "Amount deposited successfully. New balance: " + user.getBalance();
    }

    public String withdraw(WithdrawRequest request) {
        UserAccount user = getCurrentUser();

        if (user.getBalance().compareTo(request.getAmount()) < 0) {
            throw new InsufficientFundsException("Insufficient funds");
        }

        user.setBalance(user.getBalance().subtract(request.getAmount()));
        userAccountRepository.save(user);

        return "Amount withdrawn successfully. New balance: " + user.getBalance();
    }

    @Transactional
    public String transfer(TransferRequest request) {
        UserAccount sender = getCurrentUser();

        if (sender.getAccountNumber().equals(request.getReceiverAccountNumber())) {
            throw new InvalidOperationException("You cannot transfer money to your own account");
        }

        UserAccount receiver = userAccountRepository.findByAccountNumber(request.getReceiverAccountNumber())
                .orElseThrow(() -> new ResourceNotFoundException("Receiver account not found"));

        if (sender.getBalance().compareTo(request.getAmount()) < 0) {
            throw new InsufficientFundsException("Insufficient funds");
        }

        sender.setBalance(sender.getBalance().subtract(request.getAmount()));
        receiver.setBalance(receiver.getBalance().add(request.getAmount()));

        userAccountRepository.save(sender);
        userAccountRepository.save(receiver);

        return "Transfer successful. New balance: " + sender.getBalance();
    }

    public String updateContact(UpdateContactRequest request) {
        UserAccount user = getCurrentUser();

        if (!user.getEmail().equals(request.getEmail())
                && userAccountRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Email already exists");
        }

        if (!user.getMobileNumber().equals(request.getMobileNumber())
                && userAccountRepository.existsByMobileNumber(request.getMobileNumber())) {
            throw new DuplicateResourceException("Mobile number already exists");
        }

        user.setEmail(request.getEmail());
        user.setMobileNumber(request.getMobileNumber());

        userAccountRepository.save(user);

        return "Email and mobile number updated successfully";
    }

    public Map<String, Object> checkBalance() {
        UserAccount user = getCurrentUser();

        Map<String, Object> response = new HashMap<>();
        response.put("username", user.getUsername());
        response.put("accountNumber", user.getAccountNumber());
        response.put("balance", user.getBalance());

        return response;
    }

    public Map<String, Object> getMyProfile() {
        UserAccount user = getCurrentUser();

        Map<String, Object> response = new HashMap<>();
        response.put("username", user.getUsername());
        response.put("email", user.getEmail());
        response.put("mobileNumber", user.getMobileNumber());
        response.put("accountNumber", user.getAccountNumber());
        response.put("balance", user.getBalance());
        response.put("active", user.isActive());

        return response;
    }

    public String closeAccount() {
        UserAccount user = getCurrentUser();

        user.setActive(false);
        userAccountRepository.save(user);

        return "Account closed successfully";
    }
}