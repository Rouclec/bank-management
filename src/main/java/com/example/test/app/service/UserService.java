package com.example.test.app.service;

import com.example.test.app.models.*;
import com.example.test.app.repository.*;
import com.example.test.app.request.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;


    @Autowired
    AccountRepository accountRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    SavingsRepository savingsRepository;

    @Autowired
    TransferRepository transferRepository;

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    WithdrawalRepository withdrawalRepository;

    @Autowired
    private  AdminService adminService;

    @Autowired
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public User createUser(CreateUserRequest createUserRequest){
        User user = new User();

        user.setUserName(createUserRequest.getUserName());
        user.setEmail(createUserRequest.getEmail());
        user.setPhoneNumber(createUserRequest.getPhoneNumber());
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encryptedPassword = passwordEncoder.encode(createUserRequest.getPassword());
        user.setPassword(encryptedPassword);
        Account account = new Account();
        Product product = productRepository.findByProductName(createUserRequest.getProductName());
        account.setAccountNumber(createUserRequest.getAccountNumber());
        account.setAccountBalance((long) 0.00);
        account.setProduct(product);
        account.setExpiration(LocalDateTime.now().plusMinutes(createUserRequest.getAccountDurationInMonths()));
        account.setUserName(createUserRequest.getUserName());
        account.setActive(true);
        account.setSuspended(false);
        accountRepository.save(account);
        List<Account> accounts = new ArrayList<Account>();
        accounts.add(account);
        user.setAccountList(accounts);
        user.setDateCreated(LocalDateTime.now());
        userRepository.save(user);
        return user;
    }
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }
    public Account addAccount(CreateAccountRequest createAccountRequest) throws IllegalStateException {
        Account account = new Account();
        account.setAccountNumber(createAccountRequest.getAccountNumber());
        Optional<User> user = userRepository.findByUserName(createAccountRequest.getUserName());
        Product product = productRepository.findByProductName(createAccountRequest.getProductName().toUpperCase());
        account.setProduct(product);
        account.setAccountBalance((long) 0.00);
        account.setUserName(user.get().getUserName());
        account.setExpiration(LocalDateTime.now().plusMinutes(createAccountRequest.getDurationInMonths()));
        account.setActive(true);
        account.setSuspended(false);
        user.get().getAccountList().add(account);
        accountRepository.save(account);
        userRepository.save(user.get());

        return account;
    }

    public User updateUser(UpdateUserRequest updateUserRequest) {
        Optional<User> user = userRepository.findByUserName(updateUserRequest.getUserName());
        if (user != null) {
            if (updateUserRequest.getUserName() != null) {
                user.get().setUserName(updateUserRequest.getUserName());
            }
            if (updateUserRequest.getEmail() != null) {
                user.get().setEmail(updateUserRequest.getEmail());
            }
            if (updateUserRequest.getPhoneNumber() != null) {
                user.get().setPhoneNumber(updateUserRequest.getPhoneNumber());
            }
            if (updateUserRequest.getPassword() != null) {
                user.get().setPassword(updateUserRequest.getPassword());
            }
            userRepository.save(user.get());
        } else {
            throw new UsernameNotFoundException("invalid Username");
        }
        return user.get();
    }
    public ResponseEntity<User> forgotPassword(String userName, String newPassword){
        Optional<User> user = userRepository.findByUserName(userName);
        user.get().setPassword(passwordEncoder.encode(newPassword));
        return ResponseEntity.ok(user.get());
    }
    public String deactivateAccount(String accountNumber){
        Optional<Account> account = accountRepository.findByAccountNumber(accountNumber);
        account.get().setActive(false);
        accountRepository.save(account.get());
        return "Account with account number " + accountNumber +" deactivated successfully";
    }
    public void save(SavingsRequest saveRequest) {
        Optional<Account> account = accountRepository.findByAccountNumber(saveRequest.getAccountNumber());
        if(account.get().isActive()){
            if(!account.get().isSuspended()){
                Long balance = (account.get().getAccountBalance()+saveRequest.getAmount());
                if (account.get().getProduct().getMaximumAmount().compareTo(balance)<0)
                    throw new Error("Account balance cannot exceed " + account.get().getProduct().getMaximumAmount());

                Transactions transaction = new Transactions(null, "SAVING", account.get().getAccountNumber(), null, saveRequest.getAmount(), LocalDateTime.now(),"PENDING");
                transactionRepository.save(transaction);
                adminService.confirmTransaction(transaction.getTransactionId());
                Savings saving = new Savings(transaction.getTransactionId(),saveRequest.getAccountNumber(), saveRequest.getAmount(), LocalDateTime.now(), transaction.getStatus());
                savingsRepository.save(saving);
            }
            else {
                throw new Error("Account is suspended!!");
            }
        }
        else{
            throw new Error("Account deactivated!!");
        }

    }
    public void withdraw(WithdrawalRequest withdrawRequest) {
        Optional<Account> account = accountRepository.findByAccountNumber(withdrawRequest.getAccountNumber());
        if(account.get().isActive()){
            if(!account.get().isSuspended()){
                if (withdrawRequest.getAmount().compareTo(account.get().getAccountBalance()) > 0)
                    throw new Error("Insufficient funds");
                Transactions transaction = new Transactions(null, "WITHDRAWAL", account.get().getAccountNumber(), null, withdrawRequest.getAmount(), LocalDateTime.now(),"PENDING");
                transactionRepository.save(transaction);
                Withdrawal withdrawal= new Withdrawal(transaction.getTransactionId(),account.get().getAccountNumber(), withdrawRequest.getAmount(), LocalDateTime.now(), transaction.getStatus());
                withdrawalRepository.save(withdrawal);
            }
            else {
                throw new Error("Account is suspended!!");
            }
        }
        else{
            throw new Error("Account deactivated!!");
        }
    }
    public void transfer(TransferRequest transferRequest) {
        Optional<Account> senderAccount = accountRepository.findByAccountNumber(transferRequest.getSenderAccountNumber());
        Optional<Account> receiverAccount = accountRepository.findByAccountNumber(transferRequest.getReceiverAccountNumber());
        if(senderAccount.get().isActive()){
            if(!senderAccount.get().isSuspended()){
                if(receiverAccount.get().isActive())
                {
                    if(!receiverAccount.get().isSuspended()){
                        if (transferRequest.getAmount().compareTo(senderAccount.get().getAccountBalance()) > 0)
                            throw new Error("Insufficient funds");
                        Long receiverBalance = (receiverAccount.get().getAccountBalance()+transferRequest.getAmount());
                        if (receiverAccount.get().getProduct().getMaximumAmount().compareTo(receiverBalance)<0)
                            throw new Error("Account balance cannot exceed " + receiverAccount.get().getProduct().getMaximumAmount());
                        Transactions transaction = new Transactions(null, "TRANSFER", senderAccount.get().getAccountNumber(), receiverAccount.get().getAccountNumber(), transferRequest.getAmount(), LocalDateTime.now(),"PENDING");
                        transactionRepository.save(transaction);
                        Transfer transfer = new Transfer(transaction.getTransactionId(), senderAccount.get().getAccountNumber(), receiverAccount.get().getAccountNumber(), transferRequest.getAmount(), LocalDateTime.now(),transaction.getStatus());
                        transferRepository.save(transfer);
                    }
                    else{
                        throw new Error("Receiver account suspended!");
                    }
                }
                else
                {
                    throw new Error("Receiver account deactivated!");
                }
            }
            else{
                throw new Error("Sender account suspended!");
            }
        }
        else{
            throw new Error("Sender account deactivated!");
        }
    }
}
