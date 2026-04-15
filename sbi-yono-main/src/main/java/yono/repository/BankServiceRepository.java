package yono.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yono.entity.BankServiceEntity;
import yono.entity.UserAccount;
import yono.enums.ServiceType;

public interface BankServiceRepository extends JpaRepository<BankServiceEntity, Long> {

    boolean existsByUserAccountAndServiceType(UserAccount userAccount, ServiceType serviceType);
}