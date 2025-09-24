package in.banking.cbs.action_service.service;

import in.banking.cbs.action_service.DTO.BankDto;
import in.banking.cbs.action_service.entity.Bank;
import in.banking.cbs.action_service.exception.NotFoundException;
import in.banking.cbs.action_service.repository.BankRepository;
import in.banking.cbs.action_service.utility.MapObject;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BankService {
    private final MapObject mapper;
    private final BankRepository bankRepository;

    public Bank createBank(BankDto bankDto) {

        Bank bank = mapper.mapDtoToBank(bankDto);

        return bankRepository.save(bank);
    }

    public void deleteBank(int bankId) {
        bankRepository.deleteById(bankId);
    }

    public Bank updateBank(int bankId,BankDto bankDto) {
        Optional<Bank> byName = bankRepository.findByBankName(bankDto.getName());
        if(byName.isPresent()){
            throw new RuntimeException("Bank name '"+ bankDto.getName()+"' already exits!");
        }
        Bank bank = bankRepository.findById(bankId).orElseThrow(() -> new NotFoundException("Bank Not Found With Id" + bankId));
        bank.setHeadOfficeAddress(bankDto.getHeadOfficeAddress());
        bank.setBankName(bankDto.getName());
        bank.setContactNumber(bankDto.getContactNumber());
        bank.setEmail(bankDto.getEmail());
        return bankRepository.save(bank);
    }
}
