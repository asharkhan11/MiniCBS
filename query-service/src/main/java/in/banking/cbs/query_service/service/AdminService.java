package in.banking.cbs.query_service.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import in.banking.cbs.query_service.DTO.*;
import in.banking.cbs.query_service.client.SecurityServiceClient;
import in.banking.cbs.query_service.entity.*;
import in.banking.cbs.query_service.exception.AlreadyExistsException;
import in.banking.cbs.query_service.exception.NotFoundException;
import in.banking.cbs.query_service.exception.UnAuthorizedException;
import in.banking.cbs.query_service.repository.*;
import in.banking.cbs.query_service.security.LoggedInUser;
import in.banking.cbs.query_service.utility.Helper;
import in.banking.cbs.query_service.utility.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final ObjectMapper objectMapper;
    private final BankRepository bankRepository;
    private final BranchRepository branchRepository;
    private final SecurityServiceClient securityServiceClient;
    private final AdminRepository adminRepository;
    private final LoggedInUser loggedInUser;
    private final EmployeeRepository employeeRepository;
    private final Helper helper;
    private final CustomerRepository customerRepository;

}
