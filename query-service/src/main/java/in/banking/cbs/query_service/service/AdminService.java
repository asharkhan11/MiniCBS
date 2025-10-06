package in.banking.cbs.query_service.service;


import in.banking.cbs.query_service.DTO.AdminResponseDto;
import in.banking.cbs.query_service.DTO.Credential;
import in.banking.cbs.query_service.DTO.Roles;
import in.banking.cbs.query_service.client.SecurityServiceClient;
import in.banking.cbs.query_service.entity.Admin;
import in.banking.cbs.query_service.entity.Bank;
import in.banking.cbs.query_service.entity.Branch;
import in.banking.cbs.query_service.entity.Employee;
import in.banking.cbs.query_service.exception.NotFoundException;
import in.banking.cbs.query_service.repository.*;
import in.banking.cbs.query_service.security.LoggedInUser;
import in.banking.cbs.query_service.utility.Helper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AdminService {

    @Autowired
    private final BankRepository bankRepository;
    private final BranchRepository branchRepository;
    private final SecurityServiceClient securityServiceClient;
    private final AdminRepository adminRepository;
    private final LoggedInUser loggedInUser;
    private final EmployeeRepository employeeRepository;
    private final Helper helper;
    private final CustomerRepository customerRepository;


    //Bank
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
        if (banks.isEmpty()) {
            throw new NotFoundException("No banks found at address: " + headOfficeAddress);
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


    //Branch
    public Branch getBranchById(int branchId) {
        Branch branch = branchRepository.findById(branchId)
                .orElseThrow(() -> new NotFoundException("Branch not found with id: " + branchId));
        return branch;
    }

    public Branch getBranchByName(String branchName) {
        Branch branch = branchRepository.findByBranchName(branchName)
                .orElseThrow(() -> new NotFoundException("Branch not found with name: " + branchName));
        return branch;
    }

    public Branch getBranchByIfsc(String ifscCode) {
        Branch branch = branchRepository.findByIfscCode(ifscCode)
                .orElseThrow(() -> new NotFoundException("Branch not found with IFSC: " + ifscCode));
        return branch;
    }

    public List<Branch> getAllBranches() {
        return branchRepository.findAll().stream().toList();
    }

    public List<Branch> getBranchesByCity(String city) {
        return branchRepository.findByCity(city).stream().toList();
    }

    public List<Branch> getBranchesByState(String state) {
        return branchRepository.findByState(state).stream().toList();
    }

    public List<Branch> getBranchesByAddress(String address) {
        return branchRepository.findByAddress(address);
    }

    public Branch getBranchByContactNumber(String contactNumber) {
        return branchRepository.findByContactNumber(contactNumber)
                .orElseThrow(() -> new NotFoundException("Branch not found with contact number: " + contactNumber));
    }

    public List<Branch> getBranchesByManager(Employee manager) {
        return branchRepository.findByManager(manager);
    }

    public List<Branch> getBranchesByBank(Bank bank) {
        return branchRepository.findByBank(bank);
    }

    public AdminResponseDto getMyDetails(){
        AdminResponseDto adminResponseDto = new AdminResponseDto();
        Admin admin = loggedInUser.getLoggedInAdmin();

        int credentialId = admin.getCredentialId();

        Credential credential = securityServiceClient.getCredentialById(credentialId);

        String email = credential.getUsername();
        String password = credential.getPassword();
        Set<Roles> roles = credential.getRoles();

        adminResponseDto.setFirstName(admin.getFirstName());
        adminResponseDto.setLastName(admin.getLastName());
        adminResponseDto.setDob(admin.getDob());
        adminResponseDto.setEmail(email);
        adminResponseDto.setPassword(password);
        adminResponseDto.setRoles(roles);
        adminResponseDto.setAddress(admin.getAddress());
        adminResponseDto.setPhone(admin.getPhone());
        return adminResponseDto;
    }
}
