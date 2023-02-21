package com.revature.repositories;

import java.sql.SQLException;

public interface RepoGenerics {
    public <T> void registerToDatabase(T object, Class givenClass) throws SQLException;
    public <T> T getAllFromDatabase();
}
