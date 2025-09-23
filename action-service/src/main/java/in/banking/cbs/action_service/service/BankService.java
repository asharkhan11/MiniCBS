package in.banking.cbs.action_service.service;

import in.banking.cbs.action_service.entity.Bank;
import in.banking.cbs.action_service.repository.BankRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BankService {

    private final BankRepository bankRepository;

    public Bank createBank(Bank bank) {
        return bankRepository.save(bank);
    }

}
