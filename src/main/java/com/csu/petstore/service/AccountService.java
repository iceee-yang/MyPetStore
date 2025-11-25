package com.csu.petstore.service;

import com.csu.petstore.domain.Account;
import com.csu.petstore.persistence.AccountDao;
import com.csu.petstore.persistence.impl.AccountDaoImpl;

public class AccountService {

    private AccountDao accountDao;

    public AccountService() {
        this.accountDao = new AccountDaoImpl();
    }

    public Account getAccount(String username, String password) {
        Account account = new Account();
        account.setUsername(username);
        account.setPassword(password);
        return this.accountDao.getAccountByUsernameAndPassword(account);
    }

    public void insertAccount(Account account) {
        this.accountDao.insertAccount(account);
        this.accountDao.insertProfile(account);
        this.accountDao.insertSignon(account);
    }

    public void updateAccount(Account account) {
        this.accountDao.updateAccount(account);
        this.accountDao.updateProfile(account);
        if (account.getPassword() != null && account.getPassword().length() > 0) {
            this.accountDao.updateSignon(account);
        }

    }
}
