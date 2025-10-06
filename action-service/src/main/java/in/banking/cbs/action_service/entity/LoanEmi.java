package in.banking.cbs.action_service.entity;


import in.banking.cbs.action_service.utility.EmiFrequency;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class LoanEmi {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "emi_seq")
    @SequenceGenerator(name = "emi_seq", sequenceName = "emi_seq")
    private int emiId;

    private int totalEmi;

    private double emiAmount;

    private int interestRate;

    private int emiPaid;

    private LocalDate nextEmiDate;

    private double remainingAmount;

    private int dueEmiCount;

    @Builder.Default
    private EmiFrequency frequency = EmiFrequency.MONTHLY;
}
