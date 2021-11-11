package com.example.test.app.controller;

import com.example.test.app.models.*;
import com.example.test.app.repository.*;
import com.example.test.app.request.*;
import com.example.test.app.service.AdminService;
import com.example.test.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/")
public class Controller {

    @Autowired
    UserService userService;

    @Autowired
    AdminService adminService;

    @Autowired
    AccountRepository accountRepository;


    @Autowired
    ProductRepository productRepository;


    @PutMapping("/user/deactivate_account")
    public String deactivateAccount(@RequestParam String accountNumber){
      return userService.userDeactivateAccount(accountNumber);
    }

    @GetMapping("/all/signup")
    public String signUp(@ModelAttribute CreateUserRequest createUserRequest) {
        return userService.createUser(createUserRequest);
    }
    @GetMapping("/user/getLastTenTransactions")
    public List<Transactions> getLastTenTransactions(){
        return userService.getLastTenTransactions();
    }

    @PutMapping("/admin/deactivate_account")
    public String adminDeactivateAccount(@RequestParam  String accountNumber){
       return adminService.adminDeactivateAccount(accountNumber);
    }
    @PutMapping("/admin/suspend_account")
    public String suspendAccount(@RequestParam String accountNumber){
        return adminService.suspendAccount(accountNumber);
    }
    @PutMapping("/admin/restore_account")
    public String restoreAccount(@RequestParam String accountNumber){
      return adminService.restoreAccount(accountNumber);
    }

    @PostMapping("/admin/create_product")
    public ResponseEntity<Product> createProduct(@ModelAttribute CreateProductRequest createProductRequest){
        return ResponseEntity.ok(adminService.createProduct(createProductRequest));
    }
    @GetMapping("/admin/getPendingTransactions")
    public List<Transactions> getPendingTransactions(){
        return adminService.getPendingTransactions();
    }
    @GetMapping("/user/getAccountNumber")
    public String getAccountBalance(@RequestParam String accountNumber){
        return userService.getAccountBalance(accountNumber);
    }

    @PostMapping("/user/save")
    public void save(@ModelAttribute  SavingsRequest saveRequest) {
        userService.save(saveRequest);
    }
    @GetMapping("/user/getAllUsers")
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @PostMapping("/user/withdraw")
    public void withdraw(@ModelAttribute  WithdrawalRequest withdrawRequest) {
        userService.withdraw(withdrawRequest);
    }
    @PostMapping("/user/transfer")
    public void transfer(@ModelAttribute  TransferRequest transferRequest) {
     userService.transfer(transferRequest);
    }

    @PutMapping("/user/create_account")
    public ResponseEntity<Account> createAccount(@ModelAttribute CreateAccountRequest createAccountRequest){
        Account account = userService.addAccount(createAccountRequest);
        return ResponseEntity.ok(account);
    }

    @PutMapping("/user/updateUser")
    public String updateUser(@ModelAttribute UpdateUserRequest updateUserRequest){
      User user =  userService.updateUser(updateUserRequest);
        return user.getUserName() + " updated Successfully!";
    }

    @PutMapping("/admin/confirmTransaction/{transactionId}")
    public ResponseEntity<?> confirmTransaction(@PathVariable Long transactionId) {
      return adminService.confirmTransaction(transactionId);
    }

    @GetMapping("/all/show_all_products")
    public List<Product> showProducts(){
        return productRepository.findAll();
    }

    @PostMapping("/super_admin/create")
    public ResponseEntity<User> createAdmin(@RequestParam MultipartFile file,
                                             @RequestParam MultipartFile file2,
                                             @RequestParam String userName,
                                             @RequestParam String email,
                                             @RequestParam String password,
                                             @RequestParam String phoneNumber,
                                             @RequestParam String shorteeName,
                                             @RequestParam String shorteeName2){

        return adminService.createAdmin(file, file2, userName, email, password, phoneNumber,shorteeName, shorteeName2);
    }

    public ResponseEntity<User> createSuperAdmin(@RequestParam String userName,
                                            @RequestParam String email,
                                            @RequestParam String password,
                                            @RequestParam String phoneNumber){

        return adminService.createSuperAdmin(userName,email,password,phoneNumber);
    }

    @Scheduled(fixedRate = 60000)
    public void checkExpiration(){
        List<Account> accounts = accountRepository.findAll();
        if(!accounts.isEmpty()) {
            accounts.stream().forEach(acc -> {
                if ((acc.getExpiration().isBefore(LocalDateTime.now()))&&(acc.isActive())) {
                    adminService.adminDeactivateAccount(acc.getAccountNumber());
                }
            });

        }
        }
    }

