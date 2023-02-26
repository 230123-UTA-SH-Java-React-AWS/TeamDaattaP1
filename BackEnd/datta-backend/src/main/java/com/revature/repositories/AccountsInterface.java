package com.revature.repositories;

import com.revature.model.Account;

public interface AccountsInterface {
    public Account getAccount(int id);
    public void changeAccountInfo(Account newInfo);
    //public List<Account> getFriends(Account user);
}
