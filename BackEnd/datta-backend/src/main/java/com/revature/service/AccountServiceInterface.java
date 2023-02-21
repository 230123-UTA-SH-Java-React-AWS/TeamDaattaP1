package com.revature.service;

import com.revature.model.Account;

public interface AccountServiceInterface {
    public Account loginUser(String jsonLogin);
    public void registerUser(String jsonUser);
    public void changeAccountInfo(String jsonAccount);
    
}
