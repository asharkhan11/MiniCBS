package in.banking.cbs.query_service.service;


import in.banking.cbs.query_service.entity.*;
import in.banking.cbs.query_service.exception.NotFoundException;
import in.banking.cbs.query_service.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    @Autowired
    private final BankRepository bankRepository;

    public Bank getBankByName(String name) {
        return bankRepository.findByBankNameIgnoreCase(name)
                .orElseThrow(() -> new NotFoundException("Bank not found with this name: " + name));
    }

    public Bank getBankByEmail(String email) {
        return bankRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Bank not found with email: " + email));
    }

    public List<Bank> getBanksByAddress(String headOfficeAddress) {
        List<Bank> banks = bankRepository.findByHeadOfficeAddressIgnoreCase(headOfficeAddress);
        if(banks.isEmpty()) {
            throw new NotFoundException ("No banks found at address: " + headOfficeAddress);
        }
        return banks;
    }

    public List<Bank> getBanksByAddressKeyword(String keyword) {
        List<Bank> banks = bankRepository.findByHeadOfficeAddressContainingIgnoreCase(keyword);
        if (banks.isEmpty()) {
            throw new NotFoundException("No banks found containing keyword in address: " + keyword);
        }
        return banks;
    }
}
