package com.revature.repositories;

import java.util.List;

import com.revature.model.Account;
import com.revature.model.LoginCred;

public interface AccountsInterface {
    public Account getAccount(int id);
    public void changeAccountInfo(Account newInfo);
    //public List<Account> getFriends(Account user);
}
