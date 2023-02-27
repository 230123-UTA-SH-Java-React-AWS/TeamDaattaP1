package com.revature.service;

import java.util.List;
import java.util.Map;

import com.revature.model.Account;

public interface AccountServiceInterface {
    public Map<String,Object> loginUser(String jsonLogin);
    public void registerUser(String jsonUser);
    public void changeAccountInfo(String jsonAccount);
    public List<Account> searchUsers(String jsonSearch);
    
}
