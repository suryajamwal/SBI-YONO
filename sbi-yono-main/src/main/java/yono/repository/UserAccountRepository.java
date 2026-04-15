package yono.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yono.entity.UserAccount;

import java.util.Optional;

public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {

    Optional<UserAccount> findByUsername(String username);

    Optional<UserAccount> findByEmail(String email);

    Optional<UserAccount> findByAccountNumber(String accountNumber);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsByMobileNumber(String mobileNumber);
}