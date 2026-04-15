package yono.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import yono.dto.AuthResponse;
import yono.dto.LoginRequest;
import yono.dto.RegisterRequest;
import yono.entity.UserAccount;
import yono.exception.DuplicateResourceException;
import yono.exception.InvalidOperationException;
import yono.repository.UserAccountRepository;
import yono.security.JwtService;

import java.math.BigDecimal;
import java.util.Random;

@Service
public class AuthService {

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    public String register(RegisterRequest request) {

        if (request.getInitialDeposit() == null ||
                request.getInitialDeposit().compareTo(new BigDecimal("1000")) < 0) {
            throw new InvalidOperationException("Can't open 0 balance account. Minimum deposit should be 1000");
        }

        if (userAccountRepository.existsByUsername(request.getUsername())) {
            throw new DuplicateResourceException("Username already exists");
        }

        if (userAccountRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Email already exists");
        }

        if (userAccountRepository.existsByMobileNumber(request.getMobileNumber())) {
            throw new DuplicateResourceException("Mobile number already exists");
        }

        UserAccount user = UserAccount.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .mobileNumber(request.getMobileNumber())
                .email(request.getEmail())
                .accountNumber(generateAccountNumber())
                .balance(request.getInitialDeposit())
                .active(true)
                .build();

        userAccountRepository.save(user);

        return "Account created successfully. Account Number: " + user.getAccountNumber();
    }

    public AuthResponse login(LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        String token = jwtService.generateToken(request.getUsername());

        return new AuthResponse(token, "Login successful");
    }

    private String generateAccountNumber() {
        Random random = new Random();
        String accountNumber;

        do {
            accountNumber = "SBI" + (100000000 + random.nextInt(900000000));
        } while (userAccountRepository.findByAccountNumber(accountNumber).isPresent());

        return accountNumber;
    }
}