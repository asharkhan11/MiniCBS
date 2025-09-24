package in.banking.cbs.action_service.service;

import in.banking.cbs.action_service.DTO.BankDto;
import in.banking.cbs.action_service.entity.Bank;
import in.banking.cbs.action_service.repository.BankRepository;
import in.banking.cbs.action_service.utility.MapObject;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BankService {
    private final MapObject mapper;
    private final BankRepository bankRepository;

    public Bank createBank(BankDto bankDto) {

        Bank bank = mapper.mapDtoToBank(bankDto);

        return bankRepository.save(bank);
    }

}
