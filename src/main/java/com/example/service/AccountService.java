package com.example.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.exception.BadRequestException;
import com.example.exception.ConflictException;
import com.example.exception.UnathorizedException;
import com.example.repository.AccountRepository;

@Service
public class AccountService {
    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    // find account by Id
    public Account findAccountById(int id) {
        Optional<Account> optionalAccount = accountRepository.findById(id);

        if(optionalAccount.isPresent()) {
            return optionalAccount.get();
        }

        return null;
    }

    // find account by username
    public Account findAccountByUsername (String username) {
        Account existingAccount = accountRepository.findByUsername(username);
        return existingAccount;
    } 

    // register account
    public Account registerAccount(Account account) throws BadRequestException, ConflictException {
        System.out.println("Account" + account.toString());
        if (account.getUsername().isBlank() || 
            account.getPassword().isBlank() || 
            account.getPassword().length() < 4) {
                throw new BadRequestException("User name must not be empty. Password must be at least 4 characters long");
        }

        Account existingAccount = findAccountByUsername(account.getUsername());
        if (existingAccount != null) {
            throw new ConflictException("Username already in use");
        }
        
        return accountRepository.save(account);
    }

    // login
    public Account login(Account account) throws UnathorizedException {
        Account existingAccount = findAccountByUsername(account.getUsername());
        
        if (existingAccount == null) {
            throw new UnathorizedException("Invalid user name or password");
        }

        String providedPass = account.getPassword();
        String retrievedPass = existingAccount.getPassword();

        if (!providedPass.equals(retrievedPass)) {
            throw new UnathorizedException("Invalid user name or password");
        }

        return existingAccount;
    }
}
