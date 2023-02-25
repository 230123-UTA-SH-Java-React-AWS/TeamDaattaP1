package com.revature.service;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;


import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import com.revature.model.Account;
import com.revature.model.LoginCred;
import com.revature.repositories.AccountsRepo;
import com.revature.repositories.LoginCredsRepo;

public class AccountService implements AccountServiceInterface, ServiceGenerics{
    //perhaps make a repo in a constructor? or make a static repo? check on that later
    //static repos are what makes sense to me -DP
    //I agree on the static repo, just feels better -AB
    private static LoginCredsRepo LCrepo = new LoginCredsRepo();
    private static AccountsRepo Accrepo = new AccountsRepo();

    ///Communicates with the repo to check if inputted credentials are in the database
    //Will almost certainly need a return type later
    @Override
    public Account loginUser(String jsonLogin){ //We can throw an exception to UserController here -TS
    
        System.out.println("We're logging in a user");
        LoginCred newLoginCred = convertToObject(jsonLogin, LoginCred.class);
        System.out.println(newLoginCred.getEmail());

        HashMap<String, LoginCred> AllLoginCreds = LCrepo.getAll();
        System.out.println(AllLoginCreds);
        LoginCred realCreds = null;
        AllLoginCreds.forEach((key, value) -> {
            System.out.println(value);

        });
        if (AllLoginCreds.containsKey(newLoginCred.getEmail()) //login exists
             && AllLoginCreds.get(newLoginCred.getEmail()).getPassword().equals(newLoginCred.getPassword()) //password matches
             ){
                realCreds = AllLoginCreds.get(newLoginCred.getEmail());
                System.out.println("The password matched woohoo");
        } else {
            RuntimeException e = new NoSuchElementException("Login or Password is incorrect");
            throw e;
        }
        Account response = new Account();
        response = Accrepo.getAccount(realCreds.getCredential_id());
        return response;
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
             Accrepo.RegisterAccount(newAccount);
             LCrepo.RegisterLogin(newLogin);
        } else {
            RuntimeException e = new RuntimeException("unable to register account, account with this login already exists");
            throw e;
        }

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
        Account newAccount = convertToObject(jsonAccount, Account.class);
        Accrepo.changeAccountInfo(newAccount);
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