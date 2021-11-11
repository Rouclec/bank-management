package com.example.test.app.service;

import com.example.test.app.models.*;
import com.example.test.app.repository.*;
import com.example.test.app.request.CreateAdminRequest;
import com.example.test.app.request.CreateProductRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class AdminService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ShorteeRepository shorteeRepository;



    @Autowired
    AccountRepository accountRepository;

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    WithdrawalRepository withdrawalRepository;

    @Autowired
    TransferRepository transferRepository;

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    public Product createProduct(CreateProductRequest createProductRequest){
        Product product = new Product();
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        product.setCreatedBy(username);
        product.setCreatedOn(LocalDateTime.now());
        product.setProductName(createProductRequest.getProductName());
        product.setDescription(createProductRequest.getDescription());
        product.setMaximumAmount(createProductRequest.getMaximumAmount());
        productRepository.save(product);
        return product;
    }

    public List<Transactions> getPendingTransactions(){
        return transactionRepository.findByStatus("PENDING");
    }


    public Shortee saveShortee(MultipartFile file, String fullName) {
        Shortee shortee = new Shortee();
        shortee.setId(null);
        shortee.setFullName(fullName);
        String imageName = StringUtils.cleanPath(file.getOriginalFilename());
        if (imageName.contains("..")) {
            System.out.println("not a a valid file");
        }
        try {
            shortee.setIdCardPhoto(Base64.getEncoder().encodeToString(file.getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        shorteeRepository.save(shortee);

        return shortee;
    }


        public void confirmSave(Long transactionId){
        Transactions transaction = transactionRepository.getById(transactionId);
        Optional<Account> account = accountRepository.findByAccountNumber(transaction.getSenderAccountNumber());
        Long balance = account.get().getAccountBalance() + transaction.getAmount();
        if(account.get().getProduct().getMaximumAmount().compareTo(balance)<0)
            throw new Error("Cannot confirm transaction, account balance cannot exceed "+account.get().getProduct().getMaximumAmount());
        account.get().setAccountBalance(balance);
        transaction.setStatus("CONFIRMED");
        transactionRepository.save(transaction);
        accountRepository.save(account.get());
    }
    public void confirmWithdrawal(Long transactionId){
        Transactions transaction = transactionRepository.getById(transactionId);
        Withdrawal withdrawal = withdrawalRepository.getById(transactionId);
        Optional<Account> account = accountRepository.findByAccountNumber(transaction.getSenderAccountNumber());
        Long balance = (account.get().getAccountBalance() - transaction.getAmount());
        if(balance<0)
            throw new Error("Cannot confirm transaction due to insufficient funds");
        account.get().setAccountBalance(balance);
        transaction.setStatus("CONFIRMED");
        transactionRepository.save(transaction);
        withdrawal.setStatus(transaction.getStatus());
        withdrawalRepository.save(withdrawal);
        accountRepository.save(account.get());
    }

    public String adminDeactivateAccount(String accountNumber){


        Optional<Account> account = accountRepository.findByAccountNumber(accountNumber);
            if(account.get().isSuspended()){
                throw new Error("Account is suspended!!");
            }
            else {
                account.get().setActive(false);
                accountRepository.save(account.get());
                return "Account with account number " + accountNumber + " deactivated successfully";
            }
    }
    public String suspendAccount(String accountNumber){
        Optional<Account> account = accountRepository.findByAccountNumber(accountNumber);
        if(account.get().isActive())
        {
            account.get().setSuspended(true);
            accountRepository.save(account.get());
            return "Account with account number " + accountNumber + " suspended successfully";
        }
        else{
            throw new Error("Account Deactivated!!");
        }
    }
    public ResponseEntity<?> confirmTransaction(Long transactionId) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        Transactions transaction = transactionRepository.getById(transactionId);
        if(transaction.getStatus().equals("CONFIRMED"))
            throw new Error("Transaction already confirmed");
        String transactionType = transaction.getTransactionType();
        boolean save = Objects.equals(transactionType,"SAVING");
        boolean withdraw = Objects.equals(transactionType,"WITHDRAWAL");
        boolean transfer = Objects.equals(transactionType,"TRANSFER");
        System.out.println(save);
        System.out.println(withdraw);
        System.out.println(withdraw);
        if (save) {
            confirmSave(transactionId);
        }
        if (withdraw) {
            confirmWithdrawal(transactionId);
        }
        if(transfer){
            confirmTransfer(transactionId);
        }
        transaction.setLastModifiedOn(LocalDateTime.now());
        transaction.setLastModifiedBy(username);
        transactionRepository.save(transaction);
        return ResponseEntity.ok().build();
    }
    public String restoreAccount(String accountNumber){
        Optional<Account> account = accountRepository.findByAccountNumber(accountNumber);
        account.get().setSuspended(false);
        accountRepository.save(account.get());
        return "Account with account number " + accountNumber +" restored successfully";
    }
    public ResponseEntity<User> createAdmin(MultipartFile file,
                                              MultipartFile file2,
                                             String userName,
                                             String email,
                                          String password,
                                            String phoneNumber,
                                           String shorteeName,
                                           String shorteeName2){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        Shortee shortee = saveShortee(file,shorteeName);
        Shortee shortee1 = saveShortee(file2,shorteeName2);
        List<Shortee> shortees = new ArrayList<>(2);
        shortees.add(shortee);
        shortees.add(shortee1);
        User user = new User();
        user.setUserName(userName);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setPhoneNumber(phoneNumber);
        user.setRole("ROLE_ADMIN");
        user.setAccountList(null);
        user.setShorteeList(shortees);
        user.setCreatedOn(LocalDateTime.now());
        user.setCreatedBy(username);
        user.setLastModifiedBy(username);
        user.setLastModifiedOn(LocalDateTime.now());
         userRepository.save(user);
        return ResponseEntity.ok().build();
    }
    public ResponseEntity<User> createSuperAdmin(
                                            String userName,
                                            String email,
                                            String password, String phoneNumber
                                            )
    {
        User user = new User();
        user.setUserName(userName);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setPhoneNumber(phoneNumber);
        user.setRole("ROLE_SUPER_ADMIN");
        user.setAccountList(null);
        user.setShorteeList(null);
        user.setCreatedOn(LocalDateTime.now());
        user.setLastModifiedOn(LocalDateTime.now());
        userRepository.save(user);
        return ResponseEntity.ok().build();
    }

    public void confirmTransfer(Long transactionId) {
        Transactions transaction = transactionRepository.getById(transactionId);
        Optional<Account> senderAccount = accountRepository.findByAccountNumber(transaction.getSenderAccountNumber());
        Optional<Account> receiverAccount = accountRepository.findByAccountNumber(transaction.getReceiverAccountNumber());
        Long senderBalance = (senderAccount.get().getAccountBalance() - transaction.getAmount());
        Long receiverBalance = (receiverAccount.get().getAccountBalance()+transaction.getAmount());
        if (senderBalance<0)
            throw new Error("Cannot confirm transaction due to insufficient funds");
        if (receiverAccount.get().getProduct().getMaximumAmount().compareTo(receiverBalance)<0)
            throw new Error("Cannot confirm transaction, receiver's account balance cannot exceed " + receiverAccount.get().getProduct().getMaximumAmount());
        senderAccount.get().setAccountBalance(senderBalance);
        receiverAccount.get().setAccountBalance(receiverBalance);
        transaction.setStatus("CONFIRMED");
        transactionRepository.save(transaction);
        Transfer transfer = transferRepository.getById(transactionId);
        transfer.setStatus(transaction.getStatus());
        transferRepository.save(transfer);
        accountRepository.save(senderAccount.get());
        accountRepository.save(receiverAccount.get());
    }
}
