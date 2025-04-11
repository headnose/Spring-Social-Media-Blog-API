package com.example.service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public Account register(Account account) {
        if (account.getUsername() == null || account.getUsername().isBlank()) {
            return null;
        }

        if (account.getPassword() == null || account.getPassword().length() < 4) {
            return null;
        }

        if (accountRepository.findByUsername(account.getUsername()) != null) {
            return null;
        }

        return accountRepository.save(account);
    }

    public Account login(Account account) {
        Account found = accountRepository.findByUsername(account.getUsername());

        if (found != null && found.getPassword().equals(account.getPassword())) {
            return found;
        }

        return null;
    }
}
