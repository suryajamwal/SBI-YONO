package yono.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import yono.enums.ServiceType;

@Entity
@Table(
        name = "bank_services",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"user_id", "service_type"})
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BankServiceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "service_type", nullable = false)
    private ServiceType serviceType;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserAccount userAccount;
}