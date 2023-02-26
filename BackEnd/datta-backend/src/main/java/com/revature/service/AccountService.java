package com.revature.service;

import java.lang.reflect.Field;
import java.util.*;


import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import com.revature.model.Account;
import com.revature.model.LoginCred;
import com.revature.repositories.AccountsRepo;
import com.revature.repositories.LoginCredsRepo;

public class AccountService implements AccountServiceInterface, ServiceGenerics{

    private LoginCredsRepo LCrepo;
    private AccountsRepo Accrepo;

    public AccountService(LoginCredsRepo LCrepo, AccountsRepo Accrepo){
        this.LCrepo = LCrepo;
        this.Accrepo = Accrepo;
    }

    ///Communicates with the repo to check if inputted credentials are in the database
    @Override
    public Account loginUser(String jsonLogin){
    
        System.out.println("We're logging in a user");
        System.out.println(jsonLogin);

        LoginCred newLogin = convertToObject(jsonLogin, LoginCred.class);
        String email  = newLogin.getEmail();
        System.out.println(email);
        String password = newLogin.getPassword();


        if (LCrepo.checkLogin(email)){
                System.out.println("Account exists woohoo"); // nice
            return LCrepo.hashLogin(email, password);
        } else {
            throw new NoSuchElementException("Login or Password is incorrect");
        }

    }

    @Override
    public void registerUser(String jsonUser) {
        /* TODO: Register User: -TS
         * 
         * 1. Check to make sure the email is not already registered
         * 
         * 2. Create an Account from the jsonUser
         * 
         * 3. Send the Account to the AccountRepo to be stored in the accounts table
         * 
         * 4. Create a LoginCred fromt he username and password
         * 
         * 5. Send the LoginCred to the LoginCredsRepo to be stored in the logincredentials table
         */

         //needs testing, assumes that jsonUser has both account info and login cred info
         //not sure if convertToObject will work like this. let me know if it doesn't -ab
        Account newAccount = convertToObject(jsonUser, Account.class);
        LoginCred newLogin = convertToObject(jsonUser, LoginCred.class);

        if(!LCrepo.getAll().containsKey(newLogin.getEmail())){
//             Accrepo.RegisterAccount(newAccount);
//             LCrepo.RegisterLogin(newLogin);
            String email  = newLogin.getEmail();
            String password = newLogin.getPassword();
            LCrepo.hashRegister(email, password);
        } //else ??? brain melting ngl will come back to this tomorrow - ab

    }

    @Override
    public void changeAccountInfo(String jsonAccount){
        /* TODO: Change Account Info: -TS
         * 
         * 1. Check to make sure the user is logged in
         * 
         * 2. Create an Account from the jsonAccount
         * 
         * 3. Send the Account to the AccountRepo to update accounts
         * 
         * 4. If the Password is being changed, instead of Account use LoginCred
         */
    }
    ///Maybe a convert from string method here, or several as needed
    
    //TODO: Return List of Users from UserService.searchUsers(searchJson) -TS
    @Override
    public List<Account> searchUsers(String jsonSearch) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'searchUsers'");
    }

    //Not sure on the syntax here (Looks like it works -TS)
    //need a better way to do this, rather than brute forcing
    //wish object mapper would just ignore irrelevant fields while making an object- ab
    @Override
    public <T> T convertToObject(String json, Class <T> returnType) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> map = new HashMap<String, Object>();
            Field[] fields = returnType.getDeclaredFields();
            T newObject = returnType.newInstance();

            map = mapper.readValue(json, new TypeReference<Map<String, Object>>(){});

            for (Map.Entry<String, Object> me : map.entrySet()){
                for(Field f : fields){
                    if(f.getName().equals(me.getKey())){
                        f.setAccessible(true);
                        f.set(newObject, me.getValue());
                    }
                }
            }
            return newObject;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}